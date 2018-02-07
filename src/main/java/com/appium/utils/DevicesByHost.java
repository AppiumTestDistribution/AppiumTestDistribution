package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DevicesByHost {
    private Map<String, List<AppiumDevice>> devicesByHost;

    public DevicesByHost(Map<String, List<AppiumDevice>> devicesByHost) {
        this.devicesByHost = devicesByHost;
    }

    public List<AppiumDevice> getAllDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream())
                .collect(Collectors.toList());
    }

    public String getHostOfDevice(String uuid) {
        final String[] hostIPKey = new String[1];
        devicesByHost.entrySet().forEach(hostIP -> {
            hostIP.getValue().forEach(device -> {
                if (device.getDevice().getUdid().equals(uuid)) {
                    hostIPKey[0] = hostIP.getKey();
                }
            });

        });
        return String.valueOf(hostIPKey[0]);
    }

    public List<AppiumDevice> getAllSimulators() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getDevice().getUdid().length() == IOSDeviceConfiguration.SIM_UDID_LENGTH))
                .collect(Collectors.toList());
    }

    public List<AppiumDevice> getAllIOSDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getDevice().getUdid().length() == IOSDeviceConfiguration.IOS_UDID_LENGTH))
                .collect(Collectors.toList());
    }

    public List<AppiumDevice> getAllAndroidDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        (device.getDevice().getUdid().length() != IOSDeviceConfiguration.IOS_UDID_LENGTH
                                && device.getDevice().getUdid().length()
                                != IOSDeviceConfiguration.SIM_UDID_LENGTH)))
                .collect(Collectors.toList());
    }

    public AppiumDevice getDeviceProperty(String uuid) {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getDevice().getUdid().equalsIgnoreCase(uuid)))
                .collect(Collectors.toList()).get(0);
    }

    public Set<String> getAllHosts() {
        return devicesByHost.keySet();
    }

    public Map<String, List<AppiumDevice>> getAllHostDevices() {
        return devicesByHost;
    }
}
