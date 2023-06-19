package com.video.recorder;


import java.io.IOException;

public class AppiumScreenRecordFactory {

    public static IScreenRecord recordScreen() {
        return new AppiumScreenRecorder();
    }
}
