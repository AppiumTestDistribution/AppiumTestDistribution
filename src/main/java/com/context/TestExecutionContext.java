package com.context;

import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ConfigFileManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TestExecutionContext {
    private final String testName;
    private final AppiumDriver driver;

    public List<AppiumDriver<MobileElement>> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<AppiumDriver<MobileElement>> drivers) {
        this.drivers = drivers;
    }

    private List<AppiumDriver<MobileElement>> drivers;
    private final HashMap<String, Object> testExecutionState;
    private List<String> deviceId;
    private final Map<String, String> configFileMap;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getSimpleName());


    public TestExecutionContext(String testName) {
        SessionContext.addContext(Thread.currentThread().getId(), this);
        this.configFileMap = ConfigFileManager.configFileMap;
        this.testName = testName;
        this.testExecutionState = new HashMap<String, Object>();
        if (testName.equalsIgnoreCase(SessionContext.TEST_RUNNER)) {
            this.deviceId = Collections.singletonList("ATD-RUNNER");
            this.driver = null;
        } else {
            this.driver = AppiumDriverManager.getDriver();
            this.drivers = AppiumDriverManager.getDrivers();
        }
        LOGGER.info(String.format("%s - TestExecution context created", testName));
    }

    public String getTestName() {
        return testName;
    }

    public List<String> getDeviceIds() {
        return deviceId;
    }

    public Map<String, String> getLoadedConfig() {
        return configFileMap;
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
