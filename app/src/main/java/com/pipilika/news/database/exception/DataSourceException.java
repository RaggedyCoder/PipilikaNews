package com.pipilika.news.database.exception;

/**
 * Created by tuman on 4/5/2015.
 */
public class DataSourceException extends Exception {
    public static final String DATA_SOURCE_EXCEPTION_TAG = "DataSourceException";
    public String message = "Invalid TAG";

    @Override
    public String getMessage() {
        return DATA_SOURCE_EXCEPTION_TAG + " " + message;
    }
}
