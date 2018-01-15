package com.appium.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class DeviceProperties {
    private  String udid;
    private  String name;
    private String state = "Not Supported";
    @JsonIgnoreProperties(ignoreUnknown = true)
    private boolean isAvailable;
    private String osVersion;
    private String os = "Not Supported";
    private String deviceType = "Not Supported";
    private String brand = "Not Supported";
    private String apiLevel = "Not Supported";
    private boolean isDevice;
    private String deviceModel="";

    public String getUdid() {
        return udid;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(String apiLevel) {
        this.apiLevel = apiLevel;
    }

    public boolean isDevice() {
        return isDevice;
    }

    public void setDevice(boolean device) {
        isDevice = device;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    private String screenSize;


    public void setName(String name) {
        this.name = name;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }
}
