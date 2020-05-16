package com.polynom.server.exception;

/**
 *
 **/

public class TCPStartException extends TCPException {

    public TCPStartException() {
    }

    public TCPStartException(String message) {
        super(message);
    }

    public TCPStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public TCPStartException(Throwable cause) {
        super(cause);
    }

    public TCPStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
