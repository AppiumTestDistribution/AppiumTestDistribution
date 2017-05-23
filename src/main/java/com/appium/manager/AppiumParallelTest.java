package com.appium.manager;

import com.annotation.values.Author;
import com.annotation.values.Description;
import com.annotation.values.SkipIf;

import com.appium.utils.GetDescriptionForChildNode;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.*;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class AppiumParallelTest implements ITestListener, IClassListener, IInvokedMethodListener {

    private DeviceManager deviceManager;
    private TestLogger testLogger;
    private DeviceAllocationManager deviceAllocationManager;
    private ConfigFileManager prop;
    public AppiumServerManager appiumServerManager;
    public String category = null;
    public String testDescription = "";
    private String CI_BASE_URI = null;
    public ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    public ExtentTest parent;
    public ExtentTest child;

    private String currentMethodName = null;
    private DeviceSingleton deviceSingleton;
    private GetDescriptionForChildNode getDescriptionForChildNode;
    private AppiumDriverManager appiumDriverManager;


    public AppiumParallelTest() {
        try {
            deviceManager = new DeviceManager();
            appiumServerManager = new AppiumServerManager();
            prop = ConfigFileManager.getInstance();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            testLogger = new TestLogger();
            deviceSingleton = DeviceSingleton.getInstance();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAppiumServer(
            String device, String methodName, String tags) throws Exception {
        if (prop.getProperty("CI_BASE_URI") != null) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        allocateDevice(device, deviceSingleton.getDeviceUDID());
        if (DeviceManager.getDeviceUDID() == null) {
            System.out.println("No devices are free to run test or Failed to run test");
        }
        System.out.println("****************Device*************" + DeviceManager.getDeviceUDID());
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            deviceManager.getDeviceCategory();
        } else {
            category = deviceManager.getDeviceModel();
        }
        createParentNodeExtent(methodName, "").assignCategory(tags);

        appiumServerManager.startAppiumServer(methodName);
    }

    public void allocateDevice(String device, String deviceUDID) {
        if (device.isEmpty()) {
            DeviceManager.setDeviceUDID(deviceUDID);
        } else {
            DeviceManager.setDeviceUDID(device);
        }
    }


    public void startAppiumServer(String device) throws Exception {
        if (prop.getProperty("CI_BASE_URI") !=null) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        if (device.isEmpty()) {
            DeviceManager.setDeviceUDID(deviceAllocationManager.getNextAvailableDeviceId());
        } else {
            deviceAllocationManager.getNextAvailableDeviceId();
            DeviceManager.setDeviceUDID(device);
        }

        if (DeviceManager.getDeviceUDID() == null) {
            System.out.println("No devices are free to run test or Failed to run test");
        }
        System.out.println("****************Device*************" + DeviceManager.getDeviceUDID());
    }

    public void getCatAndStartServer(String className) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            deviceManager.getDeviceCategory();
        } else {
            category = deviceManager.getDeviceModel();
        }
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            if (getClass().getAnnotation(Description.class) != null) {
                testDescription = getClass().getAnnotation(Description.class).value();
            }
            createParentNodeExtent(className, testDescription);
        }
    }


    public ExtentTest createParentNodeExtent(String methodName, String testDescription)
        throws IOException, InterruptedException {
        parent = ExtentTestManager.createTest(methodName, testDescription,
            deviceManager.getDeviceModel() +
                        DeviceManager.getDeviceUDID());
        parentTest.set(parent);
        ExtentTestManager.getTest().log(Status.INFO,
                "<a target=\"_parent\" href=" + "appiumlogs/"
                        + DeviceManager.getDeviceUDID() + "__" + methodName
                        + ".txt" + ">AppiumServerLogs</a>");
        return parent;
    }

    public void startAppiumServerInParallel(
            DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        Thread.sleep(3000);
        startingServerInstance(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        //startLogResults(currentMethodName);
    }

    public void startAppiumServerInParallel(
            String methodName, DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("cucumber")) {
            currentMethodName = methodName;
        }
        Thread.sleep(3000);
        startingServerInstance(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        //startLogResults(currentMethodName);
    }

    public AppiumParallelTest createChildNodeWithCategory(String methodName,
                                                          String tags) {
        child = parentTest.get().createNode(methodName, category
                + DeviceManager.getDeviceUDID()).assignCategory(tags);
        test.set(child);
        return this;
    }

    public void setAuthorName(IInvokedMethod methodName) throws NoSuchMethodException {
        String authorName;
        boolean methodNamePresent;
        ArrayList<String> listeners = new ArrayList<>();
        String descriptionMethodName;
        String description = methodName.getTestMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(Test.class).description();
        getDescriptionForChildNode = new GetDescriptionForChildNode(methodName, description)
                .invoke();
        methodNamePresent = getDescriptionForChildNode.isMethodNamePresent();
        descriptionMethodName = getDescriptionForChildNode.getDescriptionMethodName();
        if (methodNamePresent) {
            authorName = methodName.getTestMethod()
                    .getConstructorOrMethod().getMethod()
                    .getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = parentTest.get()
                    .createNode(descriptionMethodName,
                            category + "_" + DeviceManager.getDeviceUDID()).assignAuthor(
                            String.valueOf(listeners));
            test.set(child);
        } else {
            child = parentTest.get().createNode(descriptionMethodName,
                    category + "_" + DeviceManager.getDeviceUDID());
            test.set(child);
        }
    }

    public void startingServerInstance(Optional<DesiredCapabilities> iosCaps,
                                       Optional<DesiredCapabilities> androidCaps)
            throws Exception {

    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {
        testLogger.endLog(result, deviceManager.getDeviceModel(), test);
    }

    public void stopServerCucumber()
            throws IOException, InterruptedException {
        CloseReport();
    }

    public void CloseReport() throws IOException, InterruptedException {
        System.out.println("**************ClosingAppiumSession****************"
                + Thread.currentThread().getId());
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            ExtentManager.getExtent().flush();
        }
        deviceAllocationManager.freeDevice();
    }

    @Override
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device").toString();
            String className = testClass.getRealClass().getSimpleName();
            allocateDevice(device, deviceAllocationManager.getNextAvailableDeviceId());
            appiumServerManager.startAppiumServer(className);
            //below func needs to be refractored
            getCatAndStartServer(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass testClass) {
        try {
            appiumServerManager.stopAppiumServer();
            CloseReport();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            setAuthorName(method);
            currentMethodName = method.getTestMethod().getMethodName();
            appiumDriverManager.startAppiumServerInParallel(currentMethodName,
                    testResult.getTestClass().getRealClass().getSimpleName());
            SkipIf skip =
                    method.getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod().getAnnotation(SkipIf.class);
            if (skip != null) {
                String info = skip.platform();
                if (AppiumDriverManager.getDriver().toString().split("\\(")[0].trim().toString().contains(info)) {
                    System.out.println("skipping test");
                    throw new SkipException("Skipped because property was set to :::" + info);
                }
            }
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            endLogTestResults(testResult);
            AppiumDriverManager.getDriver().quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
