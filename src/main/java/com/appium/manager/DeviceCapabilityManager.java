package com.appium.manager;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

/**
 * Created by saikrisv on 24/01/17.
 */
public class DeviceCapabilityManager {

    private final ConfigurationManager configurationManager;
    DeviceSingleton deviceSingleton;
    AvailablePorts ap = new AvailablePorts();

    public DeviceCapabilityManager() throws IOException {
        configurationManager = ConfigurationManager.getInstance();
        deviceSingleton = DeviceSingleton.getInstance();

    }

    public DesiredCapabilities androidNative() {
        System.out.println("Setting Android Desired Capabilities:");
        System.out.println("Running caps::" + DeviceUDIDManager.getDeviceUDID() + Thread.currentThread().getId());
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                configurationManager.getProperty("APP_ACTIVITY"));
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                configurationManager.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                AutomationName.ANDROID_UIAUTOMATOR2);
        androidCapabilities.setCapability("browserName", "");
        //checkSelendroid(androidCapabilities);
        androidCapabilities
                .setCapability(MobileCapabilityType.APP,
                        configurationManager.getProperty("ANDROID_APP_PATH"));
        System.out.println(DeviceUDIDManager.getDeviceUDID() + Thread.currentThread().getId());
        androidCapabilities.setCapability(MobileCapabilityType.UDID, DeviceUDIDManager.getDeviceUDID());
        try {
            androidCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,ap.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidCapabilities;
    }

    public DesiredCapabilities androidWeb() {
        DesiredCapabilities androidWebCapabilities = new DesiredCapabilities();
        androidWebCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidWebCapabilities
                .setCapability(MobileCapabilityType.BROWSER_NAME,
                        configurationManager.getProperty("BROWSER_TYPE"));
        androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        return androidWebCapabilities;
    }

    public DesiredCapabilities iosNative()
            throws InterruptedException, IOException {
        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        System.out.println("Setting iOS Desired Capabilities:");
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                "iOS");
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                "10.0");
        iOSCapabilities.setCapability(MobileCapabilityType.APP,
                configurationManager.getProperty("IOS_APP_PATH"));
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        iOSCapabilities
                .setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
        iOSCapabilities.setCapability(MobileCapabilityType.UDID, DeviceUDIDManager.getDeviceUDID());
        return iOSCapabilities;
    }
}
