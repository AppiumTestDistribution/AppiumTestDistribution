package com.appium.manager;

import com.annotation.values.Author;
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
import org.testng.SkipException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;


public final class AppiumParallelTestListener extends Helpers
        implements IClassListener, IInvokedMethodListener, ISuiteListener, ITestListener {

    private TestLogger testLogger;
    private DeviceAllocationManager deviceAllocationManager;
    private AppiumServerManager appiumServerManager;
    private AppiumDriverManager appiumDriverManager;
    private Optional<String> atdHost;
    private Optional<String> atdPort;

    public AppiumParallelTestListener() throws Exception {
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
     * Handle Skipif annotation
     * SendResults to ATD service if required
     */
    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult testResult) {
        if(atdHost.isPresent() && atdPort.isPresent()) {
            String postTestResults = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
            sendResultsToAtdService(testResult, "Started", postTestResults, new HashMap<>());
        }
        SkipIf annotation = iInvokedMethod.getTestMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(SkipIf.class);
        if(annotation != null && AppiumDriverManager.getDriver().getPlatformName()
                .equalsIgnoreCase(annotation.platform())) {
            throw new SkipException("Skipped because property was set to :::" + annotation.platform());
        }
    }

    /*
     * Send results to ATD service if required
     * Stop Appium Driver after method invocation completed
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        JSONObject json = new JSONObject();
        json.put("id", AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
        json.put("version", new AppiumDeviceManager().getDeviceVersion());
        json.put("platform", AppiumDeviceManager.getMobilePlatform());
        json.put("model", new AppiumDeviceManager().getDeviceModel());
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS
                    || testResult.getStatus() == ITestResult.FAILURE) {
                HashMap<String, String> logs = testLogger.endLogging(testResult
                        , AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel());
                if (atdHost.isPresent() && atdPort.isPresent()) {
                    String postTestResults = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
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
     * Allocates device for test execution
     */
    @Override
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device");
            String hostName = testClass.getXmlClass().getAllParameters().get("hostName");
            DevicesByHost devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
            AppiumDevice appiumDevice = devicesByHost.getAppiumDevice(device, hostName);
            deviceAllocationManager.allocateDevice(appiumDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Deallocated device post test execution
     */
    @Override
    public void onAfterClass(ITestClass iTestClass) {
        deviceAllocationManager.freeDevice();
    }

    /*
     * Sets description for each test method with platform and Device UDID allocated to it.
     * Starts Driver instance for execution
     */
    @Override
    public void onTestStart(ITestResult iTestResult) {
        try {
            appiumDriverManager.startAppiumDriverInstance();
            testLogger.startLogging(iTestResult.getMethod().getMethodName(),
                    iTestResult.getTestClass().getRealClass().getSimpleName());
            // Sets description for each test method with platform and Device UDID allocated to it.
            Optional<String> originalDescription = Optional.ofNullable(iTestResult.getMethod().getDescription());
            String description = "Platform: " + AppiumDeviceManager.getMobilePlatform()
                    + " Device UDID: " + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
            Author annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                    .getAnnotation(Author.class);
            if (annotation != null) {
                description += "\nAuthor: " + annotation.name();
            }
            if (originalDescription.isPresent() &&
                    !originalDescription.get().contains(AppiumDeviceManager.getAppiumDevice().getDevice().getUdid())) {
                iTestResult.getMethod().setDescription(originalDescription.get() + "\n" + description);
            } else {
                iTestResult.getMethod().setDescription(description);
            }
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
        System.out.println("Skipped...");
        if (atdHost.isPresent() && atdPort.isPresent()) {
            String url = "http://" + atdHost.get() + ":" + atdPort.get() + "/testresults";
            sendResultsToAtdService(iTestResult, "UnKnown", url, new HashMap<>());
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
}
