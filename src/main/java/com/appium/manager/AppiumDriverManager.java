package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver
            = new ThreadLocal<>();
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private AndroidDeviceConfiguration androidDeviceConfiguration;
    private AppiumServerManager appiumServerManager;
    private ConfigFileManager prop;
    private DesiredCapabilityManager desiredCapabilityManager;

    public AppiumDriverManager() throws IOException {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        androidDeviceConfiguration = new AndroidDeviceConfiguration();
        desiredCapabilityManager = new DesiredCapabilityManager();
        appiumServerManager = new AppiumServerManager();
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    public void startAppiumDriver() throws Exception {
        AppiumDriver<MobileElement> currentDriverSession;
        if (ConfigFileManager.configFileMap.get("APP_TYPE").equalsIgnoreCase("web")) {
            currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                desiredCapabilityManager.androidWeb());
            AppiumDriverManager.setDriver(currentDriverSession);
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (ConfigFileManager.configFileMap.get("IOS_APP_PATH") != null
                    && iosDeviceConfiguration.checkiOSDevice()) {
                    currentDriverSession = new IOSDriver<>(appiumServerManager.getAppiumUrl(),
                        iosCaps.orElse(desiredCapabilityManager.iosNative()));
                    AppiumDriverManager.setDriver(currentDriverSession);

                } else if (!iosDeviceConfiguration.checkiOSDevice()) {
                    currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                        androidCaps.orElse(desiredCapabilityManager.androidNative()));
                    AppiumDriverManager.setDriver(currentDriverSession);
                }
            } else {
                currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                    androidCaps
                        .orElse(desiredCapabilityManager.androidNative()));
                AppiumDriverManager.setDriver(currentDriverSession);
            }
        }
    }

}
