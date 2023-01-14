package com.appium.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Device {
    public String name;
    public String udid;
    public String state;
    public String sdk;
    public String platform;
    public int wdaLocalPort;
    public Meta meta;
    public int mjpegServerPort;
    public boolean busy;
    public boolean realDevice;
    public String deviceType;
    public Object capability;
    public String platformVersion;
    public String deviceName;
    public String host;
    public int totalUtilizationTimeMilliSec;
    public String derivedDataPath;
    public boolean offline;
    public int sessionStartTime;

    public static class Meta {
        public int revision;
        public long created;
        public int version;
    }
}
