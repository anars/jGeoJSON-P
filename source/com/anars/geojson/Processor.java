package com.anars.geojson;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Processor {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private static final Processor INSTANCE = new Processor();

    /**
     */
    private Processor() {
        super();
    }

    /**
     * @return
     */
    public static Processor getInstance() {
        return INSTANCE;
    }

    /**
     * @param jsonReader
     * @return
     */
    public GeoJSONType read(JsonReader jsonReader)
        throws InvalidGeoJSONTypeException {
        JsonObject jsonObject = jsonReader.readObject();
        switch(jsonObject.getString("type").toLowerCase()) {
            case "point":
                return (new Point(jsonObject));
            case "multipoint":
                return (new MultiPoint(jsonObject));
            case "linestring":
                return (new LineString(jsonObject));
            case "multilinestring":
                return (new MultiLineString(jsonObject));
            case "polygon":
                return (new Polygon(jsonObject));
            case "multipolygon":
                return (new MultiPolygon(jsonObject));
            case "feature":
                return (new Feature(jsonObject));
            case "featurecollection":
                return (new FeatureCollection(jsonObject));
            case "geometrycollection":
                return (new GeometryCollection(jsonObject));
            default:
                throw new InvalidGeoJSONTypeException("Unknown GeoJSON type");
        }
    }

    /**
     * @param inputStream
     * @return
     */
    public GeoJSONType read(InputStream inputStream)
        throws InvalidGeoJSONTypeException {
        return (read(Json.createReader(inputStream)));
    }

    /**
     * @param reader
     * @return
     */
    public GeoJSONType read(Reader reader)
        throws InvalidGeoJSONTypeException {
        return (read(Json.createReader(reader)));
    }

    /**
     * @param outputStream
     */
    public void write(OutputStream outputStream, GeoJSONType geoJSONType) {}

    /**
     * @param writer
     */
    public void write(Writer writer, GeoJSONType geoJSONType) {}
}
