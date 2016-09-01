package com.appium.utils.ios;

import com.appium.utils.CommandPrompt;
import net.dongliu.apk.parser.ApkParser;

import java.io.File;
import java.io.IOException;

/**
 * Created by ansonliao on 13/5/2016.
 */
public class IOSUtils {
    private static CommandPrompt commandPrompt = new CommandPrompt();

    public static final String IPA_BUNDLE_ID = "CFBundleIdentifier";
    public static final String IPA_BUNDLE_NAME = "CFBundleName";
    public static final String IPA_BUNDLE_VER = "CFBundleVersion";
    public static final String IPA_BUNDLE_DISPLAY_NAME = "CFBundleDisplayName";
    public static final String IPA_BUNDLE_MIN_VER = "MinimumOSVersion";

    public static synchronized String getDeviceName(String devicdUDID) {
        String deviceName = null;

        try {
            deviceName = commandPrompt.runCommand(
                    "idevicename --udid " + devicdUDID).replace("\\W", "_");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceName;
    }

    public static synchronized String getIOSVersion(String devicdUDID) {
        String iOSVerison = null;

        try {
            iOSVerison = commandPrompt.runCommandThruProcessBuilder(
                    "ideviceinfo --udid " + devicdUDID
                            + " | grep ProductVersion | awk '{print $2}'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iOSVerison;
    }

    public static synchronized String getDeviceClass(String deviceUDID) {
        String deviceClass = "";

        try {
            deviceClass = commandPrompt.runCommandThruProcessBuilder(
                    "ideviceinfo --udid " + deviceUDID + " | grep DeviceClass | awk '{print $2}'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceClass;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ApkParser apkParser = new ApkParser(new File("/Users/AnsonLiao/Desktop/Maaii.apk"));
        System.out.println(apkParser.getManifestXml());
    }
}