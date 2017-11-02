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
public class Polygon
    extends ArrayList<MultiPoint>
    implements GeoJSONType, Geometry {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     * @param collection
     */
    public Polygon(Collection<? extends MultiPoint> collection) {
        super(collection);
    }

    /**
     */
    public Polygon() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public Polygon(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public Polygon(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for Polygon geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("coordinates");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("Polygon object has no member with the name \"coordinates\"");
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public Polygon(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     * @throws InvalidGeoJSONTypeException
     */
    public void setCoordinates(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        for(int index = 0; index < jsonArray.size(); index++) {
            MultiPoint multiPoint = new MultiPoint();
            multiPoint.setCoordinates(jsonArray.getJsonArray(index));
            add(multiPoint);
        }
    }

    /**
     * @return
     */
    public JsonObject toJSONObject() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("type", getClass().getSimpleName());
        jsonObjectBuilder.add("coordinates", getCoordinatesJSONArray());
        return (jsonObjectBuilder.build());
    }

    /**
     * @return
     */
    public JsonArray getCoordinatesJSONArray() {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(MultiPoint multiPoint : this)
            jsonArrayBuilder.add(multiPoint.getCoordinatesJSONArray());
        return (jsonArrayBuilder.build());
    }
}
