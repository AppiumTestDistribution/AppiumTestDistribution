package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.AppiumDevice;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;

import org.json.JSONObject;
import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class AppiumParallelTestListener
        implements IClassListener, IInvokedMethodListener, ISuiteListener,ITestListener {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    public AppiumServerManager appiumServerManager;
    public String testDescription = "";
    private AppiumDriverManager appiumDriverManager;

    List<String> syncal =
            Collections.synchronizedList(new ArrayList<String>());

    public static Map<String, String> userLogs =
            Collections.synchronizedMap(new HashMap<String, String>());

    public AppiumParallelTestListener() throws Exception {
        try {
            reportManager = new ReportManager();
            appiumServerManager = new AppiumServerManager();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            SkipIf skip =
                    method.getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod().getAnnotation(SkipIf.class);
            if (skip != null) {
                String info = skip.platform();
                if (AppiumDriverManager.getDriver().getPlatformName().contains(info)) {
                    System.out.println("skipping childTest");
                    throw new SkipException("Skipped because property was set to :::" + info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        JSONObject json = new JSONObject();
        json.put("id", AppiumDeviceManager.getDevice().getDevice().getUdid());
        json.put("version", new AppiumDeviceManager().getDeviceVersion());
        json.put("platform", AppiumDeviceManager.getMobilePlatform());
        //json.put("resolution", AppiumDeviceManager.getMobilePlatform());
        try {
            json.put("model", new AppiumDeviceManager().getDeviceModel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS
                    || testResult.getStatus() == ITestResult.FAILURE) {
                HashMap<String, String> getLogDetails = reportManager.endLogTestResults(testResult);
                JSONObject status = getStatus(json, getExecutionStatus(testResult),
                        String.valueOf(testResult.getThrowable()),
                        getLogDetails, method.getTestMethod().getMethodName(),
                        testResult.getInstance().getClass().getSimpleName(),
                        Duration.of(testResult.getStartMillis(), ChronoUnit.MILLIS).getSeconds(),
                        Duration.of(testResult.getEndMillis(), ChronoUnit.MILLIS).getSeconds(),
                        Duration.of(testResult.getEndMillis() - testResult.getStartMillis(),
                                ChronoUnit.MILLIS).getSeconds());

                sync(status.toString());
            }
            if (method.isTestMethod()) {
                appiumDriverManager.stopAppiumDriver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject getStatus(JSONObject json, String status, String error,
                                HashMap<String, String> deviceLogsPath,
                                String methodname, String classname,
                                long startTime, long endTime, long totalTime) {
        JSONObject jsonObj = new JSONObject();
        JSONObject logs = new JSONObject();
        jsonObj.put("results", status);
        jsonObj.put("methodname", methodname);
        jsonObj.put("classname", classname);
        jsonObj.put("exceptiontrace", error);
        jsonObj.put("starttime", startTime);
        jsonObj.put("endtime", endTime);
        json.put("totaltime", totalTime);
        deviceLogsPath.forEach((key, value) -> {
            if ("videoLogs".equals(key)
                    || "screenShotFailure".equals(key)) {
                if (new File(value).exists()) {
                    logs.put(key, value);
                }
            }
            logs.put(key, value);
        });
        jsonObj.put("logs", logs);
        json.put("testDetails", jsonObj);
        return json;
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

    private void sync(String message) {
        //Adding elements to synchronized ArrayList
        syncal.add(message);
    }


    @Override
    public void onFinish(ISuite iSuite) {
        try {
            appiumServerManager.stopAppiumServer();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getSummaryDetails(Map<String, ISuiteResult> results) {

        Object firstSuite = results.keySet().toArray()[0];
        ISuiteResult result = results.get(firstSuite);
        ITestContext testContext = result.getTestContext();
        int passedTests = testContext.getPassedTests().getAllResults().size();
        int failedTests = testContext.getFailedTests().getAllResults().size();
        int skippedTests = testContext.getSkippedTests().getAllResults().size();
        String suiteName = testContext.getName();
        //String includedGroups = testContext.getIncludedGroups().toString();

        JSONObject jsonSummary = new JSONObject();
        jsonSummary.put("suite_name", suiteName);
        //jsonSummary.put("Included Groups", includedGroups);
        jsonSummary.put("passed", passedTests);
        jsonSummary.put("failed", failedTests);
        jsonSummary.put("skipped", skippedTests);
        jsonSummary.put("duration", Duration.of(testContext.getEndDate().getTime()
                - testContext.getStartDate().getTime(), ChronoUnit.SECONDS));

        return jsonSummary;
    }

    private String getExecutionStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "Pass";
            case ITestResult.FAILURE:
                return "Fail";
            case ITestResult.SKIP:
                return "Skip";
            default:
                throw new RuntimeException("Invalid status");
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
