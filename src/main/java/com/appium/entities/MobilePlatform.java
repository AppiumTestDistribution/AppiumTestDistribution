package com.appium.entities;

/**
 * Created by saikrisv on 2016/11/16.
 */
public enum MobilePlatform {

    IOS("IOS"),
    ANDROID("ANDROID"),
    WINDOWS("WINDOWS");

    public final String platformName;
    MobilePlatform(String platformName) {
        this.platformName = platformName;
    }

}
