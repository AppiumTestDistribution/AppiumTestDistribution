package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.plugin.PluginClI;

import java.io.IOException;

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


    public static MobilePlatform getMobilePlatform() throws IOException {
        return MobilePlatform.valueOf(PluginClI.getInstance().getPlatFormName().toUpperCase());
    }

    public String getDeviceModel() throws IOException {
        if (getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            return androidDeviceConfiguration.getDeviceModel();
        } else if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            return AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel();
        }
        throw new IllegalArgumentException("DeviceModel is Empty");
    }

    public static boolean isPlatform(MobilePlatform expectedPlatform) throws IOException {
        return AppiumDeviceManager.getMobilePlatform().equals(expectedPlatform);
    }
}
