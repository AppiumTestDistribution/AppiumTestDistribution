package com.cucumber.listener;

import com.appium.capabilities.CapabilityManager;
import com.appium.manager.ATDRunner;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.AppiumServerManager;
import com.appium.manager.DeviceAllocationManager;
import com.context.SessionContext;
import com.context.TestExecutionContext;
import io.appium.java_client.AppiumDriver;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Optional;

public class CucumberScenarioListener implements ConcurrentEventListener {
    private final AppiumDriverManager appiumDriverManager;
    private final DeviceAllocationManager deviceAllocationManager;
    private final AppiumServerManager appiumServerManager;
    private final Optional<String> atdHost;
    private final Optional<String> atdPort;

    public CucumberScenarioListener() {
        System.out.printf("ThreadID: %d: CucumberScenarioListener%n",
                Thread.currentThread().getId());
        new ATDRunner();
        appiumServerManager = new AppiumServerManager();
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        appiumDriverManager = new AppiumDriverManager();
        atdHost =
                Optional.ofNullable(CapabilityManager.getInstance()
                        .getMongoDbHostAndPort().get("atdHost"));
        atdPort =
                Optional.ofNullable(CapabilityManager.getInstance()
                        .getMongoDbHostAndPort().get("atdPort"));
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        System.out.printf("ThreadID: %d: beforeSuite: %n", Thread.currentThread().getId());
    }

    @AfterSuite
    public void afterSuite() {
        System.out.printf("ThreadID: %d: afterSuite: %n", Thread.currentThread().getId());
    }

    private void allocateDeviceAndStartDriver() {
        try {
            AppiumDriver driver = AppiumDriverManager.getDriver();
            if (driver == null || driver.getSessionId() == null) {
                deviceAllocationManager.allocateDevice(
                        deviceAllocationManager.getNextAvailableDevice());
                appiumDriverManager.startAppiumDriverInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isCloudExecution() {
        return AppiumDeviceManager.getAppiumDevice().getDevice().isCloud();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
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

    private void caseStartedHandler(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        System.out.println("caseStartedHandler: " + scenarioName);
        System.out.printf(
                "ThreadID: %d: beforeScenario: for scenario: %s%n",
                Thread.currentThread().getId(), scenarioName);
        allocateDeviceAndStartDriver();
        if (!isCloudExecution()) {
            if (atdHost.isPresent() && atdPort.isPresent()) {
                HashMap<String, String> logs = new HashMap<>();
                String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
            }
        }
        new TestExecutionContext(scenarioName);
    }

    private void caseFinishedHandler(TestCaseFinished event) {
        System.out.println("caseFinishedHandler: " + event.getTestCase().getName());
        System.out.println("caseFinishedHandler: " + event.getResult().toString());
        System.out.printf(
                "ThreadID: %d: afterScenario: for scenario: %s%n",
                Thread.currentThread().getId(), event.getTestCase().getName());
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
