package com.appium.device;

import com.appium.utils.Variable;

public class AtdEnvironment {
    public String get(String environmentVariableName) {
        return Variable.getOverriddenStringValue(environmentVariableName);
    }
}
