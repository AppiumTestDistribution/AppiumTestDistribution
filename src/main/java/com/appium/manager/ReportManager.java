package com.appium.manager;

import com.annotation.values.Author;
import com.appium.utils.GetDescriptionForChildNode;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentTestManager;
import org.testng.IInvokedMethod;
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
    private DeviceManager deviceManager;
    public ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    public ExtentTest parent;
    public ExtentTest child;
    private GetDescriptionForChildNode getDescriptionForChildNode;
    public String category = null;


    public ReportManager() {
        testLogger = new TestLogger();
        deviceManager = new DeviceManager();
    }

    public void startLogResults(String methodName,String className) throws FileNotFoundException {
        testLogger.startLogging(methodName, className);
    }

    public HashMap<String, String> endLogTestResults(ITestResult result)
            throws IOException, InterruptedException {
        return testLogger.endLog(result, deviceManager.getDeviceModel(), test);
    }

    public ExtentTest createParentNodeExtent(String methodName, String testDescription)
        throws IOException, InterruptedException {
        parent = ExtentTestManager.createTest(methodName, testDescription,
            deviceManager.getDeviceModel()
                    + DeviceManager.getDeviceUDID());
        parentTest.set(parent);
        ExtentTestManager.getTest().log(Status.INFO,
            "<a target=\"_parent\" href=" + "appiumlogs/"
                + DeviceManager.getDeviceUDID() + "__" + methodName
                + ".txt" + ">AppiumServerLogs</a>");
        return parent;
    }

    public void setAuthorName(IInvokedMethod methodName) throws Exception {
        String authorName;
        String dataProvider = null;
        boolean methodNamePresent;
        ArrayList<String> listeners = new ArrayList<>();
        String description = methodName.getTestMethod()
            .getConstructorOrMethod().getMethod()
            .getAnnotation(Test.class).description();
        Object dataParameter = methodName.getTestResult().getParameters();
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
            category = deviceManager.getDeviceCategory();
        } else {
            category = deviceManager.getDeviceModel();
        }
        String testName = dataProvider == null ? descriptionMethodName
                : descriptionMethodName + "[" + dataProvider + "]";
        if (methodNamePresent) {
            authorName = methodName.getTestMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = parentTest.get()
                .createNode(testName,
                    category + "_" + DeviceManager.getDeviceUDID()).assignAuthor(
                    String.valueOf(listeners));
            test.set(child);
        } else {
            child = parentTest.get().createNode(testName,
                category + "_" + DeviceManager.getDeviceUDID());
            test.set(child);
        }
    }

    public void createChildNodeWithCategory(String methodName,
        String tags) {
        child = parentTest.get().createNode(methodName, category
            + DeviceManager.getDeviceUDID()).assignCategory(tags);
        test.set(child);
    }
}
