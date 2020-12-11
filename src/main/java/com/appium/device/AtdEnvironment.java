package com.appium.device;

public class AtdEnvironment {

    public String getEnv(String environmentVariableName) {
        return System.getenv(environmentVariableName);
    }
}
