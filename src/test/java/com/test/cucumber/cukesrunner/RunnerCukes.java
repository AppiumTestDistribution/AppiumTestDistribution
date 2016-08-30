package com.test.cucumber.cukesrunner;

import com.appium.manager.ParallelThread;
import org.junit.Test;

/**
 * Created by saikrisv on 30/03/16.
 */
public class RunnerCukes {

    @Test public void testCukesRunner() throws Exception {
        ParallelThread cucumberParallelThread = new ParallelThread();
        cucumberParallelThread.runner("output");
    }
}
