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
import java.io.IOException;
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

    private AppiumDriver<MobileElement>
        getMobileAndroidElementAppiumDriver(Optional<DesiredCapabilities> androidCaps) {
        AppiumDriver<MobileElement> currentDriverSession;
        DesiredCapabilities desiredCapabilities = androidCaps.isPresent()
                ? androidCaps.get() : desiredCapabilityManager.androidNative();

        currentDriverSession = new AndroidDriver<>(appiumServerManager.getAppiumUrl(),
                desiredCapabilities);
        return currentDriverSession;
    }

    private AppiumDriver<MobileElement>
        getMobileiOSElementAppiumDriver(Optional<DesiredCapabilities> iOSCaps)
            throws IOException, InterruptedException {
        AppiumDriver<MobileElement> currentDriverSession;
        DesiredCapabilities desiredCapabilities = iOSCaps.isPresent()
                ? iOSCaps.get() : desiredCapabilityManager.iosNative();
        currentDriverSession = new IOSDriver<>(appiumServerManager.getAppiumUrl(),
                desiredCapabilities);
        return currentDriverSession;
    }

    public void startAppiumDriverInstance(Optional<DesiredCapabilities> iosCaps,
                                          Optional<DesiredCapabilities> androidCaps)
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
                        .contains(AppiumDeviceManager.getDeviceUDID())) {
                    currentDriverSession = getMobileiOSElementAppiumDriver(iosCaps);
                    AppiumDriverManager.setDriver(currentDriverSession);

                } else if (!iosDeviceConfiguration.deviceUDIDiOS
                        .contains(AppiumDeviceManager.getDeviceUDID())) {
                    currentDriverSession = getMobileAndroidElementAppiumDriver(androidCaps);
                    AppiumDriverManager.setDriver(currentDriverSession);
                }
            } else {
                currentDriverSession = getMobileAndroidElementAppiumDriver(androidCaps);
                AppiumDriverManager.setDriver(currentDriverSession);
            }
        }
    }

    // Should be used by Cucumber as well
    public void startAppiumDriverInstance()
            throws Exception {
        DesiredCapabilities iOS = null;
        DesiredCapabilities android = null;
        String userSpecifiedCaps = System.getProperty("user.dir")
                + "/caps/capabilities.json";
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            android = getDesiredAndroidCapabilities(android, userSpecifiedCaps);
        } else {
            iOS = getDesiredIOSCapabilities(iOS, userSpecifiedCaps);
        }
        System.out.println("Caps generated" + android + iOS);
        startAppiumDriverInstance(Optional.ofNullable(iOS), Optional.ofNullable(android));
    }

    public DesiredCapabilities getDesiredIOSCapabilities(DesiredCapabilities iOS,
                                                         String userSpecifiediOSCaps)
            throws Exception {
        String iOSJsonFilePath;
        if (prop.getProperty("CAPS") != null) {
            iOSJsonFilePath = prop.getProperty("CAPS");
            Path path = FileSystems.getDefault().getPath(iOSJsonFilePath.toString());
            if (!path.getParent().isAbsolute()) {
                iOSJsonFilePath = path.normalize()
                        .toAbsolutePath().toString();
            }
            desiredCapabilityBuilder
                    .buildDesiredCapability("iOS", iOSJsonFilePath);
            iOS = DesiredCapabilityBuilder.getDesiredCapability();
        } else if (new File(userSpecifiediOSCaps).exists()) {
            iOSJsonFilePath = userSpecifiediOSCaps;
            desiredCapabilityBuilder
                    .buildDesiredCapability("iOS", iOSJsonFilePath);
            iOS = DesiredCapabilityBuilder.getDesiredCapability();
        }
        return iOS;
    }

    public DesiredCapabilities getDesiredAndroidCapabilities(DesiredCapabilities android,
                                                             String userSpecifiedAndroidCaps)
            throws Exception {
        String androidJsonFilePath;
        if (prop.getProperty("CAPS") != null) {
            androidJsonFilePath = prop.getProperty("CAPS");
            Path path = FileSystems.getDefault().getPath(androidJsonFilePath.toString());
            if (!path.getParent().isAbsolute()) {
                androidJsonFilePath = path.normalize()
                        .toAbsolutePath().toString();
            }
            desiredCapabilityBuilder
                    .buildDesiredCapability("android", androidJsonFilePath);
            android = DesiredCapabilityBuilder.getDesiredCapability();
        } else if (new File(userSpecifiedAndroidCaps).exists()) {
            androidJsonFilePath = userSpecifiedAndroidCaps;
            desiredCapabilityBuilder
                    .buildDesiredCapability("android", androidJsonFilePath);
            android = DesiredCapabilityBuilder.getDesiredCapability();
        }

        return android;
    }

    public void stopAppiumDriver() throws IOException, InterruptedException {
        if (AppiumDeviceManager.getDeviceUDID().length()
                == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            iosDeviceConfiguration.destroyIOSWebKitProxy();
        }
        if (AppiumDriverManager.getDriver() != null) {
            AppiumDriverManager.getDriver().quit();
        }
    }

}
