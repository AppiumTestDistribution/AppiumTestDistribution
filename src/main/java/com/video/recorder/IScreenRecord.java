package com.video.recorder;

import com.appium.manager.AppiumDevice;

import java.io.IOException;

public interface IScreenRecord {

    void stopVideoRecording(String className, String methodName,
                       String videoFileName, AppiumDevice device) throws IOException, InterruptedException;

    void startVideoRecording(String className, String methodName,
                             String videoFileName, AppiumDevice device) throws IOException, InterruptedException;

}
