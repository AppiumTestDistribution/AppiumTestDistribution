package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.utils.Helpers;
import com.appium.utils.ScreenShotManager;
import com.epam.reportportal.service.ReportPortal;
import com.video.recorder.Flick;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by saikrisv on 24/01/17.
 */
class TestLogger extends Helpers {

    private Flick videoRecording;
    private File logFile;
    private List<LogEntry> logEntries;
    private PrintWriter log_file_writer;
    private ScreenShotManager screenShotManager;
    private String videoPath;

    private String getVideoPath() {
        return videoPath;
    }

    private void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    TestLogger() {
        this.videoRecording = new Flick();
        screenShotManager = new ScreenShotManager();
    }

    protected void startLogging(String methodName, String className) throws IOException, InterruptedException {
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                && AppiumDriverManager.getDriver().getCapabilities().getCapability("browserName") == null) {
            String udid = AppiumDeviceManager.getAppiumDevice().getDevice().getUdid();
            logEntries = AppiumDriverManager.getDriver().manage()
                    .logs().get("logcat").filter(Level.ALL);
            logFile = new File(System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY
                    + udid + "__" + methodName + ".txt");
            log_file_writer = new PrintWriter(logFile);
        }
        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
            videoRecording.startVideoRecording(className, methodName, methodName);
        }
    }

    protected HashMap<String, String> endLogging(ITestResult result, String deviceModel)
            throws Exception {
        HashMap<String, String> logs = new HashMap<>();
        String className = result.getInstance().getClass().getSimpleName();
        stopViewRecording(result, className);
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                && AppiumDriverManager.getDriver().getCapabilities()
                .getCapability("browserName") == null) {
            String adbPath = System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY
                    + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                    + "__" + result.getMethod().getMethodName() + ".txt";
            logs.put("adbLogs", adbPath);
            log_file_writer.println(logEntries);
            log_file_writer.close();
            ReportPortal.emitLog("ADB Logs", "ERROR", new Date(), new File(adbPath));
        }
        /*
         * Failure Block
         */
        handleTestFailure(result, className, deviceModel);
        String baseHostUrl = "http://" + getHostMachineIpAddress() + ":"
                + getRemoteAppiumManagerPort(AppiumDeviceManager
                .getAppiumDevice().getHostName());
        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
            setVideoPath("screenshot/" + AppiumDeviceManager.getMobilePlatform()
                    .toString().toLowerCase()
                    + "/" + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                    + "/" + className + "/" + result.getMethod()
                    .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4");

            String videoPath = System.getProperty("user.dir")
                    + FileLocations.OUTPUT_DIRECTORY + getVideoPath();
            if (new File(videoPath).exists()) {
                ReportPortal.emitLog("Video Logs", "Trace", new Date(), new File(videoPath));
                logs.put("videoLogs", baseHostUrl + "/" + getVideoPath());
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

    private void handleTestFailure(ITestResult result, String className,
                                   String deviceModel) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenShotNameWithTimeStamp = screenShotManager
                    .captureScreenShot(result.getStatus(),
                            result.getInstance().getClass().getSimpleName(),
                            result.getMethod().getMethodName(),
                            result.getMethod().getMethodName(), deviceModel);

            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
                String imagePath = System.getProperty("user.dir")
                        + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                        + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + screenShotNameWithTimeStamp
                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
                ReportPortal.emitLog("Screenshots",
                        "ERROR", new Date(), new File(imagePath));
            }
            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                String imagePath = System.getProperty("user.dir")
                        + FileLocations.IOS_SCREENSHOTS_DIRECTORY
                        + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + screenShotNameWithTimeStamp
                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
                ReportPortal.emitLog("Screenshots",
                        "ERROR", new Date(), new File(imagePath));
            }
        }
    }


}
