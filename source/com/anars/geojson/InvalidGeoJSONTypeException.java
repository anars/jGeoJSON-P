package com.anars.geojson;

/**
 */
public class InvalidGeoJSONTypeException
    extends Exception {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InvalidGeoJSONTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param cause
     */
    public InvalidGeoJSONTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidGeoJSONTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public InvalidGeoJSONTypeException(String message) {
        super(message);
    }

    /**
     */
    public InvalidGeoJSONTypeException() {
        super();
    }
}
