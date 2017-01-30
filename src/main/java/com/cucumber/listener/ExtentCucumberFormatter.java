package com.cucumber.listener;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AndroidDeviceConfiguration;
import com.appium.manager.AppiumParallelTest;
import com.appium.manager.ConfigurationManager;
import com.appium.utils.ImageUtils;

import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;

import com.video.recorder.XpathXML;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

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
    public AppiumDriver<MobileElement> appium_driver;
    public AppiumParallelTest appiumParallelTest;
    private AndroidDeviceConfiguration androidDevice;
    private IOSDeviceConfiguration iosDevice;
    public String deviceModel;
    public ImageUtils imageUtils = new ImageUtils();
    public static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    public XpathXML xpathXML = new XpathXML();
    protected DesiredCapabilities iosCapabilities;
    protected DesiredCapabilities androidCapabilities;
    private ConfigurationManager prop;

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

    public ExtentCucumberFormatter()  {
        appiumParallelTest = new AppiumParallelTest();
        try {
            iosDevice = new IOSDeviceConfiguration();
            androidDevice = new AndroidDeviceConfiguration();
            prop = ConfigurationManager.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void before(Match match, Result result) {
    }

    public void result(Result result) {
        if ("passed".equals(result.getStatus())) {
            appiumParallelTest.test.get().log(Status.PASS, testSteps.poll().getName());
        } else if ("failed".equals(result.getStatus())) {
            String failed_StepName = testSteps.poll().getName();
            appiumParallelTest.test.get().log(Status.FAIL, result.getErrorMessage());
            String context = getDriver().getContext();
            boolean contextChanged = false;
            if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver") && !context
                    .equals("NATIVE_APP")) {
                getDriver().context("NATIVE_APP");
                contextChanged = true;
            }
            File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            if (contextChanged) {
                getDriver().context(context);
            }
            if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver")) {
                deviceModel = androidDevice.getDeviceModel(appiumParallelTest.device_udid);
                screenShotAndFrame(failed_StepName, scrFile, "android");
            } else if (getDriver().toString().split(":")[0].trim().equals("IOSDriver")) {
                try {
                    deviceModel =
                            iosDevice.getIOSDeviceProductTypeAndVersion(
                                    appiumParallelTest.device_udid);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                screenShotAndFrame(failed_StepName, scrFile, "iPhone");
            }
            try {
                attachScreenShotToReport(failed_StepName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("skipped".equals(result.getStatus())) {
            appiumParallelTest.test.get().log(Status.SKIP, testSteps.poll().getName());
        } else if ("undefined".equals(result.getStatus())) {
            appiumParallelTest.test.get().log(Status.WARNING, testSteps.poll().getName());
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
        if (prop.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
            AppiumParallelTest.getNextAvailableDeviceId();
            String[] deviceThreadNumber = Thread.currentThread().getName().toString().split("_");
            String[] tagsArray = getTagArray(feature.getTags());
            System.out.println(deviceThreadNumber);
            System.out.println(Integer.parseInt(deviceThreadNumber[1])
                    + prop.getProperty("RUNNER"));
            System.out.println("Feature Tag Name::" + feature.getTags());
            try {
                appiumParallelTest.startAppiumServer(
                        xpathXML.parseXML(Integer.parseInt(deviceThreadNumber[1])), 
                        feature.getName(),
                        tagsArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String[] tagsArray = getTagArray(feature.getTags());
            try {
                appiumParallelTest.startAppiumServer("", feature.getName(), tagsArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getTagArray(List<Tag> tags) {
        String[] tagArray = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            tagArray[i] = tags.get(i).getName();
        }
        return tagArray;
    }
    
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    public void examples(Examples examples) {

    }

    public void startOfScenarioLifeCycle(Scenario scenario) {
        createAppiumInstance(scenario);
        this.testSteps = new LinkedList<Step>();
        System.out.println(testSteps);
    }

    public void createAppiumInstance(Scenario scenario) {
        String[] tagsArray = getTagArray(scenario.getTags());
        try {
            startAppiumServer(scenario, tagsArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAppiumServer(Scenario scenario, String[] tags) throws Exception {
        appium_driver = appiumParallelTest.createChildNodeWithCategory(scenario.getName(), tags)
                .startAppiumServerInParallel("",
                        iosCapabilities, androidCapabilities);
        setWebDriver(appium_driver);
    }

    public void background(Background background) {

    }

    public void scenario(Scenario scenario) {

    }

    public void step(Step step) {
        testSteps.add(step);
    }

    public void endOfScenarioLifeCycle(Scenario scenario) {
        ExtentManager.getExtent().flush();
        getDriver().quit();
    }

    public void done() {

    }

    public void close() {

    }

    public void eof() {
        ExtentManager.getExtent().flush();
        try {
            appiumParallelTest.killAppiumServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void screenShotAndFrame(String failed_StepName, File scrFile, String device) {
        try {
            File framePath =
                    new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            FileUtils.copyFile(scrFile, new File(
                    System.getProperty("user.dir") + "/target/screenshot/" + device + "/"
                            + appiumParallelTest.device_udid.replaceAll("\\W", "_")
                            + "/" + deviceModel
                            + "/failed_" + failed_StepName.replaceAll(" ", "_") + ".png"));
            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { //this line weeds out other directories/folders
                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (deviceModel.toString().toLowerCase()
                                .contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                imageUtils.wrapDeviceFrames(
                                        files1[i].toString(),
                                        System.getProperty("user.dir")
                                                + "/target/screenshot/" + device
                                                + "/" + appiumParallelTest.device_udid
                                                .replaceAll("\\W", "_") + "/"
                                                + deviceModel + "/failed_"
                                                + failed_StepName.replaceAll(" ", "_") + ".png",
                                        System.getProperty("user.dir")
                                                + "/target/screenshot/" + device
                                                + "/" + appiumParallelTest.device_udid
                                                .replaceAll("\\W", "_") + "/"
                                                + deviceModel + "/failed_"
                                                + failed_StepName.replaceAll(" ", "_")
                                                + "_framed.png");
                                break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IM4JavaException e) {
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

    public void attachScreenShotToReport(String stepName) throws IOException {
        String platform = null;
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
            appiumParallelTest.test.get().log(Status.INFO,
                    "Snapshot below: " + ExtentTestManager.getTest().addScreenCaptureFromPath(
                            System.getProperty("user.dir")
                                    + "/target/screenshot/"
                                    + platform + "/"
                                    + appiumParallelTest.device_udid.replaceAll("\\W", "_")
                                    + "/" + deviceModel
                                    + "/failed_" + stepName.replaceAll(" ", "_") + "_framed.png"));
        } else {
            appiumParallelTest.test.get().log(Status.INFO,
                    "Snapshot below: " + ExtentTestManager.getTest().addScreenCaptureFromPath(
                            System.getProperty("user.dir") + "/target/screenshot/"
                                    + platform + "/"
                                    + appiumParallelTest.device_udid.replaceAll("\\W", "_")
                                    + "/" + deviceModel
                                    + "/failed_" + stepName.replaceAll(" ", "_") + ".png"));
        }
    }

}
