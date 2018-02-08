package com.appium.utils;

import com.thoughtworks.device.Device;

public class AppiumDevice {

    Device device;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    int port;

    public void setDeviceState(String deviceState) {
        this.deviceState = deviceState;
    }

    public String getDeviceState() {
        return deviceState;
    }

    String deviceState = "Available";

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}
