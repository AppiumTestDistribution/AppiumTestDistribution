package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.DesiredCapabilityBuilder;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestClass;

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
    public void startAppiumServerInParallel(String methodName,String className)
            throws Exception {
        DesiredCapabilities iOS = null;
        DesiredCapabilities android = null;
        // Check if json file does not exists and pick the default caps
        DesiredCapabilities desiredCapabilities = desiredCapabilityBuilder
                .buildDesiredCapability(System.getProperty("user.dir")
                        + "/caps/android.json");
        // Check for web chrome appplication
        if (desiredCapabilities.getCapability("automationName")
                .equals("UIAutomator2")) {
            android = DesiredCapabilityBuilder.getDesiredCapability();
        } else {
            iOS = DesiredCapabilityBuilder.getDesiredCapability();
        }
        System.out.println("Caps generated" + android + iOS);
        startAppiumServerInParallel(methodName, className, iOS, android);
    }

    public void startAppiumServerInParallel(
            String methodName, String className,
            DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        startAppiumDriver(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        Thread.sleep(3000);
        startLogResults(methodName,className);
    }

    private void startLogResults(String methodName,String className) throws FileNotFoundException {
        testLogger.startLogging(methodName, className);
    }

}
