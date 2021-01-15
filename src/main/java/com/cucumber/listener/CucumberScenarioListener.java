package com.cucumber.listener;

import com.appium.capabilities.Capabilities;
import com.appium.filelocations.FileLocations;
import com.appium.manager.ATDRunner;
import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.AppiumServerManager;
import com.appium.manager.DeviceAllocationManager;
import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.epam.reportportal.service.ReportPortal;
import io.appium.java_client.AppiumDriver;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class CucumberScenarioListener implements ConcurrentEventListener {
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private final AppiumDriverManager appiumDriverManager;
    private final DeviceAllocationManager deviceAllocationManager;
    private final AppiumServerManager appiumServerManager;
    private final Optional<String> atdHost;
    private final Optional<String> atdPort;
    private Map<String, Integer> scenarioRunCounts = new HashMap<String, Integer>();

    public CucumberScenarioListener() {
        LOGGER.info(String.format("ThreadID: %d: CucumberScenarioListener\n",
                Thread.currentThread().getId()));
        new ATDRunner();
        appiumServerManager = new AppiumServerManager();
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        appiumDriverManager = new AppiumDriverManager();
        atdHost =
                Optional.ofNullable(Capabilities.getInstance()
                        .getMongoDbHostAndPort().get("atdHost"));
        atdPort =
                Optional.ofNullable(Capabilities.getInstance()
                        .getMongoDbHostAndPort().get("atdPort"));
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
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::runStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::caseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::caseFinishedHandler);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::runFinishedHandler);
    }

    private void runStartedHandler(TestRunStarted event) {
        LOGGER.info("runStartedHandler");
        LOGGER.info(String.format("ThreadID: %d: beforeSuite: \n", Thread.currentThread().getId()));
        try {
            appiumServerManager.startAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void caseStartedHandler(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        LOGGER.info("caseStartedHandler: " + scenarioName);
        Integer scenarioRunCount = getScenarioRunCount(scenarioName);
        LOGGER.info(
                String.format("ThreadID: %d: beforeScenario: for scenario: %s\n",
                        Thread.currentThread().getId(), scenarioName));
        AppiumDevice allocatedDevice = allocateDeviceAndStartDriver();
        String deviceLogFileName = null;
        String normalisedScenarioName = normaliseScenarioName(scenarioName);
        try {
            deviceLogFileName =
                    allocatedDevice.startDataCapture(normalisedScenarioName, scenarioRunCount);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if (!isCloudExecution()) {
            if (atdHost.isPresent() && atdPort.isPresent()) {
                HashMap<String, String> logs = new HashMap<>();
                String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
            }
        }
        TestExecutionContext testExecutionContext = new TestExecutionContext(scenarioName);
        testExecutionContext.addTestState("deviceLog", deviceLogFileName);
        testExecutionContext.addTestState("scenarioDirectory", FileLocations.REPORTS_DIRECTORY
                + normalisedScenarioName);
        testExecutionContext.addTestState("scenarioScreenshotsDirectory",
                FileLocations.REPORTS_DIRECTORY
                        + normalisedScenarioName
                        + File.separator
                        + "screenshot"
                        + File.separator);
    }

    private String normaliseScenarioName(String scenarioName) {
        return scenarioName.replaceAll("[`~ !@#$%^&*()\\-=+\\[\\]{}\\\\|;:'\",<.>/?]", "_");
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
        LOGGER.info("caseFinishedHandler Name: " + event.getTestCase().toString());
        LOGGER.info("caseFinishedHandler Result: " + event.getResult().getStatus().toString());
        long threadId = Thread.currentThread().getId();
        LOGGER.info(
                String.format("ThreadID: %d: afterScenario: for scenario: %s\n",
                        threadId, event.getTestCase().toString()));
        deviceAllocationManager.freeDevice();
        try {
            appiumDriverManager.stopAppiumDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TestExecutionContext testExecutionContext =
                SessionContext.getTestExecutionContext(threadId);
        String deviceLogFileName = testExecutionContext.getTestStateAsString("deviceLog");
        LOGGER.info("deviceLogFileName: " + deviceLogFileName);
        ReportPortal.emitLog("ADB Logs", "DEBUG", new Date(), new File(deviceLogFileName));
        SessionContext.remove(threadId);
    }

    private void runFinishedHandler(TestRunFinished event) {
        LOGGER.info("runFinishedHandler: " + event.getResult().toString());
        LOGGER.info(String.format("ThreadID: %d: afterSuite: \n", Thread.currentThread().getId()));
        try {
            appiumServerManager.stopAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
