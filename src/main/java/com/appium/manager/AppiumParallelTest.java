package com.appium.manager;

import com.annotation.values.Author;
import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.MobilePlatform;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.Test;


import java.awt.Image;
import java.awt.Toolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AppiumParallelTest implements ITestListener, IClassListener, IInvokedMethodListener {

    public DeviceCapabilityManager deviceCapabilityManager;
    private TestLogger testLogger;
    private DeviceManager deviceManager;
    public ConfigurationManager prop;
    public IOSDeviceConfiguration iosDevice;
    public static ConcurrentHashMap<String, Boolean> deviceMapping =
            new ConcurrentHashMap<String, Boolean>();
    public AppiumDriver<MobileElement> driver = null;
    public AppiumManager appiumMan;
    public String device_udid;
    public String category = null;
    public String deviceModel;
    public File scrFile = null;
    public String testDescription = "";
    private String CI_BASE_URI = null;
    public ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    public ExtentTest parent;
    public ExtentTest child;
    private AndroidDeviceConfiguration androidDevice;
    public AvailablePorts ports;
    private String currentMethodName = null;
    DeviceSingleton deviceSingleton;

    private AppiumDriver<MobileElement> currentDriverSession;


    public AppiumParallelTest() {
        try {
            iosDevice = new IOSDeviceConfiguration();
            androidDevice = new AndroidDeviceConfiguration();
            appiumMan = new AppiumManager();
            prop = ConfigurationManager.getInstance();
            deviceManager = DeviceManager.getInstance();
            testLogger = new TestLogger();
            deviceCapabilityManager = new DeviceCapabilityManager();
            ports = new AvailablePorts();
            deviceSingleton = DeviceSingleton.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public void startAppiumServer(
            String device, String methodName, String tags) throws Exception {
        if (prop.containsKey("CI_BASE_URI")) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        if (device.isEmpty()) {
            DeviceUDIDManager.setDeviceUDID(deviceSingleton.getDeviceUDID());
        } else {
            DeviceUDIDManager.setDeviceUDID(device);
        }
        if (DeviceUDIDManager.getDeviceUDID() == null) {
            System.out.println("No devices are free to run test or Failed to run test");
        }
        System.out.println("****************Device*************" + DeviceUDIDManager.getDeviceUDID());
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            getDeviceCategory();
        } else {
            category = androidDevice.getDeviceModel();
        }
        //System.out.println("******Tags::::" + Arrays.toString(tags));
        //Will fix the tag once extent-report issue
        //https://github.com/anshooarora/extentreports-java/issues/757
        ExtentTest extentTest = createParentNodeExtent(methodName, "").assignCategory(tags);

        checkOSAndStartServer(methodName);
    }


    public void startAppiumServer(String device) throws Exception {
        if (prop.containsKey("CI_BASE_URI")) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        if (device.isEmpty()) {
            DeviceUDIDManager.setDeviceUDID(deviceManager.getNextAvailableDeviceId());
        } else {
            deviceManager.getNextAvailableDeviceId();
            DeviceUDIDManager.setDeviceUDID(device);
        }

        if (DeviceUDIDManager.getDeviceUDID() == null) {
            System.out.println("No devices are free to run test or Failed to run test");
        }
        System.out.println("****************Device*************" + DeviceUDIDManager.getDeviceUDID());
    }

    public void getCatAndStartServer(String className) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            getDeviceCategory();
        } else {
            category = androidDevice.getDeviceModel();
        }
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            if (getClass().getAnnotation(Description.class) != null) {
                testDescription = getClass().getAnnotation(Description.class).value();
            }
            createParentNodeExtent(className, testDescription);
        }
        checkOSAndStartServer(className);
    }

    private void checkOSAndStartServer(String methodName) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (getMobilePlatform().equals(MobilePlatform.IOS)) {
                appiumMan.appiumServerForIOS(methodName);
            } else {
                appiumMan.appiumServerForAndroid(methodName);
            }
        } else {
            appiumMan.appiumServerForAndroid(methodName);
        }
    }

    public synchronized void getDeviceCategory() throws Exception {
        if (iosDevice.checkiOSDevice()) {
            iosDevice.setIOSWebKitProxyPorts();
            category = iosDevice.getDeviceName().replace(" ", "_");
        } else {
            category = androidDevice.getDeviceModel();
            System.out.println(category + Thread.currentThread().getId());
        }
    }

    public ExtentTest createParentNodeExtent(String methodName, String testDescription) {
        parent = ExtentTestManager.createTest(methodName, testDescription,
                DeviceUDIDManager.getDeviceUDID());
        parentTest.set(parent);
        ExtentTestManager.getTest().log(Status.INFO,
                "<a target=\"_parent\" href=" + "appiumlogs/"
                        + DeviceUDIDManager.getDeviceUDID() + "__" + methodName
                        + ".txt" + ">AppiumServerLogs</a>");
        return parent;
    }

    public void startAppiumServerInParallel(
            DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        Thread.sleep(3000);
        startingServerInstance(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        startLogResults(currentMethodName);
    }

    public void startAppiumServerInParallel(
            String methodName, DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("cucumber")) {
            currentMethodName = methodName;
        }
        Thread.sleep(3000);
        startingServerInstance(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        startLogResults(currentMethodName);
    }

    public void startAppiumServerInParallel(String methodName)
            throws Exception {
         startAppiumServerInParallel(methodName, null, null);
    }

    public void startAppiumServerInParallel(
            String methodName, DesiredCapabilities caps) throws Exception {
          startAppiumServerInParallel(methodName, caps, caps);
    }

    public AppiumParallelTest createChildNodeWithCategory(String methodName,
                                                          String tags) {
        child = parentTest.get().createNode(methodName, category
                + DeviceUDIDManager.getDeviceUDID()).assignCategory(tags);
        test.set(child);
        return this;
    }

    public void setAuthorName(IInvokedMethod methodName) throws NoSuchMethodException {
        String authorName;
        boolean methodNamePresent;
        ArrayList<String> listeners = new ArrayList<>();
        String descriptionMethodName;
        String description = methodName.getTestMethod()
                .getConstructorOrMethod().getMethod()
                    .getAnnotation(Test.class).description();
        if (description.isEmpty()) {
            descriptionMethodName = methodName.getTestMethod().getMethodName();
        } else {
            descriptionMethodName = description;
        }
        if (methodName.getTestMethod().getConstructorOrMethod()
                .getMethod().getAnnotation(Author.class) != null) {
            methodNamePresent = true;
        } else {
            methodNamePresent = false;
        }
        if (methodNamePresent) {
            authorName = methodName.getTestMethod()
                    .getConstructorOrMethod().getMethod()
                        .getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = parentTest.get()
                    .createNode(descriptionMethodName,
                            category + "_" + DeviceUDIDManager.getDeviceUDID()).assignAuthor(
                            String.valueOf(listeners));
            test.set(child);
        } else {
            child = parentTest.get().createNode(descriptionMethodName,
                    category + "_" + DeviceUDIDManager.getDeviceUDID());
            test.set(child);
        }
    }

    public void startingServerInstance(Optional<DesiredCapabilities> iosCaps,
                                       Optional<DesiredCapabilities> androidCaps)
            throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            currentDriverSession = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                    deviceCapabilityManager.androidWeb());
            DriverManager.setWebDriver(currentDriverSession);
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (prop.getProperty("IOS_APP_PATH") != null
                        && iosDevice.checkiOSDevice()) {
                    if (iosDevice.getIOSDeviceProductVersion()
                        .contains("10")) {
                        iosCaps.get().setCapability(MobileCapabilityType.AUTOMATION_NAME,
                            AutomationName.IOS_XCUI_TEST);
                        iosCaps.get().setCapability(IOSMobileCapabilityType
                            .WDA_LOCAL_PORT,ports.getPort());
                    }
                    currentDriverSession = new IOSDriver<>(appiumMan.getAppiumUrl(),
                            iosCaps.orElse(deviceCapabilityManager.iosNative()));
                    DriverManager.setWebDriver(currentDriverSession);

                } else if (!iosDevice.checkiOSDevice()) {
                    currentDriverSession = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                            deviceCapabilityManager.androidNative());
                    DriverManager.setWebDriver(currentDriverSession);
                }
            } else {
                currentDriverSession = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                        androidCaps
                                .orElse(deviceCapabilityManager.androidNative()));
                DriverManager.setWebDriver(currentDriverSession);
            }
        }
    }

    public void startingServerInstance() throws Exception {
        startingServerInstance(Optional.empty(), Optional.empty());
    }

    public void startingServerInstance(DesiredCapabilities caps) throws Exception {
        startingServerInstance(Optional.ofNullable(caps), Optional.ofNullable(caps));
    }

    private void startLogResults(String methodName) throws FileNotFoundException {
        testLogger.startLogging(methodName, getClass().getName());
    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {
        testLogger.endLog(result,getDeviceModel(), test);
    }

    private String getDeviceModel() throws InterruptedException, IOException {
        if (DriverManager.getDriver().getCapabilities().getCapability("platformName")
                .toString().equals("Android")
                && DriverManager.getDriver().getCapabilities().getCapability("browserName")
                    .toString().isEmpty()) {
            deviceModel = androidDevice.getDeviceModel();
        } else if (DriverManager.getDriver().getCapabilities().getCapability("platformName").toString().equals("iOS")) {
            deviceModel = iosDevice.getIOSDeviceProductTypeAndVersion();
        }
        return deviceModel;
    }

    public void stopServerCucumber()
            throws IOException, InterruptedException {
        stopAppiumServerAndCloseReport();
    }

    public void killAppiumServer()
            throws InterruptedException, IOException {
        stopAppiumServerAndCloseReport();
    }

    public void stopAppiumServerAndCloseReport() throws IOException, InterruptedException {
        System.out.println("**************ClosingAppiumSession****************"
                + Thread.currentThread().getId());
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            //ExtentManager.getInstance().endTest(parent);
            ExtentManager.getExtent().flush();
        }
        appiumMan.destroyAppiumNode();
        if (getMobilePlatform().equals(MobilePlatform.IOS)) {
            iosDevice.destroyIOSWebKitProxy();
        }
        deviceManager.freeDevice();
    }

    public AppiumDriver<MobileElement> getDriver() {
        return DriverManager.getDriver();
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

    public MobilePlatform getMobilePlatform() {
        MobilePlatform platform = null;

        if (DeviceUDIDManager.getDeviceUDID().length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            platform = MobilePlatform.IOS;
        } else {
            platform = MobilePlatform.ANDROID;
        }
        return platform;
    }

    @Override
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device").toString();
            System.out.println("Current Thread:" + Thread.currentThread().getId() + "::" + device);
            String className = testClass.getRealClass().getSimpleName();
            DeviceUDIDManager.setDeviceUDID(device);
            getCatAndStartServer(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass testClass) {
        try {
            killAppiumServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            setAuthorName(method);
            currentMethodName = method.getTestMethod().getMethodName();
            startAppiumServerInParallel(currentMethodName);
            SkipIf skip =
                    method.getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod().getAnnotation(SkipIf.class);
            if (skip != null) {
                String info = skip.platform();
                if (DriverManager.getDriver().toString().split("\\(")[0].trim().toString().contains(info)) {
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
            endLogTestResults(testResult);
            DriverManager.getDriver().quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //DriverManager.getDriver().quit();
    }
}
