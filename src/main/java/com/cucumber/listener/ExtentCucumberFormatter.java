package com.cucumber.listener;

import com.appium.manager.AndroidDeviceConfiguration;
import com.appium.manager.AppiumParallelTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Cucumber custom format listener which generates ExtentsReport html file
 */
public class ExtentCucumberFormatter implements Reporter, Formatter {

    public LinkedList<Step> testSteps;
    private Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();
    public ExtentTest parent;
    public ExtentTest child;
    public AppiumDriver<MobileElement> appium_driver;
    public AppiumParallelTest appiumParallelTest = new AppiumParallelTest();
    private AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();

    public static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void setWebDriver(AppiumDriver driver_) {
        driver.set(driver_);
    }

    private static final Map<String, String> MIME_TYPES_EXTENSIONS = new HashMap() {
        {
            this.put("image/bmp", "bmp");
            this.put("image/gif", "gif");
            this.put("image/jpeg", "jpg");
            this.put("image/png", "png");
            this.put("image/svg+xml", "svg");
            this.put("video/ogg", "ogg");
        }
    };


    public void before(Match match, Result result) {
    }

    public void result(Result result) {
        if ("passed".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.PASS, testSteps.poll().getName(), "PASSED");
        } else if ("failed".equals(result.getStatus())) {
            String failed_StepName = testSteps.poll().getName();
            ExtentTestManager.getTest().log(LogStatus.FAIL, failed_StepName, result.getError());
            File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            String androidModel = androidDevice.getDeviceModel(appiumParallelTest.device_udid);
            try {
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/screenshot/android/" +
                        appiumParallelTest.device_udid.replaceAll("\\W", "_") + "/"
                        + androidModel + "/failed_" + failed_StepName.replaceAll(" ", "_") + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ExtentTestManager.getTest().log(LogStatus.INFO, failed_StepName,ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/android/" +
                    appiumParallelTest.device_udid.replaceAll("\\W", "_") + "/"
                    + androidModel + "/failed_" + failed_StepName.replaceAll(" ", "_") + ".png"));
        } else if ("skipped".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, testSteps.poll().getName(), "SKIPPED");
        } else if ("undefined".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.UNKNOWN, testSteps.poll().getName(), "UNDEFINED");
        }
    }

    public void after(Match match, Result result) {

    }

    public void match(Match match) {

    }

    public void embedding(String s, byte[] bytes) {
    }

    public void write(String s) {
        // ExtentTestManager.endTest(parent);
    }

    public void syntaxError(String s, String s1, List<String> list, String s2, Integer integer) {

    }

    public void uri(String s) {

    }

    public void feature(Feature feature) {
        try {
            appiumParallelTest.startAppiumServer(feature.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Tag tag : feature.getTags()) {
            parent = ExtentTestManager.startTest(feature.getName()).assignCategory(appiumParallelTest.category
                    + appiumParallelTest.device_udid.replaceAll("\\W", "_"), tag.getName());
        }
        parentContext.put(Thread.currentThread().getId(), parent);
    }

    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    public void examples(Examples examples) {

    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        try {
            appium_driver = appiumParallelTest.startAppiumServerInParallel(scenario.getName());
            setWebDriver(appium_driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.testSteps = new LinkedList<Step>();
        System.out.println(testSteps);
        child = ExtentTestManager.startTest(scenario.getName()).assignCategory(appiumParallelTest.category
                + appiumParallelTest.device_udid.replaceAll("\\W", "_"));

    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {
        testSteps.add(step);
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {
        parentContext.get(Thread.currentThread().getId()).appendChild(child);
        ExtentManager.getInstance().flush();
        getDriver().quit();
    }

    public void done() {

    }

    public void close() {

    }

    public void eof() {
        ExtentManager.getInstance().endTest(parent);
        ExtentManager.getInstance().flush();
        try {
            appiumParallelTest.killAppiumServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
