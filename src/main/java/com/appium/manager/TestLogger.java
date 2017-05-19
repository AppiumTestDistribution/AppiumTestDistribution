package com.appium.manager;

import com.appium.utils.ScreenShotManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import com.video.recorder.Flick;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Driver;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by saikrisv on 24/01/17.
 */
class TestLogger {
    private Flick videoRecording;
    public File logFile;
    public List<LogEntry> logEntries;
    public PrintWriter log_file_writer;
    private ScreenShotManager screenShotManager;

    public TestLogger() {
        this.videoRecording = new Flick();
        screenShotManager = new ScreenShotManager();
    }

    public void startLogging(String methodName,String className) throws FileNotFoundException {
        startVideoRecording(methodName, className);
        Capabilities capabilities = DriverManager.getDriver().getCapabilities();
        if (capabilities.getCapability("platformName")
                .toString().equals("Android")
                && capabilities.getCapability("browserName").toString().isEmpty()) {
            startVideoRecording(methodName, className);
            System.out.println("Starting ADB logs" + DeviceUDIDManager.getDeviceUDID());
            logEntries = DriverManager.getDriver().manage().logs().get("logcat").filter(Level.ALL);
            logFile = new File(System.getProperty("user.dir") + "/target/adblogs/" + DeviceUDIDManager.getDeviceUDID()
                    + "__" + methodName + ".txt");
            log_file_writer = new PrintWriter(logFile);
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

    public void endLog(ITestResult result, String deviceModel, ThreadLocal<ExtentTest> test)
            throws IOException, InterruptedException {
        String className = result.getInstance().getClass().getSimpleName();
        stopViewRecording(result, className);

        if (result.isSuccess()) {
            test.get().log(Status.PASS, result.getMethod().getMethodName());
            getAdbLogs(result, test);
        }
        /*
         * Failure Block
         */
        //handleTestFailure(result, className, test, deviceModel);
        /*
         * Skip block
         */
        if (result.getStatus() == ITestResult.SKIP) {
            test.get().log(Status.SKIP, "Test skipped");
        }

        if (System.getenv("VIDEO_LOGS") != null) {
            if (DriverManager.getDriver().getSessionDetails().get("platformName").toString().equals("Android")) {
                String videoFilePath = System.getProperty("user.dir")
                        + "/target/screenshot/android/" + DeviceUDIDManager.getDeviceUDID()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4";
                boolean exists = new File(videoFilePath)
                        .exists();
                System.out.println("****************" + exists + videoFilePath);
                if (exists) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + "screenshot/android/" + DeviceUDIDManager.getDeviceUDID()
                            + "/" + className
                            + "/" + result.getMethod().getMethodName()
                            + "/" + result.getMethod()
                            .getMethodName() + ".mp4" + ">Videologs</a>");
                }

            } else if (DriverManager.getDriver().getSessionDetails().get("platformName").toString().equals("iOS")) {

                String iosVideoFilePath = System.getProperty("user.dir")
                        + "/target/screenshot/iOS/" + DeviceUDIDManager.getDeviceUDID()
                        + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4";
                if (new File(iosVideoFilePath)
                        .exists()) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + "screenshot/iOS/" + DeviceUDIDManager.getDeviceUDID()
                            + "/" + className
                            + "/" + result.getMethod().getMethodName() + "/" + result.getMethod()
                            .getMethodName() + ".mp4" + ">Videologs</a>");
                }
            }

        }
        ExtentManager.getExtent().flush();
    }

    public String getClassName(String s) {
        final String classNameCur = s.substring(1);
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
        return className;
    }

    private void stopViewRecording(ITestResult result, String className) throws IOException, InterruptedException {
        if (System.getenv("VIDEO_LOGS") != null) {
            try {
                videoRecording.stopVideoRecording(className, result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (IOException e) {
                videoRecording.stopVideoRecording(className, result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (InterruptedException e) {
                System.out.println("");
            }
        }
        deleteSuccessVideos(result, className);
    }

    private void deleteSuccessVideos(ITestResult result, String className) {
        if (result.isSuccess()) {
            File videoFile = new File(System.getProperty("user.dir") + "/target/screenshot/android/"
                    + DeviceUDIDManager.getDeviceUDID() + "/"
                    + className + "/" + result.getMethod().getMethodName()
                    + "/" + result.getMethod().getMethodName() + ".mp4");
            System.out.println(videoFile);
            if (videoFile.exists()) {
                videoFile.delete();
            }
        }
    }

    public void getAdbLogs(ITestResult result,
                           ThreadLocal<ExtentTest> test) {
        if (DriverManager.getDriver().getSessionDetails().get("platformName").toString().equals("Android")) {
            log_file_writer.println(logEntries);
            log_file_writer.flush();
            test.get().log(Status.INFO,
                    "<a target=\"_parent\" href=" + "adblogs/" + DeviceUDIDManager.getDeviceUDID()
                            + "__"
                            + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
            System.out.println(DriverManager.getDriver().getSessionId() + ": Saving device log - Done.");
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
                            result.getTestClass().getName(), result.getMethod().getMethodName(), deviceModel);

            if (DriverManager.getDriver().toString().split(":")[0].trim().equals("AndroidDriver")) {
                File framedImageAndroid = new File(
                        System.getProperty("user.dir") + "/target/screenshot/android/" + DeviceUDIDManager.getDeviceUDID()
                                + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.jpeg");
                if (framedImageAndroid.exists()) {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + DeviceUDIDManager.getDeviceUDID() + "/"
                                    + className + "/" + result.getMethod().getMethodName()
                                    + "/" + screenShotNameWithTimeStamp
                                    + "_failed_" + result
                                    .getMethod().getMethodName() + "_framed.jpeg");
                } else {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + DeviceUDIDManager.getDeviceUDID() + "/"
                                    + className + "/" + result.getMethod().getMethodName() + "/"
                                    + screenShotNameWithTimeStamp + "_" + result
                                    .getMethod().getMethodName() + "_failed.jpeg");
                }


            }
            if (DriverManager.getDriver().getSessionDetails().get("platformName").toString().equals("iOS")) {
                File framedImageIOS = new File(
                        System.getProperty("user.dir") + "/target/screenshot/iOS/" + DeviceUDIDManager.getDeviceUDID()
                                + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.jpeg");
                System.out.println("************************" + framedImageIOS.exists()
                        + "***********************");
                if (framedImageIOS.exists()) {
                    log.addScreenCaptureFromPath("screenshot/iOS/"
                            + DeviceUDIDManager.getDeviceUDID()
                            + "/" + className
                            + "/" + result.getMethod().getMethodName() + "/"
                            + screenShotNameWithTimeStamp + "_failed_" + result
                            .getMethod().getMethodName() + "_framed.jpeg");
                } else {
                    log.addScreenCaptureFromPath("screenshot/iOS/"
                            + DeviceUDIDManager.getDeviceUDID()
                            + "/" + className
                            + "/" + result.getMethod().getMethodName() + "/"
                            + screenShotNameWithTimeStamp + "_" + result
                            .getMethod().getMethodName() + "_failed.jpeg");
                }

            }


            getAdbLogs(result, test);

        }
    }
}
