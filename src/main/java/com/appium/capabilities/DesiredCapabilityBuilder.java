package com.appium.capabilities;

import com.appium.plugin.PluginClI;
import com.appium.utils.AvailablePorts;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.FileSystems;
import java.nio.file.Path;
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
        return desiredCapabilityForLocalAndRemoteATD(
                platform,
                capabilityFilePath);
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
            if (!(PluginClI.getInstance().isCloud())
                    && !(new UrlValidator()).isValid(app.toString())) {
                Path path = FileSystems.getDefault().getPath(app.toString());
                appPath = path.normalize().toAbsolutePath().toString();
            } else {
                appPath = app.toString();
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
