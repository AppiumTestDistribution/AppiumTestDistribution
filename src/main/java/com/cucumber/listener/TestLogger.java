package com.cucumber.listener;

import com.annotation.values.Author;
import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.ScreenShotManager;
import com.appium.utils.Helpers;
import com.epam.reportportal.service.ReportPortal;
import com.video.recorder.AppiumScreenRecordFactory;
import com.video.recorder.IScreenRecord;
import io.cucumber.core.exception.CucumberException;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Created by saikrisv on 24/01/17.
 */
class TestLogger extends Helpers {

    private File logFile;
    private ThreadLocal<List<LogEntry>> logEntries = new ThreadLocal<>();
    private ThreadLocal<PrintWriter> log_file_writer = new ThreadLocal<>();
    private ScreenShotManager screenShotManager;
    private String videoPath;

    private String getVideoPath() {
        return videoPath;
    }

    private void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    TestLogger() {
        screenShotManager = new ScreenShotManager();
    }

    private File createLogsDir(String fileName){
        logFile = new File(System.getProperty("user.dir") + FileLocations.DEVICE_LOGS_DIRECTORY
                + fileName + ".txt");
        System.out.println("The logs file is created : " + logFile.getAbsolutePath());

//        if (!logFile.getParentFile().exists()) {
//            try {
//                logFile.mkdir();
//            } catch (SecurityException se) {
//                se.printStackTrace();
//            }
//        }
        System.out.println("The file exists? " + logFile.exists());
        if(!logFile.exists()){
            try {
                System.out.println("The parent directory should be 'reports' : " + logFile.getParentFile().getAbsolutePath());
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return logFile;
    }


    protected void startLogging(String testName) throws IOException {
        if (isNativeAndroid()) {
            String udid = AppiumDeviceManager
                    .getAppiumDevice()
                    .getDevice()
                    .getUdid();
            List<LogEntry> logcat = AppiumDriverManager
                    .getDriver()
                    .manage()
                    .logs()
                    .get("logcat").filter(Level.ALL);
            logEntries.set(logcat);
            createLogsDir("/" + udid + "__" + testName.replaceAll(" ", "_"));
            log_file_writer.set(new PrintWriter(logFile));
        }
//        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
//            IScreenRecord videoRecording = AppiumScreenRecordFactory.recordScreen();
//            videoRecording.startVideoRecording(testName, testName, testName);
//        }
        //setDescription(testName);
    }

    protected HashMap<String, String> endLogging(String testName, String testResult)
            throws Exception {
        HashMap<String, String> logs = new HashMap<>();
        //stopViewRecording(result, className);
        if (isNativeAndroid()) {
            String adbPath = System.getProperty("user.dir") + FileLocations.DEVICE_LOGS_DIRECTORY
                    + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
                    + "__" + testName + ".txt";
            logs.put("adbLogs", adbPath);
            logEntries.get().forEach(logEntry -> {
                log_file_writer.get().println(logEntry);
            });
            log_file_writer.get().close();
            ReportPortal.emitLog("ADB Logs", "DEBUG", new Date(), new File(adbPath));
        }
        /*
         * Failure Block
//         */
//        String deviceModel = AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel();
//        handleTestFailure(testName, testResult, deviceModel);
//        String baseHostUrl = "http://" + getHostMachineIpAddress() + ":"
//                + getRemoteAppiumManagerPort(AppiumDeviceManager
//                .getAppiumDevice().getHostName());
//        if ("true".equalsIgnoreCase(System.getenv("VIDEO_LOGS"))) {
//            setVideoPath("screenshot/" + AppiumDeviceManager.getMobilePlatform()
//                    .toString().toLowerCase()
//                    + "/" + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
//                    + "/" + className + "/" + result.getMethod()
//                    .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4");
//
//            String videoPath = System.getProperty("user.dir")
//                    + FileLocations.OUTPUT_DIRECTORY + getVideoPath();
//            if (new File(videoPath).exists()) {
//                ReportPortal.emitLog("Video Logs", "Trace", new Date(), new File(videoPath));
//                logs.put("videoLogs", baseHostUrl + "/" + getVideoPath());
//            }
//        }
//        String failedScreen = screenShotManager.getFailedScreen();
//        String framedFailureScreen = screenShotManager.getFramedFailedScreen();
//
//        if (result.getStatus() == ITestResult.FAILURE) {
//            String screenShotFailure = null;
//            try {
//                screenShotFailure = baseHostUrl;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String screenFailure = System.getProperty("user.dir")
//                    + FileLocations.OUTPUT_DIRECTORY + failedScreen;
//            if (new File(screenFailure).exists()) {
//                screenShotFailure = screenShotFailure
//                        + "/" + failedScreen;
//                logs.put("screenShotFailure", screenShotFailure);
//            } else {
//                String framedScreenFailure = System.getProperty("user.dir")
//                        + FileLocations.OUTPUT_DIRECTORY + framedFailureScreen;
//                if (new File(framedScreenFailure).exists()) {
//                    screenShotFailure = screenShotFailure
//                            + "/" + framedScreenFailure;
//                    logs.put("screenShotFailure", screenShotFailure);
//                }
//            }
//        }
        return logs;
    }

    private boolean isNativeAndroid() {
        return AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
            && AppiumDriverManager.getDriver().getCapabilities()
            .getCapability("browserName") == null;
    }

//    private void handleTestFailure(String testName, String testResult,
//                                   String deviceModel) {
//        if (testResult == "fail") {
//            String screenShotNameWithTimeStamp = screenShotManager
//                    .captureScreenShot(testResult,
//                            testName,
//                            testName,
//                            testName, deviceModel);
//
//            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
//                String imagePath = System.getProperty("user.dir")
//                        + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
//                        + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
//                        + "/" + className + "/" + result.getMethod()
//                        .getMethodName() + "/" + screenShotNameWithTimeStamp
//                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
//                ReportPortal.emitLog("Screenshots",
//                        "ERROR", new Date(), new File(imagePath));
//            }
//            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
//                String imagePath = System.getProperty("user.dir")
//                        + FileLocations.IOS_SCREENSHOTS_DIRECTORY
//                        + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
//                        + "/" + className + "/" + result.getMethod()
//                        .getMethodName() + "/" + screenShotNameWithTimeStamp
//                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
//                ReportPortal.emitLog("Screenshots",
//                        "ERROR", new Date(), new File(imagePath));
//            }
//        }
//    }


}
