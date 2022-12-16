package com.appium.capabilities;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.plugin.PluginClI;
import com.appium.utils.ArtifactsUploader;
import com.appium.utils.AvailablePorts;
import com.appium.utils.HostArtifact;
import com.github.device.Device;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.appium.manager.AppiumDeviceManager.isPlatform;
import static com.appium.utils.ConfigFileManager.CAPS;
import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;

/**
 * Created by saikrisv on 20/05/17.
 */
public class DesiredCapabilityBuilder {

    private static final Logger LOGGER = Logger.getLogger(DesiredCapabilityBuilder.class.getName());
    public static final String APP_PACKAGE = "APP_PACKAGE";
    private AvailablePorts availablePorts;

    public DesiredCapabilityBuilder() {
        super();
        availablePorts = new AvailablePorts();
    }

    public DesiredCapabilities buildDesiredCapability(String testMethodName,
                                                      String capabilityFilePath)
        throws Exception {
        String platform = PluginClI.getInstance().getPlatFormName();
        return  desiredCapabilityForLocalAndRemoteATD(
                    platform,
                    capabilityFilePath);
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
            if (desiredCapabilities.getCapability(CapabilityType.BROWSER_NAME) == null ) {
                desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, "");
            } else {
                desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME,
                         desiredCapabilities.getCapability(CapabilityType.BROWSER_NAME));
            }
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
                                                                      String capabilityFilePath)
        throws Exception {
       return updateCapabilities(platform, capabilityFilePath);
    }

    private DesiredCapabilities updateCapabilities(String platform,
                                    String capabilityFilePath) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        JSONObject platFormCapabilities;
        JSONObject fullCapabilities;
        if (CAPS.get().equalsIgnoreCase(capabilityFilePath)) {
            LOGGER.info("Capabilities file is not specified. Using default capabilities file");
            fullCapabilities = Capabilities.getInstance()
                    .getCapabilities();
            platFormCapabilities = fullCapabilities
                    .getJSONObject(platform);
        } else {
            LOGGER.info("Capabilities file is specified. Using specified capabilities file: "
                                + capabilityFilePath);
            fullCapabilities = Capabilities.getInstance()
                    .createInstance(capabilityFilePath);
            platFormCapabilities = fullCapabilities.getJSONObject(platform);
        }
        JSONObject finalPlatformCapabilities = platFormCapabilities;
        platFormCapabilities.keySet().forEach(key -> {
            desiredCapabilities.setCapability(key, finalPlatformCapabilities.get(key));
        });
        desiredCapabilities.setCapability("app",
                getAppPathInCapabilities(platform, fullCapabilities));
        return desiredCapabilities;
    }

    private String getAppPathInCapabilities(String platform, JSONObject fullCapabilities) {
        String appPath = null;
        if (fullCapabilities.getJSONObject(platform).has("app")) {
            Object app = fullCapabilities.getJSONObject(platform).get("app");
            if (!(new UrlValidator()).isValid(app.toString())) {
                Path path = FileSystems.getDefault().getPath(app.toString());
                appPath = path.normalize().toAbsolutePath().toString();
            } else {
                appPath = app.toString();
            }
        }
        return appPath;
    }

    private String hostAppPath(Object values, List<HostArtifact> hostArtifacts) {
        HostArtifact hostArtifact = (HostArtifact)((List)hostArtifacts.stream().filter((s) -> {
            return s.getHost().equalsIgnoreCase(AppiumDeviceManager.getAppiumDevice()
                    .getHostName());
        }).collect(Collectors.toList())).parallelStream().findFirst().get();
        String appPath = hostArtifact.getArtifactPath("APK");
        if (values instanceof JSONObject) {
            Device device = AppiumDeviceManager.getAppiumDevice().getDevice();
            if (!device.isCloud()) {
                String deviceOS = device.getOs();
                if (deviceOS.equalsIgnoreCase("iOS") && !device.isDevice()) {
                    appPath = hostArtifact.getArtifactPath("APP");
                } else if (deviceOS.equals("iOS") && device.isDevice()) {
                    appPath = hostArtifact.getArtifactPath("IPA");
                } else if (deviceOS.equals("windows") && device.isDevice()) {
                    appPath = hostArtifact.getArtifactPath("EXE");
                }
            } else if (StringUtils.isEmpty(appPath)
                    || ((JSONObject)values).has("simulator")
                    || ((JSONObject)values).has("device")) {
                appPath = hostArtifact.getArtifactPath(((JSONObject)values).has("simulator")
                        ? "APP" : "IPA");
            }
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
