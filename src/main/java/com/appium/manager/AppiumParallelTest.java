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
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public synchronized void startAppiumServer(
            String device, String methodName, String tags) throws Exception {
        if (prop.containsKey("CI_BASE_URI")) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        if (device.isEmpty()) {
            System.out.println("Into Empty devices");
            device_udid = deviceManager.getNextAvailableDeviceId();
            System.out.println("Into block" + device_udid);
        } else {
            device_udid = device;
        }
        System.out.println("Devices*******" + device_udid);
        if (device_udid == null) {
            System.out.println("No devices are free to run test or Failed to run test");
        }
        System.out.println("****************Device*************" + device_udid);
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            getDeviceCategory();
        } else {
            category = androidDevice.getDeviceModel(device_udid);
        }
        //System.out.println("******Tags::::" + Arrays.toString(tags));
        //Will fix the tag once extent-report issue
        //https://github.com/anshooarora/extentreports-java/issues/757
        ExtentTest extentTest = createParentNodeExtent(methodName, "", category
                + device_udid).assignCategory(tags);

        checkOSAndStartServer(methodName);
    }


    public synchronized void startAppiumServer(String device) throws Exception {
        String className = getClass().getSimpleName();
        if (prop.containsKey("CI_BASE_URI")) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        if (device.isEmpty()) {
            device_udid = deviceManager.getNextAvailableDeviceId();
        } else {
            deviceManager.getNextAvailableDeviceId();
            device_udid = device;
        }

        if (device_udid == null) {
            System.out.println("No devices are free to run test or Failed to run test");
        }
        System.out.println("****************Device*************" + device_udid);
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            getDeviceCategory();
        } else {
            category = androidDevice.getDeviceModel(device_udid);
        }

        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            if (getClass().getAnnotation(Description.class) != null) {
                testDescription = getClass().getAnnotation(Description.class).value();
            }
            createParentNodeExtent(className, testDescription,
                    category + "_" + device_udid);
        }
        checkOSAndStartServer(className);
    }

    private void checkOSAndStartServer(String methodName) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (getMobilePlatform(device_udid).equals(MobilePlatform.IOS)) {
                appiumMan.appiumServerForIOS(device_udid, methodName);
            } else {
                appiumMan.appiumServerForAndroid(device_udid, methodName);
            }
        } else {
            appiumMan.appiumServerForAndroid(device_udid, methodName);
        }
    }

    public void getDeviceCategory() throws Exception {
        if (iosDevice.checkiOSDevice(device_udid)) {
            iosDevice.setIOSWebKitProxyPorts(device_udid);
            category = iosDevice.getDeviceName(device_udid).replace(" ", "_");
        } else if (!iosDevice.checkiOSDevice(device_udid)) {
            category = androidDevice.getDeviceModel(device_udid);
            System.out.println(category);
        }
    }

    public ExtentTest createParentNodeExtent(String methodName, String testDescription,
                                             String deviceId) {
        parent = ExtentTestManager.createTest(methodName, testDescription,
                deviceId);
        parentTest.set(parent);
        ExtentTestManager.getTest().log(Status.INFO,
                "<a target=\"_parent\" href=" + "appiumlogs/"
                        + device_udid + "__" + methodName
                        + ".txt" + ">AppiumServerLogs</a>");
        return parent;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(
            DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        Thread.sleep(3000);
        startingServerInstance(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        startLogResults(currentMethodName);
        return driver;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(
            String methodName, DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("cucumber")) {
            currentMethodName = methodName;
        }
        Thread.sleep(3000);
        startingServerInstance(Optional.ofNullable(iosCaps), Optional.ofNullable(androidCaps));
        startLogResults(currentMethodName);
        return driver;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName)
            throws Exception {
        return startAppiumServerInParallel(methodName, null, null);
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(
            String methodName, DesiredCapabilities caps) throws Exception {
        return startAppiumServerInParallel(methodName, caps, caps);
    }

    public AppiumParallelTest createChildNodeWithCategory(String methodName,
                                                          String tags) {
        child = parentTest.get().createNode(methodName, category
                + device_udid).assignCategory(tags);
        test.set(child);
        return this;
    }

    public void setAuthorName(IInvokedMethod methodName) throws NoSuchMethodException {
        String authorName;
        boolean methodNamePresent;
        ArrayList<String> listeners = new ArrayList<>();
        String descriptionMethodName;
        String description = methodName.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description();
        if (description.isEmpty()) {
            descriptionMethodName = methodName.getTestMethod().getMethodName();
        } else {
            descriptionMethodName = description;
        }
        if (methodName.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Author.class) != null) {
            methodNamePresent = true;
        } else {
            methodNamePresent = false;
        }
        if (methodNamePresent) {
            authorName = methodName.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = parentTest.get()
                    .createNode(descriptionMethodName,
                            category + "_" + device_udid).assignAuthor(
                            String.valueOf(listeners));
            test.set(child);
        } else {
            child = parentTest.get().createNode(descriptionMethodName,
                    category + "_" + device_udid);
            test.set(child);
        }
    }

    public void startingServerInstance(Optional<DesiredCapabilities> iosCaps,
                                       Optional<DesiredCapabilities> androidCaps)
            throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                    deviceCapabilityManager.androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (prop.getProperty("IOS_APP_PATH") != null
                        && iosDevice.checkiOSDevice(device_udid)) {
                    if (iosDevice.getIOSDeviceProductVersion(device_udid)
                        .contains("10")) {
                        iosCaps.get().setCapability(MobileCapabilityType.AUTOMATION_NAME,
                            AutomationName.IOS_XCUI_TEST);
                        iosCaps.get().setCapability(IOSMobileCapabilityType
                            .WDA_LOCAL_PORT,ports.getPort());
                    }
                    driver = new IOSDriver<>(appiumMan.getAppiumUrl(),
                            iosCaps.orElse(deviceCapabilityManager.iosNative(device_udid)));
                } else if (!iosDevice.checkiOSDevice(device_udid)) {
                    driver = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                            androidCaps
                                    .orElse(deviceCapabilityManager.androidNative(device_udid)));
                }
            } else {
                driver = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                        androidCaps
                                .orElse(deviceCapabilityManager.androidNative(device_udid)));
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
        testLogger.startLogging(methodName, driver, device_udid, getClass().getName());
    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {
        testLogger.endLog(result, device_udid, getDeviceModel(), test, driver);
    }

    private String getDeviceModel() throws InterruptedException, IOException {
        Capabilities capabilities = driver.getCapabilities();
        if (capabilities.getCapability("platformName")
                .toString().equals("Android")
                && capabilities.getCapability("browserName") == null) {
            deviceModel = androidDevice.getDeviceModel(device_udid);
        } else if (capabilities.getCapability("platformName").toString().equals("iOS")) {
            deviceModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
        }
        return deviceModel;
    }

    public synchronized void stopServerCucumber()
            throws IOException, InterruptedException {
        stopAppiumServerAndCloseReport();
    }

    public synchronized void killAppiumServer()
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
        if (getMobilePlatform(device_udid).equals(MobilePlatform.IOS)) {
            iosDevice.destroyIOSWebKitProxy();
        }
        deviceManager.freeDevice(device_udid);
    }

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    public void onTestStart(ITestResult result) {
        Object currentClass = result.getInstance();
        AppiumDriver<MobileElement> driver = ((AppiumParallelTest) currentClass).getDriver();
        SkipIf skip =
                result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(SkipIf.class);
        if (skip != null) {
            String info = skip.platform();
            if (driver.toString().split("\\(")[0].trim().toString().contains(info)) {
                System.out.println("skipping test");
                throw new SkipException("Skipped because property was set to :::" + info);
            }
        }
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

    public void captureScreenshot(String methodName, String device, String className)
            throws IOException, InterruptedException {
        String context = getDriver().getContext();
        boolean contextChanged = false;
        if ("Android".equals(getDriver().getSessionDetails().get("platformName").toString())
                && !"NATIVE_APP".equals(context)) {
            getDriver().context("NATIVE_APP");
            contextChanged = true;
        }
        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            getDriver().context(context);
        }
        FileUtils.copyFile(scrFile, new File(
                "screenshot/" + device + "/"
                        + device_udid
                        + "/" + className
                        + "/" + methodName + "/" + deviceModel + "_"
                        + methodName + "_failed" + ".jpeg"));
        Thread.sleep(3000);
    }

    public MobilePlatform getMobilePlatform(String device_udid) {
        MobilePlatform platform = null;

        if (device_udid.length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            platform = MobilePlatform.IOS;
        } else {
            platform = MobilePlatform.ANDROID;
        }
        return platform;
    }

    @Override
    public void onBeforeClass(ITestClass testClass, IMethodInstance mi) {
        try {
            System.out.printf("hi");
            startAppiumServer(testClass.getXmlClass().getAllParameters().get("device").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass testClass, IMethodInstance mi) {
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
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }
}
