package com.appium.android;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.DeviceAllocationManager;
import com.appium.utils.CommandPrompt;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import com.thoughtworks.device.Device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AndroidDeviceConfiguration {

    private CommandPrompt cmd = new CommandPrompt();
    public static List<String> validDeviceIds = new ArrayList<>();
    private DevicesByHost devicesByHost;

    public AndroidDeviceConfiguration() {
        devicesByHost = HostMachineDeviceManager.getInstance();
    }

    /*
     * This method gets the device model name
     */
    public String getDeviceModel() {
        Optional<Device> getModel = getDevice();
        return (getModel.get().getDeviceModel() + getModel.get().getBrand())
                .replaceAll("[^a-zA-Z0-9\\.\\-]", "");
    }

    /*
     * This method gets the device OS API Level
     */
    public String getDeviceOS() {
        Optional<Device> deviceOS = getDevice();
        return deviceOS.get().getOsVersion();
    }

    private Optional<Device> getDevice() {
        Optional<Device> deviceOS = null;
        try {
            deviceOS = DeviceAllocationManager.getInstance().deviceList.stream().filter(device ->
                    device.getUdid().equals(AppiumDeviceManager.getDeviceUDID())).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceOS;
    }

    public String screenRecord(String fileName)
            throws IOException, InterruptedException {
        return "adb -s " + AppiumDeviceManager.getDeviceUDID()
                + " shell screenrecord --bit-rate 3000000 /sdcard/" + fileName
                + ".mp4";
    }

    public boolean checkIfRecordable() throws IOException, InterruptedException {
        String screenrecord =
                cmd.runCommand("adb -s " + AppiumDeviceManager.getDeviceUDID()
                        + " shell ls /system/bin/screenrecord");
        if (screenrecord.trim().equals("/system/bin/screenrecord")) {
            return true;
        } else {
            return false;
        }
    }

    public String getDeviceManufacturer()
            throws IOException, InterruptedException {
        return devicesByHost.getDeviceProperty(AppiumDeviceManager.getDeviceUDID())
                .getDeviceManufacturer();
    }

    public AndroidDeviceConfiguration pullVideoFromDevice(String fileName, String destination)
            throws IOException, InterruptedException {
        ProcessBuilder pb =
                new ProcessBuilder("adb", "-s", AppiumDeviceManager.getDeviceUDID(),
                        "pull", "/sdcard/" + fileName + ".mp4",
                        destination);
        Process pc = pb.start();
        pc.waitFor();
        System.out.println("Exited with Code::" + pc.exitValue());
        System.out.println("Done");
        Thread.sleep(5000);
        return new AndroidDeviceConfiguration();
    }

    public void removeVideoFileFromDevice(String fileName)
            throws IOException, InterruptedException {
        cmd.runCommand("adb -s " + AppiumDeviceManager.getDeviceUDID() + " shell rm -f /sdcard/"
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
