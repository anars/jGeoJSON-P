package com.anars.geojson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.nio.charset.StandardCharsets;

import java.util.logging.Logger;

public class GeoJSONReader {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private Reader _reader;

    /**
     * @param inputStream
     */
    public GeoJSONReader(InputStream inputStream)
        throws IOException {
        this(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    /**
     * @param writer
     */
    public GeoJSONReader(Reader reader)
        throws IOException {
        super();
        _reader = reader;
        while(_reader.ready() && (char)_reader.read() != '{')
            ;
    }

    /**
     */
    public GeoJSONType next()
        throws IOException, InvalidGeoJSONTypeException {
        while(_reader.ready() && (char)_reader.read() != '{')
            ;
        StringBuilder stringBuilder = new StringBuilder("{");
        int braces = 1;
        while(_reader.ready() && braces > 0) {
            char character = (char)_reader.read();
            stringBuilder.append(character);
            if(character == '{')
                braces++;
            else if(character == '}')
                braces--;
        }
        GeoJSONType geoJSONType;
        try {
            geoJSONType = GeoJSON.getInstance().parse(stringBuilder.toString());
        }
        catch(javax.json.stream.JsonParsingException jsonParsingException) {
            geoJSONType = null;
        }
        return (geoJSONType);
    }

    /**
     */
    public void close()
        throws IOException {
        _reader.close();
    }
}
