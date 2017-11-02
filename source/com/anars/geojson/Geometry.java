package com.anars.geojson;

import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 */
public interface Geometry {

    /**
     * @return
     */
    public JsonObject toJSONObject();

    /**
     * @return
     */
    public JsonArray getCoordinatesJSONArray();
}
