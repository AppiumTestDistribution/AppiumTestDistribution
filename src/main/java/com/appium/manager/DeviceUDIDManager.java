package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.MobilePlatform;

/**
 * Created by saikrisv on 19/05/17.
 */
public class DeviceUDIDManager {

    private static ThreadLocal<String> deviceUDID = new ThreadLocal<>();

    public static String getDeviceUDID() {
        return deviceUDID.get();
    }

    static void setDeviceUDID(String UDID) {
        deviceUDID.set(UDID);
    }

    public static MobilePlatform getMobilePlatform() {
        MobilePlatform platform = null;

        if (DeviceUDIDManager.getDeviceUDID().length()
                == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            platform = MobilePlatform.IOS;
        } else {
            platform = MobilePlatform.ANDROID;
        }
        return platform;
    }
}
