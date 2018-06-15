package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.AppiumDevice;
import com.appium.utils.CapabilityManager;
import com.appium.utils.DevicesByHost;
import com.appium.utils.Helpers;
import com.appium.utils.HostMachineDeviceManager;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import org.json.JSONObject;
import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.HashMap;


public final class AppiumParallelTestListener extends Helpers
        implements IClassListener, IInvokedMethodListener, ISuiteListener, ITestListener {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    private AppiumServerManager appiumServerManager;
    private String testDescription = "";
    private AppiumDriverManager appiumDriverManager;
    private String atdHost = null;
    private String atdPort = null;

    public AppiumParallelTestListener() throws Exception {
        try {
            reportManager = new ReportManager();
            appiumServerManager = new AppiumServerManager();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            appiumDriverManager = new AppiumDriverManager();
            atdHost = CapabilityManager.getInstance()
                    .getMongoDbHostAndPort().get("atdHost");
            atdPort = CapabilityManager.getInstance()
                    .getMongoDbHostAndPort().get("atdPort");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        String postTestResults = "http://" + atdHost + ":"
                + atdPort + "/testresults";
        sendResultsToAtdService(testResult,
                "Started", postTestResults, new HashMap<>());
        try {
            SkipIf skip =
                    method.getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod().getAnnotation(SkipIf.class);
            AppiumParallelMethodTestListener.isSkip(skip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        JSONObject json = new JSONObject();
        json.put("id", AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
        json.put("version", new AppiumDeviceManager().getDeviceVersion());
        json.put("platform", AppiumDeviceManager.getMobilePlatform());
        //json.put("resolution", AppiumDeviceManager.getMobilePlatform());
        try {
            json.put("model", new AppiumDeviceManager().getDeviceModel());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS
                    || testResult.getStatus() == ITestResult.FAILURE) {
                HashMap<String, String> logs = reportManager.endLogTestResults(testResult);
                if (atdHost != null && atdPort != null) {
                    String postTestResults = "http://" + atdHost + ":"
                            + atdPort + "/testresults";
                    sendResultsToAtdService(testResult,
                            "Completed", postTestResults, logs);
                }
            }
            if (method.isTestMethod()) {
                appiumDriverManager.stopAppiumDriver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
    Document to make codacy happy
    */
    @Override
    public void onStart(ISuite iSuite) {
        try {
            appiumServerManager.startAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFinish(ISuite iSuite) {
        try {
            appiumServerManager.stopAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device");
            String hostName = testClass.getXmlClass().getAllParameters().get("hostName");
            DevicesByHost devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
            AppiumDevice appiumDevice = devicesByHost.getAppiumDevice(device, hostName);
            String className = testClass.getRealClass().getSimpleName();
            deviceAllocationManager.allocateDevice(appiumDevice);

            if (getClass().getAnnotation(Description.class) != null) {
                testDescription = getClass().getAnnotation(Description.class).value();
            }
            reportManager.createParentNodeExtent(className,
                    testDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass iTestClass) {
        ExtentManager.getExtent().flush();
        deviceAllocationManager.freeDevice();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        try {
            appiumDriverManager.startAppiumDriverInstance();
            reportManager.setAuthorName(iTestResult);
            reportManager.startLogResults(iTestResult.getMethod().getMethodName(),
                    iTestResult.getTestClass().getRealClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Skipped...");
        if (atdHost != null && atdPort != null) {
            String url = "http://" + atdHost + ":"
                    + atdPort + "/testresults";
            sendResultsToAtdService(iTestResult,
                    "UnKnown", url, new HashMap<>());
        }
        (reportManager.parentTest.get()).getModel().setStatus(Status.SKIP);
        (reportManager.childTest.get()).getModel().setStatus(Status.SKIP);
        ExtentManager.getExtent().flush();
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
