package com.appium.webtest;

import com.appium.manager.ATDRunner;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RunnerWeb {


    @Test
    public void runWebTests() throws Exception {
        List<String> tests = new ArrayList<>();
        ATDRunner atdRunner = new ATDRunner();

        tests.add("LoginFailureTest");
        boolean hasFailures = atdRunner.runner("com.appium.webtest", tests);

    }
}
