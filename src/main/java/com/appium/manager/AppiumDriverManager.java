package com.appium.manager;

import com.appium.capabilities.DesiredCapabilityBuilder;
import com.appium.entities.MobilePlatform;
import com.appium.utils.CommandPrompt;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;

import static com.appium.manager.AppiumDeviceManager.getMobilePlatform;
import static com.appium.utils.ConfigFileManager.CAPS;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();
    private static final Logger LOGGER = Logger.getLogger(AppiumDriverManager.class.getName());

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        LOGGER.info("AppiumDriverManager: Created AppiumDriver with capabilities: ");
        Capabilities capabilities = driver.getCapabilities();
        capabilities.getCapabilityNames().forEach(
            key -> LOGGER.info("\t" + key + ":: " + capabilities.getCapability(key)));
        appiumDriver.set(driver);
    }


    private AppiumDriver initialiseDriver(DesiredCapabilities desiredCapabilities)
            throws Exception {
        LOGGER.info("Initialise Driver with Capabilities: ");
        desiredCapabilities.getCapabilityNames().forEach(
            key -> LOGGER.info("\t" + key + ":: " + desiredCapabilities.getCapability(key)));
        LocalAppiumManager localAppiumManager = new LocalAppiumManager();
        String remoteWDHubIP = localAppiumManager.getRemoteWDHubIP("127.0.0.1");
        return createAppiumDriver(desiredCapabilities, remoteWDHubIP);
    }

    @NotNull
    private AppiumDriver createAppiumDriver(DesiredCapabilities desiredCapabilities,
                                             String remoteWDHubIP)
            throws IOException {
        AppiumDriver currentDriverSession;
        MobilePlatform mobilePlatform = getMobilePlatform();
        switch (mobilePlatform) {
            case IOS:
                currentDriverSession = new IOSDriver(new URL(remoteWDHubIP),
                        desiredCapabilities);
                break;
            case ANDROID:
                currentDriverSession = new AndroidDriver(new URL(remoteWDHubIP),
                        desiredCapabilities);
                break;
            case WINDOWS:
                currentDriverSession = new WindowsDriver(new URL(remoteWDHubIP),
                        desiredCapabilities);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mobilePlatform);
        }
        LOGGER.info("Session Created for "
                + AppiumDeviceManager.getMobilePlatform().name()
                + "\n\tSession Id: " + currentDriverSession.getSessionId()
                + "\n\tUDID: " + currentDriverSession.getCapabilities().getCapability("udid"));
        return currentDriverSession;
    }

    private String getRemoteWDHubIP() throws Exception {
        String hostName = AppiumDeviceManager.getAppiumDevice().getHostName();
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(hostName);
        String remoteWDHubIP = appiumManager.getRemoteWDHubIP(hostName);
        LOGGER.info("getRemoteWDHubIP: " + remoteWDHubIP);
        return remoteWDHubIP;
    }


    private AppiumDriver startAppiumDriverInstance(
            Optional<DesiredCapabilities> desiredCapabilities)
        throws Exception {
        LOGGER.info("startAppiumDriverInstance");
        AppiumDriver currentDriverSession =
                initialiseDriver(desiredCapabilities.get());
        AppiumDriverManager.setDriver(currentDriverSession);
        return currentDriverSession;
    }

    // Should be used by Cucumber as well
    public AppiumDriver startAppiumDriverInstance(String testMethodName)
            throws Exception {
        return startAppiumDriverInstance(testMethodName, CAPS.get());
    }

    // Should be used by Cucumber as well
    public AppiumDriver startAppiumDriverInstance(String testMethodName,
                                                                 String capabilityFilePath)
            throws Exception {
        LOGGER.info(String.format("startAppiumDriverInstance for %s using capability file: %s",
                testMethodName, capabilityFilePath));
        return startAppiumDriverInstance(
                Optional.ofNullable(buildDesiredCapabilities(testMethodName, capabilityFilePath)));
    }

    private DesiredCapabilities buildDesiredCapabilities(String testMethodName,
                                                         String capabilityFilePath)
        throws Exception {
        if (new File(capabilityFilePath).exists()) {
            return new DesiredCapabilityBuilder()
                .buildDesiredCapability(testMethodName, capabilityFilePath);
        } else {
            throw new RuntimeException("Capability file not found");
        }
    }

    public void stopAppiumDriver() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        String command;
        AppiumDevice appiumDevice = AppiumDeviceManager.getAppiumDevice();
        if (appiumDevice.getDevice().getOs().equalsIgnoreCase("iOS")
            && appiumDevice.getDevice().isDevice()) {
            String hostName = appiumDevice.getHostName();
            AppiumManagerFactory.getAppiumManager(hostName).destoryIOSWebKitProxy(hostName);
        }
        if (appiumDevice.getChromeDriverPort() > 0) {
            if (OS.contains("mac")) {
                command = "kill -9 $(lsof -ti tcp:"
                    + appiumDevice.getChromeDriverPort() + ")";
                new CommandPrompt().runCommand(command);
            }
            appiumDevice.setChromeDriverPort(0);
        }
        if (AppiumDriverManager.getDriver() != null
            && AppiumDriverManager.getDriver().getSessionId() != null) {
            LOGGER.info("Session Deleting ---- "
                + AppiumDriverManager.getDriver().getSessionId() + "---"
                + AppiumDriverManager.getDriver().getCapabilities().getCapability("udid"));
            AppiumDriverManager.getDriver().quit();
        }
    }
}