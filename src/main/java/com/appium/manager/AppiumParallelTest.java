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
import com.video.recorder.Flick;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


import java.awt.Image;
import java.awt.Toolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AppiumParallelTest extends TestListenerAdapter implements ITestListener {

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
    private String className;

    public AppiumParallelTest() {
        try {
            iosDevice = new IOSDeviceConfiguration();
            androidDevice = new AndroidDeviceConfiguration();
            appiumMan = new AppiumManager();
            prop = ConfigurationManager.getInstance();
            deviceManager = DeviceManager.getInstance();
            testLogger = new TestLogger();
            deviceCapabilityManager = new DeviceCapabilityManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public synchronized AppiumServiceBuilder startAppiumServer(
            String device, String methodName,String[] tags) throws Exception {
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
            return null;
        }
        System.out.println("****************Device*************" + device_udid);
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            getDeviceCategory();
        } else {
            category = androidDevice.getDeviceModel(device_udid);
        }
        System.out.println("******Tags::::" + Arrays.toString(tags));
        //System.out.println("******" + tag.isEmpty() + "::::" + tag);
        ExtentTest extentTest = createParentNodeExtent(methodName, "", category
                        + device_udid.replaceAll("\\W", "_")).assignCategory(tags);

        AppiumServiceBuilder appiumServiceBuilder = checkOSAndStartServer(methodName);
        if (appiumServiceBuilder != null) {
            return appiumServiceBuilder;
        }

        return null;
    }


    @BeforeClass(alwaysRun = true)
    @Parameters({"device"})
    public synchronized AppiumServiceBuilder startAppiumServer(String device) throws Exception {
        String methodName = getClass().getSimpleName();
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
            return null;
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
            createParentNodeExtent(methodName, testDescription,
                    category + "_" + device_udid.replaceAll("\\W", "_"));
        }
        AppiumServiceBuilder webKitPort = checkOSAndStartServer(methodName);
        if (webKitPort != null) {
            return webKitPort;
        }
        return null;
    }

    public AppiumServiceBuilder checkOSAndStartServer(String methodName) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (getMobilePlatform(device_udid).equals(MobilePlatform.IOS)) {
                AppiumServiceBuilder webKitPort = getAppiumServiceBuilder(methodName);
                if (webKitPort != null) {
                    return webKitPort;
                }
            } else {
                return appiumMan.appiumServerForAndroid(device_udid, methodName);
            }
        } else {
            return appiumMan.appiumServerForAndroid(device_udid, methodName);
        }
        return null;
    }

    public AppiumServiceBuilder getAppiumServiceBuilder(String methodName) throws Exception {
        String webKitPort = iosDevice.startIOSWebKit(device_udid);
        return appiumMan.appiumServerForIOS(device_udid, methodName, webKitPort);
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
                        + device_udid.replaceAll("\\W", "_") + "__" + methodName
                        + ".txt" + ">AppiumServerLogs</a>");
        return parent;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(
            String methodName, DesiredCapabilities iosCaps,
            DesiredCapabilities androidCaps) throws Exception {
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            setAuthorName(methodName);
        }
        Thread.sleep(3000);
        startingServerInstance(iosCaps, androidCaps);
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
                                                          String[] tags) {
        child = parentTest.get().createNode(methodName, category
                        + device_udid.replaceAll("\\W", "_")).assignCategory(tags);
        test.set(child);
        return this;
    }

    public void setAuthorName(String methodName) throws NoSuchMethodException {
        String authorName;
        ArrayList<String> listeners = new ArrayList<>();
        if (getClass().getMethod(methodName).getAnnotation(Author.class) != null) {
            authorName = getClass().getMethod(methodName).getAnnotation(Author.class).name();
            Collections.addAll(listeners, authorName.split("\\s*,\\s*"));
            child = parentTest.get()
                    .createNode(methodName,
                            category + "_" + device_udid.replaceAll("\\W", "_")).assignAuthor(
                            String.valueOf(listeners));
            test.set(child);
        } else {
            child = parentTest.get().createNode(methodName,
                    category + "_" + device_udid.replaceAll("\\W", "_"));
            test.set(child);
        }
    }

    public void startingServerInstance(DesiredCapabilities iosCaps, DesiredCapabilities androidCaps)
            throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumMan.getAppiumUrl(),
                    deviceCapabilityManager.androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (prop.getProperty("IOS_APP_PATH") != null
                        && iosDevice.checkiOSDevice(device_udid)) {
                    if (iosCaps == null) {
                        iosCaps = deviceCapabilityManager.iosNative(device_udid);
                    }
                    driver = new IOSDriver<>(appiumMan.getAppiumUrl(), iosCaps);
                } else if (!iosDevice.checkiOSDevice(device_udid)) {
                    if (androidCaps == null) {
                        androidCaps = deviceCapabilityManager.androidNative(device_udid);
                    }
                    //checkSelendroid(androidCaps);
                    driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidCaps);
                }
            } else {
                if (androidCaps == null) {
                    androidCaps = deviceCapabilityManager.androidNative(device_udid);
                }
                //checkSelendroid(androidCaps);
                driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidCaps);
            }
        }
    }

    public void startingServerInstance() throws Exception {
        startingServerInstance(null, null);
    }

    public void startingServerInstance(DesiredCapabilities caps) throws Exception {
        startingServerInstance(caps, caps);
    }

    public DesiredCapabilities checkSelendroid(DesiredCapabilities androidCaps) {
        int android_api = 0;
        try {
            System.out.println("System API Level" + androidDevice.getDevices().get(device_udid));
            String deviceAPI = androidDevice.getDevices().get(device_udid);
            android_api = Integer.parseInt(deviceAPI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Android API Level::" + android_api);
        if (android_api <= 16) {
            androidCaps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
        }
        return androidCaps;
    }

    public void startLogResults(String methodName) throws FileNotFoundException {
        testLogger.startLogging(methodName, driver, device_udid,getClass().getName());
    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {
        if (driver.getSessionDetails().get("platformName").toString().equals("Android")) {
            deviceModel = androidDevice.getDeviceModel(device_udid);
        }else if (driver.getSessionDetails().get("platformName").toString().equals("iOS")) {
            deviceModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
        }
        testLogger.endLog(result, device_udid, deviceModel, test, driver);
    }

    @AfterClass(alwaysRun = true)
    public synchronized void killAppiumServer()
            throws InterruptedException, IOException {
        System.out.println("**************ClosingAppiumSession****************"
                + Thread.currentThread().getId());
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            //ExtentManager.getInstance().endTest(parent);
            ExtentManager.getExtent().flush();
        }
        appiumMan.destroyAppiumNode();
        if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            iosDevice.destroyIOSWebKitProxy();
        }
        deviceManager.freeDevice(device_udid);
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public void waitForElement(By id, int time) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable((id)));
    }

    public void resetAppData() throws InterruptedException, IOException {
        androidDevice.clearAppData(device_udid, prop.getProperty("APP_PACKAGE"));
    }

    public void closeOpenApp() throws InterruptedException, IOException {
        androidDevice.closeRunningApp(device_udid, prop.getProperty("APP_PACKAGE"));
    }

    public void removeApkFromDevice(String app_package) throws Exception {
        androidDevice.removeApkFromDevices(device_udid, app_package);
    }

    public void deleteAppIOS(String bundleID) throws InterruptedException, IOException {
        iosDevice.unInstallApp(device_udid, bundleID);
    }

    public void installAppIOS(String appPath) throws InterruptedException, IOException {
        iosDevice.installApp(device_udid, appPath);
    }

    public Boolean checkAppIsInstalled(String bundleID) throws InterruptedException, IOException {
        return iosDevice.checkIfAppIsInstalled(bundleID);
    }

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    public void onTestStart(ITestResult result) {
        Object currentClass = result.getInstance();
        className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println("on test start:::" + className);
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

    public void captureScreenshot(String methodName, String device, String className)
            throws IOException, InterruptedException {
        String context = getDriver().getContext();
        boolean contextChanged = false;
        if (getDriver().getSessionDetails().get("platformName").toString().equals("Android") && !context
                .equals("NATIVE_APP")) {
            getDriver().context("NATIVE_APP");
            contextChanged = true;
        }
        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            getDriver().context(context);
        }
        FileUtils.copyFile(scrFile, new File(
                "screenshot/" + device + "/"
                        + device_udid.replaceAll("\\W", "_")
                        + "/" + className
                        + "/" + methodName + "/" + deviceModel + "_"
                        + methodName + "_failed" + ".png"));
        Thread.sleep(3000);
    }

    public void logger(String message) {
        ExtentTestManager.logger(message);
    }

    public void captureScreenShot(String screenShotName) throws InterruptedException, IOException {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        String className = new Exception().getStackTrace()[1].getClassName();
        //testLogger.captureScreenShot(screenShotName, 1, className, methodName, this);
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

}
