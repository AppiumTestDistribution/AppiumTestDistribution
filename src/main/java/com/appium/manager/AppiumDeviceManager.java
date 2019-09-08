package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.device.DevicesByHost;
import com.appium.device.HostMachineDeviceManager;
import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Device Manager - Handles all device related information's e.g UDID, Model, etc
 */
public class AppiumDeviceManager {

    private static ThreadLocal<List<AppiumDevice>> appiumDevice = new ThreadLocal<>();

    public static List<AppiumDevice> getAppiumDevices() {
        return appiumDevice.get();
    }

    protected static void setDevice(List<AppiumDevice> device) {
        appiumDevice.set(device);
    }

    public static MobilePlatform getMobilePlatform(String uuid) {
        String os = AppiumDeviceManager.getAppiumDevices().parallelStream().filter(device ->
            device.getDevice().getUdid().equalsIgnoreCase(uuid)
        ).collect(Collectors.toList()).get(0).getDevice().getOs();
        if (os.equalsIgnoreCase("ios")) {
            return MobilePlatform.IOS;
        } else {
            return MobilePlatform.ANDROID;
        }
    }
}
