package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.CommandPrompt;
import com.appium.utils.ConfigFileManager;
import com.appium.capabilities.DesiredCapabilityBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AppiumDriverManager {
    private static ThreadLocal<List<AppiumDriver<MobileElement>>> appiumDrivers
        = new ThreadLocal<>();

    private static ThreadLocal<AppiumDriver<MobileElement>> appiumDriver
        = new ThreadLocal<AppiumDriver<MobileElement>>();

    private DesiredCapabilityBuilder desiredCapabilityBuilder;
    private ConfigFileManager prop;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public AppiumDriverManager() {
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
        prop = ConfigFileManager.getInstance();
    }

    public static AppiumDriver<MobileElement> getDriver() {
        return appiumDrivers.get().get(0);
    }

    public static List<AppiumDriver<MobileElement>> getDrivers() {
        return appiumDrivers.get();
    }

    protected static void setDrivers(List<AppiumDriver<MobileElement>> driver) {
        appiumDrivers.set(driver);
    }

    private List<AppiumDriver<MobileElement>> initialiseDriver(
        Optional<List<DesiredCapabilities>> capabilities) {
        List<AppiumDriver<MobileElement>> driverSessions = new ArrayList<>();
        List<DesiredCapabilities> desiredCapabilities = capabilities.get();
        LOGGER.info("Capabilities: " + desiredCapabilities.toString());
        List<AppiumDevice> appiumDevices = AppiumDeviceManager.getAppiumDevices();

        appiumDevices.parallelStream().forEach(appiumDevice -> {
            DesiredCapabilities dc = desiredCapabilities.stream().filter(cap ->
                cap.getCapability("udid")
                    .equals(appiumDevice.getDevice().getUdid())).findAny()
                .orElse(null);
            String remoteWDHubIP = null;
            try {
                remoteWDHubIP = getRemoteWDHubIP(appiumDevice);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AppiumDriver driverSession = null;
            if (!appiumDevice.getDevice().isCloud()
                && appiumDevice.getDevice().getOs().equalsIgnoreCase("iOS")) {
                try {
                    driverSession = new IOSDriver(new URL(remoteWDHubIP),
                        dc);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Session Created for iOS ---- "
                    + driverSession.getSessionId() + "---"
                    + driverSession.getSessionDetail("udid"));
            } else if (!appiumDevice.getDevice().isCloud()
                && appiumDevice.getDevice().getOs().equalsIgnoreCase("android")) {
                try {
                    driverSession = new AndroidDriver(new URL(remoteWDHubIP),
                        dc);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Session Created for Android ---- "
                    + driverSession.getSessionId() + "---"
                    + driverSession.getSessionDetail("udid"));
            } else {
                try {
                    driverSession = new AppiumDriver(new URL(remoteWDHubIP),
                        dc);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Session Created ---- "
                    + driverSession.getSessionId() + "---"
                    + driverSession.getRemoteAddress().getHost() + "---"
                    + driverSession.getSessionDetail("udid"));

            }
            driverSessions.add(driverSession);
        });
        return driverSessions;
    }

    private String getRemoteWDHubIP(AppiumDevice appiumDevice) throws Exception {
        String hostName = appiumDevice.getHostName();
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(hostName);
        return appiumManager.getRemoteWDHubIP(hostName);
    }

    private void startAppiumDriverInstance(Optional<List<DesiredCapabilities>> desiredCapabilities)
        throws Exception {
        List<AppiumDriver<MobileElement>> currentDriverSession;
        // TODO use parallel stream to start session
        currentDriverSession = initialiseDriver(desiredCapabilities);
        AppiumDriverManager.setDrivers(currentDriverSession);
    }

    // Should be used by Cucumber as well
    public void startAppiumDriverInstance() throws Exception {
        String userSpecifiedCaps;
        List<DesiredCapabilities> desiredCapabilities;
        userSpecifiedCaps = getCapsPath();
        desiredCapabilities = buildDesiredCapabilities(userSpecifiedCaps);
        startAppiumDriverInstance(Optional.ofNullable(desiredCapabilities));
    }

    private String getCapsPath() {
        if (prop.getProperty("CAPS") == null) {
            return System.getProperty("user.dir")
                + "/caps/capabilities.json";
        } else {
            return prop.getProperty("CAPS");
        }
    }

    private List<DesiredCapabilities> buildDesiredCapabilities(String capabilityPath)
        throws Exception {
        String capabilities = capabilityPath;
        if (new File(capabilityPath).exists()) {
            Path path = FileSystems.getDefault().getPath(capabilityPath);
            if (!path.getParent().isAbsolute()) {
                capabilities = path.normalize()
                    .toAbsolutePath().toString();
            }
            desiredCapabilityBuilder
                .buildDesiredCapability(capabilities);
            return DesiredCapabilityBuilder.getDesiredCapabilities();
        } else {
            throw new RuntimeException("Capability file not found");
        }
    }

    protected void stopAppiumDriver() {
        String OS = System.getProperty("os.name").toLowerCase();
        AtomicReference<String> command = null;
        List<AppiumDevice> appiumDevices = AppiumDeviceManager.getAppiumDevices();

        for (AppiumDevice appiumDevice : appiumDevices) {
            if (appiumDevice.getDevice().getUdid().length()
                == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                String hostName = appiumDevice.getHostName();
                try {
                    AppiumManagerFactory.getAppiumManager(hostName)
                        .destoryIOSWebKitProxy(hostName, appiumDevice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (appiumDevice.getChromeDriverPort() > 0) {
                if (OS.contains("mac")) {
                    command.set("kill -9 $(lsof -ti tcp:"
                        + appiumDevice.getChromeDriverPort() + ")");
                    try {
                        new CommandPrompt().runCommand(command.get());
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
                appiumDevice.setChromeDriverPort(0);
            }
            List<AppiumDriver<MobileElement>> drivers = AppiumDriverManager.getDrivers();
            drivers.parallelStream().forEach(driver -> {
                if (driver.getSessionId() != null) {
                    LOGGER.info("Session Deleting ---- "
                        + driver.getSessionId() + "---"
                        + driver.getSessionDetail("udid"));
                    driver.quit();
                }
            });
        }
    }
}
