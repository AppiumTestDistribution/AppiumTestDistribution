package com.appium.utils;

import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.DeviceManager;
import com.thoughtworks.device.SimulatorManager;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by saikrisv on 20/05/17.
 */
public class DesiredCapabilityBuilder {

    private AvailablePorts availablePorts;
    private IOSDeviceConfiguration iosDevice;
    private SimulatorManager simulatorManager;

    public static ThreadLocal<DesiredCapabilities> desiredCapabilitiesThreadLocal
            = new ThreadLocal<>();

    public DesiredCapabilityBuilder() throws IOException {
        availablePorts = new AvailablePorts();
        iosDevice = new IOSDeviceConfiguration();
        simulatorManager = new SimulatorManager();
    }

    public static DesiredCapabilities getDesiredCapability() {
        return desiredCapabilitiesThreadLocal.get();
    }

    public DesiredCapabilities buildDesiredCapability(String platform,
                                                      String jsonPath) throws Exception {
        final boolean[] flag = {false};
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        JSONArray jsonParsedObject = new JsonParser(jsonPath).getJsonParsedObject();
        Object getPlatformObject = jsonParsedObject.stream().filter(o -> ((JSONObject) o)
                .get(platform) != null)
                .findFirst();
        Object platFormCapabilities = ((JSONObject)((Optional) getPlatformObject)
                .get()).get(platform);
        ((JSONObject) platFormCapabilities)
                .forEach((caps, values) -> {
                    if ("app".equals(caps)) {
                        if (values instanceof JSONObject) {
                            if (DeviceManager.getDeviceUDID().length()
                                    == IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                                values = ((JSONObject) values).get("simulator");
                            } else if (DeviceManager.getDeviceUDID().length()
                                    == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                                values = ((JSONObject) values).get("device");
                            }

                        }
                        Path path = FileSystems.getDefault().getPath(values.toString());
                        if (!path.getParent().isAbsolute()) {
                            desiredCapabilities.setCapability(caps.toString(), path.normalize()
                                    .toAbsolutePath().toString());
                        } else {
                            desiredCapabilities.setCapability(caps.toString(), path
                                    .toString());
                        }
                    } else {
                        desiredCapabilities.setCapability(caps.toString(), values.toString());
                    }
                });
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID) && !flag[0]) {
            if (desiredCapabilities.getCapability("automationName") == null
                    || desiredCapabilities.getCapability("automationName")
                    .toString() != "UIAutomator2") {
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                        AutomationName.ANDROID_UIAUTOMATOR2);
                desiredCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,
                        availablePorts.getPort());
            }
            appPackage(desiredCapabilities);
        } else if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            String version = iosDevice.getIOSDeviceProductVersion();
            appPackageBundle(desiredCapabilities);
            //Check if simulator.json exists and add the deviceName and OS
            if (DeviceManager.getDeviceUDID().length() == IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                        simulatorManager.getSimulatorDetailsFromUDID(
                                DeviceManager.getDeviceUDID()).getName());
                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                        simulatorManager.getSimulatorDetailsFromUDID(DeviceManager.getDeviceUDID())
                                .getOsVersion());
            }
            if (Float.valueOf(version.substring(0, version.length() - 2)) >= 10.0) {
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                        AutomationName.IOS_XCUI_TEST);
                desiredCapabilities.setCapability(IOSMobileCapabilityType
                        .WDA_LOCAL_PORT, availablePorts.getPort());
            }
            desiredCapabilities.setCapability(MobileCapabilityType.UDID,
                    DeviceManager.getDeviceUDID());
        }
        desiredCapabilities.setCapability(MobileCapabilityType.UDID,
                DeviceManager.getDeviceUDID());
        desiredCapabilitiesThreadLocal.set(desiredCapabilities);
        return desiredCapabilities;
    }

    public void appPackage(DesiredCapabilities desiredCapabilities) {
        if (System.getenv("APP_PACKAGE") != null) {
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                    System.getenv("APP_PACKAGE"));
        }
    }

    private void appPackageBundle(DesiredCapabilities iOSCapabilities) {
        if (System.getenv("APP_PACKAGE") != null) {
            iOSCapabilities
                    .setCapability(IOSMobileCapabilityType.BUNDLE_ID,
                            System.getenv("APP_PACKAGE"));
        }
    }
}
