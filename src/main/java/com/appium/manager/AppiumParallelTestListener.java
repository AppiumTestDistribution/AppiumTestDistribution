package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.RetryCount;
import com.annotation.values.SkipIf;
import com.report.factory.ExtentManager;

import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.io.IOException;
import java.lang.reflect.Method;

public final class AppiumParallelTestListener
    implements ITestListener, IClassListener, IInvokedMethodListener,
    IRetryAnalyzer {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    private ConfigFileManager prop;
    public AppiumServerManager appiumServerManager;
    public String testDescription = "";
    private int retryCount = 0;
    private int maxRetryCount;
    private AppiumDriverManager appiumDriverManager;

    public AppiumParallelTestListener() throws Exception {
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
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device").toString();
            String className = testClass.getRealClass().getSimpleName();
            deviceAllocationManager.allocateDevice(device,
                deviceAllocationManager.getNextAvailableDeviceId());
            appiumServerManager.startAppiumServer(className);
            if (getClass().getAnnotation(Description.class) != null) {
                testDescription = getClass().getAnnotation(Description.class).value();
            }
            reportManager.createParentNodeExtent(className, testDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass testClass) {
        try {
            appiumServerManager.stopAppiumServer();
            ExtentManager.getExtent().flush();
            deviceAllocationManager.freeDevice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            reportManager.setAuthorName(method);
            appiumDriverManager.startAppiumDriver();
            reportManager.startLogResults(method.getTestMethod().getMethodName(),
                    testResult.getTestClass().getRealClass().getSimpleName());
            SkipIf skip =
                    method.getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod().getAnnotation(SkipIf.class);
            if (skip != null) {
                String info = skip.platform();
                if (AppiumDriverManager.getDriver().toString()
                        .split("\\(")[0].trim().toString().contains(info)) {
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
            reportManager.endLogTestResults(testResult);
            appiumDriverManager.stopAppiumDriver();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override public boolean retry(ITestResult iTestResult) {
        Method[] methods = iTestResult.getInstance().getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals(iTestResult.getMethod().getMethodName())) {
                if (m.isAnnotationPresent(RetryCount.class)) {
                    RetryCount ta = m.getAnnotation(RetryCount.class);
                    maxRetryCount = ta.maxRetryCount();
                } else {
                    try {
                        maxRetryCount = Integer.parseInt(prop.getProperty("MAX_RETRY_COUNT"));
                    } catch (Exception e) {
                        maxRetryCount = 0;
                    }
                }
            }
        }
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test Failed");
            if (retryCount == maxRetryCount) {
                System.out.println("Log report");
            }
            if (retryCount < maxRetryCount) {
                retryCount++;
                System.out.println(
                    "Retrying Failed Test Cases " + retryCount + "out of " + maxRetryCount);
                return true;

            }
        }
        return false;
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
