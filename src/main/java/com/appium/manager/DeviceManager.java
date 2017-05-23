package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.entities.MobilePlatform;

import java.io.IOException;

/**
 * Device Manager - Handles all device related information's e.g UDID, Model, etc
 */
public class DeviceManager {

    private static ThreadLocal<String> deviceUDID = new ThreadLocal<>();
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private AndroidDeviceConfiguration androidDeviceConfiguration;

    public DeviceManager() {
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

    protected static void setDeviceUDID(String UDID) {
        deviceUDID.set(UDID);
    }

    public static MobilePlatform getMobilePlatform() {
        if (DeviceManager.getDeviceUDID().length()
                == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            return MobilePlatform.IOS;
        } else {
            return MobilePlatform.ANDROID;
        }
    }

    public String getDeviceModel() throws InterruptedException, IOException {
        if (getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            return androidDeviceConfiguration.getDeviceModel();
        } else if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            return iosDeviceConfiguration.getIOSDeviceProductTypeAndVersion();
        }
        return null;
    }

    public String getDeviceCategory() throws Exception {
        if (iosDeviceConfiguration.checkiOSDevice()) {
            iosDeviceConfiguration.setIOSWebKitProxyPorts();
            return iosDeviceConfiguration.getDeviceName().replace(" ", "_");
        } else {
            return androidDeviceConfiguration.getDeviceModel();
        }
    }
}
