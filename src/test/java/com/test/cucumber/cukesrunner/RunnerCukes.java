package com.test.cucumber.cukesrunner;

import com.appium.manager.ParallelThread;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by saikrisv on 30/03/16.
 */
public class RunnerCukes {

    @Test
    public void testCukesRunner() throws Exception {
        ParallelThread cucumberParallelThread = new ParallelThread();
        boolean hasFailures = cucumberParallelThread.runner("output");
        Assert.assertFalse(hasFailures, "Testcases have failed in parallel execution");
    }
}
