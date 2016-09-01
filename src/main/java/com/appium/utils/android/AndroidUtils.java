package com.appium.utils.android;

import com.appium.utils.CommandPrompt;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ansonliao on 13/5/2016.
 */
public class AndroidUtils {
    private static CommandPrompt commandPrompt = new CommandPrompt();
    public static final String APK_PACKAGE_KEY_NAME = "PackageName";
    public static final String APK_LAUNCH_ACTIVITY = "LaunchActivity";

    public static synchronized String getDeviceBrandName(String deviceId) {
        String brandName = "";

        try {
            brandName = commandPrompt.runCommand("adb -s " + deviceId
                    + " shell getprop ro.product.brand").replaceAll("\\s+", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return brandName.toUpperCase();
    }

    public static synchronized String getDeviceModel(String deviceId) {
        String model = "";

        try {
            model = commandPrompt.runCommand("adb -s " + deviceId
                    + " shell getprop ro.product.model").replaceAll("\\s + ", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model.toUpperCase();
    }

    public static synchronized String getOSVersion(String deviceId) {
        String version = "";

        try {
            version = commandPrompt.runCommand("adb -s " + deviceId
                    + " shell getprop ro.build.version.release").replaceAll("\\s+", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return version;
    }

    public static synchronized String getDeviceOSLevel(String deviceId) {
        String osLevel = "";

        try {
            osLevel = commandPrompt.runCommand("adb -s " + deviceId
                    + " shell getprop ro.build.version.sdk").replaceAll("\\s+", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return osLevel;
    }

    public static synchronized void adbShellInput(String udid, MobileElement el, String value) {
        String command = "adb -s " + udid + " shell input text " + value;

        try {
            el.click();
            commandPrompt.runCommand(command);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Map<String, String> getAPKInfo(String apkFilePath) {
        Properties properties = new Properties();
        String configProperties = "config.properties";
        String getPackageCmd = " dump badging " +  apkFilePath
                + " | awk '/package/{gsub(\"name=|'\"'\"'\",\"\");  print $2}'";
        String getLaunchActivityCmd = " dump badging " +  apkFilePath
                + " | awk '/activity/{gsub(\"name=|'\"'\"'\",\"\");  print $2}'";
        Map<String, String> apkInfoMap = new HashMap<>();

        try {
            properties.load(new FileInputStream(configProperties));
            String aaptToolPath = properties.getProperty("AAPT_PATH").trim();
            String packageName = commandPrompt
                    .getBufferedReader(aaptToolPath + getPackageCmd).readLine();
            String launchActivity = commandPrompt
                    .getBufferedReader(aaptToolPath + getLaunchActivityCmd).readLine();
            apkInfoMap.put(APK_PACKAGE_KEY_NAME, packageName);
            apkInfoMap.put(APK_LAUNCH_ACTIVITY, launchActivity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apkInfoMap;
    }

//    public static synchronized int
//    getNetworkdConnectionStatus(AndroidDriver<MobileElement> driver) {
//        return driver.getNetworkConnection().value;
//    }

//    @Test
//    public void getAPKInfoTest() {
//        getAPKInfo("/git/maaii-automation/Maaii-Appium-Wispi/binary/Shatel.apk")
//                .forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
//    }
}