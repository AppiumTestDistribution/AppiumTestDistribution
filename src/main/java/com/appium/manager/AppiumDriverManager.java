package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.DesiredCapabilityBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver
            = new ThreadLocal<>();
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private AndroidDeviceConfiguration androidDeviceConfiguration;
    private AppiumServerManager appiumServerManager;
    private ConfigFileManager prop;
    private DesiredCapabilityManager desiredCapabilityManager;
    private DesiredCapabilityBuilder desiredCapabilityBuilder;
    private TestLogger testLogger;

    public AppiumDriverManager() throws IOException {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        androidDeviceConfiguration = new AndroidDeviceConfiguration();
        desiredCapabilityManager = new DesiredCapabilityManager();
        appiumServerManager = new AppiumServerManager();
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
        testLogger = new TestLogger();
        prop = ConfigFileManager.getInstance();
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    public void startAppiumDriver(Optional<DesiredCapabilities> iosCaps,
                                  Optional<DesiredCapabilities> androidCaps)
            throws Exception {
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

    // Should be used by Cucumber as well
    public void startAppiumServerInParallel(String methodName, String className)
            throws Exception {
        DesiredCapabilities iOS = null;
        DesiredCapabilities android = null;
        String userSpecifiedAndroidCaps = System.getProperty("user.dir")
                + "/caps/android.json";
        String userSpecifiediOSCaps = System.getProperty("user.dir")
                + "/caps/android.json";

        android = getDesiredAndroidCapabilities(android, userSpecifiedAndroidCaps);
        iOS = getDesiredIOSCapabilities(iOS, userSpecifiediOSCaps);
        System.out.println("Caps generated" + android + iOS);
        startAppiumServerInParallel(methodName, className, iOS, android);
    }

    public DesiredCapabilities getDesiredIOSCapabilities(DesiredCapabilities iOS, String userSpecifiediOSCaps) throws Exception {
        String iOSJsonFilePath;
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            if (prop.getProperty("IOS_CAPS") == null) {
                iOSJsonFilePath = prop.getProperty("IOS_CAPS");
                desiredCapabilityBuilder
                        .buildDesiredCapability(iOSJsonFilePath);
                iOS = DesiredCapabilityBuilder.getDesiredCapability();
            } else if (new File(userSpecifiediOSCaps).exists()) {
                iOSJsonFilePath = userSpecifiediOSCaps;
                desiredCapabilityBuilder
                        .buildDesiredCapability(iOSJsonFilePath);
                iOS = DesiredCapabilityBuilder.getDesiredCapability();
            }
        }
        return iOS;
    }

    public DesiredCapabilities getDesiredAndroidCapabilities(DesiredCapabilities android, String userSpecifiedAndroidCaps) throws Exception {
        String androidJsonFilePath;
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            if (prop.getProperty("ANDROID_CAPS") != null) {
                androidJsonFilePath = prop.getProperty("ANDROID_CAPS");
                System.out.println("Picking Caps from property file");
                desiredCapabilityBuilder
                        .buildDesiredCapability(androidJsonFilePath);
                android = DesiredCapabilityBuilder.getDesiredCapability();
            } else if (new File(userSpecifiedAndroidCaps).exists()) {
                System.out.println("Picking Caps from default path");
                androidJsonFilePath = userSpecifiedAndroidCaps;
                desiredCapabilityBuilder
                        .buildDesiredCapability(androidJsonFilePath);
                android = DesiredCapabilityBuilder.getDesiredCapability();
            }
            //Check for web chrome appplication
        }
        return android;
    }

    public void startAppiumServerInParallel(
            String methodName, String className,
            DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        startAppiumDriver(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        Thread.sleep(3000);
        startLogResults(methodName, className);
    }

    private void startLogResults(String methodName, String className) throws FileNotFoundException {
        testLogger.startLogging(methodName, className);
    }

}
