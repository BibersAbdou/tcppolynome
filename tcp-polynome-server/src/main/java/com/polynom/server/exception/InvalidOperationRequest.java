package com.polynom.server.exception;

/**
 *
 **/

public class InvalidOperationRequest extends Exception {
    public InvalidOperationRequest() {
    }

    public InvalidOperationRequest(String message) {
        super(message);
    }

    public InvalidOperationRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationRequest(Throwable cause) {
        super(cause);
    }

    public InvalidOperationRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
