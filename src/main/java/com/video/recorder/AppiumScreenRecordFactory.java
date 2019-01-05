package com.video.recorder;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.manager.AppiumDeviceManager;

import java.io.IOException;

public class AppiumScreenRecordFactory {
    private static AndroidDeviceConfiguration androidDeviceConfiguration
            = new AndroidDeviceConfiguration();

    public static IScreenRecord recordScreen() throws IOException, InterruptedException {
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            if (androidDeviceConfiguration.getDeviceManufacturer()
                    .equals("unknown") && !androidDeviceConfiguration
                    .checkIfRecordable()) {
                return new Flick();
            }
        }
        return new AppiumScreenRecorder();
    }
}
