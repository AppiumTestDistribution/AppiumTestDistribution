package com.appium.device;

public class AtdEnvironment {
    public static AtdEnvironment getInstance() {
        return new AtdEnvironment();
    }

    public String getEnv(String environmentVariableName) {
        return System.getenv(environmentVariableName);
    }
}
