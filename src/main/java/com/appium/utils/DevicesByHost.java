package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;
import com.thoughtworks.device.Device;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DevicesByHost {
    private Map<String, List<Device>> devicesByHost;

    public DevicesByHost(Map<String, List<Device>> devicesByHost) {
        this.devicesByHost = devicesByHost;
    }

    public List<Device> getAllDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream())
                .collect(Collectors.toList());
    }

    public String getHostOfDevice(String uuid) {
        final String[] hostIPKey = new String[1];
        devicesByHost.entrySet().forEach(hostIP -> {
            hostIP.getValue().forEach(device -> {
                if (device.getUdid().equals(uuid)) {
                    hostIPKey[0] = hostIP.getKey();
                }
            });

        });
        return String.valueOf(hostIPKey[0]);
    }

    public List<Device> getAllSimulators() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getUdid().length() == IOSDeviceConfiguration.SIM_UDID_LENGTH))
                .collect(Collectors.toList());
    }

    public List<Device> getAllIOSDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getUdid().length() == IOSDeviceConfiguration.IOS_UDID_LENGTH))
                .collect(Collectors.toList());
    }

    public List<Device> getAllAndroidDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        (device.getUdid().length() != IOSDeviceConfiguration.IOS_UDID_LENGTH
                                && device.getUdid().length()
                                != IOSDeviceConfiguration.SIM_UDID_LENGTH)))
                .collect(Collectors.toList());
    }

    public Device getDeviceProperty(String uuid) {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getUdid().equalsIgnoreCase(uuid)))
                .collect(Collectors.toList()).get(0);
    }

    public Set<String> getAllHosts() {
        return devicesByHost.keySet();
    }
}
