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
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.json.JSONObject;
import org.json.XML;
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
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class AppiumParallelTest extends TestListenerAdapter implements ITestListener {
    public AppiumDriver<MobileElement> driver;
    public AppiumManager appiumMan = new AppiumManager();
    public String device_udid;
    public List<LogEntry> logEntries;
    public File logFile;
    public PrintWriter log_file_writer;
    public DesiredCapabilities capabilities = new DesiredCapabilities();
    public String category;
    public ExtentTest parent;
    public ExtentTest child;

    private Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();
    private static ArrayList<String> devices = new ArrayList<String>();
    public static Properties prop = new Properties();
    private static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
    private static AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
    private static ConcurrentHashMap<String, Boolean> deviceMapping = new ConcurrentHashMap<String, Boolean>();
    public ImageUtils imageUtils = new ImageUtils();

    static {
        try {
            prop.load(new FileInputStream("config.properties"));
            if (iosDevice.getIOSUDID() != null) {
                System.out.println("Adding iOS devices");
                devices.addAll(iosDevice.getIOSUDID());
            }
            if (androidDevice.getDeviceSerail() != null) {
                System.out.println("Adding Android devices");
                devices.addAll(androidDevice.getDeviceSerail());
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
        for (String device : devices) {
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

        if (iosDevice.checkiOSDevice(device_udid)) {
            iosDevice.setIOSWebKitProxyPorts(device_udid);
            category = iosDevice.getDeviceName(device_udid).replace("\n", " ");
        } else {
            category = androidDevice.getDeviceModel(device_udid);
        }
        parent = ExtentTestManager.startTest(methodName, "Mobile Appium Test",
                category + device_udid.replaceAll("\\W", "_"));
        parentContext.put(Thread.currentThread().getId(), parent);
        ExtentTestManager.getTest().log(LogStatus.INFO, "AppiumServerLogs", "<a href=" + System.getProperty("user.dir")
                + "/target/appiumlogs/" + device_udid.replaceAll("\\W", "_") + "__" + methodName + ".txt" + ">Logs</a>");
        if (iosDevice.checkiOSDevice(device_udid)) {
            String webKitPort = iosDevice.startIOSWebKit(device_udid);
            return appiumMan.appiumServerForIOS(device_udid, methodName, webKitPort);
        } else {
            return appiumMan.appiumServerForAndroid(device_udid, methodName);
        }
    }

    public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName) throws Exception {
        child = ExtentTestManager
                .startTest(methodName).assignCategory(category + device_udid.replaceAll("\\W", "_"));
        Thread.sleep(3000);
        if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
        	driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidWeb());
        } else{
        	if (iosDevice.checkiOSDevice(device_udid)) {
        		driver = new IOSDriver<>(appiumMan.getAppiumUrl(), iosNative());
        	} else if (!iosDevice.checkiOSDevice(device_udid)){
        		driver = new AndroidDriver<>(appiumMan.getAppiumUrl(), androidNative());
        	}
        }

        return driver;
    }

    public void startLogResults(String methodName) throws FileNotFoundException {
        if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
            System.out.println("Starting ADB logs" + device_udid);
            logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            logFile = new File(
                    System.getProperty("user.dir") + "/target/adblogs/" + device_udid.replaceAll("\\W", "_") + "__" + methodName + ".txt");
            log_file_writer = new PrintWriter(logFile);
        }
    }

    public void endLogTestResults(ITestResult result) throws IOException, InterruptedException {
        if (result.isSuccess()) {
            ExtentTestManager.getTest().log(LogStatus.PASS, result.getMethod().getMethodName(), "Pass");
            if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                        "ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/" + device_udid.replaceAll("\\W", "_") + "__"
                                + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
                System.out.println(driver.getSessionId() + ": Saving device log - Done.");
            }

        }
        if (result.getStatus() == ITestResult.FAILURE) {
            String androidModel = null;
            String iosModel = null;
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName(), result.getThrowable());
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                androidModel = androidDevice.getDeviceModel(device_udid);
                try {
                    File framePath = new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
                    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                            + androidModel + "/failed_" + result.getMethod().getMethodName() + ".png"));
                    File[] files1 = framePath.listFiles();
                    if(framePath.exists()){
                        for (int i = 0; i < files1.length; i++) {
                            if (files1[i].isFile()) { //this line weeds out other directories/folders
                                Path p = Paths.get(files1[i].toString());
                                String fileName = p.getFileName().toString().toLowerCase();
                                if (androidModel.toString().toLowerCase().contains(fileName.split(".png")[0].toLowerCase())) {
                                    try {
                                        imageUtils.wrapDeviceFrames(files1[i].toString(), System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                                + androidModel + "/failed_" + result.getMethod().getMethodName() + ".png", System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                                + androidModel + "/failed_" + result.getMethod().getMethodName() + "_framed.png");
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
            } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                iosModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
                try {
                    File framePath = new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
                    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                            + iosModel + "/failed_" + result.getMethod().getMethodName() + ".png"));
                    File[] files1 = framePath.listFiles();
                    if(framePath.exists()){
                        for (int i = 0; i < files1.length; i++) {
                            if (files1[i].isFile()) {
                                Path p = Paths.get(files1[i].toString());
                                String fileName = p.getFileName().toString().toLowerCase();
                                if (iosModel.toString().toLowerCase().contains(fileName.split(".png")[0].toLowerCase())) {
                                    try {
                                        imageUtils.wrapDeviceFrames(files1[i].toString(), System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                                + iosModel + "/failed_" + result.getMethod().getMethodName() + ".png", System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                                + iosModel + "/failed_" + result.getMethod().getMethodName() + "_framed.png");
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

            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                File framedImageAndroid = new File(System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                        + androidModel + "/failed_" + result.getMethod().getMethodName() + "_framed.png");
                if(framedImageAndroid.exists()){
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                    + androidModel + "/failed_" + result.getMethod().getMethodName() + "_framed.png"));
                }else{
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                    + androidModel + "/failed_" + result.getMethod().getMethodName() + ".png"));
                }


            }
            if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
                File framedImageIOS = new File(System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                        + iosModel + "/failed_" + result.getMethod().getMethodName() + "_framed.png");
                if(framedImageIOS.exists()){
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                    + iosModel + "/failed_" + result.getMethod().getMethodName() + "_framed.png"));
                }else{
                    ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                            "Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                    + iosModel + "/failed_" + result.getMethod().getMethodName() + ".png"));
                }

            }


            if (driver.toString().split("\\(")[0].trim().equals("AndroidDriver:  on LINUX")) {
                log_file_writer.println(logEntries);
                log_file_writer.flush();
                ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
                        "ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/" + device_udid.replaceAll("\\W", "_") + "__"
                                + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
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
        System.out.println("**************ClosingAppiumSession****************");
        ExtentManager.getInstance().endTest(parent);
        ExtentManager.getInstance().flush();
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

    public synchronized DesiredCapabilities androidNative() {
        System.out.println("Setting Android Desired Capabilities:");
        DesiredCapabilities androidCapabilities = new DesiredCapabilities();
        androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.X");
        androidCapabilities.setCapability("browserName", "");
        if (Integer.parseInt(androidDevice.deviceOS(device_udid)) <= 16) {
            androidCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
        }
        androidCapabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("ANDROID_APP_PATH"));
        androidCapabilities.setCapability(MobileCapabilityType.APP_PACKAGE, prop.getProperty("APP_PACKAGE"));
        androidCapabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, prop.getProperty("APP_ACTIVITY"));
        if (prop.getProperty("APP_WAIT_ACTIVITY") != null) {
            androidCapabilities.setCapability(MobileCapabilityType.APP_WAIT_ACTIVITY, prop.getProperty("APP_WAIT_ACTIVITY"));
        }
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
        iOSCapabilities.setCapability("platformVersion", "9.0");
        iOSCapabilities.setCapability("app", prop.getProperty("IOS_APP_PATH"));
        iOSCapabilities.setCapability("bundleId", prop.getProperty("BUNDLE_ID"));
        iOSCapabilities.setCapability("autoAcceptAlerts", true);
        iOSCapabilities.setCapability("deviceName", "iPhone");
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

    public void captureScreenShot(String screenShotName) throws IOException, InterruptedException {
        File framePath = new File(System.getProperty("user.dir") + "/src/main/resources/frames/");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
            String androidModel = androidDevice.getDeviceModel(device_udid);
            try {
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                        + androidModel + "/" + screenShotName + ".png"));
                File[] files1 = framePath.listFiles();
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { //this line weeds out other directories/folders
                        System.out.println(files1[i]);
                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (androidModel.toString().toLowerCase().contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                imageUtils.wrapDeviceFrames(files1[i].toString(), System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                        + androidModel + "/" + screenShotName + ".png", System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                        + androidModel + "/" + screenShotName + "_framed.png");
                                ExtentTestManager.logOutPut(System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                        + androidModel + "/" + screenShotName + "_framed.png", screenShotName.toUpperCase());
                                break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IM4JavaException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (driver.toString().split(":")[0].trim().equals("IOSDriver")) {
            String iosModel = iosDevice.getIOSDeviceProductTypeAndVersion(device_udid);
            try {
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                        + iosModel + "/" + screenShotName + ".png"));
                File[] files1 = framePath.listFiles();
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { //this line weeds out other directories/folders
                        System.out.println(files1[i]);
                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (iosModel.toString().toLowerCase().contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                imageUtils.wrapDeviceFrames(files1[i].toString(), System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                        + iosModel + "/" + screenShotName + ".png", System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                        + iosModel + "/" + screenShotName + "_framed.png");
                                ExtentTestManager.logOutPut(System.getProperty("user.dir") + "/target/screenshot/iPhone/" + device_udid.replaceAll("\\W", "_") + "/"
                                        + iosModel + "/" + screenShotName + "_framed.png", screenShotName.toUpperCase());
                                break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IM4JavaException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @SuppressWarnings("unused")
    public void convertXmlToJSon() throws IOException {
        String fileName = "report.json";
        BufferedWriter bufferedWriter = null;
        try {
            int i = 1;
            FileWriter fileWriter;
            int dir_1 = new File(System.getProperty("user.dir") + "/test-output/junitreports").listFiles().length;

            List textFiles = new ArrayList();
            File dir = new File(System.getProperty("user.dir") + "/test-output/junitreports");

            for (File file : dir.listFiles()) {
                if (file.getName().contains(("Test"))) {
                    System.out.println(file);

                    fileWriter = new FileWriter(fileName, true);
                    InputStream inputStream = new FileInputStream(file);
                    StringBuilder builder = new StringBuilder();
                    int ptr = 0;
                    while ((ptr = inputStream.read()) != -1) {
                        builder.append((char) ptr);
                    }

                    String xml = builder.toString();
                    JSONObject jsonObj = XML.toJSONObject(xml);

                    // Always wrap FileWriter in BufferedWriter.
                    bufferedWriter = new BufferedWriter(fileWriter);

                    // Always close files.
                    String jsonPrettyPrintString = jsonObj.toString(4);
                    // bufferedWriter.write(jsonPrettyPrintString);
                    if (i == 1) {
                        bufferedWriter.append("[");
                    }
                    bufferedWriter.append(jsonPrettyPrintString);
                    if (i != dir_1) {
                        bufferedWriter.append(",");
                    }

                    if (i == dir_1) {
                        bufferedWriter.append("]");
                    }

                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    i++;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
