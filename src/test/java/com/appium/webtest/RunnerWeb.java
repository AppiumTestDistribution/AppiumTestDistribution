package com.appium.webtest;

import com.appium.manager.ParallelThread;
import org.testng.annotations.Test;

public class RunnerWeb {


    @Test public void runWebTests() throws Exception {
        ParallelThread parallelThread = new ParallelThread();
        parallelThread.runner("com.appium.webtest");
    }
}
