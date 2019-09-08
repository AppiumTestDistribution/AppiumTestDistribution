package com.appium.android;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDeviceManager;

import com.appium.manager.AppiumDevice;
import com.appium.utils.CommandPrompt;
import com.appium.device.DevicesByHost;
import com.appium.device.HostMachineDeviceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AndroidDeviceConfiguration {

    private CommandPrompt cmd = new CommandPrompt();
    public static List<String> validDeviceIds = new ArrayList<>();
    private DevicesByHost devicesByHost;

    public AndroidDeviceConfiguration() {
        devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
    }

    /*
     * This method gets the device model name
     */
    public String getDeviceModel(AppiumDevice device) {
        return (device.getDevice().getDeviceModel()
            + device.getDevice().getBrand())
            .replaceAll("[^a-zA-Z0-9\\.\\-]", "");
    }


    public boolean checkIfRecordable(AppiumDevice device) throws IOException, InterruptedException {
        String screenrecord =
            cmd.runCommand("adb -s " + device.getDevice().getUdid()
                + " shell ls /system/bin/screenrecord");
        if (screenrecord.trim().equals("/system/bin/screenrecord")) {
            return true;
        } else {
            return false;
        }
    }

    public String getDeviceManufacturer(AppiumDevice device) {
        return devicesByHost.getDeviceProperty(device
            .getDevice().getUdid())
            .getDevice().getDeviceManufacturer();
    }

    public AndroidDeviceConfiguration pullVideoFromDevice(AppiumDevice device,
                                                          String fileName, String destination)
        throws IOException, InterruptedException {
        ProcessBuilder pb =
            new ProcessBuilder("adb", "-s",
                device.getDevice().getUdid(),
                "pull", "/sdcard/" + fileName + ".mp4",
                destination);
        Process pc = pb.start();
        pc.waitFor();
        System.out.println("Exited with Code::" + pc.exitValue());
        System.out.println("Done");
        Thread.sleep(5000);
        return new AndroidDeviceConfiguration();
    }

    public void removeVideoFileFromDevice(AppiumDevice device, String fileName)
        throws IOException, InterruptedException {
        cmd.runCommand("adb -s " + device.getDevice().getUdid()
            + " shell rm -f /sdcard/"
            + fileName + ".mp4");
    }

    public void setValidDevices(List<String> deviceID) {
        deviceID.forEach(deviceList -> {
            if (deviceList.length() < IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                validDeviceIds.add(deviceList);
            }
        });
    }
}
