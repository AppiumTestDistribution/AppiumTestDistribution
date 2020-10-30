package com.appium.exceptions;

public class LocalConnectionException extends RuntimeException {
    public LocalConnectionException(String message, Exception e) {
        super(message, e);
    }
}
