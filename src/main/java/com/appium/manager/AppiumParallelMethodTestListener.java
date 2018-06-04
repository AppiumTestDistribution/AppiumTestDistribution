package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.Api;
import com.appium.utils.Retry;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;
import com.report.factory.TestStatusManager;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.io.IOException;

public final class AppiumParallelMethodTestListener
        implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    private ConfigFileManager prop;
    private AppiumServerManager appiumServerManager;
    private String testDescription = "";
    private AppiumDriverManager appiumDriverManager;

    public AppiumParallelMethodTestListener() throws Exception {
        try {
            reportManager = new ReportManager();
            appiumServerManager = new AppiumServerManager();
            prop = ConfigFileManager.getInstance();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        SkipIf skip = getSkipIf(method);
        isSkip(skip);

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
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        String methodName = method.getTestMethod().getMethodName();
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS
                    || testResult.getStatus() == ITestResult.FAILURE) {
                try {
                    String className = testResult.getMethod().getRealClass().getSimpleName()
                            + "-------" + methodName;
                    if (getClass().getAnnotation(Description.class) != null) {
                        testDescription = getClass().getAnnotation(Description.class).value();
                    }
                    reportManager.createParentNodeExtent(className, testDescription);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                reportManager.setAuthorName(testResult);
                reportManager.endLogTestResults(testResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            String reportEventJson = null;
            try {
                reportEventJson = new TestStatusManager()
                        .getReportEventJson(AppiumDeviceManager.getAppiumDevice(),
                                "Completed",
                                methodName, getStatus(testResult));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            new Api().post("http://127.0.0.1:3000/testresults",reportEventJson);
            ExtentManager.getExtent().flush();
            deviceAllocationManager.freeDevice();
            try {
                appiumDriverManager.stopAppiumDriver();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        try {
            System.out.println(Thread.currentThread().getId());
            deviceAllocationManager.allocateDevice(deviceAllocationManager
                    .getNextAvailableDevice());
            appiumDriverManager.startAppiumDriverInstance();
            String methodName = iTestResult.getMethod().getMethodName();
            reportManager.startLogResults(methodName,
                    iTestResult.getTestClass().getRealClass().getSimpleName());
            String reportEventJson = new TestStatusManager()
                    .getReportEventJson(AppiumDeviceManager.getAppiumDevice(),
                            "Started",
                            methodName, "UnKnown");
            new Api().post("http://127.0.0.1:3000/testresults",reportEventJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    /*
        Document to make codacy happy
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.extent.removeTest(ExtentTestManager.getTest());
        IRetryAnalyzer retryAnalyzer = result.getMethod().getRetryAnalyzer();
        if (((Retry) retryAnalyzer).retryCountForTest == ((Retry) retryAnalyzer).maxRetryCount) {
            (reportManager.parentTest.get()).getModel().setStatus(Status.SKIP);
            (reportManager.childTest.get()).getModel().setStatus(Status.SKIP);
            ExtentManager.getExtent().flush();
        }
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

    private String getStatus(ITestResult result) {
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

}
