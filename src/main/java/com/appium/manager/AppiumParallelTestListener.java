package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.GenerateReportJson;

import com.appium.utils.ScreenShotManager;
import org.testng.*;

import java.io.IOException;


public final class AppiumParallelTestListener
        implements IClassListener, IInvokedMethodListener, ISuiteListener {

    private DeviceAllocationManager deviceAllocationManager;
    public AppiumServerManager appiumServerManager;
    public String testDescription = "";
    private AppiumDriverManager appiumDriverManager;
    private GenerateReportJson generateReportJson;
    ScreenShotManager screenShotManager;

    public AppiumParallelTestListener() throws Exception {
        try {
            appiumServerManager = new AppiumServerManager();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            appiumDriverManager = new AppiumDriverManager();
            generateReportJson = new GenerateReportJson();
            screenShotManager = new ScreenShotManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            appiumDriverManager.startAppiumDriverInstance();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        generateReportJson.getTestResultsAndQuitDriver(method, testResult);
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        generateReportJson.finalReportCreation(iSuite);
    }

    @Override
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device").toString();
            String className = testClass.getRealClass().getSimpleName();
            deviceAllocationManager.allocateDevice(device,
                    deviceAllocationManager.getNextAvailableDeviceId());
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
}
