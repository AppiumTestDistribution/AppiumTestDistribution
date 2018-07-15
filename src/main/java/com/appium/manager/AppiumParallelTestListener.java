package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.AppiumDevice;
import com.appium.utils.CapabilityManager;
import com.appium.utils.DevicesByHost;
import com.appium.utils.Helpers;
import com.appium.utils.HostMachineDeviceManager;
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
import java.util.Optional;


public final class AppiumParallelTestListener extends Helpers
        implements IClassListener, IInvokedMethodListener, ISuiteListener, ITestListener {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    private AppiumServerManager appiumServerManager;
    private String testDescription = "";
    private AppiumDriverManager appiumDriverManager;
    private Optional<String> atdHost;
    private Optional<String> atdPort;

    public AppiumParallelTestListener() throws Exception {
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


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(atdHost.isPresent() && atdPort.isPresent()) {
            String postTestResults = "http://" + atdHost + ":" + atdPort + "/testresults";
            sendResultsToAtdService(testResult, "Started", postTestResults, new HashMap<>());
        }

        try {
            SkipIf skip = method.getTestMethod().getConstructorOrMethod()
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
        try {
            json.put("model", new AppiumDeviceManager().getDeviceModel());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS || testResult.getStatus() == ITestResult.FAILURE) {
                HashMap<String, String> logs = reportManager.endLogTestResults(testResult);
                if (atdHost.isPresent() && atdPort.isPresent()) {
                    String postTestResults = "http://" + atdHost + ":" + atdPort + "/testresults";
                    sendResultsToAtdService(testResult, "Completed", postTestResults, logs);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass iTestClass) {
        deviceAllocationManager.freeDevice();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        try {
            appiumDriverManager.startAppiumDriverInstance();
            reportManager.setAuthorName(iTestResult);
            reportManager.startLogResults(iTestResult.getMethod().getMethodName(),
                    iTestResult.getTestClass().getRealClass().getSimpleName());
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
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Skipped...");
        if (atdHost.isPresent() && atdPort.isPresent()) {
            String url = "http://" + atdHost + ":" + atdPort + "/testresults";
            sendResultsToAtdService(iTestResult, "UnKnown", url, new HashMap<>());
        }
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
