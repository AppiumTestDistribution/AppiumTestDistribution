package com.appium.capabilities;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.utils.ArtifactsUploader;
import com.appium.utils.AvailablePorts;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static com.appium.manager.AppiumDeviceManager.isPlatform;
import static com.appium.utils.ConfigFileManager.CAPS;
import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;

/**
 * Created by saikrisv on 20/05/17.
 */
public class DesiredCapabilityBuilder extends ArtifactsUploader {

    private static final Logger LOGGER = Logger.getLogger(DesiredCapabilityBuilder.class.getName());
    public static final String APP_PACKAGE = "APP_PACKAGE";
    private AvailablePorts availablePorts;

    public static ThreadLocal<DesiredCapabilities> desiredCapabilitiesThreadLocal
        = new ThreadLocal<>();

    public DesiredCapabilityBuilder() {
        super();
        availablePorts = new AvailablePorts();
    }

    public DesiredCapabilities buildDesiredCapability(String testMethodName,
                                                      String capabilityFilePath)
        throws Exception {
        int port = AppiumDeviceManager.getAppiumDevice().getPort();
        String platform = AppiumDeviceManager.getAppiumDevice().getDevice().getOs();
        boolean isCloud = AppiumDeviceManager.getAppiumDevice().getDevice().isCloud();
        DesiredCapabilities desiredCapabilities;
        if (isCloud) {
            desiredCapabilities = desiredCapabilityForCloud(
                    testMethodName,
                    platform,
                    capabilityFilePath);
        } else {
            desiredCapabilities = desiredCapabilityForLocalAndRemoteATD(
                    platform,
                    port,
                    capabilityFilePath);
        }
        return desiredCapabilities;
    }

    private DesiredCapabilities desiredCapabilityForCloud(String testMethodName,
                                                          String platform,
                                                          String capabilityFilePath) {
        DesiredCapabilities desiredCapabilities = updateCapabilities(platform, capabilityFilePath);
        AppiumDevice deviceProperty = AppiumDeviceManager.getAppiumDevice();
        String deviceName = deviceProperty.getDevice().getName();
        if (deviceName != null) {
            desiredCapabilities.setCapability("device", deviceName);
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        }

        String udid = deviceProperty.getDevice().getUdid();
        if (udid != null) {
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, udid);
        }

        if (!desiredCapabilities.getCapabilityNames().contains("name")) {
            desiredCapabilities.setCapability("name", testMethodName);
        }

        Object pCloudyApiKey = desiredCapabilities.getCapability("pCloudy_ApiKey");
        if (null == pCloudyApiKey) {
            desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "");

