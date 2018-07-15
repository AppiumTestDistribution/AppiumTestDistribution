package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.CapabilityManager;
import com.appium.utils.Helpers;


import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public final class AppiumParallelMethodTestListener extends Helpers
        implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    private AppiumServerManager appiumServerManager;
    private String testDescription = "";
    private AppiumDriverManager appiumDriverManager;
    private Optional<String> atdHost;
    private Optional<String> atdPort;

    public AppiumParallelMethodTestListener() throws Exception {
        try {
            reportManager = new ReportManager();
            appiumServerManager = new AppiumServerManager();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            appiumDriverManager = new AppiumDriverManager();
            atdHost = Optional.ofNullable(CapabilityManager.getInstance()
                    .getMongoDbHostAndPort().get("atdHost"));
            atdPort = Optional.ofNullable(CapabilityManager.getInstance()
                    .getMongoDbHostAndPort().get("atdPort"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Starts Appium Server before each test suite
     */
    @Override
    public void onStart(ISuite iSuite) {
        try {
            appiumServerManager.startAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Allocates Devices to each test method
     * Instantiate Android or IOS Driver Instance
     * Starts ADB, Video Logging
     * Set Description for each test method with platform and UDID allocated to it.
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        try {
            deviceAllocationManager.allocateDevice(deviceAllocationManager.getNextAvailableDevice());
            appiumDriverManager.startAppiumDriverInstance();
            String methodName = iTestResult.getMethod().getMethodName();
            reportManager.startLogResults(methodName, iTestResult.getTestClass().getRealClass().getSimpleName());
            if (atdHost.isPresent() && atdPort.isPresent()) {
                String url = "http://" + atdHost + ":" + atdPort + "/testresults";
                sendResultsToAtdService(iTestResult, "Started", url, new HashMap<>());
            }
            // Sets description for each test method with platform and Device UDID allocated to it.
            Optional<String> originalDescription = Optional.ofNullable(iTestResult.getMethod().getDescription());
            String description = "Platform: " + AppiumDeviceManager.getMobilePlatform()
                    + " Device UDID: " + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
            if (originalDescription.isPresent()) {
                iTestResult.getMethod().setDescription(originalDescription.get() + "\n" + description);
            } else {
                iTestResult.getMethod().setDescription(description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        SkipIf skip = getSkipIf(iInvokedMethod);
        isSkip(skip);

    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        HashMap<String, String> logs = null;
        String methodName = iInvokedMethod.getTestMethod().getMethodName();
        try {
            if (iTestResult.getStatus() == ITestResult.SUCCESS || iTestResult.getStatus() == ITestResult.FAILURE) {
                try {
                    String className = iTestResult.getMethod().getRealClass().getSimpleName() + "-------" + methodName;
                    if (getClass().getAnnotation(Description.class) != null) {
                        testDescription = getClass().getAnnotation(Description.class).value();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                reportManager.setAuthorName(iTestResult);
                logs = reportManager.endLogTestResults(iTestResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (atdHost.isPresent() && atdPort.isPresent()) {
                String url = "http://" + atdHost + ":" + atdPort + "/testresults";
                sendResultsToAtdService(iTestResult, "Completed", url,logs);
            }
            deviceAllocationManager.freeDevice();
            try {
                appiumDriverManager.stopAppiumDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Stops Appium Server after each test suite
     */
    @Override
    public void onFinish(ISuite iSuite) {
        try {
            appiumServerManager.stopAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void isSkip(SkipIf skip) {
        if (skip != null) {
            String info = skip.platform();
            if (AppiumDriverManager.getDriver().getPlatformName().contains(info)) {
                System.out.println("skipping childTest");
                throw new SkipException("Skipped because property was set to :::" + info);
            }
        }
    }

    private SkipIf getSkipIf(IInvokedMethod method) {
        return method.getTestMethod()
                .getConstructorOrMethod()
                .getMethod().getAnnotation(SkipIf.class);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
