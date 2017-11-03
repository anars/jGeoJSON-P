package com.anars.geojson;

import java.io.OutputStream;
import java.io.Writer;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonWriter;

public class GeoJSONWriter {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private JsonWriter _jsonWriter;

    /**
     * @param outputStream
     */
    public GeoJSONWriter(OutputStream outputStream) {
        super();
        _jsonWriter = Json.createWriter(outputStream);
    }

    /**
     * @param writer
     */
    public GeoJSONWriter(Writer writer) {
        super();
        _jsonWriter = Json.createWriter(writer);
    }
}