            String osVersion = deviceProperty.getDevice().getOsVersion();
            if (osVersion != null) {
                desiredCapabilities.setCapability(CapabilityType.VERSION, osVersion);
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
            }
        }
        LOGGER.info("desiredCapabilityForCloud: ");
        desiredCapabilities.getCapabilityNames().forEach(
            key -> LOGGER.info("\t" + key + ":: " + desiredCapabilities.getCapability(key)));
        return desiredCapabilities;
    }

    private DesiredCapabilities desiredCapabilityForLocalAndRemoteATD(String platform,
                                                                      int port,
                                                                      String capabilityFilePath)
        throws Exception {
        DesiredCapabilities desiredCapabilities = updateCapabilities(platform, capabilityFilePath);
        if (isPlatform(MobilePlatform.ANDROID)) {
            try {
                AppiumDeviceManager.getAppiumDevice().setChromeDriverPort(
                        availablePorts.getAvailablePort(AppiumDeviceManager
                                .getAppiumDevice().getHostName()));
                desiredCapabilities.setCapability(AndroidMobileCapabilityType
                    .CHROMEDRIVER_PORT,
                        AppiumDeviceManager.getAppiumDevice().getChromeDriverPort());
                desiredCapabilities.setCapability(AndroidMobileCapabilityType
                            .CHROMEDRIVER_EXECUTABLE,
                    AppiumDeviceManager.getAppiumDevice().getChromeDriverExecutable());
            } catch (Exception e) {
                throw new RuntimeException("Unable to allocate chromedriver with unique port");
            }
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,
                port);
            appPackage(desiredCapabilities);
        } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            appPackageBundle(desiredCapabilities);
            //Check if simulator.json exists and add the deviceName and OS
            if (AppiumDeviceManager.getAppiumDevice().getDevice().getUdid().length()
                == IOSDeviceConfiguration.SIM_UDID_LENGTH) {

                AppiumDevice deviceProperty = AppiumDeviceManager.getAppiumDevice();
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                    deviceProperty.getDevice().getName());
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                    deviceProperty.getDevice().getOsVersion());
                desiredCapabilities.setCapability("webkitDebugProxyPort",
                    new AvailablePorts().getAvailablePort(AppiumDeviceManager
                        .getAppiumDevice().getHostName()));
            } else {
                desiredCapabilities.setCapability("webkitDebugProxyPort",
                    new AvailablePorts().getAvailablePort(AppiumDeviceManager
                        .getAppiumDevice().getHostName()));
            }

            desiredCapabilities.setCapability(IOSMobileCapabilityType
                .WDA_LOCAL_PORT, port);
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                "XCUITest");
            desiredCapabilities.setCapability(MobileCapabilityType.UDID,
                AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
            File derivedDataPath = new File(System.getProperty("user.dir")
                + FileLocations.DERIVED_DATA
                + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
            if (!derivedDataPath.exists()) {
                derivedDataPath.mkdirs();
            }
            desiredCapabilities.setCapability("derivedDataPath",
                    derivedDataPath.getAbsolutePath());
        }
        desiredCapabilities.setCapability(MobileCapabilityType.UDID,
            AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
        return desiredCapabilities;
    }

    private DesiredCapabilities updateCapabilities(String platform,
                                    String capabilityFilePath) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        JSONObject platFormCapabilities;
        if (CAPS.get().equalsIgnoreCase(capabilityFilePath)) {
            LOGGER.info("Capabilities file is not specified. Using default capabilities file");
            platFormCapabilities = Capabilities.getInstance()
                    .getCapabilities()
                    .getJSONObject(platform);
        } else {
            LOGGER.info("Capabilities file is specified. Using specified capabilities file: "
                                + capabilityFilePath);
            JSONObject capabilitiesForAnotherApp = Capabilities.getInstance()
                    .createInstance(capabilityFilePath);
            platFormCapabilities = capabilitiesForAnotherApp.getJSONObject(platform);
        }
        JSONObject finalPlatFormCapabilities = platFormCapabilities;
        platFormCapabilities.keySet().forEach(key -> {
            desiredCapabilities.setCapability(key, finalPlatFormCapabilities.get(key));
        });
        desiredCapabilities.setCapability("app", getAppPathInCapabilities(platFormCapabilities));
        return desiredCapabilities;
    }

    private String getAppPathInCapabilities(JSONObject platFormCapabilities) {
        String appPath = null;
        if (AppiumDeviceManager.getAppiumDevice().getDevice().isCloud()) {
            appPath = platFormCapabilities.getJSONObject("app").getString("cloud");
        } else {
            appPath = new File(platFormCapabilities.getJSONObject("app")
                    .getString("local")).getAbsolutePath();
        }
        return appPath;
    }

    private void appPackage(DesiredCapabilities desiredCapabilities) {
        if (getOverriddenStringValue(APP_PACKAGE) != null) {
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                getOverriddenStringValue(APP_PACKAGE));
        }
    }

    private void appPackageBundle(DesiredCapabilities iOSCapabilities) {
        if (getOverriddenStringValue(APP_PACKAGE) != null) {
            iOSCapabilities
                .setCapability(IOSMobileCapabilityType.BUNDLE_ID,
                    getOverriddenStringValue(APP_PACKAGE));
        }
    }
}
