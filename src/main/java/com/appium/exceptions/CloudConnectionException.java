package com.appium.exceptions;

public class CloudConnectionException extends RuntimeException {
    public CloudConnectionException(String message) {
        super(message);
    }

    public CloudConnectionException(String message, Exception e) {
        super(message, e);
    }
}
