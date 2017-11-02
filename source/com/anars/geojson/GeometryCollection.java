package com.anars.geojson;

import java.util.ArrayList;
import java.util.Collection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 */
public class GeometryCollection
    extends ArrayList<Geometry>
    implements GeoJSONType {

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
