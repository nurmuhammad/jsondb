package com.rvc.jsondb;

/**
 * @author Nurmuhammad
 */
public class JsonDBException extends RuntimeException {
    public JsonDBException() {
    }

    public JsonDBException(String message) {
        super(message);
    }

    public JsonDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonDBException(Throwable cause) {
        super(cause);
    }

    public JsonDBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
