package com.video.recorder;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;

import java.io.IOException;

public class AppiumScreenRecordFactory {
    private static AndroidDeviceConfiguration androidDeviceConfiguration
            = new AndroidDeviceConfiguration();

    public static IScreenRecord recordScreen(AppiumDevice device) throws IOException, InterruptedException {
        if (AppiumDeviceManager.getMobilePlatform(device.getDevice().getUdid()).equals(MobilePlatform.ANDROID)) {
            if (androidDeviceConfiguration.getDeviceManufacturer(device)
                    .equals("unknown") && !androidDeviceConfiguration
                    .checkIfRecordable(device)) {
                return new Flick();
            }
        }
        return new AppiumScreenRecorder();
    }
}
