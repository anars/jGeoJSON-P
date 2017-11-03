package com.anars.geojson;

import java.io.InputStream;
import java.io.Reader;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonReader;

public class GeoJSONReader {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private JsonReader _jsonReader;

    /**
     * @param inputStream
     */
    public GeoJSONReader(InputStream inputStream) {
        super();
        _jsonReader = Json.createReader(inputStream);
    }

    /**
     * @param writer
     */
    public GeoJSONReader(Reader writer) {
        super();
        _jsonReader = Json.createReader(writer);
    }
}
