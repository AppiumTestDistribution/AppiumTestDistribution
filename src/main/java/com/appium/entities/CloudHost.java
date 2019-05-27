package com.appium.entities;

import org.apache.commons.lang3.EnumUtils;

public enum CloudHost {
    SAUCE,
    BROWSERSTACK;

    public static boolean isCloud(String host) {
        return EnumUtils.isValidEnumIgnoreCase(CloudHost.class, host);
    }
}
