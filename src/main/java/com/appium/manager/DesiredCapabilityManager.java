package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AvailablePorts;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

/**
 * Created by saikrisv on 24/01/17.
 */
public class DesiredCapabilityManager {

    private final ConfigFileManager configFileManager;
    DeviceSingleton deviceSingleton;
    AvailablePorts availablePorts;
    IOSDeviceConfiguration iosDevice;

    public DesiredCapabilityManager() throws IOException {
        configFileManager = ConfigFileManager.getInstance();
        deviceSingleton = DeviceSingleton.getInstance();
        iosDevice = new IOSDeviceConfiguration();
        availablePorts = new AvailablePorts();

    }

    public DesiredCapabilities androidNative() {
        System.out.println("Setting Android Desired Capabilities:");
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                configFileManager.getProperty("APP_ACTIVITY"));
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                configFileManager.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                AutomationName.ANDROID_UIAUTOMATOR2);
        //checkSelendroid(androidCapabilities);
        androidCapabilities
                .setCapability(MobileCapabilityType.APP,
                        configFileManager.getProperty("ANDROID_APP_PATH"));
        System.out.println(DeviceManager.getDeviceUDID() + Thread.currentThread().getId());
        androidCapabilities.setCapability(MobileCapabilityType.UDID, DeviceManager.getDeviceUDID());
        try {
            androidCapabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,
                    availablePorts.getPort());
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
                        configFileManager.getProperty("BROWSER_TYPE"));
        androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        androidWebCapabilities.setCapability(MobileCapabilityType.UDID,
                DeviceManager.getDeviceUDID());
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
                configFileManager.getProperty("IOS_APP_PATH"));
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        iOSCapabilities
                .setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
        iOSCapabilities.setCapability(MobileCapabilityType.UDID, DeviceManager.getDeviceUDID());
        if (iosDevice.getIOSDeviceProductVersion()
                .contains("10")) {
            iOSCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                    AutomationName.IOS_XCUI_TEST);
            try {
                iOSCapabilities.setCapability(IOSMobileCapabilityType
                        .WDA_LOCAL_PORT, availablePorts.getPort());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iOSCapabilities;
    }


}
