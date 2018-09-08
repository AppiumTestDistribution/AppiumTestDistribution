package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.entities.ServerArgs;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.CommandPrompt;
import com.appium.utils.DesiredCapabilityBuilder;
import com.appium.utils.JsonParser;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
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
    private Logger logger;

    public AppiumDriverManager() throws Exception {
        iosDeviceConfiguration = new IOSDeviceConfiguration();
        desiredCapabilityBuilder = new DesiredCapabilityBuilder();
        prop = ConfigFileManager.getInstance();
    }

    public static AppiumDriver getDriver() {
        return appiumDriver.get();
    }

    protected static void setDriver(AppiumDriver driver) {
        appiumDriver.set(driver);
    }

    private AppiumDriver<MobileElement> getMobileAndroidElementAppiumDriver(
            Optional<DesiredCapabilities> androidCaps) throws Exception {
        AppiumDriver<MobileElement> currentDriverSession = null;
        DesiredCapabilities desiredCapabilities = androidCaps.get();
        String remoteWDHubIP = getRemoteWDHubIP();
        currentDriverSession = new AndroidDriver(new URL(remoteWDHubIP),
                desiredCapabilities);
        LOGGER.info("Session Created ---- "
                + currentDriverSession.getSessionId()
                + "---" + currentDriverSession.getSessionDetail("udid"));
        return currentDriverSession;
    }

    private AppiumDriver<MobileElement> getMobileiOSElementAppiumDriver(
            Optional<DesiredCapabilities> iOSCaps)
            throws Exception {
        AppiumDriver<MobileElement> currentDriverSession;
        DesiredCapabilities desiredCapabilities = iOSCaps.get();
        String remoteWDHubIP = getRemoteWDHubIP();
        System.out.println(remoteWDHubIP);
        currentDriverSession = new AppiumDriver<>(new URL(remoteWDHubIP),
                desiredCapabilities);
        LOGGER.info("Session Created ---- "
                + currentDriverSession.getSessionId() + "---"
                + currentDriverSession.getSessionDetail("udid"));
        return currentDriverSession;
    }

    private String getRemoteWDHubIP() throws Exception {
        String hostName = AppiumDeviceManager.getAppiumDevice().getHostName();
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(hostName);
        return appiumManager.getRemoteWDHubIP(hostName);
    }

    private void startAppiumDriverInstance(Optional<DesiredCapabilities> iosCaps,
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
    public void startAppiumDriverInstance() throws Exception {
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

    private String getCapsPath() {
        String userSpecifiedCaps;
        if (prop.getProperty("CAPS") == null) {
            userSpecifiedCaps = System.getProperty("user.dir")
                    + "/caps/capabilities.json";
        } else {
            userSpecifiedCaps = prop.getProperty("CAPS");
        }
        return userSpecifiedCaps;
    }

    private DesiredCapabilities getDesiredIOSCapabilities(String userSpecifiediOSCaps)
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

    private DesiredCapabilities getDesiredAndroidCapabilities(String userSpecifiedAndroidCaps)
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
            return DesiredCapabilityBuilder.getDesiredCapability();
        } else {
            System.out.println("Capability file not found");
            return null;
        }
    }

    public void stopAppiumDriver() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        String command;
        if (AppiumDeviceManager.getAppiumDevice().getDevice().getUdid().length()
                == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            String hostName = AppiumDeviceManager.getAppiumDevice().getHostName();
            AppiumManagerFactory.getAppiumManager(hostName).destoryIOSWebKitProxy(hostName);
        }
        if (AppiumDeviceManager.getAppiumDevice().getChromeDriverPort() > 0) {
            if (OS.indexOf("mac") >= 0) {
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

    public JSONObject getDesiredServerCapabilities ()
            throws Exception {
        String userSpecifiedServerCaps = getCapsPath();
        if (new File(userSpecifiedServerCaps).exists()) {
            String serverJsonFilePath = userSpecifiedServerCaps;
            Path path = FileSystems.getDefault().getPath(serverJsonFilePath);
            if (!path.getParent().isAbsolute()) {
                serverJsonFilePath = path.normalize()
                        .toAbsolutePath().toString();
            }
            return new JsonParser(serverJsonFilePath).getObjectFromJSON()
                    .getJSONObject("server");
        } else {
            System.out.println("Capability file not found");
            return null;
        }
    }

    public GeneralServerFlag getServerArgument ( String capability ) {

        switch (capability) {
            case "SESSION_OVERRIDE":
                return ServerArgs.SESSION_OVERRIDE.getArgument();

            case "PRE_LAUNCH":
                return ServerArgs.PRE_LAUNCH.getArgument();

            case "LOG_LEVEL":
                return ServerArgs.LOG_LEVEL.getArgument();

            case "RELAXED_SECURITY":
                return ServerArgs.RELAXED_SECURITY.getArgument();

            case "CALLBACK_ADDRESS":
                return ServerArgs.CALLBACK_ADDRESS.getArgument();

            case "CALLBACK_PORT":
                return ServerArgs.CALLBACK_PORT.getArgument();

            case "LOG_TIMESTAMP":
                return ServerArgs.LOG_TIMESTAMP.getArgument();

            case "LOCAL_TIMEZONE":
                return ServerArgs.LOCAL_TIMEZONE.getArgument();

            case "LOG_NO_COLORS":
                return ServerArgs.LOG_NO_COLORS.getArgument();

            case "WEB_HOOK":
                return ServerArgs.WEB_HOOK.getArgument();

            case "CONFIGURATION_FILE":
                return ServerArgs.CONFIGURATION_FILE.getArgument();

            case "ROBOT_ADDRESS":
                return ServerArgs.ROBOT_ADDRESS.getArgument();

            case "ROBOT_PORT":
                return ServerArgs.ROBOT_PORT.getArgument();

            case "SHOW_CONFIG":
                return ServerArgs.SHOW_CONFIG.getArgument();

            case "NO_PERMS_CHECKS":
                return ServerArgs.NO_PERMS_CHECKS.getArgument();

            case "STRICT_CAPS":
                return ServerArgs.STRICT_CAPS.getArgument();

            case "TEMP_DIRECTORY":
                return ServerArgs.TEMP_DIRECTORY.getArgument();

            case "DEBUG_LOG_SPACING":
                return ServerArgs.DEBUG_LOG_SPACING.getArgument();

            case "ASYNC_TRACE":
                return ServerArgs.ASYNC_TRACE.getArgument();

            case "ENABLE_HEAP_DUMP":
                return ServerArgs.ENABLE_HEAP_DUMP.getArgument();

            default:
                return null;
        }
    }
}
