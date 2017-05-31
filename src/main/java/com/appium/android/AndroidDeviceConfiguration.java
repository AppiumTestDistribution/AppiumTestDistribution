package com.appium.android;

import com.appium.manager.DeviceManager;
import com.appium.utils.CommandPrompt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidDeviceConfiguration {

    private CommandPrompt cmd = new CommandPrompt();
    private Map<String, String> devices = new HashMap<String, String>();
    public static ArrayList<String> deviceSerial = new ArrayList<String>();
    ArrayList<String> deviceModel = new ArrayList<String>();
    public static List<String> validDeviceIds;

    /**
     * This method start adb server
     */
    public void startADB() throws Exception {
        String output = cmd.runCommand("adb start-server");
        String[] lines = output.split("\n");
        if (lines[0].contains("internal or external command")) {
            System.out.println("Please set ANDROID_HOME in your system variables");
        }
    }

    /**
     * This method stop adb server
     */
    public void stopADB() throws Exception {
        cmd.runCommand("adb kill-server");
    }

    /**
     * This method return connected devices
     *
     * @return hashmap of connected devices information
     */
    public Map<String, String> getDevices() throws Exception {

        startADB(); // start adb service
        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");

        if (lines.length <= 1) {
            System.out.println("No Android Device Connected");
            stopADB();
            return null;
        } else {
            for (int i = 1; i < lines.length; i++) {
                lines[i] = lines[i].replaceAll("\\s+", "");

                if (lines[i].contains("device")) {
                    lines[i] = lines[i].replaceAll("device", "");
                    String deviceID = lines[i];

                    if (validDeviceIds == null
                            || (validDeviceIds != null && validDeviceIds.contains(deviceID))) {
                        String model =
                                cmd.runCommand("adb -s " + deviceID
                                        + " shell getprop ro.product.model")
                                        .replaceAll("\\s+", "");
                        String brand =
                                cmd.runCommand("adb -s " + deviceID
                                        + " shell getprop ro.product.brand")
                                        .replaceAll("\\s+", "");
                        String osVersion = cmd.runCommand(
                                "adb -s " + deviceID + " shell getprop ro.build.version.release")
                                .replaceAll("\\s+", "");
                        String deviceName = brand + " " + model;
                        String apiLevel =
                                cmd.runCommand("adb -s " + deviceID
                                        + " shell getprop ro.build.version.sdk")
                                        .replaceAll("\n", "");

                        devices.put("deviceID" + i, deviceID);
                        devices.put("deviceName" + i, deviceName);
                        devices.put("osVersion" + i, osVersion);
                        devices.put(deviceID, apiLevel);
                        deviceSerial.add(deviceID);
                    }
                } else if (lines[i].contains("unauthorized")) {
                    lines[i] = lines[i].replaceAll("unauthorized", "");
                    String deviceID = lines[i];
                } else if (lines[i].contains("offline")) {
                    lines[i] = lines[i].replaceAll("offline", "");
                    String deviceID = lines[i];
                }
            }
            return devices;
        }
    }

    public ArrayList<String> getDeviceSerial() throws Exception {

        startADB(); // start adb service
        String output = cmd.runCommand("adb devices");
        String[] lines = output.split("\n");

        if (lines.length <= 1) {
            System.out.println("No Android Device Connected");
            return null;
        } else {
            for (int i = 1; i < lines.length; i++) {
                lines[i] = lines[i].replaceAll("\\s+", "");

                if (lines[i].contains("device")) {
                    lines[i] = lines[i].replaceAll("device", "");
                    String deviceID = lines[i];
                    if (validDeviceIds == null
                            || (validDeviceIds != null && validDeviceIds.contains(deviceID))) {
                        if (validDeviceIds == null) {
                            System.out.println("validDeviceIds is null!!!");
                        }
                        System.out.println("Adding device: " + deviceID);
                        deviceSerial.add(deviceID);
                    }
                } else if (lines[i].contains("unauthorized")) {
                    lines[i] = lines[i].replaceAll("unauthorized", "");
                    String deviceID = lines[i];
                } else if (lines[i].contains("offline")) {
                    lines[i] = lines[i].replaceAll("offline", "");
                    String deviceID = lines[i];
                }
            }
            return deviceSerial;
        }
    }

    /*
     * This method gets the device model name
     */
    public String getDeviceModel() {
        String deviceModelName = null;
        String brand = null;
        String deviceModel = null;
        try {
            deviceModelName =
                    cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
                            + " shell getprop ro.product.model");

            brand = cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
                    + " shell getprop ro.product.brand");
        } catch (InterruptedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        deviceModel = deviceModelName.concat("_" + brand).replace("\n", "");

        return deviceModel.trim();

    }

    /*
     * This method gets the device OS API Level
     */
    public String deviceOS(String deviceID) {
        String deviceOSLevel = null;
        try {
            deviceOSLevel =
                    cmd.runCommand("adb -s " + deviceID
                            + " shell getprop ro.build.version.release")
                            .replace("\n", "");
        } catch (InterruptedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return deviceOSLevel;

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

    public void setValidDevices(List<String> validDeviceIds) {
        this.validDeviceIds = validDeviceIds;
    }
}
