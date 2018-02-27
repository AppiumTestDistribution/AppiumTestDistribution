package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.DesiredCapabilityBuilder;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver
            = new ThreadLocal<>();
    private IOSDeviceConfiguration iosDeviceConfiguration;
    private DesiredCapabilityBuilder desiredCapabilityBuilder;
    private ConfigFileManager prop;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private DevicesByHost devicesByHost;

    public AppiumDriverManager() throws Exception {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
        prop = ConfigFileManager.getInstance();
        devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    private AppiumDriver<MobileElement> getMobileAndroidElementAppiumDriver(
            Optional<DesiredCapabilities> androidCaps) throws IOException {
        AppiumDriver<MobileElement> currentDriverSession = null;
        DesiredCapabilities desiredCapabilities = androidCaps.get();
        String remoteWDHubIP = getRemoteWDHubIP(desiredCapabilities);
        currentDriverSession = new AndroidDriver(new URL(remoteWDHubIP),
                desiredCapabilities);
        LOGGER.info("Session Created ---- "
                + currentDriverSession.getSessionId()
                + "---" + currentDriverSession.getSessionDetail("udid"));
        return currentDriverSession;
    }

    private AppiumDriver<MobileElement> getMobileiOSElementAppiumDriver(
            Optional<DesiredCapabilities> iOSCaps)
            throws IOException, InterruptedException {
        AppiumDriver<MobileElement> currentDriverSession;
        DesiredCapabilities desiredCapabilities = iOSCaps.get();
        String remoteWDHubIP = getRemoteWDHubIP(desiredCapabilities);
        System.out.println(remoteWDHubIP);
        currentDriverSession = new AppiumDriver<>(new URL(remoteWDHubIP),
                        desiredCapabilities);
        LOGGER.info("Session Created ---- "
                + currentDriverSession.getSessionId() + "---"
                + currentDriverSession.getSessionDetail("udid"));
        return currentDriverSession;
    }

    private String getRemoteWDHubIP(DesiredCapabilities desiredCapabilities) throws IOException {
        RemoteAppiumManager remoteAppiumManager = new RemoteAppiumManager();
        String appiumUrl = remoteAppiumManager.getRemoteWDHubIP(devicesByHost
                .getHostOfDevice(String.valueOf(desiredCapabilities
                        .getCapability("udid"))));
        return appiumUrl;
    }

    public void startAppiumDriverInstance(Optional<DesiredCapabilities> iosCaps,
                                          Optional<DesiredCapabilities> androidCaps)
            throws Exception {
        AppiumDriver<MobileElement> currentDriverSession;
        if (System.getProperty("os.name").toLowerCase().contains("mac")
                && System.getenv("Platform").equalsIgnoreCase("iOS")
                || System.getenv("Platform").equalsIgnoreCase("Both")) {
            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                currentDriverSession = getMobileiOSElementAppiumDriver(iosCaps);
                AppiumDriverManager.setDriver(currentDriverSession);
            } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
                currentDriverSession = getMobileAndroidElementAppiumDriver(androidCaps);
                AppiumDriverManager.setDriver(currentDriverSession);
            }
        } else {
            currentDriverSession = getMobileAndroidElementAppiumDriver(androidCaps);
            AppiumDriverManager.setDriver(currentDriverSession);
        }
    }

    // Should be used by Cucumber as well
    public void startAppiumDriverInstance()
            throws Exception {
        String userSpecifiedCaps;
        DesiredCapabilities iOS = null;
        DesiredCapabilities android = null;
        userSpecifiedCaps = getCapsPath();
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            android = getDesiredAndroidCapabilities(userSpecifiedCaps);
        } else {
            iOS = getDesiredIOSCapabilities(userSpecifiedCaps);
        }
        LOGGER.info("Caps generated---" + android + iOS);
        startAppiumDriverInstance(Optional.ofNullable(iOS), Optional.ofNullable(android));
    }

    public String getCapsPath() {
        String userSpecifiedCaps;
        if (prop.getProperty("CAPS") == null) {
            userSpecifiedCaps = System.getProperty("user.dir")
                    + "/caps/capabilities.json";
        } else {
            userSpecifiedCaps = prop.getProperty("CAPS");
        }
        return userSpecifiedCaps;
    }

    public DesiredCapabilities getDesiredIOSCapabilities(String userSpecifiediOSCaps)
            throws Exception {

        if (new File(userSpecifiediOSCaps).exists()) {
            String iOSJsonFilePath = userSpecifiediOSCaps;
            Path path = FileSystems.getDefault().getPath(iOSJsonFilePath.toString());
            if (!path.getParent().isAbsolute()) {
                iOSJsonFilePath = path.normalize()
                        .toAbsolutePath().toString();
            }
            desiredCapabilityBuilder
                    .buildDesiredCapability("iOS", iOSJsonFilePath);
            DesiredCapabilities iOS = DesiredCapabilityBuilder.getDesiredCapability();
            return iOS;
        } else {
            System.out.println("Capability file not found");
            return null;
        }
    }

    public DesiredCapabilities getDesiredAndroidCapabilities(String userSpecifiedAndroidCaps)
            throws Exception {

        if (new File(userSpecifiedAndroidCaps).exists()) {
            String androidJsonFilePath = userSpecifiedAndroidCaps;
            Path path = FileSystems.getDefault().getPath(androidJsonFilePath.toString());
            if (!path.getParent().isAbsolute()) {
                androidJsonFilePath = path.normalize()
                        .toAbsolutePath().toString();
            }
            desiredCapabilityBuilder
                    .buildDesiredCapability("android", androidJsonFilePath);
            DesiredCapabilities android = DesiredCapabilityBuilder.getDesiredCapability();
            return android;
        } else {
            System.out.println("Capability file not found");
            return null;
        }

    }

    public void stopAppiumDriver() throws IOException, InterruptedException {
        if (AppiumDeviceManager.getAppiumDevice().getDevice().getUdid().length()
                == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            iosDeviceConfiguration.destroyIOSWebKitProxy();
        }
        if (AppiumDriverManager.getDriver() != null
                && AppiumDriverManager.getDriver().getSessionId() != null) {
            LOGGER.info("Session Deleting ---- "
                    + AppiumDriverManager.getDriver().getSessionId() + "---"
                    + AppiumDriverManager.getDriver().getSessionDetail("udid"));
            AppiumDriverManager.getDriver().quit();
        }
    }

}
