package com.anars.geojson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

public class GeoJSON {

    /**
     */
    private final transient Logger _logger = Logger.getLogger(getClass().getCanonicalName());

    /**
     */
    private static final GeoJSON INSTANCE = new GeoJSON();

    /**
     */
    private GeoJSON() {
        super();
    }

    /**
     * @return
     */
    public static GeoJSON getInstance() {
        return INSTANCE;
    }

    /**
     * @param string
     * @return
     * @throws InvalidGeoJSONTypeException
     */
    public GeoJSONType parse(String string)
        throws InvalidGeoJSONTypeException {
        return (read(Json.createReader(new StringReader(string))));
    }

    /**
     * @param jsonReader
     * @return
     * @throws InvalidGeoJSONTypeException
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
    public void write(OutputStream outputStream, GeoJSONType geoJSONType) {
        JsonWriter jsonWriter = Json.createWriter(outputStream);
        jsonWriter.writeObject(geoJSONType.toJSONObject());
        jsonWriter.close();
    }

    /**
     * @param writer
     */
    public void write(Writer writer, GeoJSONType geoJSONType) {
        JsonWriter jsonWriter = Json.createWriter(writer);
        jsonWriter.writeObject(geoJSONType.toJSONObject());
        jsonWriter.close();
    }

    /**
     * @param inputStream
     * @return
     */
    public GeoJSONReader getReader(InputStream inputStream)
        throws IOException {
        return (new GeoJSONReader(inputStream));
    }

    /**
     * @param reader
     * @return
     */
    public GeoJSONReader getReader(Reader reader)
        throws IOException {
        return (new GeoJSONReader(reader));
    }

    /**
     * @param outputStream
     * @return
     */
    public GeoJSONWriter getWriter(OutputStream outputStream) {
        return (new GeoJSONWriter(outputStream));
    }

    /**
     * @param writer
     * @return
     */
    public GeoJSONWriter getWriter(Writer writer) {
        return (new GeoJSONWriter(writer));
    }
}
