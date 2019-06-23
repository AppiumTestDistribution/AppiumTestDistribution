package com.context;

import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ConfigFileManager;
import io.appium.java_client.AppiumDriver;

import java.util.HashMap;
import java.util.Map;

public class TestExecutionContext {
    private final String testName;
    private final AppiumDriver driver;
    private final HashMap<String, Object> testExecutionState;
    private String deviceId = "NOT-YET-SET";
    private final Map<String, String> configFileMap;

    public TestExecutionContext(String testName) {
        SessionContext.addContext(Thread.currentThread().getId(), this);
        this.configFileMap = ConfigFileManager.configFileMap;
        this.testName = testName;
        this.testExecutionState = new HashMap<String, Object>();
        if (testName.equalsIgnoreCase(SessionContext.TEST_RUNNER)) {
            this.deviceId = "ATD-RUNNER";
            this.driver = null;
        } else {
            this.driver = AppiumDriverManager.getDriver();
            this.deviceId = AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
        }
    }

    public String getTestName() {
        return testName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void addTestState(String key, Object details) {
        testExecutionState.put(key, details);
    }

    public Object getTestState(String key) {
        return testExecutionState.get(key);
    }

    public String getTestStateAsString(String key) {
        return (String) testExecutionState.get(key);
    }
}
