package com.appium.manager;

import com.annotation.values.RetryCount;
import com.annotation.values.SkipIf;
import com.appium.capabilities.CapabilityManager;
import com.appium.device.GenyMotionManager;
import com.appium.utils.FileFilterParser;
import com.appium.utils.Helpers;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import org.json.simple.parser.ParseException;
import org.testng.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    private static ThreadLocal<HashMap<String, String>> testResults = new ThreadLocal<>();
    private List<ITestNGListener> listeners = new LinkedList<>();

    public AppiumParallelMethodTestListener() {
        testLogger = new TestLogger();
        appiumServerManager = new AppiumServerManager();
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        appiumDriverManager = new AppiumDriverManager();
        atdHost = Optional.ofNullable(CapabilityManager.getInstance()
            .getMongoDbHostAndPort().get("atdHost"));
        atdPort = Optional.ofNullable(CapabilityManager.getInstance()
            .getMongoDbHostAndPort().get("atdPort"));
        initialiseListeners();
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
        allocateDeviceAndStartDriver(iTestResult);
    }

    private boolean isCloudExecution() {
        return AppiumDeviceManager.getAppiumDevice().getDevice().isCloud();
    }

    private void startReportLogging(ITestResult iTestResult) throws IOException,
        InterruptedException {
        testLogger.startLogging(iTestResult);
    }

    /*
     * Skips execution based on platform
     */
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (!isCloudExecution()) {
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
        new TestExecutionContext(iInvokedMethod.getTestMethod().getMethodName());

        for (ITestNGListener listener : listeners) {
            //Lets filter out only IInvokedMethodListener instances.
            if (listener instanceof IInvokedMethodListener) {
                ((IInvokedMethodListener) listener).beforeInvocation(iInvokedMethod, iTestResult);
            }
        }
    }

    private void allocateDeviceAndStartDriver(ITestResult iTestResult) {
        try {
            deviceAllocationManager.allocateDevice(deviceAllocationManager
                .getNextAvailableDevice());
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
        try {
            if (!isCloudExecution() && !isRetry(iTestResult)) {
                HashMap<String, String> logs = testLogger.endLogging(iTestResult,
                    AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel());
                if (atdHost.isPresent() && atdPort.isPresent()) {
                    String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
                    sendResultsToAtdService(iTestResult, "Completed", url, logs);
                } else {
                    new FileFilterParser()
                        .getScreenShotPaths(AppiumDeviceManager.getAppiumDevice()
                            .getDevice().getUdid(), iTestResult);
                    testResults.set(logs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            AppiumDriverManager.getDriver().quit();
            deviceAllocationManager.freeDevice();
            try {
                if (!isCloudExecution()) {
                    appiumDriverManager.stopAppiumDriver();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SessionContext.remove(Thread.currentThread().getId());
        for (ITestNGListener listener : listeners) {
            //Lets filter out only IInvokedMethodListener instances.
            if (listener instanceof IInvokedMethodListener) {
                ((IInvokedMethodListener) listener).afterInvocation(iInvokedMethod, iTestResult);
            }
        }
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
        if (!isCloudExecution()) {
            System.out.println("In Skipped...");
            RetryCount retryCount = iTestResult.getMethod().getConstructorOrMethod()
                .getMethod().getAnnotation(RetryCount.class);
            if (retryCount == null && (atdHost.isPresent() && atdPort.isPresent())) {
                HashMap<String, String> logs = new HashMap<>();
                String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
                sendResultsToAtdService(iTestResult, "Completed", url, logs);
            }
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
        SessionContext.setReportPortalLaunchURL(iTestContext);
    }

    public static ITestNGMethod getTestMethod() {
        return checkNotNull(currentMethods.get(),
            "Did you forget to register the %s listener?",
            AppiumParallelMethodTestListener.class.getName());
    }

    private void initialiseListeners() {
        String file = "META-INF/services/listeners.txt";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getStream(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listeners.add(instantiate(line));
            }
        } catch (IOException e) {
            throw new TestNGException(e);
        }
    }

    private static InputStream getStream(String file) {
        InputStream stream;
        stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        if (stream == null) {
            throw new IllegalStateException("Unable to locate the file " + file);
        }
        return stream;
    }

    private static ITestNGListener instantiate(String className) {
        if (className == null || className.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid class name");
        }
        try {
            Class<?> clazz = Class.forName(className);
            if (!ITestNGListener.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException(className
                    + " does not implement a TestNG listener");
            }
            return (ITestNGListener) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new TestNGException(e);
        }
    }
}
