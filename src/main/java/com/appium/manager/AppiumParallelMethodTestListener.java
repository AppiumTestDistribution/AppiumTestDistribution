package com.appium.manager;

import com.annotation.values.MultiATDDriver;
import com.annotation.values.RetryCount;
import com.annotation.values.SkipIf;
import com.appium.capabilities.CapabilityManager;
import com.appium.device.GenyMotionManager;
import com.appium.utils.Helpers;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.json.simple.parser.ParseException;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public final class AppiumParallelMethodTestListener extends Helpers
    implements ITestListener, IInvokedMethodListener, ISuiteListener {

    private DeviceAllocationManager deviceAllocationManager;
    private AppiumServerManager appiumServerManager;
    private AppiumDriverManager appiumDriverManager;
    private static ThreadLocal<ITestNGMethod> currentMethods = new ThreadLocal<>();
    TestLogger testLogger;

    public AppiumParallelMethodTestListener() {
        testLogger = new TestLogger();
        appiumServerManager = new AppiumServerManager();
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        appiumDriverManager = new AppiumDriverManager();
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


    private boolean isCloudExecution() {
        return AppiumDeviceManager.getAppiumDevices()
            .stream().allMatch(device -> device.getDevice().isCloud());
    }

    private void startReportLogging(ITestResult iTestResult) throws IOException,
        InterruptedException {
        testLogger.startLogging(iTestResult);
    }

    /*
     * Skips execution based on platform
     * Allocates Devices to each test method
     * Instantiate Android or IOS Driver Instance
     * Starts ADB, Video Logging
     * Set Description for each test method with platform and UDID allocated to it.
     */
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        BeforeMethod beforeMethodAnnotation = iInvokedMethod.getTestMethod()
            .getConstructorOrMethod().getMethod().getAnnotation(BeforeMethod.class);
        BeforeTest beforeTestAnnotation = iInvokedMethod.getTestMethod()
            .getConstructorOrMethod().getMethod().getAnnotation(BeforeTest.class);
        if (beforeTestAnnotation != null) {
            throw new RuntimeException("ATD will only support @BeforeMethod annotation.");
        }

        if (beforeMethodAnnotation != null || AppiumDriverManager.getDrivers() == null) {
            allocateDeviceAndStartDriver(iTestResult);
        }

        if (!isCloudExecution()) {
            currentMethods.set(iInvokedMethod.getTestMethod());
            SkipIf annotation = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(SkipIf.class);
            if (annotation != null) {
                List<AppiumDriver<MobileElement>> drivers = AppiumDriverManager.getDrivers();
                if (!(drivers.size() > 1)) {
                    // TODO check for driver to skip
                    throw new SkipException("Skipped because property was set to :::"
                        + annotation.platform());
                }
            }
        }
        new TestExecutionContext(iInvokedMethod.getTestMethod().getMethodName());
    }

    private void allocateDeviceAndStartDriver(ITestResult iTestResult) {
        MultiATDDriver multiATDDriver = iTestResult.getMethod().getConstructorOrMethod()
            .getMethod().getAnnotation(MultiATDDriver.class);
        int numberOfMultiTestDrivers = 1;
        if (multiATDDriver != null) {
            numberOfMultiTestDrivers = multiATDDriver.devices();
        }
        try {
            deviceAllocationManager.allocateDevice(deviceAllocationManager
                .getNextAvailableDevice(numberOfMultiTestDrivers));
            appiumDriverManager.startAppiumDriverInstance();
            if (!isCloudExecution()) {
                startReportLogging(iTestResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Stops driver after each test method execution
     * De-allocates device after each test method execution
     * Terminate logs getting captured after each test method execution
     */
    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        Test annotation = iInvokedMethod.getTestMethod()
            .getConstructorOrMethod().getMethod().getAnnotation(Test.class);
        if (annotation != null) {
            try {
                if (!isCloudExecution() && !isRetry(iTestResult)) {
                    HashMap<String, String> logs = testLogger.endLogging(iTestResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                deviceAllocationManager.freeDevice();
                try {
                    if (!isCloudExecution()) {
                        appiumDriverManager.stopAppiumDriver();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        SessionContext.remove(Thread.currentThread().getId());

    }

    /*
     * Stops Appium Server after each test suite
     */
    @Override
    public void onFinish(ISuite iSuite) {
        if (CapabilityManager.getInstance()
            .getCapabilityObjectFromKey("genycloud") != null) {
            try {
                new GenyMotionManager().stopAllGenymotionInstances();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*
     * Document to make codacy happy
     */
    @Override
    public void onFinish(ITestContext iTestContext) {
        SessionContext.setReportPortalLaunchURL(iTestContext);
    }

    public static ITestNGMethod getTestMethod() {
        return checkNotNull(currentMethods.get(),
            "Did you forget to register the %s listener?",
            AppiumParallelMethodTestListener.class.getName());
    }
}
