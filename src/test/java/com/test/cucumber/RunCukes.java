package com.test.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        plugin = {
                "com.cucumber.listener.CucumberScenarioReporterListener",
                "com.cucumber.listener.CucumberScenarioListener",
                "html:target/results.html",
                "message:target/results.ndjson",
                "timeline:target/timline"
        })
public class RunCukes extends AbstractTestNGCucumberTests {
    public RunCukes() {
        System.out.printf("ThreadID: %d: RunCucumberTest", Thread.currentThread().getId());
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        System.out.printf("ThreadID: %d: in overridden scenarios%n", Thread.currentThread().getId());
        Object[][] scenarios = super.scenarios();
        System.out.println(scenarios);
        return scenarios;
    }
}
