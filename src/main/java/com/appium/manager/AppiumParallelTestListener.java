package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.utils.GenerateReportJson;

import com.appium.utils.ScreenShotManager;
import javafx.stage.Screen;
import org.json.JSONObject;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

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
        HashMap<String,String> logs = new HashMap<>();
        JSONObject json = new JSONObject();
        json.put("id", DeviceManager.getDeviceUDID());
        json.put("version", new DeviceManager().getDeviceVersion());
        json.put("platform", DeviceManager.getMobilePlatform());
        //json.put("resolution", DeviceManager.getMobilePlatform());
        try {
            json.put("model", new DeviceManager().getDeviceModel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS
                    || testResult.getStatus() == ITestResult.FAILURE) {
                if(testResult.getStatus() == ITestResult.FAILURE) {
                    screenShotManager.captureScreenShot(2,
                            testResult.getClass().getSimpleName(),
                            testResult.getMethod().getMethodName());
                    String screenShotFailure;
                    if (new File(System.getProperty("user.dir")
                            + "/target/" + screenShotManager.getFailedScreen()).exists()) {
                        screenShotFailure = screenShotManager.getFailedScreen();
                        logs.put("screenShotFailure",screenShotFailure);
                    } else if (new File(System.getProperty("user.dir")
                            + "/target/" + screenShotManager.getFramedFailedScreen()).exists()) {
                        screenShotFailure = screenShotManager.getFramedFailedScreen();
                        logs.put("screenShotFailure",screenShotFailure);
                    }
                }
                JSONObject status = generateReportJson.getStatus(json,logs,
                        getExecutionStatus(testResult),
                        String.valueOf(testResult.getThrowable()),
                        method.getTestMethod().getMethodName(),
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
        generateReportJson.syncal.add(message);
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
