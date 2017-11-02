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
public class MultiPoint
    extends ArrayList<Point>
    implements GeoJSONType, Geometry {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     * @param collection
     */
    public MultiPoint(Collection<? extends Point> collection) {
        super(collection);
    }

    /**
     */
    public MultiPoint() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public MultiPoint(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public MultiPoint(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for MultiPoint geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("coordinates");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("MultiPoint object has no member with the name \"coordinates\"");
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public MultiPoint(JsonArray jsonArray)
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
            Point point = new Point();
            point.setCoordinates(jsonArray.getJsonArray(index));
            add(point);
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
        for(Point point : this)
            jsonArrayBuilder.add(point.getCoordinatesJSONArray());
        return (jsonArrayBuilder.build());
    }
}
