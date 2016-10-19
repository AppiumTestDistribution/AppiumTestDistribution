package com.appium.utils;

/**
 * Created by ansonliao on 19/10/2016.
 */
public enum MobilePlatform {

    IOS("IOS"),
    ANDROID("ANDROID"),
    AndroidOnLinux("Android On Linux"),
    AndroidOnWindows("Android On Windows");

    public final String platformName;
    MobilePlatform(String platformName) {
        this.platformName = platformName;
    }

}