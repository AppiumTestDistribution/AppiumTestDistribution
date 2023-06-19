package com.video.recorder;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.Helpers;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;

public class AppiumScreenRecorder extends Helpers implements IScreenRecord {
    private static final Logger LOGGER = Logger.getLogger(AppiumScreenRecorder.class.getName());

    @Override
    public void stopVideoRecording(String className, String methodName,
                                   String videoFileName) {
        String videoPath = System.getProperty("user.dir");
        if (AppiumDeviceManager.getMobilePlatform()
            .equals(MobilePlatform.IOS)) {
            String videoLocationIOS =
                videoPath + FileLocations.IOS_SCREENSHOTS_DIRECTORY
                    + AppiumDeviceManager.getAppiumDevice().getUdid()
                    + "/" + getCurrentTestClassName()
                    + "/" + getCurrentTestMethodName()
                    + "/" + getCurrentTestMethodName() + ".mp4";
            String base64 = ((IOSDriver) AppiumDriverManager.getDriver()).stopRecordingScreen();
            saveVideo(base64, videoLocationIOS);
        } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            String videoLocationAndroid =
                videoPath + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                    + AppiumDeviceManager.getAppiumDevice().getUdid()
                    + "/" + getCurrentTestClassName()
                    + "/" + getCurrentTestMethodName()
                    + "/" + getCurrentTestMethodName() + ".mp4";
            String base64 = ((AndroidDriver) AppiumDriverManager.getDriver()).stopRecordingScreen();
            saveVideo(base64, videoLocationAndroid);
        } else {
            LOGGER.error("Video recording not supported for platform: "
                                 + AppiumDeviceManager.getMobilePlatform().platformName);
        }
    }

    private void saveVideo(String base64, String videoLocation) {
        byte[] decode = Base64.getDecoder().decode(base64);
        try {
            FileUtils.writeByteArrayToFile(new File(videoLocation),
                decode);
        } catch (IOException e) {
            LOGGER.error("Unable to save video", e);
        }
    }

    @Override
    public void startVideoRecording() {
        if (AppiumDeviceManager.getMobilePlatform()
            .equals(MobilePlatform.IOS)) {
            ((IOSDriver) AppiumDriverManager.getDriver()).startRecordingScreen();
        } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            ((AndroidDriver) AppiumDriverManager.getDriver())
                .startRecordingScreen(new AndroidStartScreenRecordingOptions()
                .withTimeLimit(Duration.ofSeconds(1800)));
        } else {
            LOGGER.error("Video recording not supported for platform: "
                             + AppiumDeviceManager.getMobilePlatform().platformName);
        }
    }
}
