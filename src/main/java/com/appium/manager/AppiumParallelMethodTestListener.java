package com.appium.manager;

import com.annotation.values.Author;
import com.annotation.values.RetryCount;
import com.annotation.values.SkipIf;
import com.appium.utils.CapabilityManager;
import com.appium.utils.FileFilterParser;
import com.appium.utils.Helpers;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public final class AppiumParallelMethodTestListener extends Helpers
        implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private TestLogger testLogger;
    private DeviceAllocationManager deviceAllocationManager;
    private AppiumServerManager appiumServerManager;
    private AppiumDriverManager appiumDriverManager;
    private Optional<String> atdHost;
    private Optional<String> atdPort;
    private static ThreadLocal<ITestNGMethod> currentMethods = new ThreadLocal<>();

    public AppiumParallelMethodTestListener() throws Exception {
        try {
            testLogger = new TestLogger();
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
            deviceAllocationManager.allocateDevice(deviceAllocationManager
                    .getNextAvailableDevice());
            appiumDriverManager.startAppiumDriverInstance();
            testLogger.startLogging(iTestResult.getMethod().getMethodName(),
                    iTestResult.getTestClass()
                            .getRealClass().getSimpleName());
            // Sets description for each test method with platform and Device UDID allocated to it.
            Optional<String> originalDescription = Optional.ofNullable(iTestResult
                    .getMethod().getDescription());
            String description = "Platform: " + AppiumDeviceManager.getMobilePlatform()
                    + " UDID: " + AppiumDeviceManager.getAppiumDevice()
                    .getDevice().getUdid()
                    + " Name: " + AppiumDeviceManager.getAppiumDevice()
                    .getDevice().getName()
                    + " Host: " + AppiumDeviceManager.getAppiumDevice().getHostName();
            Author annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                    .getAnnotation(Author.class);
            if (annotation != null) {
                description += "\nAuthor: " + annotation.name();
            }
            if (originalDescription.isPresent()
                    && !originalDescription.get().contains(AppiumDeviceManager.getAppiumDevice()
                    .getDevice().getUdid())) {
                iTestResult.getMethod().setDescription(originalDescription.get()
                        + "\n" + description);
            } else {
                iTestResult.getMethod().setDescription(description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Skips execution based on platform
     */
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        currentMethods.set(iInvokedMethod.getTestMethod());
        SkipIf annotation = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(SkipIf.class);
        if (annotation != null && AppiumDriverManager.getDriver().getPlatformName()
                .equalsIgnoreCase(annotation.platform())) {
            if (atdHost.isPresent() && atdPort.isPresent()) {
                HashMap<String, String> logs = new HashMap<>();
                String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
                sendResultsToAtdService(iTestResult, "Completed", url, logs);
            }
            throw new SkipException("Skipped because property was set to :::"
                    + annotation.platform());
        }
    }

    /*
     * Stops driver after each test method execution
     * De-allocates device after each test method execution
     * Terminate logs getting captured after each test method execution
     */
    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        try {
            if (!isRetry(iTestResult)) {
                HashMap<String, String> logs = testLogger.endLogging(iTestResult,
                        AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel());
                if (atdHost.isPresent() && atdPort.isPresent()) {
                    String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
                    sendResultsToAtdService(iTestResult, "Completed", url, logs);
                } else {
                    new FileFilterParser()
                            .getScreenShotPaths(AppiumDeviceManager.getAppiumDevice()
                                    .getDevice().getUdid(), iTestResult);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

    /*
     * Document to make codacy happy
     */
    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    /*
     * Document to make codacy happy
     */
    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    /*
     * Document to make codacy happy
     */
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("In Skipped...");
        RetryCount retryCount = iTestResult.getMethod().getConstructorOrMethod()
                .getMethod().getAnnotation(RetryCount.class);
        if (retryCount == null && (atdHost.isPresent() && atdPort.isPresent())) {
            HashMap<String, String> logs = new HashMap<>();
            String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
            sendResultsToAtdService(iTestResult, "Completed", url, logs);
        }

    }

    /*
     * Document to make codacy happy
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    /*
     * Document to make codacy happy
     */
    @Override
    public void onStart(ITestContext iTestContext) {

    }

    /*
     * Document to make codacy happy
     */
    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    public static ITestNGMethod getTestMethod() {
        return checkNotNull(currentMethods.get(),
                "Did you forget to register the %s listener?",
                AppiumParallelMethodTestListener.class.getName());
    }
}
