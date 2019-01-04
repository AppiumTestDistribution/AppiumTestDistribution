package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;

/**
 * Device Manager - Handles all device related information's e.g UDID, Model, etc
 */
public class AppiumDeviceManager {

    private static ThreadLocal<AppiumDevice> appiumDevice = new ThreadLocal<>();
    private AndroidDeviceConfiguration androidDeviceConfiguration;

    public AppiumDeviceManager() {
        androidDeviceConfiguration = new AndroidDeviceConfiguration();
    }

    public static AppiumDevice getAppiumDevice() {
        return appiumDevice.get();
    }

    protected static void setDevice(AppiumDevice device) {
        appiumDevice.set(device);
    }


    public static MobilePlatform getMobilePlatform() {
        String os = AppiumDeviceManager.getAppiumDevice().getDevice().getOs();
        if (os.equalsIgnoreCase("ios")) {
            return MobilePlatform.IOS;
        } else {
            return MobilePlatform.ANDROID;
        }
    }

    public String getDeviceModel() {
        if (getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            return androidDeviceConfiguration.getDeviceModel();
        } else if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            return AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel();
        }
        throw new IllegalArgumentException("DeviceModel is Empty");
    }


    public String getDeviceVersion() {
        if (getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            return androidDeviceConfiguration.getDeviceOS();
        } else if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            return AppiumDeviceManager.getAppiumDevice().getDevice().getOsVersion();
        }
        throw new IllegalArgumentException("DeviceVersion is Empty");
    }
}
