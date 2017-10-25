package com.appium.android;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.DeviceAllocationManager;
import com.appium.manager.DeviceManager;
import com.appium.utils.CommandPrompt;
import com.thoughtworks.android.AndroidManager;
import com.thoughtworks.device.Device;

import java.io.IOException;
import java.util.*;

public class AndroidDeviceConfiguration {

    private CommandPrompt cmd = new CommandPrompt();
    public static List<String> validDeviceIds = new ArrayList<>();

    /*
     * This method gets the device model name
     */
    public String getDeviceModel() {
        Optional<Device> getModel = getDevice();
        return getModel.get().getDeviceModel() + getModel.get().getBrand();
    }

    /*
     * This method gets the device OS API Level
     */
    public String getDeviceOS() {
        Optional<Device> deviceOS = getDevice();
        System.out.println("DeviceOS---" + deviceOS.toString() + "----" + DeviceManager.getDeviceUDID());
        return deviceOS.get().getOsVersion();
    }

    private Optional<Device> getDevice() {
        Optional<Device> deviceOS = null;
        try {
            deviceOS = DeviceAllocationManager.getInstance().androidManager.stream().filter(device ->
                    device.getUdid().equals(DeviceManager.getDeviceUDID())).findFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceOS;
    }

    /**
     * This method will close the running app
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void closeRunningApp(String deviceID, String app_package)
            throws InterruptedException, IOException {
        // adb -s 192.168.56.101:5555 com.android2.calculator3
        cmd.runCommand("adb -s " + deviceID + " shell am force-stop "
                + app_package);
    }

    /**
     * This method clears the app data only for android
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void clearAppData(String deviceID, String app_package)
            throws InterruptedException, IOException {
        // adb -s 192.168.56.101:5555 com.android2.calculator3
        cmd.runCommand("adb -s " + deviceID + " shell pm clear "
                + app_package);
    }

    /**
     * This method removes apk from the devices attached
     *
     * @param app_package
     * @throws Exception
     */

    public void removeApkFromDevices(String deviceID, String app_package)
            throws Exception {
        cmd.runCommand("adb -s " + deviceID + " uninstall " + app_package);
    }

    public String screenRecord(String fileName)
            throws IOException, InterruptedException {
        return "adb -s " + DeviceManager.getDeviceUDID()
                + " shell screenrecord --bit-rate 3000000 /sdcard/" + fileName
                + ".mp4";
    }

    public boolean checkIfRecordable() throws IOException, InterruptedException {
        String screenrecord =
                cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
                        + " shell ls /system/bin/screenrecord");
        if (screenrecord.trim().equals("/system/bin/screenrecord")) {
            return true;
        } else {
            return false;
        }
    }

    public String getDeviceManufacturer()
            throws IOException, InterruptedException {
        return cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
                + " shell getprop ro.product.manufacturer")
                .trim();
    }

    public AndroidDeviceConfiguration pullVideoFromDevice(String fileName, String destination)
            throws IOException, InterruptedException {
        ProcessBuilder pb =
                new ProcessBuilder("adb", "-s", DeviceManager.getDeviceUDID(),
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
        cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID() + " shell rm -f /sdcard/"
                + fileName + ".mp4");
    }

    public void setValidDevices(List<String> deviceID) {
        deviceID.forEach(deviceList -> {
            if (deviceList.length() < IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                validDeviceIds.add(deviceList);
            }
        });
    }

    public String getDeviceResolution() throws IOException, InterruptedException {
        return cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
                + "shell wm size").split(":")[1].replace("\n","");
    }
}
