package com.polynom.server.exception;

/**
 *
 **/

public class TCPException extends Exception {
    public TCPException() {
    }

    public TCPException(String message) {
        super(message);
    }

    public TCPException(String message, Throwable cause) {
        super(message, cause);
    }

    public TCPException(Throwable cause) {
        super(cause);
    }

    public TCPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
