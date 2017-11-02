package com.anars.geojson;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 */
public class Point
    implements GeoJSONType, Geometry {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private double _longitude;

    /**
     */
    private double _latitude;

    /**
     */
    private double _altitude = Double.NaN;

    /**
     */
    public Point() {
        super();
    }

    /**
     * @param jsonObject
     */
    public Point(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for Point geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("coordinates");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("Point object has no member with the name \"coordinates\"");
        setCoordinates(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public Point(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        setCoordinates(jsonArray);
    }

    /**
     * @param longitude
     * @param latitude
     */
    public Point(double longitude, double latitude) {
        _longitude = longitude;
        _latitude = latitude;
    }

    /**
     * @param longitude
     * @param latitude
     * @param altitude
     */
    public Point(double longitude, double latitude, double altitude) {
        _longitude = longitude;
        _latitude = latitude;
        _altitude = altitude;
    }

    /**
     * @param jsonArray
     * @throws InvalidGeoJSONTypeException
     */
    public void setCoordinates(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        if(jsonArray.size() < 2)
            throw new InvalidGeoJSONTypeException("Missing position information");
        if(jsonArray.size() > 3)
            _logger.warning("Point position array has more than three elements. They are ignored.");
        _longitude = jsonArray.getJsonNumber(0).doubleValue();
        _latitude = jsonArray.getJsonNumber(1).doubleValue();
        if(jsonArray.size() > 2)
            _altitude = jsonArray.getJsonNumber(2).doubleValue();
    }

    /**
     * @param longitude
     */
    public void setLongitude(double longitude) {
        _longitude = longitude;
    }

    /**
     * @return
     */
    public double getLongitude() {
        return (_longitude);
    }

    /**
     * @param latitude
     */
    public void setLatitude(double latitude) {
        _latitude = latitude;
    }

    /**
     * @return
     */
    public double getLatitude() {
        return (_latitude);
    }

    /**
     * @param altitude
     */
    public void setAltitude(double altitude) {
        _altitude = altitude;
    }

    /**
     * @return
     */
    public double getAltitude() {
        return (_altitude);
    }

    /**
     * @return
     */
    public boolean hasAltitude() {
        return (!Double.isNaN(_altitude));
    }

    /**
     * @param object
     * @return
     */
    @Override public boolean equals(Object object) {
        if(this == object)
            return (true);
        if(!(object instanceof Point))
            return (false);
        final Point other = (Point)object;
        if(Double.compare(_longitude, other._longitude) != 0)
            return (false);
        if(Double.compare(_latitude, other._latitude) != 0)
            return (false);
        if(Double.compare(_altitude, other._altitude) != 0)
            return (false);
        return (true);
    }

    /**
     * @return
     */
    @Override public int hashCode() {
        int result = 1;
        long temp = Double.doubleToLongBits(_longitude);
        result += (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(_latitude);
        result += (int)(temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(_altitude);
        result += (int)(temp ^ (temp >>> 32));
        return (result);
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
        jsonArrayBuilder.add(getLongitude());
        jsonArrayBuilder.add(getLatitude());
        if(hasAltitude())
            jsonArrayBuilder.add(getAltitude());
        return (jsonArrayBuilder.build());
    }

    /**
     * @return
     */
    @Override public String toString() {
        return ("[" + _longitude + ", " + _latitude + (hasAltitude() ? ", " + _altitude : "") + "]");
    }
}
