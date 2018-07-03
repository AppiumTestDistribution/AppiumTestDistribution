package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.utils.Helpers;
import com.appium.utils.ScreenShotManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.video.recorder.Flick;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by saikrisv on 24/01/17.
 */
class TestLogger extends Helpers {

    private Flick videoRecording;
    public File logFile;
    private List<LogEntry> logEntries;
    private PrintWriter log_file_writer;
    private ScreenShotManager screenShotManager;
    private String videoPath;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public TestLogger() throws IOException {
        this.videoRecording = new Flick();
        screenShotManager = new ScreenShotManager();
    }

    public void startLogging(String methodName, String className) throws FileNotFoundException {
        Capabilities capabilities = AppiumDriverManager.getDriver().getCapabilities();
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            if (capabilities.getCapability("browserName") == null) {
                String udid = AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
                System.out.println("Starting ADB logs" + udid);
                logEntries = AppiumDriverManager.getDriver().manage()
                        .logs().get("logcat").filter(Level.ALL);
                logFile = new File(System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY
                        + udid
                        + "__" + methodName + ".txt");
                log_file_writer = new PrintWriter(logFile);
            }
        }
        startVideoRecording(methodName, className);
    }


    private void startVideoRecording(String methodName, String className) {
        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
            try {
                videoRecording
                        .startVideoRecording(className, methodName, methodName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, String> endLog(ITestResult result, String deviceModel,
                                          ThreadLocal<ExtentTest> test)
            throws Exception {
        HashMap<String, String> logs = new HashMap<>();
        String className = result.getInstance().getClass().getSimpleName();
        stopViewRecording(result, className);
        String adbPath = System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY
                + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                + "__"
                + result.getMethod().getMethodName()
                + ".txt";
        logs.put("adbLogs", adbPath);

        if (result.isSuccess()) {
            test.get().log(Status.PASS, result.getMethod().getMethodName());
            getAdbLogs(result, adbPath, test);
        }
        /*
         * Failure Block
         */
        handleTestFailure(result, className, test, deviceModel);
        String baseHostUrl = "http://" + getHostMachineIpAddress() + ":"
                + getRemoteAppiumManagerPort(AppiumDeviceManager
                .getAppiumDevice().getHostName());
        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
            setVideoPath("screenshot/" + AppiumDeviceManager.getMobilePlatform()
                    .toString().toLowerCase()
                    + "/" + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                    + "/" + className + "/" + result.getMethod()
                    .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4");

            if (new File(System.getProperty("user.dir")
                    + FileLocations.OUTPUT_DIRECTORY + getVideoPath()).exists()) {
                logs.put("videoLogs", baseHostUrl + "/" + getVideoPath());
                test.get().log(Status.INFO, "<a target=\"_parent\" href="
                        + getVideoPath() + ">Videologs</a>");
            }
        }
        String failedScreen = screenShotManager.getFailedScreen();
        String framedFailureScreen = screenShotManager.getFramedFailedScreen();

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenShotFailure = null;
            try {
                screenShotFailure = baseHostUrl;
            } catch (Exception e) {
                e.printStackTrace();
            }
            String screenFailure = System.getProperty("user.dir")
                    + FileLocations.OUTPUT_DIRECTORY + failedScreen;
            if (new File(screenFailure).exists()) {
                screenShotFailure = screenShotFailure
                        + "/" + failedScreen;
                logs.put("screenShotFailure", screenShotFailure);
            } else {
                String framedScreenFailure = System.getProperty("user.dir")
                        + FileLocations.OUTPUT_DIRECTORY + framedFailureScreen;
                if (new File(framedScreenFailure).exists()) {
                    screenShotFailure = screenShotFailure
                            + "/" + framedScreenFailure;
                    logs.put("screenShotFailure", screenShotFailure);
                }
            }
        }
        return logs;
    }

    private void stopViewRecording(ITestResult result, String className)
            throws IOException, InterruptedException {
        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
            try {
                videoRecording.stopVideoRecording(className, result.getMethod()
                        .getMethodName(), result.getMethod().getMethodName());
            } catch (IOException e) {
                videoRecording.stopVideoRecording(className, result.getMethod()
                        .getMethodName(), result.getMethod().getMethodName());
            } catch (InterruptedException e) {
                System.out.println("");
            }
        }
        deleteSuccessVideos(result, className);
    }

    private void deleteSuccessVideos(ITestResult result, String className) {
        if (result.isSuccess()) {
            File videoFile = new File(System.getProperty("user.dir")
                    + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                    + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid() + "/"
                    + className + "/" + result.getMethod().getMethodName()
                    + "/" + result.getMethod().getMethodName() + ".mp4");
            System.out.println(videoFile);
            if (videoFile.exists()) {
                videoFile.delete();
            }
        }
    }

    public void getAdbLogs(ITestResult result, String adbPath,
                           ThreadLocal<ExtentTest> test) {
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                && AppiumDriverManager.getDriver().getCapabilities()
                .getCapability("browserName") == null) {
            log_file_writer.println(logEntries);
            log_file_writer.close();
            test.get().log(Status.INFO,
                    "<a target=\"_parent\" href=" + adbPath + ">AdbLogs</a>");
            System.out.println(AppiumDriverManager.getDriver()
                    .getSessionId() + ": Saving device log - Done.");
        }
    }

    private void handleTestFailure(ITestResult result, String className,
                                   ThreadLocal<ExtentTest> test,
                                   String deviceModel) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTest log = test.get()
                    .log(Status.FAIL, "<pre>" + result.getThrowable() + "</pre>");
            String screenShotNameWithTimeStamp = screenShotManager
                    .captureScreenShot(result.getStatus(),
                            result.getInstance().getClass().getSimpleName(),
                            result.getMethod().getMethodName(),
                            result.getMethod().getMethodName(), deviceModel);

            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
                String pathname = System.getProperty("user.dir")
                        + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                        + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + screenShotNameWithTimeStamp
                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
                File framedImageAndroid = new File(
                        pathname);
                if (framedImageAndroid.exists()) {
                    log.addScreenCaptureFromPath(pathname);
                }


            }
            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                String imagePath = System.getProperty("user.dir")
                        + FileLocations.IOS_SCREENSHOTS_DIRECTORY
                        + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + screenShotNameWithTimeStamp
                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
                File framedImageIOS = new File(
                        imagePath);
                System.out.println("************************" + framedImageIOS.exists()
                        + "***********************");
                if (framedImageIOS.exists()) {
                    log.addScreenCaptureFromPath(imagePath);
                }

            }
            String adbPath = "adblogs/"
                    + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                    + "__"
                    + result.getMethod().getMethodName()
                    + ".txt";
            getAdbLogs(result, adbPath, test);

        }
    }


}
