package com.video.recorder;

import java.io.IOException;

public interface IScreenRecord {

    void stopVideoRecording(String className, String methodName,
                       String videoFileName) throws IOException, InterruptedException;

    void startVideoRecording(String className, String methodName,
                        String videoFileName) throws IOException, InterruptedException;

}
