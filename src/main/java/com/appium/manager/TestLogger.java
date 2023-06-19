package com.appium.manager;

import com.annotation.values.Author;
import com.appium.entities.MobilePlatform;
import com.appium.filelocations.FileLocations;
import com.appium.utils.Helpers;
import com.epam.reportportal.service.ReportPortal;
import com.video.recorder.AppiumScreenRecordFactory;
import com.video.recorder.IScreenRecord;
import org.apache.log4j.Logger;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;

/**
 * Created by saikrisv on 24/01/17.
 */
public class TestLogger extends Helpers {
    private ThreadLocal<List<LogEntry>> logEntries = new ThreadLocal<>();
    private ThreadLocal<PrintWriter> log_file_writer = new ThreadLocal<>();
    private ScreenShotManager screenShotManager;
    private String videoPath;
    private static final Logger LOGGER = Logger.getLogger(TestLogger.class.getName());

    private String getVideoPath() {
        return videoPath;
    }

    private void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public TestLogger() {
        screenShotManager = new ScreenShotManager();
    }

    public void startDeviceLogAndVideoCapture(ITestResult iTestResult) {
        String methodName = iTestResult.getMethod().getMethodName();
        String logDirectory = System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY;
        startDeviceLogAndVideoCapture(logDirectory, methodName);
        // setDescription(iTestResult); Needs a fix
    }

    public void startDeviceLogAndVideoCapture(String logDirectory, String methodName) {
        startDeviceLogCapture(logDirectory, methodName);
        startVideoRecording();
    }

    private void startDeviceLogCapture(String logDirectory, String testMethodName) {
        File logFile;
        if (isNativeAndroid()) {
            String udid = AppiumDriverManager.getDriver().getCapabilities()
                            .getCapability("appium:udid").toString();

            String logFileName = logDirectory + udid + "__" + testMethodName + ".txt";
            List<LogEntry> logcat = AppiumDriverManager.getDriver().manage()
                    .logs().get("logcat").getAll();
            logEntries.set(logcat);
            logFile = new File(logFileName);
            try {
                log_file_writer.set(new PrintWriter(logFile));
            } catch (FileNotFoundException e) {
                LOGGER.error("Unable to start device log capture", e);
            }
        }
    }

    private static void startVideoRecording() {
        if ("true".equalsIgnoreCase(getOverriddenStringValue("VIDEO_LOGS"))) {
            IScreenRecord videoRecording = AppiumScreenRecordFactory.recordScreen();
            videoRecording.startVideoRecording();
        }
    }

    private void setDescription(ITestResult iTestResult) {
        Optional<String> originalDescription = Optional.ofNullable(iTestResult
                .getMethod().getDescription());
        String description = "Platform: " + AppiumDeviceManager.getMobilePlatform()
                + " UDID: " + AppiumDeviceManager.getAppiumDevice().getUdid()
                + " Name: " + AppiumDeviceManager.getAppiumDevice().getDeviceName();
        // + " Host: " + AppiumDeviceManager.getAppiumDevice().get(); // Needs a fix
        Author annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(Author.class);
        if (annotation != null) {
            description += "\nAuthor: " + annotation.name();
        }
        if (originalDescription.isPresent()
                && !originalDescription.get()
                .contains(AppiumDeviceManager.getAppiumDevice().getUdid())) {
            iTestResult.getMethod().setDescription(originalDescription.get()
                    + "\n" + description);
        } else {
            iTestResult.getMethod().setDescription(description);
        }
    }

    protected HashMap<String, String> endLogging(ITestResult result, String deviceModel) {
        HashMap<String, String> logs = new HashMap<>();
        String className = result.getInstance().getClass().getSimpleName();
        stopVideoRecording(result, className);
        stopDeviceLogCapture(result, logs);
        /*
         * Failure Block
         */
        handleTestFailure(result, className, deviceModel);
        String baseHostUrl = "http://" + getHostMachineIpAddress() + ":"
                + getRemoteAppiumManagerPort("127.0.0.1");
        if ("true".equalsIgnoreCase(getOverriddenStringValue("VIDEO_LOGS"))) {
            setVideoPath("screenshot/" + AppiumDeviceManager.getMobilePlatform()
                    .toString().toLowerCase()
                    + "/" + AppiumDeviceManager.getAppiumDevice().getUdid()
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

    private void stopDeviceLogCapture(ITestResult result, HashMap<String, String> logs) {
        if (isNativeAndroid()) {
            String adbPath = System.getProperty("user.dir") + FileLocations.ADB_LOGS_DIRECTORY
                    + AppiumDriverManager.getDriver().getCapabilities()
                        .getCapability("appium:udid").toString()
                    + "__" + result.getMethod().getMethodName() + ".txt";
            logs.put("adbLogs", adbPath);
            logEntries.get().forEach(logEntry -> {
                log_file_writer.get().println(logEntry);
            });
            log_file_writer.get().close();
            File adbLogFile = new File(adbPath);
            ReportPortal.emitLog(String.format("ADB Logs - %s", adbLogFile.getName()),
                    "DEBUG", new Date(), adbLogFile);
        }
    }

    private boolean isNativeAndroid() {
        return AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
                && AppiumDriverManager.getDriver().getCapabilities()
                .getCapability("browserName") == null;
    }

    private void stopVideoRecording(ITestResult result, String className) {
        if ("true".equalsIgnoreCase(getOverriddenStringValue("VIDEO_LOGS"))) {
            IScreenRecord videoRecording = AppiumScreenRecordFactory.recordScreen();
            videoRecording.stopVideoRecording(className, result.getMethod()
                    .getMethodName(), result.getMethod().getMethodName());
        }
        deleteSuccessVideos(result, className);
    }

    private void deleteSuccessVideos(ITestResult result, String className) {
        if (result.isSuccess()
                && (null != getOverriddenStringValue("KEEP_ALL_VIDEOS"))
                && !(getOverriddenStringValue("KEEP_ALL_VIDEOS")
                .equalsIgnoreCase("true"))) {
            File videoFile = new File(System.getProperty("user.dir")
                    + FileLocations.ANDROID_SCREENSHOTS_DIRECTORY
                    + AppiumDeviceManager.getAppiumDevice().getUdid() + "/"
                    + className + "/" + result.getMethod().getMethodName()
                    + "/" + result.getMethod().getMethodName() + ".mp4");
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
                        + AppiumDeviceManager.getAppiumDevice().getUdid()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + screenShotNameWithTimeStamp
                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
                ReportPortal.emitLog("Screenshots",
                        "ERROR", new Date(), new File(imagePath));
            }
            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                String imagePath = System.getProperty("user.dir")
                        + FileLocations.IOS_SCREENSHOTS_DIRECTORY
                        + AppiumDeviceManager.getAppiumDevice().getUdid()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + screenShotNameWithTimeStamp
                        + "-" + result.getMethod().getMethodName() + "_failed.jpeg";
                ReportPortal.emitLog("Screenshots",
                        "ERROR", new Date(), new File(imagePath));
            }
        }
    }

}
