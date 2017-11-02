package com.anars.geojson;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 */
public class Feature
    implements GeoJSONType {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private Geometry _geometry = null;

    /**
     */
    private JsonObject _properties = null;

    /**
     */
    private Point _bboxNE = null;

    /**
     */
    private Point _bboxSW = null;

    /**
     */
    public Feature() {
        super();
    }

    /**
     * @param jsonObject
     */
    public Feature(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        JsonArray jsonArrayBBox = jsonObject.getJsonArray("bbox");
        if(jsonArrayBBox != null) {
            if(jsonArrayBBox.size() == 4)
                setBBox(new Point(jsonArrayBBox.getInt(0), jsonArrayBBox.getInt(1)), new Point(jsonArrayBBox.getInt(2), jsonArrayBBox.getInt(3)));
            else if(jsonArrayBBox.size() == 6)
                setBBox(new Point(jsonArrayBBox.getInt(0), jsonArrayBBox.getInt(1), jsonArrayBBox.getInt(2)), new Point(jsonArrayBBox.getInt(3), jsonArrayBBox.getInt(4), jsonArrayBBox.getInt(5)));
            else
                throw new InvalidGeoJSONTypeException("Invalid \"bbox\" size");
        }
        JsonObject jsonObjectGeometry = jsonObject.getJsonObject("geometry");
        switch(jsonObjectGeometry.getString("type").toLowerCase()) {
            case "point":
                _geometry = new Point(jsonObjectGeometry);
                break;
            case "multipoint":
                _geometry = new MultiPoint(jsonObjectGeometry);
                break;
            case "linestring":
                _geometry = new LineString(jsonObjectGeometry);
                break;
            case "multilinestring":
                _geometry = new MultiLineString(jsonObjectGeometry);
                break;
            case "polygon":
                _geometry = new Polygon(jsonObjectGeometry);
                break;
            case "multipolygon":
                _geometry = new MultiPolygon(jsonObjectGeometry);
                break;
        }
        _properties = jsonObject.getJsonObject("properties");
    }

    /**
     * @param geometry
     */
    public Feature(Geometry geometry) {
        _geometry = geometry;
    }

    /**
     * @param geometry
     * @param bboxNE
     * @param bboxSW
     */
    public Feature(Geometry geometry, Point bboxNE, Point bboxSW) {
        _geometry = geometry;
        setBBox(bboxNE, bboxSW);
    }

    /**
     * @param geometry
     */
    public void setGeometry(Geometry geometry) {
        _geometry = geometry;
    }

    /**
     * @return
     */
    public Geometry getGeometry() {
        return (_geometry);
    }

    /**
     * @param properties
     */
    public void setProperties(JsonObject properties) {
        _properties = properties;
    }

    /**
     * @return
     */
    public JsonObject getProperties() {
        return (_properties);
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
        if(_geometry != null)
            jsonObjectBuilder.add("geometry", _geometry.toJSONObject());
        if(_properties != null)
            jsonObjectBuilder.add("properties", _properties);
        return (jsonObjectBuilder.build());
    }
}
