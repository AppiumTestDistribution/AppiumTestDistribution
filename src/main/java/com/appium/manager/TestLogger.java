package com.appium.manager;

import com.appium.entities.MobilePlatform;
import com.appium.utils.ScreenShotManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
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
class TestLogger {
    private Flick videoRecording;
    public File logFile;
    private List<LogEntry> logEntries;
    private PrintWriter log_file_writer;
    private ScreenShotManager screenShotManager;
    private  String videoPath;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public TestLogger() {
        this.videoRecording = new Flick();
        screenShotManager = new ScreenShotManager();
    }

    public void startLogging(String methodName,String className) throws FileNotFoundException {
        Capabilities capabilities = AppiumDriverManager.getDriver().getCapabilities();
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            if (capabilities.getCapability("browserName") == null) {
                System.out.println("Starting ADB logs" + DeviceManager.getDeviceUDID());
                logEntries = AppiumDriverManager.getDriver().manage()
                        .logs().get("logcat").filter(Level.ALL);
                logFile = new File(System.getProperty("user.dir") + "/target/adblogs/"
                        + DeviceManager.getDeviceUDID()
                        + "__" + methodName + ".txt");
                log_file_writer = new PrintWriter(logFile);
                startVideoRecording(methodName, className);
            }

        }
    }

    private void startVideoRecording(String methodName, String className) {
        if (System.getenv("VIDEO_LOGS") != null) {
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
            throws IOException, InterruptedException {
        HashMap<String,String> logs = new HashMap<>();
        String className = result.getInstance().getClass().getSimpleName();
        stopViewRecording(result, className);
        String adbPath = "adblogs/"
                + DeviceManager.getDeviceUDID()
                + "__"
                + result.getMethod().getMethodName()
                + ".txt";

        if (result.isSuccess()) {
            test.get().log(Status.PASS, result.getMethod().getMethodName());
            getAdbLogs(result, adbPath, test);
        }
        /*
         * Failure Block
         */
        handleTestFailure(result, className, test, deviceModel);
        /*
         * Skip block
         */
        if (result.getStatus() == ITestResult.SKIP) {
            test.get().log(Status.SKIP, "Test skipped");
        }

        if (System.getenv("VIDEO_LOGS") != null) {
            setVideoPath("screenshot/" + DeviceManager.getMobilePlatform()
                    .toString().toLowerCase()
                    + "/" + DeviceManager.getDeviceUDID()
                    + "/" + className + "/" + result.getMethod()
                    .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4");
            logs.put("videoLogs", getVideoPath());
            if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
                boolean exists = new File(getVideoPath())
                        .exists();
                System.out.println("****************" + exists + getVideoPath());
                if (exists) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + getVideoPath() + ">Videologs</a>");
                }

            } else if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                if (new File(getVideoPath())
                        .exists()) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + getVideoPath() + ">Videologs</a>");
                }
            }

        }
        String failedScreen = screenShotManager.getFailedScreen();
        String framedFailureScreen = screenShotManager.getFramedFailedScreen();

        logs.put("adbLogs", adbPath);
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenShotFailure;
            if (new File(System.getProperty("user.dir")
                    + "/target/" + failedScreen).exists()) {
                screenShotFailure = failedScreen;
                logs.put("screenShotFailure",screenShotFailure);
            } else if (new File(System.getProperty("user.dir")
                    + "/target/" + framedFailureScreen).exists()) {
                screenShotFailure = framedFailureScreen;
                logs.put("screenShotFailure",screenShotFailure);
            }
        }
        ExtentManager.getExtent().flush();
        return logs;
    }

    private void stopViewRecording(ITestResult result, String className)
            throws IOException, InterruptedException {
        if (System.getenv("VIDEO_LOGS") != null) {
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
                    + "/target/screenshot/android/"
                    + DeviceManager.getDeviceUDID() + "/"
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
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)
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
                                   String deviceModel) throws IOException, InterruptedException {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTest log = test.get()
                    .log(Status.FAIL, "<pre>" + result.getThrowable() + "</pre>");
            String screenShotNameWithTimeStamp = screenShotManager
                    .captureScreenShot(result.getStatus(),
                            result.getInstance().getClass().getSimpleName(),
                            result.getMethod().getMethodName(), deviceModel);

            if (AppiumDriverManager.getDriver().toString().split(":")[0]
                    .trim().equals("AndroidDriver")) {
                File framedImageAndroid = new File(
                        System.getProperty("user.dir")
                                + "/target/screenshot/android/" + DeviceManager
                                .getDeviceUDID()
                                + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.jpeg");
                if (framedImageAndroid.exists()) {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + DeviceManager.getDeviceUDID() + "/"
                                    + className + "/" + result.getMethod().getMethodName()
                                    + "/" + screenShotNameWithTimeStamp
                                    + "_failed_" + result
                                    .getMethod().getMethodName() + "_framed.jpeg");
                } else {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + DeviceManager.getDeviceUDID() + "/"
                                    + className + "/" + result.getMethod().getMethodName() + "/"
                                    + screenShotNameWithTimeStamp + "_" + result
                                    .getMethod().getMethodName() + "_failed.jpeg");
                }


            }
            if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                File framedImageIOS = new File(
                        System.getProperty("user.dir")
                                + "/target/screenshot/iOS/" + DeviceManager.getDeviceUDID()
                                + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.jpeg");
                System.out.println("************************" + framedImageIOS.exists()
                        + "***********************");
                if (framedImageIOS.exists()) {
                    log.addScreenCaptureFromPath("screenshot/iOS/"
                            + DeviceManager.getDeviceUDID()
                            + "/" + className
                            + "/" + result.getMethod().getMethodName() + "/"
                            + screenShotNameWithTimeStamp + "_failed_" + result
                            .getMethod().getMethodName() + "_framed.jpeg");
                } else {
                    log.addScreenCaptureFromPath("screenshot/iOS/"
                            + DeviceManager.getDeviceUDID()
                            + "/" + className
                            + "/" + result.getMethod().getMethodName() + "/"
                            + screenShotNameWithTimeStamp + "_" + result
                            .getMethod().getMethodName() + "_failed.jpeg");
                }

            }
            String adbPath = "adblogs/"
                    + DeviceManager.getDeviceUDID()
                    + "__"
                    + result.getMethod().getMethodName()
                    + ".txt";
            getAdbLogs(result, adbPath, test);

        }
    }


}
