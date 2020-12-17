package com.cucumber.listener;

import com.appium.capabilities.Capabilities;
import com.appium.manager.*;
import com.context.SessionContext;
import com.context.TestExecutionContext;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CucumberScenarioListener implements ConcurrentEventListener {
    private final AppiumDriverManager appiumDriverManager;
    private final DeviceAllocationManager deviceAllocationManager;
    private final AppiumServerManager appiumServerManager;
    private final Optional<String> atdHost;
    private final Optional<String> atdPort;
    private TestLogger testLogger;
    private final ATDRunner atdRunner;
    private Map<String, Integer> scenarioRunCounts = new HashMap<String, Integer>();

    public CucumberScenarioListener() {
        System.out.printf("ThreadID: %d: CucumberScenarioListener%n",
                Thread.currentThread().getId());
        atdRunner = new ATDRunner();
        appiumServerManager = new AppiumServerManager();
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        appiumDriverManager = new AppiumDriverManager();
        atdHost =
                Optional.ofNullable(Capabilities.getInstance()
                        .getMongoDbHostAndPort().get("atdHost"));
        atdPort =
                Optional.ofNullable(Capabilities.getInstance()
                        .getMongoDbHostAndPort().get("atdPort"));
        testLogger = new TestLogger();
    }

    @BeforeMethod
    public void beforeHook(Scenario scenario) {
        scenario.getId();
        System.out.println("In Cucumber Beforehook: " + scenario.getId());
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        System.out.printf("ThreadID: %d: beforeSuite: %n", Thread.currentThread().getId());
    }

    @AfterSuite
    public void afterSuite() {
        System.out.printf("ThreadID: %d: afterSuite: %n", Thread.currentThread().getId());
    }

    private AppiumDevice allocateDeviceAndStartDriver() {
        try {
            AppiumDriver driver = AppiumDriverManager.getDriver();
            if (driver != null && driver.getSessionId() != null) {
                return null;
            }
            AppiumDevice availableDevice = deviceAllocationManager.getNextAvailableDevice();
            deviceAllocationManager.allocateDevice(availableDevice);
            appiumDriverManager.startAppiumDriverInstance();
            return availableDevice;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean isCloudExecution() {
        return AppiumDeviceManager.getAppiumDevice().getDevice().isCloud();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestSourceRead.class, this::featureFileRead);
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::runStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::caseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::caseFinishedHandler);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::runFinishedHandler);
    }

    private void runStartedHandler(TestRunStarted event) {
        System.out.println("runStartedHandler");
        System.out.printf("ThreadID: %d: beforeSuite: %n", Thread.currentThread().getId());
        try {
            appiumServerManager.startAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void featureFileRead(TestSourceRead event) {
        String featureName = event.getSource();
        System.out.println("The feature file read is: " + featureName);
    }

    private void caseStartedHandler(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        System.out.println("caseStartedHandler: " + scenarioName);

        Integer scenarioRunCount = getScenarioRunCount(scenarioName);

        System.out.printf(
                "ThreadID: %d: beforeScenario: for scenario: %s%n",
                Thread.currentThread().getId(), scenarioName);
        AppiumDevice allocatedDevice = allocateDeviceAndStartDriver();
        try {
            allocatedDevice.startDataCapture(scenarioName, scenarioRunCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isCloudExecution()) {
            if (atdHost.isPresent() && atdPort.isPresent()) {
                HashMap<String, String> logs = new HashMap<>();
                String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
            }
        }
        new TestExecutionContext(scenarioName);
        //TODO: add the log directory to the context
    }

    private Integer getScenarioRunCount(String scenarioName) {
        if (scenarioRunCounts.containsKey(scenarioName)) {
            scenarioRunCounts.put(scenarioName, scenarioRunCounts.get(scenarioName) + 1);
        } else {
            scenarioRunCounts.put(scenarioName, 1);
        }
        return scenarioRunCounts.get(scenarioName);
    }

    private void caseFinishedHandler(TestCaseFinished event) {
        String testCaseName = event.getTestCase().getName();
        String testResult = event.getResult().toString();
        System.out.println("caseFinishedHandler Name: " + testCaseName);
        System.out.println("caseFinishedHandler Result: " + testResult);
        System.out.printf(
                "ThreadID: %d: afterScenario: for scenario: %s%n",
                Thread.currentThread().getId(), testCaseName);
        deviceAllocationManager.freeDevice();
        try {
            appiumDriverManager.stopAppiumDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SessionContext.remove(Thread.currentThread().getId());
    }

    private void runFinishedHandler(TestRunFinished event) {
        System.out.println("runFinishedHandler: " + event.getResult().toString());
        System.out.printf("ThreadID: %d: afterSuite: %n", Thread.currentThread().getId());
        try {
            appiumServerManager.stopAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
