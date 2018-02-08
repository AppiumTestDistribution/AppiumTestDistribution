package com.appium.utils;

import com.thoughtworks.device.Device;

public class AppiumDevice {

    private static final String AVAILABLE = "AVAILABLE";
    private static final String BUSY = "BUSY";
    private Device device;
    private int port;
    private String deviceState;
    private String ipAddress;

    public AppiumDevice(Device device, String ipAddress) {
        this.device = device;
        this.ipAddress = ipAddress;
        deviceState = AVAILABLE;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Device getDevice() {
        return device;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isLocalDevice() {
        return ipAddress.equals("127.0.0.1");
    }

    public boolean isAvailable() {
        return deviceState.equalsIgnoreCase(AVAILABLE);
    }

    public void blockDevice() {
        deviceState = BUSY;
    }

    public void freeDevice() {
        deviceState = AVAILABLE;
    }
}
