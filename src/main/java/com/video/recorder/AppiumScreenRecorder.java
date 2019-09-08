package com.video.recorder;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.Helpers;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class AppiumScreenRecorder extends Helpers implements IScreenRecord {

    @Override
    public void stopVideoRecording(String className, String methodName,
                                   String videoFileName, AppiumDevice device) throws IOException {
        String videoPath = System.getProperty("user.dir");
        if (AppiumDeviceManager.getMobilePlatform(device.getDevice().getUdid())
            .equals(MobilePlatform.IOS)) {
            String videoLocationIOS =
                videoPath + FileLocations.IOS_SCREENSHOTS_DIRECTORY
                    + device.getDevice().getUdid()
                    + "/" + getCurrentTestClassName()
                    + "/" + getCurrentTestMethodName()
                    + "/" + getCurrentTestMethodName() + ".mp4";
            String base64 = ((IOSDriver) AppiumDriverManager.getDriver()).stopRecordingScreen();
            saveVideo(base64, videoLocationIOS);
        } else {
            String videoLocationAndroid =
                videoPath + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                    + device.getDevice().getUdid()
                    + "/" + getCurrentTestClassName()
                    + "/" + getCurrentTestMethodName()
                    + "/" + getCurrentTestMethodName() + ".mp4";
            String base64 = ((AndroidDriver) AppiumDriverManager.getDriver()).stopRecordingScreen();
            saveVideo(base64, videoLocationAndroid);
        }
    }

    private void saveVideo(String base64, String videoLocation) throws IOException {
        byte[] decode = Base64.getDecoder().decode(base64);
        FileUtils.writeByteArrayToFile(new File(videoLocation),
            decode);
    }

    @Override
    public void startVideoRecording(String className, String methodName,
                                    String videoFileName, AppiumDevice device) {
        // TODO change to drivers
        if (AppiumDeviceManager.getMobilePlatform(device.getDevice().getUdid())
            .equals(MobilePlatform.IOS)) {
            ((IOSDriver) AppiumDriverManager.getDriver()).startRecordingScreen();
        } else {
            ((AndroidDriver) AppiumDriverManager.getDriver()).startRecordingScreen();
        }

    }
}
