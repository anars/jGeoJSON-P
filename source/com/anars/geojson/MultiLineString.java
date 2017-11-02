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
public class MultiLineString
    extends ArrayList<LineString>
    implements GeoJSONType, Geometry {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     * @param collection
     */
    public MultiLineString(Collection<? extends LineString> collection) {
        super(collection);
    }

    /**
     */
    public MultiLineString() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public MultiLineString(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public MultiLineString(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for MultiLineString geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("coordinates");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("MultiLineString object has no member with the name \"coordinates\"");
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public MultiLineString(JsonArray jsonArray)
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
            LineString lineString = new LineString();
            lineString.setCoordinates(jsonArray.getJsonArray(index));
            add(lineString);
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
        for(LineString lineString : this)
            jsonArrayBuilder.add(lineString.getCoordinatesJSONArray());
        return (jsonArrayBuilder.build());
    }
}
