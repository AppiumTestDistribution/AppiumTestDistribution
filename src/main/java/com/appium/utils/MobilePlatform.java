package com.appium.utils;

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
