package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.DesiredCapabilityBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver
            = new ThreadLocal<>();
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private AppiumServerManager appiumServerManager;
    private DesiredCapabilityManager desiredCapabilityManager;
    private DesiredCapabilityBuilder desiredCapabilityBuilder;
    private ConfigFileManager prop;

    public AppiumDriverManager() throws Exception {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        desiredCapabilityManager = new DesiredCapabilityManager();
        appiumServerManager = new AppiumServerManager();
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
        prop = ConfigFileManager.getInstance();
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    public void startAppiumDriver(Optional<DesiredCapabilities> iosCaps,
                                  Optional<DesiredCapabilities> androidCaps,
                                  String userSpecifiedAndroidCaps,
                                  String userSpecifiediOSCaps)
            throws Exception {
        AppiumDriver<MobileElement> currentDriverSession;
        if (ConfigFileManager.configFileMap.get("APP_TYPE").equalsIgnoreCase("web")) {
            currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                    desiredCapabilityManager.androidWeb());
            AppiumDriverManager.setDriver(currentDriverSession);
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")
                    && System.getenv("Platform").equalsIgnoreCase("iOS")
                    || System.getenv("Platform").equalsIgnoreCase("Both")) {
                if (iosDeviceConfiguration.deviceUDIDiOS
                        .contains(DeviceManager.getDeviceUDID())) {
                    currentDriverSession = new IOSDriver<>(appiumServerManager.getAppiumUrl(),
                            iosCaps.orElse(desiredCapabilityManager
                                    .iosNative(userSpecifiediOSCaps)));
                    AppiumDriverManager.setDriver(currentDriverSession);

                } else if (!iosDeviceConfiguration.deviceUDIDiOS
                        .contains(DeviceManager.getDeviceUDID())) {
                    currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                            androidCaps.orElse(desiredCapabilityManager
                                    .androidNative(userSpecifiedAndroidCaps)));
                    AppiumDriverManager.setDriver(currentDriverSession);
                }
            } else {
                currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                        androidCaps
                                .orElse(desiredCapabilityManager
                                        .androidNative(userSpecifiedAndroidCaps)));
                AppiumDriverManager.setDriver(currentDriverSession);
            }
        }
    }

    // Should be used by Cucumber as well
    public void startAppiumDriver()
            throws Exception {
        DesiredCapabilities iOS = null;
        DesiredCapabilities android = null;
        String userSpecifiedAndroidCaps = System.getProperty("user.dir")
                + "/caps/android.json";
        String userSpecifiediOSCaps = System.getProperty("user.dir")
                + "/caps/iOS.json";
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            android = getDesiredAndroidCapabilities(android, userSpecifiedAndroidCaps);
        } else {
            iOS = getDesiredIOSCapabilities(iOS, userSpecifiediOSCaps);
        }
        System.out.println("Caps generated" + android + iOS);
        startAppiumDriver(Optional.ofNullable(iOS), Optional.ofNullable(android),
                userSpecifiedAndroidCaps,
                userSpecifiediOSCaps);
        Thread.sleep(3000);
    }

    public DesiredCapabilities getDesiredIOSCapabilities(DesiredCapabilities iOS,
                                                         String userSpecifiediOSCaps)
            throws Exception {
        String iOSJsonFilePath;
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            if (prop.getProperty("IOS_CAPS") != null) {
                iOSJsonFilePath = prop.getProperty("IOS_CAPS");
                Path path = FileSystems.getDefault().getPath(iOSJsonFilePath.toString());
                if (!path.getParent().isAbsolute()) {
                    iOSJsonFilePath = path.normalize()
                            .toAbsolutePath().toString();
                }
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

    public DesiredCapabilities getDesiredAndroidCapabilities(DesiredCapabilities android,
                                                             String userSpecifiedAndroidCaps)
            throws Exception {
        String androidJsonFilePath;
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            if (prop.getProperty("ANDROID_CAPS") != null) {
                androidJsonFilePath = prop.getProperty("ANDROID_CAPS");
                Path path = FileSystems.getDefault().getPath(androidJsonFilePath.toString());
                if (!path.getParent().isAbsolute()) {
                    androidJsonFilePath = path.normalize()
                            .toAbsolutePath().toString();
                }
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
        }
        return android;
    }

    public void stopAppiumDriver() {
        AppiumDriverManager.getDriver().quit();
    }

}
