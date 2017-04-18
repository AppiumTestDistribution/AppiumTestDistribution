package com.test.site;

import com.appium.manager.ParallelThread;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Runner {
    @Test
    public static void testApp() throws Exception {
        //udid="32085b6d8fdb71ff,FA66VBN01980" mvn clean -Dtest=Runner test
        String udidParam = System.getenv("udid");
        String[] parts = udidParam.split(",");
        List<String> deviceList = Arrays.asList(parts);
        System.out.println(deviceList);
        ParallelThread parallelThread = new ParallelThread(deviceList);
        List<String> tests = new ArrayList<>();
        tests.add("HomePageTest2");
        tests.add("HomePageTest3");
        tests.add("HomePageTest4");
        boolean hasFailures = parallelThread.runner("com.test.site",tests);
        Assert.assertFalse(hasFailures, "Testcases have failed in parallel execution");
    }
}
