package com.appium.manager;

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
}
