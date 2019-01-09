package com.appium.manager;

import com.github.device.Device;

public class AppiumDevice {

    private static final String AVAILABLE = "AVAILABLE";
    private static final String BUSY = "BUSY";
    private Device device;
    private int port;
    private int chromeDriverPort;
    private String webkitProcessID;
    private String deviceState;
    private String ipAddress;

    public AppiumDevice(Device device, String ipAddress) {
        this.device = device;
        this.ipAddress = ipAddress;
        deviceState = AVAILABLE;
        chromeDriverPort = 0; //Setting as Zero initially
    }

    public int getChromeDriverPort() {
        return chromeDriverPort;
    }

    public void setChromeDriverPort(int chromeDriverPort) {
        this.chromeDriverPort = chromeDriverPort;
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

    public String getHostName() {
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

    public String getWebkitProcessID() {
        return webkitProcessID;
    }

    public void setWebkitProcessID(String webkitProcessID) {
        this.webkitProcessID = webkitProcessID;
    }
}
