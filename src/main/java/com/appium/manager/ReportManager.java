package com.appium.manager;

import com.annotation.values.Author;
import com.appium.utils.GetDescriptionForChildNode;
import com.aventstack.extentreports.ExtentTest;
import com.report.factory.ExtentTestManager;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * ReportManager - Handles all Reporting activities e.g communication with ExtentManager, etc
 */
public class ReportManager {

    private TestLogger testLogger;
    private AppiumDeviceManager appiumDeviceManager;
    public ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public ThreadLocal<ExtentTest> childTest = new ThreadLocal<ExtentTest>();
    public ExtentTest parent;
    public ExtentTest child;
    private GetDescriptionForChildNode getDescriptionForChildNode;
    public String category = null;


    public ReportManager() {
        testLogger = new TestLogger();
        appiumDeviceManager = new AppiumDeviceManager();
    }

    public void startLogResults(String methodName,String className) throws FileNotFoundException {
        testLogger.startLogging(methodName, className);
    }

    public HashMap<String, String> endLogTestResults(ITestResult result)
            throws IOException, InterruptedException {
        return testLogger.endLog(result, appiumDeviceManager.getDeviceModel(), childTest);
    }

    public ExtentTest createParentNodeExtent(String methodName, String testDescription)
        throws IOException, InterruptedException {
        String deviceModel = appiumDeviceManager.getDeviceModel();
        if(deviceModel.equalsIgnoreCase("not supported")) {
            deviceModel = "";
        }
        parent = ExtentTestManager.createTest(methodName, testDescription,
            deviceModel + "|" + AppiumDeviceManager.getAppiumDevice().getHostName()
                    + "|"+ AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
        parentTest.set(parent);
        return parent;
    }

    public void setAuthorName(ITestResult methodName) throws Exception {
        String authorName;
        String dataProvider = null;
        boolean methodNamePresent;
        ArrayList<String> listeners = new ArrayList<>();
        String description = methodName.getMethod()
            .getConstructorOrMethod().getMethod()
            .getAnnotation(Test.class).description();
        Object dataParameter = methodName.getParameters();
        if (((Object[]) dataParameter).length > 0) {
            dataProvider = (String) ((Object[]) dataParameter)[0];
        }
        String descriptionMethodName;
        getDescriptionForChildNode = new GetDescriptionForChildNode(methodName, description)
            .invoke();
        methodNamePresent = getDescriptionForChildNode.isMethodNamePresent();
        descriptionMethodName = getDescriptionForChildNode.getDescriptionMethodName();
        if (System.getProperty("os.name").toLowerCase().contains("mac")
                && System.getenv("Platform").equalsIgnoreCase("iOS")
                    || System.getenv("Platform")
                         .equalsIgnoreCase("Both")) {
            category = appiumDeviceManager.getDeviceCategory();
        } else {
            category = appiumDeviceManager.getDeviceModel();
        }
        String testName = dataProvider == null ? descriptionMethodName
                : descriptionMethodName + "[" + dataProvider + "]";
        String udid = AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
        if (methodNamePresent) {
            authorName = methodName.getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = parentTest.get()
                .createNode(testName,
                    category + "_" + udid).assignAuthor(
                    String.valueOf(listeners));
            childTest.set(child);
        } else {
            child = parentTest.get().createNode(testName,
                category + "_" + udid);
            childTest.set(child);
        }
    }

    public void createChildNodeWithCategory(String methodName,
        String tags) {
        child = parentTest.get().createNode(methodName, category
            + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()).assignCategory(tags);
        childTest.set(child);
    }
}
