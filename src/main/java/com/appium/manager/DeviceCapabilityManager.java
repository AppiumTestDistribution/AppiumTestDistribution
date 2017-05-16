package com.appium.manager;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

/**
 * Created by saikrisv on 24/01/17.
 */
public class DeviceCapabilityManager {

    private final ConfigurationManager configurationManager;

    public DeviceCapabilityManager() throws IOException {
        configurationManager = ConfigurationManager.getInstance();
    }

    public synchronized DesiredCapabilities androidNative(String  device_udid) {
        System.out.println("Setting Android Desired Capabilities:");
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.X");
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                configurationManager.getProperty("APP_ACTIVITY"));
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                configurationManager.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability("browserName", "");
        //checkSelendroid(androidCapabilities);
        androidCapabilities
                .setCapability(MobileCapabilityType.APP,
                        configurationManager.getProperty("ANDROID_APP_PATH"));
        androidCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return androidCapabilities;
    }

    public synchronized DesiredCapabilities androidWeb() {
        DesiredCapabilities androidWebCapabilities = new DesiredCapabilities();
        androidWebCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidWebCapabilities
                .setCapability(MobileCapabilityType.BROWSER_NAME,
                        configurationManager.getProperty("BROWSER_TYPE"));
        androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        return androidWebCapabilities;
    }

    public synchronized DesiredCapabilities iosNative(String device_udid)
            throws InterruptedException,IOException {
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
                .setCapability(MobileCapabilityType.DEVICE_NAME,"iPhone");
        iOSCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return iOSCapabilities;
    }
}
