package com.appium.device;

public class AtdEnvironment {
    public String get(String environmentVariableName) {
        return System.getenv(environmentVariableName);
    }
}
