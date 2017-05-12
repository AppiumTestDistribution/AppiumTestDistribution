package com.appium.utils;

import java.util.List;

public class TestResults {

    private String deviceName;
    private String deviceModel;
    private String deviceUDID;

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    private String deviceOS;
    private List<TestCases> testCases;



    public String getDeviceUDID() {
        return deviceUDID;
    }

    public void setDeviceUDID(String deviceUDID) {
        this.deviceUDID = deviceUDID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public List<TestCases> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCases> testCases) {
        this.testCases = testCases;
    }



}
