package com.appium.ios;

import com.appium.manager.AppiumDevice;

import java.util.ArrayList;
import java.util.List;

public class IOSDeviceConfiguration {
    public static List<AppiumDevice> deviceUDIDiOS = new ArrayList<>();
    public static List<String> validDeviceIds = new ArrayList<>();

    public final static int IOS_UDID_LENGTH = 40;
    public final static int SIM_UDID_LENGTH = 36;

    public IOSDeviceConfiguration() {

    }


    public void setValidDevices(List<String> deviceID) {
        deviceID.forEach(deviceList -> {
            if (deviceList.length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                validDeviceIds.add(deviceList);
            }
        });
    }
}
