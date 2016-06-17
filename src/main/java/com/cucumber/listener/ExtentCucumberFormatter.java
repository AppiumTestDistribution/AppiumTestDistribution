package com.cucumber.listener;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AndroidDeviceConfiguration;
import com.appium.manager.AppiumParallelTest;
import com.appium.utils.ImageUtils;
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
import org.im4java.core.IM4JavaException;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Cucumber custom format listener which generates ExtentsReport html file
 */
public class ExtentCucumberFormatter implements Reporter, Formatter {

    public LinkedList<Step> testSteps;
    private Map<Long, ExtentTest> parentContext = new HashMap<>();
    public ExtentTest parent;
    public ExtentTest child;
    public AppiumDriver<MobileElement> appium_driver;
    public AppiumParallelTest appiumParallelTest = new AppiumParallelTest();
    private AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
    private IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
    public String deviceModel;
    public ImageUtils imageUtils = new ImageUtils();
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
            File scrFile = getDriver().getScreenshotAs(OutputType.FILE);
            if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver")) {
                deviceModel = androidDevice.getDeviceModel(appiumParallelTest.device_udid);
                screenShotAndFrame(failed_StepName, scrFile, "android");
            } else if (getDriver().toString().split(":")[0].trim().equals("IOSDriver")) {
                try {
                    deviceModel =
                        iosDevice.getIOSDeviceProductTypeAndVersion(appiumParallelTest.device_udid);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
                screenShotAndFrame(failed_StepName, scrFile, "iPhone");
            }
            attachScreenShotToReport(failed_StepName);
        } else if ("skipped".equals(result.getStatus())) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, testSteps.poll().getName(), "SKIPPED");
        } else if ("undefined".equals(result.getStatus())) {
            ExtentTestManager.getTest()
                .log(LogStatus.UNKNOWN, testSteps.poll().getName(), "UNDEFINED");
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
            parent = ExtentTestManager.startTest(feature.getName()).assignCategory(
                appiumParallelTest.category + appiumParallelTest.device_udid.replaceAll("\\W", "_"),
                tag.getName());
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
        this.testSteps = new LinkedList<>();
        System.out.println(testSteps);
        child = ExtentTestManager.startTest(scenario.getName()).assignCategory(
            appiumParallelTest.category + appiumParallelTest.device_udid.replaceAll("\\W", "_"));

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
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }


    public void screenShotAndFrame(String failed_StepName, File scrFile, String device) {
        try {
            File framePath =
                new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            FileUtils.copyFile(scrFile, new File(
                System.getProperty("user.dir") + "/target/screenshot/" + device + "/"
                    + appiumParallelTest.device_udid.replaceAll("\\W", "_") + "/" + deviceModel
                    + "/failed_" + failed_StepName.replaceAll(" ", "_") + ".png"));
            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                assert files1 != null;
                for (File aFiles1 : files1) {
                    if (aFiles1.isFile()) { //this line weeds out other directories/folders
                        Path p = Paths.get(aFiles1.toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (deviceModel.toLowerCase()
                                .contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                imageUtils.wrapDeviceFrames(aFiles1.toString(),
                                        System.getProperty("user.dir") +
                                                "/target/screenshot/" + device + "/"
                                                + appiumParallelTest.device_udid.replaceAll("\\W", "_")
                                                + "/" + deviceModel + "/failed_" + failed_StepName
                                                .replaceAll(" ", "_") + ".png",
                                        System.getProperty("user.dir") + "/target/screenshot/" + device
                                                + "/" + appiumParallelTest.device_udid
                                                .replaceAll("\\W", "_") + "/" + deviceModel + "/failed_"
                                                + failed_StepName.replaceAll(" ", "_") + "_framed.png");
                                break;
                            } catch (InterruptedException | IM4JavaException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Resource Directory was not found");
        }
    }

    public void attachScreenShotToReport(String stepName) {
        String platform;
        if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver")) {
            platform = "android";
        } else if (getDriver().toString().split(":")[0].trim().equals("IOSDriver")) {
            platform = "iPhone";
        } else {
            platform = "android";
        }
        File framedImageAndroid = new File(
            System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
                + appiumParallelTest.device_udid.replaceAll("\\W", "_") + "/" + deviceModel
                + "/failed_" + stepName.replaceAll(" ", "_") + "_framed.png");
        if (framedImageAndroid.exists()) {
            ExtentTestManager.getTest().log(LogStatus.INFO, stepName,
                "Snapshot below: " + ExtentTestManager.getTest()
                    .addScreenCapture(System.getProperty("user.dir") +
                        "/target/screenshot/" + platform + "/" + appiumParallelTest.device_udid
                        .replaceAll("\\W", "_") + "/" + deviceModel + "/failed_" + stepName
                        .replaceAll(" ", "_") + "_framed.png"));
        } else {
            ExtentTestManager.getTest().log(LogStatus.INFO, stepName,
                "Snapshot below: " + ExtentTestManager.getTest()
                    .addScreenCapture(System.getProperty("user.dir") +
                        "/target/screenshot/" + platform + "/" + appiumParallelTest.device_udid
                        .replaceAll("\\W", "_") + "/" + deviceModel + "/failed_" + stepName
                        .replaceAll(" ", "_") + ".png"));
        }
    }

}
