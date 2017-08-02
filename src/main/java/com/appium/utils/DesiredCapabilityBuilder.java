package com.appium.utils;

import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.DeviceManager;
import com.thoughtworks.device.SimulatorManager;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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

    public DesiredCapabilities buildDesiredCapability(String jsonPath) throws Exception {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        JSONObject jsonParsedObject = new JsonParser(jsonPath).getJsonParsedObject();
        jsonParsedObject
                .forEach((caps, values) -> {
                    if (caps.equals("app")) {
                        Path path = FileSystems.getDefault().getPath(values.toString());
                        if (!path.getParent().isAbsolute()) {
                            desiredCapabilities.setCapability(caps.toString(), path.normalize()
                                    .toAbsolutePath().toString());
                        } else {
                            desiredCapabilities.setCapability(caps.toString(), path.normalize()
                                    .toAbsolutePath().toString());
                        }
                    } else {
                        desiredCapabilities.setCapability(caps.toString(), values.toString());
                    }
                });
        //Check for web
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            if (desiredCapabilities.getCapability("automationName") == null
                    || desiredCapabilities.getCapability("automationName")
                    .toString() != "UIAutomator2") {
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                        AutomationName.ANDROID_UIAUTOMATOR2);
            }
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,
                    availablePorts.getPort());
            appPackage(desiredCapabilities);
            desiredCapabilities.setCapability(MobileCapabilityType.UDID,
                    DeviceManager.getDeviceUDID());
        } else if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            appPackageBundle(desiredCapabilities);

                //Check if simulator.json exists and add the deviceName and OS
                if (DeviceManager.getDeviceUDID().length() == IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                   // desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET,true);
                    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                            simulatorManager.getSimulatorDetailsFromUDID(DeviceManager.getDeviceUDID(),
                                    "iOS").getName());
                    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                            simulatorManager.getSimulatorDetailsFromUDID(DeviceManager.getDeviceUDID(),
                                    "iOS").getOsVersion());
                }

//                if(Float.valueOf(iosDevice.getIOSDeviceProductVersion()) >= 10.0) {
                    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                            AutomationName.IOS_XCUI_TEST);
                    desiredCapabilities.setCapability(IOSMobileCapabilityType
                            .WDA_LOCAL_PORT, availablePorts.getPort());
//                }
            desiredCapabilities.setCapability(MobileCapabilityType.UDID,
                    DeviceManager.getDeviceUDID());
        }
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
