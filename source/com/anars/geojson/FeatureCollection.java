package com.anars.geojson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 */
public class FeatureCollection
    extends ArrayList<Feature>
    implements GeoJSONType {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private Point _bboxNE = null;

    /**
     */
    private Point _bboxSW = null;

    /**
     * @param collection
     */
    public FeatureCollection(Collection<? extends Feature> collection) {
        super(collection);
    }

    /**
     */
    public FeatureCollection() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public FeatureCollection(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public FeatureCollection(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for FeatureCollection geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("features");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("FeatureCollection object has no member with the name \"features\"");
        setFeatures(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public FeatureCollection(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        setFeatures(jsonArray);
    }

    /**
     * @param jsonArray
     * @throws InvalidGeoJSONTypeException
     */
    public void setFeatures(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        for(int index = 0; index < jsonArray.size(); index++) {
            add(new Feature(jsonArray.getJsonObject(index)));
        }
    }

    /**
     * @param bboxNE
     * @param bboxSW
     */
    public FeatureCollection(Point bboxNE, Point bboxSW) {
        super();
        setBBox(bboxNE, bboxSW);
    }

    /**
     * @param bboxNE
     */
    public void setBBox(Point bboxNE, Point bboxSW) {
        _bboxNE = bboxNE != null ? bboxNE : new Point();
        _bboxSW = bboxSW != null ? bboxSW : new Point();
        if(_bboxNE.hasAltitude() && !_bboxSW.hasAltitude())
            _bboxSW.setAltitude(_bboxNE.getAltitude());
        else if(_bboxSW.hasAltitude() && !_bboxNE.hasAltitude())
            _bboxNE.setAltitude(_bboxSW.getAltitude());
    }

    /**
     * @return
     */
    public Point getBBoxNE() {
        return (_bboxNE);
    }

    /**
     * @return
     */
    public Point getBBoxSW() {
        return (_bboxSW);
    }

    /**
     * @return
     */
    public JsonObject toJSONObject() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("type", getClass().getSimpleName());
        if(_bboxNE != null && _bboxSW != null) {
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            jsonArrayBuilder.add(_bboxNE.getLongitude());
            jsonArrayBuilder.add(_bboxNE.getLatitude());
            if(_bboxNE.hasAltitude())
                jsonArrayBuilder.add(_bboxNE.getAltitude());
            jsonArrayBuilder.add(_bboxSW.getLongitude());
            jsonArrayBuilder.add(_bboxSW.getLatitude());
            if(_bboxSW.hasAltitude())
                jsonArrayBuilder.add(_bboxSW.getAltitude());
            jsonObjectBuilder.add("bbox", jsonArrayBuilder.build());
        }
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Feature feature : this)
            jsonArrayBuilder.add(feature.toJSONObject());
        jsonObjectBuilder.add("features", jsonArrayBuilder.build());
        return (jsonObjectBuilder.build());
    }
}
