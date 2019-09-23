package com.appium.manager;

import com.appium.capabilities.DesiredCapabilityBuilder;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.CommandPrompt;
import com.appium.utils.ConfigFileManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver
        = new ThreadLocal<>();
    private DesiredCapabilityBuilder desiredCapabilityBuilder;
    private ConfigFileManager prop;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public AppiumDriverManager() {
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
        prop = ConfigFileManager.getInstance();
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    private AppiumDriver<MobileElement> initialiseDriver(
        Optional<DesiredCapabilities> capabilities)
        throws Exception {
        AppiumDriver currentDriverSession;
        DesiredCapabilities desiredCapabilities = capabilities.get();
        LOGGER.info("Capabilities: " + desiredCapabilities.toString());
        String isChromDriverPath = (String) desiredCapabilities.getCapability(
                AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE);
        boolean isPlatformAndroid = AppiumDeviceManager.getMobilePlatform().name()
                .equalsIgnoreCase("android");
        if (isPlatformAndroid && (null == isChromDriverPath)) {
            String udid = (String) desiredCapabilities.getCapability("udid");
            String pathForChromDriverForDevice = getPathForChromeDriver(udid);
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
                    pathForChromDriverForDevice);
        }
        String remoteWDHubIP = getRemoteWDHubIP();
        if (!AppiumDeviceManager.getAppiumDevice().getDevice().isCloud()
            && AppiumDeviceManager.getMobilePlatform().name().equalsIgnoreCase("iOS")) {
            currentDriverSession = new IOSDriver(new URL(remoteWDHubIP),
                desiredCapabilities);
            LOGGER.info("Session Created for iOS ---- "
                + currentDriverSession.getSessionId() + "---"
                + currentDriverSession.getSessionDetail("udid"));
        } else if (!AppiumDeviceManager.getAppiumDevice().getDevice().isCloud()
                && isPlatformAndroid) {
            currentDriverSession = new AndroidDriver(new URL(remoteWDHubIP),
                desiredCapabilities);
            LOGGER.info("Session Created for Android ---- "
                + currentDriverSession.getSessionId() + "---"
                + currentDriverSession.getSessionDetail("udid"));
        } else {
            currentDriverSession = new AppiumDriver<>(new URL(remoteWDHubIP),
                desiredCapabilities);
            LOGGER.info("Session Created ---- "
                + currentDriverSession.getSessionId() + "---"
                + currentDriverSession.getRemoteAddress().getHost() + "---"
                + currentDriverSession.getSessionDetail("udid"));

        }

        return currentDriverSession;
    }

    private String getPathForChromeDriver(String id) throws IOException {
        int[] versionNamesArr = getChromeVersionsFor(id);
        int highestChromeVersion = Arrays.stream(versionNamesArr).max().getAsInt();
        String message = "ChromeDriver for Chrome version " + highestChromeVersion
                + "on device: " + id;
        LOGGER.info(message);
        WebDriverManager.chromedriver().version(String.valueOf(highestChromeVersion)).setup();
        return WebDriverManager.chromedriver().getBinaryPath();
    }

    private int[] getChromeVersionsFor(String id) throws IOException {
        CommandPrompt cmd = new CommandPrompt();
        String resultStdOut = cmd.runCommandThruProcess("adb -s " + id
                + " shell dumpsys package com.android.chrome | grep versionName");
        int[] versionNamesArr = {};
        if (resultStdOut.contains("versionName=")) {
            String[] foundVersions = resultStdOut.split("\n");
            for (String foundVersion : foundVersions) {
                String version = foundVersion.split("=")[1].split("\\.")[0];
                String format = String.format("Found Chrome version - '%s' on - '%s'", version, id);
                LOGGER.info(format);
                versionNamesArr = ArrayUtils.add(versionNamesArr, Integer.parseInt(version));
            }
        } else {
            LOGGER.info(String.format("Chrome not found on device - '%s'", id));
        }
        return versionNamesArr;
    }

    private String getRemoteWDHubIP() throws Exception {
        String hostName = AppiumDeviceManager.getAppiumDevice().getHostName();
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(hostName);
        return appiumManager.getRemoteWDHubIP(hostName);
    }

    private void startAppiumDriverInstance(Optional<DesiredCapabilities> desiredCapabilities)
        throws Exception {
        AppiumDriver<MobileElement> currentDriverSession;
        currentDriverSession = initialiseDriver(desiredCapabilities);
        AppiumDriverManager.setDriver(currentDriverSession);
    }

    // Should be used by Cucumber as well
    public void startAppiumDriverInstance() throws Exception {
        String userSpecifiedCaps;
        DesiredCapabilities desiredCapabilities;
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

    private DesiredCapabilities buildDesiredCapabilities(String capabilityPath)
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
            return DesiredCapabilityBuilder.getDesiredCapability();
        } else {
            throw new RuntimeException("Capability file not found");
        }
    }

    protected void stopAppiumDriver() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        String command;
        if (AppiumDeviceManager.getAppiumDevice().getDevice().getUdid().length()
            == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            String hostName = AppiumDeviceManager.getAppiumDevice().getHostName();
            AppiumManagerFactory.getAppiumManager(hostName).destoryIOSWebKitProxy(hostName);
        }
        if (AppiumDeviceManager.getAppiumDevice().getChromeDriverPort() > 0) {
            if (OS.contains("mac")) {
                command = "kill -9 $(lsof -ti tcp:"
                    + AppiumDeviceManager.getAppiumDevice().getChromeDriverPort() + ")";
                new CommandPrompt().runCommand(command);
            }
            AppiumDeviceManager.getAppiumDevice().setChromeDriverPort(0);
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
