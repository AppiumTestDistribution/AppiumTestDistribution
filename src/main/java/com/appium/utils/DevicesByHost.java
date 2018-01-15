package com.appium.utils;

import com.thoughtworks.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DevicesByHost {
    private Map<String, List<Device>> devicesByHost;

    public DevicesByHost(Map<String, List<Device>> devicesByHost) {
        this.devicesByHost = devicesByHost;
    }

    public List<Device> getAllDevices() {
        ArrayList<Device> deviceList = new ArrayList<>();
        devicesByHost.forEach((host, devicesInHost) -> {
            deviceList.addAll(devicesInHost);
        });
        return deviceList;
    }

    public String getHostOfDevice(String uuid) {
        return null; //host
    }
}
