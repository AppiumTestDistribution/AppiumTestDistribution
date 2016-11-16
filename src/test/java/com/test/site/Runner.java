package com.test.site;

import com.appium.manager.AppiumParallelTest;
import com.appium.manager.ParallelThread;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    @Test
    public static void testApp() throws Exception {

        ParallelThread parallelThread = new ParallelThread();
        List<String> tests = new ArrayList<>();
        //tests.add("HomePageTest1");
        tests.add("HomePageTest2");
        boolean hasFailures = parallelThread.runner("com.test.site", tests);
        Assert.assertFalse(hasFailures, "Testcases have failed in parallel execution");
    }
}
