package com.appium.capabilities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DriverSession {
    @JsonProperty("appium:adbExecTimeout")
    @JsonAlias("adbExecTimeout")
    public int adbExecTimeout;

    @JsonProperty("appium:app")
    @JsonAlias("app")
    public String app;

    @JsonProperty("appium:appPackage")
    @JsonAlias("appPackage")
    public String appPackage;

    @JsonProperty("appium:automationName")
    @JsonAlias("automationName")
    public String automationName;

    @JsonProperty("appium:chromeDriverPort")
    @JsonAlias("chromeDriverPort")
    public String chromeDriverPort;

    @JsonProperty("appium:deviceApiLevel")
    @JsonAlias("deviceApiLevel")
    public int deviceApiLevel;

    @JsonProperty("appium:deviceManufacturer")
    @JsonAlias("deviceManufacturer")
    public String deviceManufacturer;

    @JsonProperty("appium:deviceModel")
    @JsonAlias("deviceModel")
    public String deviceModel;

    @JsonProperty("appium:deviceName")
    @JsonAlias("deviceName")
    public String deviceName;

    @JsonProperty("appium:deviceScreenDensity")
    @JsonAlias("deviceScreenDensity")
    public String deviceScreenDensity;

    @JsonProperty("appium:deviceScreenSize")
    @JsonAlias("deviceScreenSize")
    public String deviceScreenSize;

    @JsonProperty("appium:deviceUDID")
    @JsonAlias("deviceUDID")
    public String deviceUDID;

    @JsonProperty("appium:mjpegServerPort")
    @JsonAlias("mjpegServerPort")
    public String mjpegServerPort;

    @JsonProperty("appium:platformVersion")
    @JsonAlias("platformVersion")
    public String platformVersion;

    @JsonProperty("appium:systemPort")
    @JsonAlias("systemPort")
    public String systemPort;

    @JsonProperty("appium:udid")
    @JsonAlias("udid")
    public String udid;

    public String platformName;
}
