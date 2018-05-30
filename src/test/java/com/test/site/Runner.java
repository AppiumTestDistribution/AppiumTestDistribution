package com.test.site;

import com.appium.manager.AppiumParallelTestListener;
import com.appium.manager.ParallelThread;
import com.report.factory.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    @Test
    public static void testApp() throws Exception {
        //udid="32085b6d8fdb71ff,FA66VBN01980" mvn clean -Dtest=Runner test
        /*String udidParam = System.getenv("udid");
        String[] parts = udidParam.split(",");
        List<String> deviceList = Arrays.asList(parts);
        System.out.println(deviceList);*/
        ExtentManager.setSystemInfoInReport("version","1.0.0");
        List<String> tests = new ArrayList<>();
        //tests.add("HomePageTest2");
        tests.add("SliderTest");
        //tests.add("HomePageTest3");
        ParallelThread parallelThread = new ParallelThread();
        boolean hasFailures = parallelThread.runner("com.test.site",tests);
        Assert.assertFalse(hasFailures, "Testcases have failed in parallel execution");
    }
}
