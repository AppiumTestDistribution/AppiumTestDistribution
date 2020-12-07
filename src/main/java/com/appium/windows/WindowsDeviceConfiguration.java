package com.appium.windows;

import com.appium.device.DevicesByHost;
import com.appium.device.HostMachineDeviceManager;

public class WindowsDeviceConfiguration {
    private DevicesByHost devicesByHost;

    public WindowsDeviceConfiguration() {
        devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
    }
}
