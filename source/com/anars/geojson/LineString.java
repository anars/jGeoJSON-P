package com.anars.geojson;

import java.util.Collection;
import java.util.logging.Logger;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 */
public class LineString
    extends MultiPoint
    implements GeoJSONType, Geometry {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     * @param collection
     */
    public LineString(Collection<? extends Point> collection) {
        super(collection);
    }

    /**
     */
    public LineString() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public LineString(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public LineString(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for LineString geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("coordinates");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("LineString object has no member with the name \"coordinates\"");
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public LineString(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     * @throws InvalidGeoJSONTypeException
     */
    public void setCoordinates(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        super.setCoordinates(jsonArray);
    }
}
