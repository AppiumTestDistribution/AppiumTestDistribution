package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AppiumDevice;

import java.io.IOException;

/**
 * Device Manager - Handles all device related information's e.g UDID, Model, etc
 */
public class AppiumDeviceManager {

    private static ThreadLocal<String> deviceUDID = new ThreadLocal<>();
    private static ThreadLocal<AppiumDevice> appiumDevice = new ThreadLocal<>();
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private AndroidDeviceConfiguration androidDeviceConfiguration;

    public AppiumDeviceManager() {
        try {
            iosDeviceConfiguration = new IOSDeviceConfiguration();
            androidDeviceConfiguration = new AndroidDeviceConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDeviceUDID() {
        return deviceUDID.get();
    }

    public static AppiumDevice getDevice() {
        return appiumDevice.get();
    }

    protected static void setDevice(AppiumDevice device) {
        appiumDevice.set(device);
    }


    public static MobilePlatform getMobilePlatform() {
        int length = AppiumDeviceManager.getDevice().getDevice().getUdid().length();
        if (length
                == IOSDeviceConfiguration.IOS_UDID_LENGTH
                || length
                == IOSDeviceConfiguration.SIM_UDID_LENGTH) {
            return MobilePlatform.IOS;
        } else {
            return MobilePlatform.ANDROID;
        }
    }

    public String getDeviceModel() throws InterruptedException, IOException {
        if (getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            return androidDeviceConfiguration.getDeviceModel();
        } else if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            return AppiumDeviceManager.getDevice().getDevice().getDeviceModel();
        }
        throw new IllegalArgumentException("DeviceModel is Empty");
    }

    public String getDeviceCategory() throws Exception {
        if (iosDeviceConfiguration.deviceUDIDiOS
                .contains(AppiumDeviceManager.getDevice().getDevice().getUdid())) {
            return AppiumDeviceManager.getDevice().getDevice().getName().replace(" ", "_");
        } else {
            return androidDeviceConfiguration.getDeviceModel();
        }
    }

    public String getDeviceVersion() {
        if (getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            return androidDeviceConfiguration.getDeviceOS();
        } else if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            return AppiumDeviceManager.getDevice().getDevice().getOsVersion();
        }
        throw new IllegalArgumentException("DeviceVersion is Empty");
    }
}
