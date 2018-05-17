package com.appium.webtest;

import com.appium.manager.ParallelThread;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RunnerWeb {


    @Test public void runWebTests() throws Exception {
        List<String> tests = new ArrayList<>();
        ParallelThread parallelThread = new ParallelThread();

        tests.add("LoginFailureTest");
        boolean hasFailures = parallelThread.runner("com.appium.webtest",tests);

    }
}
