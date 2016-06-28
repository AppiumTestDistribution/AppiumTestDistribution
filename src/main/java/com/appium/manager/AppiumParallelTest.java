package com.appium.manager;

import com.annotation.values.SkipIf;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.ImageUtils;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
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

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class AppiumParallelTest extends TestListenerAdapter implements ITestListener {
    public AppiumDriver<MobileElement> driver = null;
    public AppiumManager appiumMan = new AppiumManager();
    public String device_udid;
    public List<LogEntry> logEntries;
    public File logFile;
    public PrintWriter log_file_writer;
    public DesiredCapabilities capabilities = new DesiredCapabilities();
    public String category = null;
    public ExtentTest parent;
    public ExtentTest child;
    public String deviceModel;
    public File scrFile = null;
    String screenShotNameWithTimeStamp;

    private Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();
    private static ArrayList<String> devices = new ArrayList<String>();
    public static Properties prop = new Properties();
    public static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
    public static AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
    public static ConcurrentHashMap<String, Boolean> deviceMapping = new ConcurrentHashMap<String, Boolean>();
    public ImageUtils imageUtils = new ImageUtils();

    static {
        try {
            prop.load(new FileInputStream("config.properties"));
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (iosDevice.getIOSUDID() != null) {
                    System.out.println("Adding iOS devices");
                    devices.addAll(iosDevice.getIOSUDID());
                }
                if (androidDevice.getDeviceSerial() != null) {
                    System.out.println("Adding Android devices");
                    devices.addAll(androidDevice.getDeviceSerial());
                }
            } else {
                if (androidDevice.getDeviceSerial() != null) {
                    System.out.println("Adding Android devices");
                    devices.addAll(androidDevice.getDeviceSerial());
                }
            }
            for (String device : devices) {
                deviceMapping.put(device, true);
            }
            System.out.println(deviceMapping);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to initialize framework");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to initialize framework");
        }
    }

    public static synchronized String getNextAvailableDeviceId() {
        ConcurrentHashMap.KeySetView<String, Boolean> devices = deviceMapping.keySet();
        int i = 0;
        for (String device : devices) {
            Thread t = Thread.currentThread();
            t.setName("Thread_" + i);
            System.out.println("Parallel Thread is::" + t.getName());
            i++;
            if (deviceMapping.get(device) == true) {
                deviceMapping.put(device, false);
                return device;
            }
        }
        return null;
    }

    public static void freeDevice(String deviceId) {
        deviceMapping.put(deviceId, true);
    }

    public synchronized AppiumServiceBuilder startAppiumServer(String methodName) throws Exception {
        device_udid = getNextAvailableDeviceId();
        if (device_udid == null) {
            System.out.println("No devices are free to run test or Failed to run test");
            return null;
        }
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (iosDevice.checkiOSDevice(device_udid)) {
                iosDevice.setIOSWebKitProxyPorts(device_udid);
                category = iosDevice.getDeviceName(device_udid).replace(" ", "_");
            } else if (!iosDevice.checkiOSDevice(device_udid)) {
                category = androidDevice.getDeviceModel(device_udid);
                System.out.println(category);
            }
        } else {
            category = androidDevice.getDeviceModel(device_udid);
        }

        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            parent = ExtentTestManager.startTest(methodName, "Mobile Appium Test",
                    category + "_" + device_udid.replaceAll("\\W", "_"));
            parentContext.put(Thread.currentThread().getId(), parent);
            ExtentTestManager.getTest().log(LogStatus.INFO, "AppiumServerLogs",
                    "<a href=" + System.getProperty("user.dir") + "/target/appiumlogs/"
                            + device_udid.replaceAll("\\W", "_") + "__" + methodName + ".txt" + ">Logs</a>");
        }
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (iosDevice.checkiOSDevice(device_udid)) {
                String webKitPort = iosDevice.startIOSWebKit(device_udid);
                return appiumMan.appiumServerForIOS(device_udid, methodName, webKitPort);
            } else if (!iosDevice.checkiOSDevice(device_udid)) {
                return appiumMan.appiumServerForAndroid(device_udid, methodName);
            }
        } else {
            return appiumMan.appiumServerForAndroid(device_udid, methodName);
        }
        return null;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName) throws Exception {
        ExtentTestManager.loadConfig();
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            child = ExtentTestManager.startTest(methodName.toString())
                    .assignCategory(category + "_" + device_udid.replaceAll("\\W", "_"));
        }
        Thread.sleep(3000);
        startingServerInstance();
        return driver;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName,
            DesiredCapabilities iosCaps, DesiredCapabilities androidCaps) throws Exception {
        ExtentTestManager.loadConfig();
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            child = ExtentTestManager.startTest(methodName.toString())
                    .assignCategory(category + "_" + device_udid.replaceAll("\\W", "_"));
        }
        Thread.sleep(3000);
        startingServerInstance(iosCaps, androidCaps);
        return driver;
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName,
            DesiredCapabilities caps) throws Exception {
        ExtentTestManager.loadConfig();
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            child = ExtentTestManager.startTest(methodName.toString())
                    .assignCategory(category + "_" + device_udid.replaceAll("\\W", "_"));
        }
        Thread.sleep(3000);
        startingServerInstance(caps);
        return driver;
    }

    public void startingServerInstance() throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (iosDevice.checkiOSDevice(device_udid)) {
                    driver = new IOSDriver<>(appiumMan.getAppiumUrl(), iosNative());
                } else if (!iosDevice.checkiOSDevice(device_udid)) {
                    driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidNative());
                }
            } else {
                driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidNative());
            }
        }
    }

    public void startingServerInstance(DesiredCapabilities caps) throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (caps.asMap().get("bundleId") != null) {
                    driver = new IOSDriver<>(appiumMan.getAppiumUrl(), caps);
                } else {
                    checkSelendroid(caps);
                    driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), caps);
                }
            } else {
                driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), caps);
            }
        }
    }

    public void startingServerInstance(DesiredCapabilities iosCaps, DesiredCapabilities androidCaps) throws Exception {
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
            driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidWeb());
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (iosDevice.checkiOSDevice(device_udid)) {
                    driver = new IOSDriver<>(appiumMan.getAppiumUrl(), iosCaps);
                } else if (!iosDevice.checkiOSDevice(device_udid)) {
                    checkSelendroid(androidCaps);
                    driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidCaps);
                }
            } else {
                checkSelendroid(androidCaps);
                driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidCaps);
            }
        }
    }

    public DesiredCapabilities checkSelendroid(DesiredCapabilities androidCaps) {
        int android_api = Integer.parseInt(androidDevice.deviceOS(device_udid));
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

    public void startLogResults(String methodName) throws IOException {
        if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
            System.out.println("Starting ADB logs" + device_udid);
            logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            new File(System.getProperty("user.dir") + "/target/adblogs/").mkdir();
            logFile = new File(System.getProperty("user.dir") + "/target/adblogs/" + device_udid.replaceAll("\\W", "_")
                    + "__" + methodName + ".txt");

            log_file_writer = new PrintWriter(logFile);
        }
    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {

        final String classNameCur = result.getName().split(" ")[2].substring(1);
        final Package[] packages = Package.getPackages();
        String className = null;
        for (final Package p : packages) {
            final String pack = p.getName();
            final String tentative = pack + "." + classNameCur;
            try {
                Class.forName(tentative);
            } catch (final ClassNotFoundException e) {
                continue;
            }
            className = tentative;
            break;
        }

        if (result.isSuccess()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, result.getMethod().getMethodName(), "Pass");
            if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                        "ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/"
                                + device_udid.replaceAll("\\W", "_") + "__" + result.getMethod().getMethodName()
                                + ".txt" + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }

        }
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName(), result.getThrowable());
            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                System.out.println("im in");
                deviceModel = androidDevice.getDeviceModel(device_udid);
                // captureScreenshot(result.getMethod().getMethodName(),
                // "android", className);
                captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                        result.getTestClass().getName());
            } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                deviceModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
                captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                        result.getTestClass().getName());
            }

            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                File framedImageAndroid = new File(System.getProperty("user.dir") + "/target/screenshot/android/"
                        + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                        + result.getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp + deviceModel
                        + "_failed_" + result.getMethod().getMethodName() + "_framed.png");
                if (framedImageAndroid.exists()) {
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest()
                                    .addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/android/"
                                            + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                                            + result.getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                            + deviceModel + "_failed_" + result.getMethod().getMethodName()
                                            + "_framed.png"));
                } else {
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest()
                                    .addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/android/"
                                            + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                                            + result.getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                            + deviceModel + "_" + result.getMethod().getMethodName() + "_failed.png"));
                }

            }
            if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                File framedImageIOS = new File(System.getProperty("user.dir") + "/target/screenshot/iOS/"
                        + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                        + result.getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp + deviceModel
                        + "_failed_" + result.getMethod().getMethodName() + "_framed.png");
                if (framedImageIOS.exists()) {
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest()
                                    .addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/iOS/"
                                            + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                                            + result.getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                            + deviceModel + "_failed_" + result.getMethod().getMethodName()
                                            + "_framed.png"));
                } else {
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest()
                                    .addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/iOS/"
                                            + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                                            + result.getMethod().getMethodName() + "/" + screenShotNameWithTimeStamp
                                            + deviceModel + "_" + result.getMethod().getMethodName() + "_failed.png"));
                }

            }

            if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                        "ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/"
                                + device_udid.replaceAll("\\W", "_") + "__" + result.getMethod().getMethodName()
                                + ".txt" + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }

        }
        if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped");
        }
        parentContext.get(Thread.currentThread().getId()).appendChild(child);
        ExtentManager.getInstance().flush();
    }

    public synchronized void killAppiumServer() throws InterruptedException, IOException {
        System.out.println("**************ClosingAppiumSession****************" + Thread.currentThread().getId());
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            ExtentManager.getInstance().endTest(parent);
            ExtentManager.getInstance().flush();
        }
        appiumMan.destroyAppiumNode();
        if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            iosDevice.destroyIOSWebKitProxy();
        }
        freeDevice(device_udid);
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

    public synchronized DesiredCapabilities androidNative() {
        System.out.println("Setting Android Desired Capabilities:");
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.X");
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, prop.getProperty("APP_ACTIVITY"));
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, prop.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability("browserName", "");
        checkSelendroid(androidCapabilities);
        androidCapabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("ANDROID_APP_PATH"));
        androidCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return androidCapabilities;
    }

    public synchronized DesiredCapabilities androidWeb() {
        DesiredCapabilities androidWebCapabilities = new DesiredCapabilities();
        androidWebCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidWebCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.X");
        // If you want the tests on real device, make sure chrome browser is
        // installed
        androidWebCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, prop.getProperty("BROWSER_TYPE"));
        androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        return androidWebCapabilities;
    }

    public synchronized DesiredCapabilities iosNative() {
        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        System.out.println("Setting iOS Desired Capabilities:");
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
        iOSCapabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("IOS_APP_PATH"));
        iOSCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, prop.getProperty("BUNDLE_ID"));
        iOSCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
        iOSCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
        iOSCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return iOSCapabilities;
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
        AppiumDriver<MobileElement> driver = ((AppiumParallelTest) currentClass).getDriver();
        SkipIf skip = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(SkipIf.class);
        if (skip != null) {
            String info = skip.platform();
            if (driver.toString().split("\\(")[0].trim().toString().contains(info)) {
                System.out.println("skipping test");
                throw new SkipException("Skipped because property was set to :::" + info);
            }
        }
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public void screenShotAndFrame(String methodName, String device, String className) {
        try {
            File framePath = new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { // this line weeds out other
                                              // directories/folders
                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();

                        if (deviceModel.toString().toLowerCase().contains(fileName.split(".png")[0].toLowerCase()))

                        {

                            try {
                                String deviceFrame = files1[i].toString();
                                String screenToBeFramed = System.getProperty("user.dir") + "/target/screenshot/"
                                        + device + "/" + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                                        + methodName + "/" + deviceModel + "_" + methodName + "_failed.png";
                                String framedScreenShot = System.getProperty("user.dir") + "/target/screenshot/"
                                        + device + "/" + device_udid.replaceAll("\\W", "_") + "/" + className + "/"
                                        + methodName + "/" + deviceModel + "_failed_" + methodName + "_framed.png";
                                imageUtils.wrapDeviceFrames(deviceFrame, screenToBeFramed, framedScreenShot);
                                File deleteActualImage = new File(screenToBeFramed);
                                deleteActualImage.delete();
                                break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IM4JavaException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Resource Directory was not found");
        }
    }

    public void captureScreenshot(String methodName, String device, String className)
            throws IOException, InterruptedException {
        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile,
                new File(System.getProperty("user.dir") + "/target/screenshot/" + device + "/"
                        + device_udid.replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + deviceModel
                        + "_" + methodName + "_failed" + ".png"));
        Thread.sleep(3000);
    }

    public void logger(String message) {
        ExtentTestManager.logger(message);
    }

    public void captureScreenShot(String screenShotName, int status, String screenClassName)
            throws IOException, InterruptedException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String methodName = screenShotName;
        String className = screenClassName;
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
            String androidModel = screenShotNameWithTimeStamp + androidDevice.getDeviceModel(device_udid);
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, androidModel, "android");
        } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            String iosModel = screenShotNameWithTimeStamp + iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, iosModel, "iOS");
        }
    }

    public void captureScreenShot(String screenShotName) throws InterruptedException, IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        String className = new Exception().getStackTrace()[1].getClassName();
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
            String androidModel = screenShotNameWithTimeStamp + androidDevice.getDeviceModel(device_udid);
            screenShotAndFrame(screenShotName, 1, scrFile, methodName, className, androidModel, "android");
        } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            String iosModel = screenShotNameWithTimeStamp + iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
            screenShotAndFrame(screenShotName, 1, scrFile, methodName, className, iosModel, "iOS");
        }
    }

    public void screenShotAndFrame(String screenShotName, int status, File scrFile, String methodName, String className,
            String model, String platform) {
        String failedScreen = System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
                + device_udid.replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + deviceModel + "_"
                + methodName + "_failed" + ".png";
        String capturedScreen = System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
                + device_udid.replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + screenShotName
                + ".png";
        String framedCapturedScreen = System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
                + device_udid.replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + model + "_"
                + screenShotName + "_results.png";
        String framedFailedScreen = System.getProperty("user.dir") + "/target/screenshot/" + platform + "/"
                + device_udid.replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + model + "_failed_"
                + methodName + "_framed.png";

        try {
            File framePath = new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            if (status == ITestResult.FAILURE) {
                FileUtils.copyFile(scrFile, new File(failedScreen));
            } else {
                FileUtils.copyFile(scrFile, new File(capturedScreen));
            }

            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { // this line weeds out other
                                              // directories/folders
                        System.out.println(files1[i]);

                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (model.toString().toLowerCase().contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                if (status == ITestResult.FAILURE) {
                                    String screenToFrame = failedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedFailedScreen);
                                    ExtentTestManager.logOutPut(framedFailedScreen, screenShotName.toUpperCase());
                                    File fileToDelete = new File(screenToFrame);
                                    fileToDelete.delete();
                                } else {
                                    String screenToFrame = capturedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedCapturedScreen);
                                    ExtentTestManager.logOutPut(framedCapturedScreen, screenShotName.toUpperCase());
                                    File fileToDelete = new File(screenToFrame);
                                    fileToDelete.delete();
                                }

                                break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IM4JavaException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }
}
