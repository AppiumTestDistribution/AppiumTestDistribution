package com.appium.manager;

import com.annotation.values.Author;
import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.ImageUtils;
import com.appium.utils.MobilePlatform;
import com.appium.utils.ios.Ipa;
import com.appium.utils.ios.IpaParser;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;
import com.video.recorder.Flick;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import net.dongliu.apk.parser.ApkParser;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;


public class AppiumParallelTest extends TestListenerAdapter implements ITestListener {
    public static ArrayList<String> devices = new ArrayList<String>();
    public static Properties prop = new Properties();
    public static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
    public static AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
    public static ConcurrentHashMap<String, Boolean> deviceMapping =
            new ConcurrentHashMap<String, Boolean>();
    public AppiumDriver<MobileElement> driver = null;
    public AppiumManager appiumMan = new AppiumManager();
    public String device_udid;
    public List<LogEntry> logEntries;
    public File logFile;
    public PrintWriter log_file_writer;
    public String category = null;
    public ExtentTest parent;
    public ExtentTest child;
    public String deviceModel;
    public File scrFile = null;
    public String testDescription = "";
    public ImageUtils imageUtils = new ImageUtils();
    private String screenShotNameWithTimeStamp;
    private String CI_BASE_URI = null;
    private Flick videoRecording = new Flick();
    public ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    public ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    private static IpaParser ipaParser = new IpaParser();
    private static Ipa ipa;
    private static Map<String, String> apkInfoMap = new HashMap<>();
    private static Map<String, String> ipaInfoMap = new HashMap<>();

    private static final String APP_INFO_FILE_PATH_KEY = "FILEPATH";
    private static final String IPA_INFO_BUNDLE_ID_KEY = "BUNDLEID";
    private static final String APK_INFO_PACKAGE_KEY = "PACKAGE";
    private static final String APK_INFO_ACTIVITY_KEY = "ACTIVITY";

