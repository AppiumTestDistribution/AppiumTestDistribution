package com.appium.manager;

import com.appium.utils.ImageUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;
import com.video.recorder.Flick;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    private String screenShotNameWithTimeStamp;
    public ImageUtils imageUtils;

    public TestLogger() {
        this.videoRecording = new Flick();
        imageUtils = new ImageUtils();
    }

    void startLogging(String methodName, AppiumDriver<MobileElement> driver,
                      String device_udid, String className) throws FileNotFoundException {
        startVideoRecording(methodName, device_udid, className);
        if (driver.getSessionDetails().get("platformName").toString().equals("Android")) {
            startVideoRecording(methodName, device_udid, className);
            System.out.println("Starting ADB logs" + device_udid);
            logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            logFile = new File(System.getProperty("user.dir") + "/target/adblogs/" + device_udid
                    .replaceAll("\\W", "_") + "__" + methodName + ".txt");
            log_file_writer = new PrintWriter(logFile);
        }
    }

    private void startVideoRecording(String methodName, String device_udid, String className) {
        if (System.getenv("VIDEO_LOGS") != null) {
            try {
                videoRecording
                        .startVideoRecording(device_udid, className,
                                methodName, methodName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void endLog(ITestResult result, String device_udid,
                       String deviceModel, ThreadLocal<ExtentTest> test,
                       AppiumDriver<MobileElement> driver)
            throws IOException, InterruptedException {
        String className = getClassName(result.getName().split(" ")[2]);
        stopViewRecording(result, className, device_udid);

        if (result.isSuccess()) {
            test.get().log(Status.PASS, result.getMethod().getMethodName());
            getAdbLogs(result, driver, test, device_udid);
        }
        /*
         * Failure Block
         */
        handleTestFailure(result, className, test, driver, device_udid, deviceModel);
        /*
         * Skip block
         */
        if (result.getStatus() == ITestResult.SKIP) {
            test.get().log(Status.SKIP, "Test skipped");
        }

        if (System.getenv("VIDEO_LOGS") != null) {
            if (driver.getSessionDetails().get("platformName").toString().equals("Android")) {
                String videoFilePath = System.getProperty("user.dir")
                        + "/target/screenshot/android/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4";
                boolean exists = new File(videoFilePath)
                        .exists();
                System.out.println("****************" + exists + videoFilePath);
                if (exists) {
                    test.get().log(Status.INFO, "<a target=\"_parent\" href="
                            + "screenshot/android/" + device_udid.replaceAll("\\W", "_")
                            + "/" + className
                            + "/" + result.getMethod().getMethodName()
                            + "/" + result.getMethod()
                            .getMethodName() + ".mp4" + ">Videologs</a>");
                }

            } else if (driver.getSessionDetails().get("platformName").toString().equals("iOS")) {
                if (new File(System.getProperty("user.dir")
                        + "/target/screenshot/iOS/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + result.getMethod()
                        .getMethodName() + "/" + result.getMethod().getMethodName() + ".mp4")
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

    private void stopViewRecording(ITestResult result, String className,
                                   String device_udid) throws IOException, InterruptedException {
        if (System.getenv("VIDEO_LOGS") != null) {
            try {
                videoRecording.stopVideoRecording(device_udid, className,
                        result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (IOException e) {
                videoRecording.stopVideoRecording(device_udid, className,
                        result.getMethod().getMethodName(), result.getMethod().getMethodName());
            } catch (InterruptedException e) {
                System.out.println("");
            }
        }
        deleteSuccessVideos(result, className, device_udid);
    }

    private void deleteSuccessVideos(ITestResult result, String className, String device_udid) {
        if (result.isSuccess()) {
            File videoFile = new File(System.getProperty("user.dir") + "/target/screenshot/android/"
                    + device_udid.replaceAll("\\W", "_") + "/"
                    + className + "/" + result.getMethod().getMethodName()
                    + "/" + result.getMethod().getMethodName() + ".mp4");
            System.out.println(videoFile);
            if (videoFile.exists()) {
                videoFile.delete();
            }
        }
    }

    public void getAdbLogs(ITestResult result, AppiumDriver<MobileElement> driver,
                           ThreadLocal<ExtentTest> test, String device_udid) {
        if (driver.getSessionDetails().get("platformName").toString().equals("Android")) {
            log_file_writer.println(logEntries);
            log_file_writer.flush();
            test.get().log(Status.INFO,
                    "<a target=\"_parent\" href=" + "adblogs/" + device_udid.replaceAll("\\W", "_")
                            + "__"
                            + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
            System.out.println(driver.getSessionId() + ": Saving device log - Done.");
        }
    }

    private void handleTestFailure(ITestResult result, String className,
                                   ThreadLocal<ExtentTest> test,
                                   AppiumDriver<MobileElement> driver, String device_udid,
                                   String deviceModel) throws IOException, InterruptedException {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTest log = test.get()
                    .log(Status.FAIL, "<pre>" + result.getThrowable() + "</pre>");
            captureScreenShot(result.getMethod().getMethodName(), result.getStatus(),
                    result.getTestClass().getName(), driver, deviceModel, device_udid);

            if (driver.toString().split(":")[0].trim().equals("AndroidDriver")) {
                File framedImageAndroid = new File(
                        System.getProperty("user.dir") + "/target/screenshot/android/" + device_udid
                                .replaceAll("\\W", "_") + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp + deviceModel
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.png");
                if (framedImageAndroid.exists()) {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                    + className + "/" + result.getMethod().getMethodName()
                                    + "/" + screenShotNameWithTimeStamp
                                    + deviceModel + "_failed_" + result
                                    .getMethod().getMethodName() + "_framed.png");
                } else {
                    log.addScreenCaptureFromPath(
                            "screenshot/android/" + device_udid.replaceAll("\\W", "_") + "/"
                                    + className + "/" + result.getMethod().getMethodName() + "/"
                                    + screenShotNameWithTimeStamp + deviceModel + "_" + result
                                    .getMethod().getMethodName() + "_failed.png");
                }


            }
            if (driver.getSessionDetails().get("platformName").toString().equals("iOS")) {
                File framedImageIOS = new File(
                        System.getProperty("user.dir") + "/target/screenshot/iOS/" + device_udid
                                .replaceAll("\\W", "_") + "/" + className + "/" + result.getMethod()
                                .getMethodName() + "/" + screenShotNameWithTimeStamp + deviceModel
                                + "_failed_" + result.getMethod().getMethodName() + "_framed.png");
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


            getAdbLogs(result, driver, test, device_udid);

        }
    }

    public void captureScreenShot(String screenShotName, int status, String screenClassName,
                                  AppiumDriver<MobileElement> driver,
                                  String deviceModel, String device_udid)
            throws IOException, InterruptedException {
        captureScreenShot(screenShotName, status, screenClassName, screenShotName,
                driver, deviceModel, device_udid);
    }

    public void captureScreenShot(String screenShotName, int status, String className,
                                  String methodName, AppiumDriver<MobileElement> driver,
                                  String deviceModel,
                                  String device_udid) throws IOException, InterruptedException {
        String context = driver.getContext();
        boolean contextChanged = false;
        if (driver.getSessionDetails().get("platformName").toString().equals("Android") && !context
                .equals("NATIVE_APP")) {
            driver.context("NATIVE_APP");
            contextChanged = true;
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            driver.context(context);
        }
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (driver.getSessionDetails().get("platformName").toString().equals("Android")) {
            String androidModel = screenShotNameWithTimeStamp + deviceModel;
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, androidModel,
                    "android", device_udid, deviceModel);
        } else if (driver.getSessionDetails().get("platformName").toString().equals("iOS")) {
            String iosModel = screenShotNameWithTimeStamp + deviceModel;
            screenShotAndFrame(screenShotName, status, scrFile, methodName, className, iosModel,
                    "iOS", device_udid, deviceModel);
        }
    }

    public String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }

    public void screenShotAndFrame(String screenShotName, int status,
                                   File scrFile, String methodName,
                                   String className, String model, String platform,
                                   String device_udid, String deviceModel) {
        String failedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/"
                        + className + "/" + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_failed" + ".png";
        String capturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className
                        + "/" + methodName + "/" + screenShotName
                        + ".png";
        String framedCapturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className
                        + "/" + methodName + "/" + model + "_"
                        + screenShotName + "_results.png";
        String framedFailedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        .replaceAll("\\W", "_") + "/" + className + "/" + methodName + "/" + model
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
}
