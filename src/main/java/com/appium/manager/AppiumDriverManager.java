package com.appium.manager;

import static com.appium.manager.AppiumDeviceManager.getMobilePlatform;
import static com.appium.manager.AppiumDeviceManager.isPlatform;
import static com.appium.utils.ConfigFileManager.CAPS;

import com.appium.entities.MobilePlatform;
import com.appium.utils.CommandPrompt;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

import static com.appium.utils.ConfigFileManager.CAPS;

public class AppiumDriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriver = new ThreadLocal<>();
    private DesiredCapabilityBuilder desiredCapabilityBuilder;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public AppiumDriverManager() {
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
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
        AppiumDriver currentDriverSession = null;
        DesiredCapabilities desiredCapabilities = capabilities.get();
        LOGGER.info("Capabilities: " + desiredCapabilities.toString());
        String remoteWDHubIP = getRemoteWDHubIP();
        if (isRunningInCloud()) {
            currentDriverSession = getRemoteAppiumDriver(
                    desiredCapabilities,
                    remoteWDHubIP);
        } else {
            currentDriverSession = getLocalAppiumDriver(
                    currentDriverSession,
                    desiredCapabilities,
                    remoteWDHubIP);
        }
        return currentDriverSession;
    }

    @NotNull
    private AppiumDriver getRemoteAppiumDriver(
            DesiredCapabilities desiredCapabilities,
            String remoteWDHubIP) throws MalformedURLException {
        AppiumDriver currentDriverSession;
        currentDriverSession = new AppiumDriver<>(new URL(remoteWDHubIP),
                desiredCapabilities);
        LOGGER.info("Remote AppiumDriver Session Created ---- "
                            + currentDriverSession.getSessionId() + "---"
                            + currentDriverSession.getRemoteAddress().getHost() + "---"
                            + currentDriverSession.getSessionDetail("udid"));
        return currentDriverSession;
    }

    @NotNull
    private AppiumDriver getLocalAppiumDriver(
            AppiumDriver currentDriverSession,
            DesiredCapabilities desiredCapabilities,
            String remoteWDHubIP)
            throws IOException {
        MobilePlatform mobilePlatform = getMobilePlatform();
        switch (mobilePlatform) {
            case IOS:
                currentDriverSession = new IOSDriver(new URL(remoteWDHubIP),
                        desiredCapabilities);
                break;
            case ANDROID:
                addChromeDriverPathIfChromeOnDevice(
                        desiredCapabilities,true);
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
                            + " ---- "
                            + currentDriverSession.getSessionId() + "---"
                            + currentDriverSession.getSessionDetail("udid"));
        return currentDriverSession;
    }

    private boolean isRunningInCloud() {
        return AppiumDeviceManager.getAppiumDevice().getDevice().isCloud();
    }

    private void addChromeDriverPathIfChromeOnDevice(DesiredCapabilities desiredCapabilities,
                                                     boolean isPlatformAndroid) throws IOException {
        String isChromDriverPath = (String) desiredCapabilities.getCapability(
                AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE);

        if (isPlatformAndroid && (null == isChromDriverPath)) {
            String udid = (String) desiredCapabilities.getCapability("udid");
            String pathForChromDriverForDevice = getPathForChromeDriver(udid);
            if (null != pathForChromDriverForDevice) {
                desiredCapabilities.setCapability(
                    AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE,
                    pathForChromDriverForDevice);
            }
        }
    }

    private String getPathForChromeDriver(String id) throws IOException {
        int[] versionNamesArr = getChromeVersionsFor(id);
        if (versionNamesArr.length > 0) {
            int highestChromeVersion = Arrays.stream(versionNamesArr).max().getAsInt();
            String message = "ChromeDriver for Chrome version " + highestChromeVersion
                + " on device: " + id;
            LOGGER.info(message);
            WebDriverManager.chromedriver()
                .version(String.valueOf(highestChromeVersion)).setup();
            return WebDriverManager.chromedriver().getBinaryPath();
        } else {
            return null;
        }
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
        AppiumDriver<MobileElement> currentDriverSession = initialiseDriver(desiredCapabilities);
        AppiumDriverManager.setDriver(currentDriverSession);
    }

    // Should be used by Cucumber as well
    public void startAppiumDriverInstance() throws Exception {
        startAppiumDriverInstance(Optional.ofNullable(buildDesiredCapabilities(CAPS.get())));
    }

    private DesiredCapabilities buildDesiredCapabilities(String capabilityFilePath)
        throws Exception {
        String absolutePathToCapabilities = capabilityFilePath;
        if (new File(capabilityFilePath).exists()) {
            Path path = FileSystems.getDefault().getPath(capabilityFilePath);
            if (!path.getParent().isAbsolute()) {
                absolutePathToCapabilities = path.normalize()
                    .toAbsolutePath().toString();
            }
            desiredCapabilityBuilder
                .buildDesiredCapability(absolutePathToCapabilities);
            return DesiredCapabilityBuilder.getDesiredCapability();
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
                + AppiumDriverManager.getDriver().getSessionDetail("udid"));
            AppiumDriverManager.getDriver().quit();
        }
    }
}