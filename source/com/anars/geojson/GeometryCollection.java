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
public class GeometryCollection
    extends ArrayList<Geometry>
    implements GeoJSONType {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     * @param collection
     */
    public GeometryCollection(Collection<? extends Geometry> collection) {
        super(collection);
    }

    /**
     */
    public GeometryCollection() {
        super();
    }

    /**
     * @param initialCapacity
     */
    public GeometryCollection(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * @param jsonObject
     */
    public GeometryCollection(JsonObject jsonObject)
        throws InvalidGeoJSONTypeException {
        String type = jsonObject.getString("type");
        if(type == null || !type.equalsIgnoreCase(getClass().getSimpleName()))
            throw new InvalidGeoJSONTypeException("Invalid \"type\" value for GeometryCollection geometry object");
        JsonArray jsonArray = jsonObject.getJsonArray("geometries");
        if(jsonArray == null)
            throw new InvalidGeoJSONTypeException("GeometryCollection object has no member with the name \"geometries\"");
        setGeometries(jsonArray);
    }

    /**
     * @param jsonArray
     */
    public GeometryCollection(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        setGeometries(jsonArray);
    }

    /**
     * @param jsonArray
     * @throws InvalidGeoJSONTypeException
     */
    public void setGeometries(JsonArray jsonArray)
        throws InvalidGeoJSONTypeException {
        for(int index = 0; index < jsonArray.size(); index++) {
            JsonObject jsonObject = jsonArray.getJsonObject(index);
            switch(jsonObject.getString("type").toLowerCase()) {
                case "point":
                    add(new Point(jsonObject));
                    break;
                case "multipoint":
                    add(new MultiPoint(jsonObject));
                    break;
                case "linestring":
                    add(new LineString(jsonObject));
                    break;
                case "multilinestring":
                    add(new MultiLineString(jsonObject));
                    break;
                case "polygon":
                    add(new Polygon(jsonObject));
                    break;
                case "multipolygon":
                    add(new MultiPolygon(jsonObject));
                    break;
                default:
                    throw new InvalidGeoJSONTypeException("Unknown GeoJSON geometry type");
            }
        }
    }

    /**
     * @return
     */
    public JsonObject toJSONObject() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("type", getClass().getSimpleName());
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for(Geometry geometry : this)
            jsonArrayBuilder.add(geometry.toJSONObject());
        jsonObjectBuilder.add("geometries", jsonArrayBuilder.build());
        return (jsonObjectBuilder.build());
    }
}
