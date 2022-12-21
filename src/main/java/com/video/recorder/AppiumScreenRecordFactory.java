package com.video.recorder;


import java.io.IOException;

public class AppiumScreenRecordFactory {

    public static IScreenRecord recordScreen() throws IOException, InterruptedException {
        return new AppiumScreenRecorder();
    }
}
