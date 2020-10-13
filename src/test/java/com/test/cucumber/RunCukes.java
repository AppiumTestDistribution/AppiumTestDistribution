package com.test.cucumber;

import com.annotation.values.SkipIf;
import com.appium.capabilities.CapabilityManager;
import com.appium.manager.*;
import com.appium.utils.FileFilterParser;
import com.context.SessionContext;
import com.context.TestExecutionContext;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.java.bs.A;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import lombok.Synchronized;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@CucumberOptions(
    plugin = {
      "com.cucumber.listener.CucumberScenarioReporterListener",
      "html:target/results.html",
      "message:target/results.ndjson",
      "timeline:target/timline"
    })
public class RunCukes extends AbstractTestNGCucumberTests {
  private final AppiumDriverManager appiumDriverManager;
  private final DeviceAllocationManager deviceAllocationManager;
  private final AppiumServerManager appiumServerManager;
  private final Optional<String> atdHost;
  private final Optional<String> atdPort;

  public RunCukes() {
    System.out.printf("ThreadID: %d: RunCucumberTest", Thread.currentThread().getId());
    ATDRunner atdRunner = new ATDRunner();
    appiumServerManager = new AppiumServerManager();
    deviceAllocationManager = DeviceAllocationManager.getInstance();
    appiumDriverManager = new AppiumDriverManager();
    atdHost =
        Optional.ofNullable(CapabilityManager.getInstance().getMongoDbHostAndPort().get("atdHost"));
    atdPort =
        Optional.ofNullable(CapabilityManager.getInstance().getMongoDbHostAndPort().get("atdPort"));
  }

  @Override
  @DataProvider(parallel = false)
  public Object[][] scenarios() {
    System.out.printf("ThreadID: %d: in overridden scenarios%n", Thread.currentThread().getId());
    Object[][] scenarios = super.scenarios();
    System.out.println(scenarios);
    return scenarios;
  }

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    System.out.printf("ThreadID: %d: beforeSuite: %n", Thread.currentThread().getId());
    try {
      appiumServerManager.startAppiumServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterSuite
  public void afterSuite() {
    System.out.printf("ThreadID: %d: afterSuite: %n", Thread.currentThread().getId());
    try {
      appiumServerManager.stopAppiumServer();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Before
  public void beforeScenario(Scenario scenario) {
    System.out.printf(
        "ThreadID: %d: beforeScenario: for scenario: %s%n",
        Thread.currentThread().getId(), scenario.getName());
    allocateDeviceAndStartDriver(scenario.getStatus());
    if (!isCloudExecution()) {
        if (atdHost.isPresent() && atdPort.isPresent()) {
          HashMap<String, String> logs = new HashMap<>();
          String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
        }
    }
    new TestExecutionContext(scenario.getName());
  }

  private void allocateDeviceAndStartDriver(Status status) {
    try {
      AppiumDriver driver = AppiumDriverManager.getDriver();
      if (driver == null || driver.getSessionId() == null) {
        deviceAllocationManager.allocateDevice(deviceAllocationManager
                                                       .getNextAvailableDevice());
        appiumDriverManager.startAppiumDriverInstance();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private boolean isCloudExecution() {
    return AppiumDeviceManager.getAppiumDevice().getDevice().isCloud();
  }

  @After
  public void afterScenario(Scenario scenario) throws Exception {
    System.out.printf(
        "ThreadID: %d: afterScenario: for scenario: %s%n",
        Thread.currentThread().getId(), scenario.getName());
    deviceAllocationManager.freeDevice();
    appiumDriverManager.stopAppiumDriver();
    SessionContext.remove(Thread.currentThread().getId());
  }
}
