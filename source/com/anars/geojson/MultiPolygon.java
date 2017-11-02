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
public class MultiPolygon
    extends ArrayList<Polygon>
    implements GeoJSONType, Geometry {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     * @param collection
     */
    public MultiPolygon(Collection<? extends Polygon> collection) {
        super(collection);
    }

    /**
     */
    public MultiPolygon() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public MultiPolygon(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public MultiPolygon(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for MultiPolygon geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("coordinates");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("MultiPolygon object has no member with the name \"coordinates\"");
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public MultiPolygon(JsonArray jsonArray)
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
            Polygon polygon = new Polygon();
            polygon.setCoordinates(jsonArray.getJsonArray(index));
            add(polygon);
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
        for(Polygon polygon : this)
            jsonArrayBuilder.add(polygon.getCoordinatesJSONArray());
        return (jsonArrayBuilder.build());
    }
}