    static {
        try {
            prop.load(new FileInputStream("config.properties"));
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                if (IOSDeviceConfiguration.deviceUDIDiOS != null) {
                    System.out.println("Adding iOS devices");
                    devices.addAll(IOSDeviceConfiguration.deviceUDIDiOS);
                }
                if (androidDevice.getDeviceSerial() != null) {
                    System.out.println("Adding Android devices");
                    devices.addAll(AndroidDeviceConfiguration.deviceSerail);
                }
            } else {
                if (androidDevice.getDeviceSerial() != null) {
                    System.out.println("Adding Android devices");
                    devices.addAll(AndroidDeviceConfiguration.deviceSerail);
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
            if (deviceMapping.get(device)) {
                deviceMapping.put(device, false);
                System.out.println(device);
                return device;
            }
        }
        return null;
    }


    public static void freeDevice(String deviceId) {
        deviceMapping.put(deviceId, true);
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static boolean buildStatus() {
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(
                    new FileReader(System.getProperty("user.dir") + "/target/failed.txt"));
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains("TestFailed")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public synchronized AppiumServiceBuilder startAppiumServer(
        String device, String methodName) throws Exception {
        if (prop.containsKey("CI_BASE_URI")) {
            CI_BASE_URI = prop.getProperty("CI_BASE_URI").toString().trim();
        } else if (CI_BASE_URI == null || CI_BASE_URI.isEmpty()) {
            CI_BASE_URI = System.getProperty("user.dir");
        }
        if (device.isEmpty()) {
            System.out.println("Into Empty devices");
            device_udid = getNextAvailableDeviceId();
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
        createParentNodeExtent(methodName, "", category
            + device_udid.replaceAll("\\W", "_"));

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            if (iosDevice.checkiOSDevice(device_udid)) {
                AppiumServiceBuilder webKitPort = getAppiumServiceBuilder(methodName);
                if (webKitPort != null) {
                    return webKitPort;
                }
            } else {
                return appiumMan.appiumServerForAndroid(device_udid, methodName);
            }
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
            device_udid = getNextAvailableDeviceId();
        } else {
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
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            AppiumServiceBuilder webKitPort = getAppiumServiceBuilder(methodName);
            if (webKitPort != null) {
                return webKitPort;
            }
        } else {
            return appiumMan.appiumServerForAndroid(device_udid, methodName);
        }
        return null;
    }

    public AppiumServiceBuilder getAppiumServiceBuilder(String methodName) throws Exception {
        if (iosDevice.checkiOSDevice(device_udid)) {
            String webKitPort = iosDevice.startIOSWebKit(device_udid);
            return appiumMan.appiumServerForIOS(device_udid, methodName, webKitPort);
        } else if (!iosDevice.checkiOSDevice(device_udid)) {
            return appiumMan.appiumServerForAndroid(device_udid, methodName);
        }
        return null;
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

    public void createParentNodeExtent(String methodName, String testDescription, String deviceId) {
        parent = ExtentTestManager.createTest(methodName, testDescription,
            deviceId);
        parentTest.set(parent);
        ExtentTestManager.getTest().log(Status.INFO,
            "<a target=\"_parent\" href=" + "appiumlogs/"
                + device_udid.replaceAll("\\W", "_") + "__" + methodName
                + ".txt" + ">AppiumServerLogs</a>");
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName)
            throws Exception {
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            setAuthorName(methodName);
        } else {
            child = parentTest.get().createNode(methodName, category
                + device_udid.replaceAll("\\W", "_"));
            test.set(child);
        }
        Thread.sleep(3000);
        startingServerInstance();
        return driver;
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

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(
            String methodName, DesiredCapabilities caps) throws Exception {
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            setAuthorName(methodName);
        }
        startingServerInstance(caps);
        return driver;
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

    public void startingServerInstance(DesiredCapabilities iosCaps, DesiredCapabilities androidCaps)
            throws Exception {
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
        if (System.getenv("VIDEO_LOGS") != null) {
            try {
                videoRecording.startVideoRecording(
                        device_udid, getClass().getName(), methodName, methodName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (getMobilePlatform() == MobilePlatform.AndroidOnLinux) {
            System.out.println("Starting ADB logs" + device_udid);
            logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            logFile = new File(System.getProperty("user.dir") + "/target/adblogs/" + device_udid
                    .replaceAll("\\W", "_") + "__" + methodName + ".txt");
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

        if (System.getenv("VIDEO_LOGS") != null) {
            try {
                videoRecording.stopVideoRecording(device_udid, getClass().getName(),
                        result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (IOException e) {
                videoRecording.stopVideoRecording(device_udid, getClass().getName(),
                        result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (InterruptedException e) {
                System.out.println("");
            }
        }

        if (result.isSuccess()) {
            writeFailureToTxt("TestPassed");
            test.get().log(Status.PASS, result.getMethod().getMethodName());
            if (getMobilePlatform() == MobilePlatform.AndroidOnLinux) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                test.get().log(Status.INFO,
                        "<a target=\"_parent\" href=" + "adblogs/" + device_udid.replaceAll("\\W", "_")
                                + "__"
                                + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }
            File videoFile = new File(System.getProperty("user.dir") + "/target/screenshot/android/"
                    + device_udid.replaceAll("\\W", "_") + "/"
                    + className + "/" + result.getMethod().getMethodName()
                    + "/" + result.getMethod().getMethodName() + ".mp4");
            System.out.println(videoFile);
            if (videoFile.exists()) {
                videoFile.delete();
            }

        }
        /*
         * Failure Block
         */
        if (result.getStatus() == ITestResult.FAILURE) {
            writeFailureToTxt("TestFailed");
            ExtentTest log = test.get()
                    .log(Status.FAIL, "<pre>" + result.getThrowable() + "</pre>");
            if (getMobilePlatform() == MobilePlatform.ANDROID) {
                deviceModel = androidDevice.getDeviceModel(device_udid);
                captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                        result.getTestClass().getName());
            } else if (getMobilePlatform() == MobilePlatform.IOS) {
                deviceModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
                captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                        result.getTestClass().getName());
            }
            if (getMobilePlatform() == MobilePlatform.ANDROID) {
                File framedImageAndroid = new File(
                        System.getProperty("user.dir") + "/target/screenshot/android/"
                                + device_udid.replaceAll("\\W", "_") + "/"
                                + className + "/" + result.getMethod().getMethodName()
                                + "/" + screenShotNameWithTimeStamp
                                + deviceModel + "_failed_"
                                + result.getMethod().getMethodName()
                                + "_framed.png");
                if (framedImageAndroid.exists()) {
                    log
                        .addScreenCaptureFromPath(
                            "screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                + className + "/" + result.getMethod().getMethodName() + "/"
                                + screenShotNameWithTimeStamp + deviceModel + "_failed_" + result
                                .getMethod().getMethodName() + "_framed.png");
                } else {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + device_udid.replaceAll("\\W", "_")
                                    + "/" + className + "/" + result.getMethod().getMethodName()
                                    + "/" + screenShotNameWithTimeStamp + deviceModel + "_"
                                    + result.getMethod().getMethodName() + "_failed.png");
                }


            }
            if (getMobilePlatform() == MobilePlatform.IOS) {
                File framedImageIOS = new File(
                        System.getProperty("user.dir") + "/target/screenshot/iOS/"
                                + device_udid.replaceAll("\\W", "_") + "/" + className
                                + "/" + result.getMethod().getMethodName() + "/"
                                + screenShotNameWithTimeStamp + deviceModel + "_failed_"
                                + result.getMethod().getMethodName() + "_framed.png");
                System.out.println("************************" + framedImageIOS.exists()
                        + "***********************");
                if (framedImageIOS.exists()) {
                    log.addScreenCaptureFromPath("screenshot/iOS/"
                        + device_udid.replaceAll("\\W", "_")
                        + "/" + className
                        + "/" + result.getMethod().getMethodName() + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_failed_" + result
                        .getMethod().getMethodName() + "_framed.png");
                } else {
                    log.addScreenCaptureFromPath("screenshot/iOS/"
                        + device_udid.replaceAll("\\W", "_")
                        + "/" + className
                        + "/" + result.getMethod().getMethodName() + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_" + result
                        .getMethod().getMethodName() + "_failed.png");
                }

            }


            if (getMobilePlatform() == MobilePlatform.AndroidOnLinux) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                test.get().log(Status.INFO,
                        "<a target=\"_parent\" href=" + "adblogs/"
                                + device_udid.replaceAll("\\W", "_")
                                + "__" + result.getMethod().getMethodName()
                                + ".txt" + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }

        }
        /*
         * Skip block
         */
        if (result.getStatus() == ITestResult.SKIP) {
            writeFailureToTxt("TestSkipped");
            test.get().log(Status.SKIP, "Test skipped");
        }

        if (System.getenv("VIDEO_LOGS") != null) {
            try {
                videoRecording.stopVideoRecording(device_udid, getClass().getName(),
                    result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (IOException e) {
                videoRecording.stopVideoRecording(device_udid, getClass().getName(),
                    result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (InterruptedException e) {
                System.out.println("");
            }
            if (getMobilePlatform() == MobilePlatform.AndroidOnLinux) {
                if (new File(
                        System.getProperty("user.dir") + "/target/screenshot/android/"
                                + device_udid.replaceAll("\\W", "_") + "/" + className
                                + "/" + result.getMethod().getMethodName() + "/"
                                + result.getMethod().getMethodName() + ".mp4")
                        .exists()) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + "screenshot/android/" + device_udid.replaceAll("\\W", "_")
                            + "/" + className
                            + "/" + result.getMethod().getMethodName()
                            + "/" + result.getMethod()
                            .getMethodName() + ".mp4" + ">Videologs</a>");
                }
            } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                if (new File(
                        System.getProperty("user.dir") + "/target/screenshot/iOS/"
                                + device_udid.replaceAll("\\W", "_") + "/" + className
                                + "/" + result.getMethod().getMethodName() + "/"
                                + result.getMethod().getMethodName() + ".mp4")
                        .exists()) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + "screenshot/iOS/" + device_udid.replaceAll("\\W", "_")
                            + "/" + className
                            + "/" + result.getMethod().getMethodName() + "/" + result.getMethod()
                            .getMethodName() + ".mp4" + ">Videologs</a>");
                }
            }
        }
        ExtentManager.getExtent().flush();
    }

    @AfterClass(alwaysRun = true) public synchronized void killAppiumServer()
            throws InterruptedException, IOException {
        System.out.println(
                "**************ClosingAppiumSession****************"
                        + Thread.currentThread().getId());
        if (prop.getProperty("FRAMEWORK").equalsIgnoreCase("testng")) {
            //ExtentManager.getInstance().endTest(parent);
            ExtentManager.getExtent().flush();
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
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                prop.getProperty("APP_ACTIVITY"));
        androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                prop.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability("browserName", "");
        checkSelendroid(androidCapabilities);
        androidCapabilities
                .setCapability(MobileCapabilityType.APP, prop.getProperty("ANDROID_APP_PATH"));
        androidCapabilities.setCapability(MobileCapabilityType.UDID, device_udid);
        return androidCapabilities;
    }

    public synchronized DesiredCapabilities androidWeb() {
        DesiredCapabilities androidWebCapabilities = new DesiredCapabilities();
        androidWebCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidWebCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.X");
        // If you want the tests on real device, make sure chrome browser is
        // installed
        androidWebCapabilities
                .setCapability(MobileCapabilityType.BROWSER_NAME, prop.getProperty("BROWSER_TYPE"));
        androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
        return androidWebCapabilities;
    }

    public synchronized DesiredCapabilities iosNative() {
        DesiredCapabilities iOSCapabilities = new DesiredCapabilities();
        System.out.println("Setting iOS Desired Capabilities:");
        iOSCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
        iOSCapabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("IOS_APP_PATH"));
        iOSCapabilities
                .setCapability(IOSMobileCapabilityType.BUNDLE_ID, prop.getProperty("BUNDLE_ID"));
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
        SkipIf skip =
                result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(SkipIf.class);
        if (skip != null) {
            MobilePlatform skipPlatform = skip.platform();
            MobilePlatform currentPlatform = getMobilePlatform();
            if (skipPlatform == MobilePlatform.AndroidOnLinux
                    || skipPlatform == MobilePlatform.AndroidOnWindows) {
                skipPlatform = MobilePlatform.ANDROID;
            }
            if (currentPlatform == MobilePlatform.AndroidOnLinux
                    || currentPlatform == MobilePlatform.AndroidOnWindows) {
                currentPlatform = MobilePlatform.ANDROID;
            }
            if (currentPlatform.equals(skipPlatform)) {
                System.out.println("skipping test");
                throw new SkipException(
                        "Skipped because property was set to :::" + skipPlatform.platformName);
            }
        }
    }

    public void captureScreenshot(String methodName, String device, String className)
            throws IOException, InterruptedException {
        String context = getDriver().getContext();
        boolean contextChanged = false;
        if (getMobilePlatform() == MobilePlatform.ANDROID && !context
                .equals("NATIVE_APP")) {
            getDriver().context("NATIVE_APP");
            contextChanged = true;
        }
        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            getDriver().context(context);
        }
        FileUtils.copyFile(scrFile, new File(
                "screenshot/" + device + "/" + device_udid.replaceAll("\\W", "_")
                        + "/" + className + "/" + methodName + "/" + deviceModel
                        + "_" + methodName + "_failed" + ".png"));
        Thread.sleep(3000);
    }

    public void logger(String message) {
        ExtentTestManager.logger(message);
    }

    public void captureScreenShot(String screenShotName, int status, String className,
                                  String methodName) throws IOException, InterruptedException {
        String context = getDriver().getContext();
        boolean contextChanged = false;
        if (getMobilePlatform() == MobilePlatform.ANDROID && !context
                .equals("NATIVE_APP")) {
            getDriver().context("NATIVE_APP");
            contextChanged = true;
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            getDriver().context(context);
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (getMobilePlatform() == MobilePlatform.ANDROID) {
            String androidModel =
                    screenShotNameWithTimeStamp + androidDevice.getDeviceModel(device_udid);
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, androidModel,
                    "android");
        } else if (getMobilePlatform() == MobilePlatform.IOS) {
            String iosModel = screenShotNameWithTimeStamp + iosDevice
                    .getIOSDeviceProductTypeAndVersion(device_udid);
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, iosModel,
                    "iOS");
        }
    }

    public void captureScreenShot(String screenShotName, int status, String screenClassName)
            throws IOException, InterruptedException {
        captureScreenShot(screenShotName, status, screenClassName, screenShotName);
    }

    public void captureScreenShot(String screenShotName) throws InterruptedException, IOException {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        String className = new Exception().getStackTrace()[1].getClassName();
        captureScreenShot(screenShotName, 1, className, methodName);
    }

    public void screenShotAndFrame(String screenShotName, int status, File scrFile,
                                   String methodName, String className, String model,
                                   String platform) {
        String failedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/"
                        + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_failed" + ".png";
        String capturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/"
                        + methodName + "/" + screenShotName
                        + ".png";
        String framedCapturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/"
                        + methodName + "/" + model + "_"
                        + screenShotName + "_results.png";
        String framedFailedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/"
                        + methodName + "/" + model
                        + "_failed_" + methodName + "_framed.png";

        try {
            File framePath =
                    new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            if (status == ITestResult.FAILURE) {
                FileUtils.copyFile(scrFile, new File(failedScreen));
            } else {
                FileUtils.copyFile(scrFile, new File(capturedScreen));
            }

            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { //this line weeds out other directories/folders
                        System.out.println(files1[i]);

                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (model.toString().toLowerCase()
                                .contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                if (status == ITestResult.FAILURE) {
                                    String screenToFrame = failedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedFailedScreen);
                                    ExtentTestManager.logOutPut(framedFailedScreen,
                                            screenShotName.toUpperCase());
                                    deleteFile(screenToFrame);
                                } else {
                                    String screenToFrame = capturedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedCapturedScreen);
                                    ExtentTestManager.logOutPut(framedCapturedScreen,
                                            screenShotName.toUpperCase());
                                    deleteFile(screenToFrame);
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

    public void deleteFile(String screenToFrame) {
        File fileToDelete = new File(screenToFrame);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }

    public void writeFailureToTxt(String content) {
        try {
            File file = new File(System.getProperty("user.dir") + "/target/failed.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Added by Anson Liao
     */
    private static String getAppFilePath(String appFilePath) {
        String appAbsPath = null;

        if (appFilePath.trim().length() == 0 || appFilePath == null) {
            try {
                throw new IOException(
                        ":::: Parsed App file path, but the provided App path is empty or null.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String appFilePathString = appFilePath.trim().toUpperCase();
        if ((appFilePathString.startsWith("HTTP") || appFilePathString.startsWith("FTP"))
                && (appFilePathString.endsWith(".APK") || appFilePathString.endsWith(".IPA"))) {
            String[] strings = appFilePath.trim().split("/");
            String appName = strings[strings.length - 1];
            String destAppRelatedPath = "build/" + appName;
            appAbsPath = (new File(destAppRelatedPath)).getAbsolutePath();

            if (!(new File(appAbsPath).exists())) {
                copyFileFromURL(appFilePath.trim(), appAbsPath);
            } else {
                System.out.println(":::: Local App file was found, no need to download: "
                        + new File(destAppRelatedPath).getAbsolutePath());
            }
        } else {
            if (!(new File(appFilePath.trim())).exists()) {
                try {
                    throw new FileNotFoundException(
                            "App file was not found, path:: " + appFilePath.trim());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                appAbsPath =  appFilePath.trim();
            }
        }

        return appAbsPath;
    }

    /**
     * Added by Anson Liao
     */
    public MobilePlatform getMobilePlatform() {
        MobilePlatform platform = null;

        if (device_udid.length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
            platform = MobilePlatform.IOS;        }
        if (getDriver().toString().split(":")[0].trim().equals("AndroidDriver")) {
            platform = MobilePlatform.ANDROID;
        }
        if (getDriver().toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
            platform = MobilePlatform.AndroidOnLinux;
        }
        if (getDriver().toString().split("\\(")[0].trim()
                .equalsIgnoreCase("AndroidDriver:  on WINDOWS")) {
            platform = MobilePlatform.AndroidOnWindows;
        }

        return platform;
    }

    /**
     * Added by Anson Liao
     */
    public Map<String, String> getIPAInfo() {
        if (ipaInfoMap.isEmpty()) {
            String ipaFilePath = getAppFilePath(prop.getProperty("IOS_APP_PATH"));
            ipa = ipaParser.parse(new File(ipaFilePath));
            String ipaBundleId = ipa.getBundleIdentifier();

            ipaInfoMap.put(IPA_INFO_BUNDLE_ID_KEY, ipaBundleId);
            ipaInfoMap.put(APP_INFO_FILE_PATH_KEY, ipaFilePath);
        }
        return ipaInfoMap;
    }

    /**
     * Added by Anson Liao
     */
    public Map<String, String> getAPKInfo() {
        if (apkInfoMap.isEmpty()) {
            String apkFilePath = getAppFilePath(prop.getProperty("ANDROID_APP_PATH"));

            System.out.println("Parsing APK for getting APK information:::: " + apkFilePath);

            try (ApkParser apkParser = new ApkParser(new File(apkFilePath))) {
                String apkPackageName = apkParser.getApkMeta().getPackageName();
                String apkLaunchActivity = androidDevice.getAPKInfo(apkFilePath)
                        .get(androidDevice.APK_LAUNCH_ACTIVITY);

                apkInfoMap.put(APP_INFO_FILE_PATH_KEY, apkFilePath);
                apkInfoMap.put(APK_INFO_PACKAGE_KEY, apkPackageName);
                apkInfoMap.put(APK_INFO_ACTIVITY_KEY, apkLaunchActivity);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return apkInfoMap;
    }


    private static synchronized void copyFileFromURL(String url, String destDirectory) {
        int TIME_OUT = 5 * 1000 * 60;   // 5 mins

        try {
            FileUtils.copyURLToFile(new URL(url), new File(destDirectory), TIME_OUT, TIME_OUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
