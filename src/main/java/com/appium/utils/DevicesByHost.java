package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;
import com.thoughtworks.device.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DevicesByHost {
    private Map<String, List<Device>> devicesByHost;

    public DevicesByHost(Map<String, List<Device>> devicesByHost) {
        this.devicesByHost = devicesByHost;
    }

    public List<Device> getAllDevices() {
        ArrayList<Device> deviceList = new ArrayList<>();
        devicesByHost.forEach((host, devicesInHost) -> {
            deviceList.addAll(devicesInHost);
        });
        return deviceList;
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
        List<Device> simulators = new ArrayList<>();
        devicesByHost.entrySet().forEach(hostIP -> {
            hostIP.getValue().forEach(device -> {
                   if(device.getUdid().length() == IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                       simulators.add(device);
                   }
            });
        });
        return simulators;
    }

    public List<Device> getAllIOSDevices() {
        List<Device> simulators = new ArrayList<>();
        devicesByHost.entrySet().forEach(hostIP -> {
            hostIP.getValue().forEach(device -> {
                if(device.getUdid().length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                    simulators.add(device);
                }
            });
        });
        return simulators;
    }

    public List<Device> getAllAndroidDevices() {
        List<Device> simulators = new ArrayList<>();
        devicesByHost.entrySet().forEach(hostIP -> {
            hostIP.getValue().forEach(device -> {
                if(device.getUdid().length() != IOSDeviceConfiguration.IOS_UDID_LENGTH
                        && device.getUdid().length() != IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                    simulators.add(device);
                }
            });
        });
        return simulators;
    }

    public Device getDeviceProperty(String uuid) {
        final Device[] deviceProperty = new Device[1];
        devicesByHost.entrySet().forEach(hostIP -> {
            hostIP.getValue().forEach(device -> {
                if (device.getUdid().equals(uuid)) {
                     deviceProperty[0] = device;
                }
            });

        });
        return deviceProperty[0];
    }
}
