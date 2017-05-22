package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AvailablePorts;
import com.appium.manager.DeviceUDIDManager;
import com.appium.manager.DriverManager;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by saikrisv on 20/05/17.
 */
public class CapabilityBuilder {
    JSONParser parser;
    AvailablePorts availablePorts;
    IOSDeviceConfiguration iosDevice;
    public static ThreadLocal<DesiredCapabilities> desiredCapabilitiesThreadLocal
            = new ThreadLocal<>();

    public CapabilityBuilder() throws IOException {
        parser = new JSONParser();
        availablePorts = new AvailablePorts();
        iosDevice = new IOSDeviceConfiguration();
    }

    public static DesiredCapabilities getDesiredCapability() {
        return desiredCapabilitiesThreadLocal.get();
    }

    public DesiredCapabilities buildDeviceCapability(String jsonPath) throws Exception {
        Object obj = parser.parse(new FileReader(jsonPath));
        JSONArray array = new JSONArray();
        array.add(obj);
        Object o = ((JSONArray) obj).get(0);
        JSONObject jsonObject = (JSONObject) o;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        jsonObject.forEach((caps, values) ->
                desiredCapabilities.setCapability(caps.toString(), values.toString()));
        //Check for web
        if (DeviceUDIDManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                    AutomationName.ANDROID_UIAUTOMATOR2);
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,
                    availablePorts.getPort());
        } else if (DeviceUDIDManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            if (iosDevice.getIOSDeviceProductVersion()
                    .contains("10")) {
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                        AutomationName.IOS_XCUI_TEST);
                desiredCapabilities.setCapability(IOSMobileCapabilityType
                        .WDA_LOCAL_PORT, availablePorts.getPort());
            }
        }
        desiredCapabilitiesThreadLocal.set(desiredCapabilities);
        return desiredCapabilities;
    }
}
