package com.appium.device;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDevice;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DevicesByHost {
    private ConcurrentHashMap<String, List<AppiumDevice>> devicesByHost;

    DevicesByHost(Map<String, List<AppiumDevice>> devicesByHost) {
        this.devicesByHost = new ConcurrentHashMap<>(devicesByHost);
    }

    public List<AppiumDevice> getAllDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream())
                .collect(Collectors.toList());
    }

    public List<AppiumDevice> getAllSimulators() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getDevice().getUdid().length()
                                == IOSDeviceConfiguration.SIM_UDID_LENGTH))
                .collect(Collectors.toList());
    }

    public List<AppiumDevice> getAllIOSDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        device.getDevice().getUdid().length()
                                == IOSDeviceConfiguration.IOS_UDID_LENGTH))
                .collect(Collectors.toList());
    }

    public List<AppiumDevice> getAllAndroidDevices() {
        return devicesByHost.entrySet().parallelStream().flatMap(stringListEntry ->
                stringListEntry.getValue().parallelStream().filter(device ->
                        (device.getDevice().getUdid().length()
                                != IOSDeviceConfiguration.IOS_UDID_LENGTH
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

    public AppiumDevice getAppiumDevice(String deviceUdid, String hostName) {
        List<AppiumDevice> devicesInHost = devicesByHost.get(hostName);
        return devicesInHost.stream().filter(appiumDevice ->
                appiumDevice.getDevice().getUdid().equals(deviceUdid)).findFirst().get();
    }
}
