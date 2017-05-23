package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.android.AndroidDeviceConfiguration;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.DeviceManager;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by saikrisv on 26/04/17.
 */
public class ScreenShotManager {

    private String screenShotNameWithTimeStamp;
    private ImageUtils imageUtils;

    public ScreenShotManager() {
        imageUtils = new ImageUtils();
    }

    public String captureScreenShot(int status, String className, String methodName, String deviceModel)
            throws IOException, InterruptedException {

        String getDeviceModel = null;
        boolean contextChanged = false;
//        if ("Android".equals(AppiumDriverManager.getDriver().getSessionDetails().get("platformName").toString())
//                && !AppiumDriverManager.getDriver().getContext()
//                .equals("NATIVE_APP")) {
//            AppiumDriverManager.getDriver().context("NATIVE_APP");
//            contextChanged = true;
//        }
        System.out.println("Current Running Thread Status" + AppiumDriverManager.getDriver().getSessionId());
        File scrFile = ((TakesScreenshot) AppiumDriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
//        if (contextChanged) {
//            driver.context(context);
//        }
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (AppiumDriverManager.getDriver().getSessionDetails().get("platformName").toString().equals("Android")) {
            getDeviceModel = screenShotNameWithTimeStamp + deviceModel;
            screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                    "android", deviceModel);
        } else if (AppiumDriverManager.getDriver().getSessionDetails().get("platformName").toString().equals("iOS")) {
            getDeviceModel = screenShotNameWithTimeStamp + deviceModel;
            screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                    "iOS", deviceModel);
        }
        return getDeviceModel;
    }

    public void captureScreenShot(String screenShotName)
            throws InterruptedException, IOException {
        String className = new Exception().getStackTrace()[1].getClassName();
        String platformName = AppiumDriverManager.getDriver().getSessionDetails().get("platformName").toString();
        String deviceModel = null;
        if (platformName.equals("Android")) {
            deviceModel = new AndroidDeviceConfiguration().getDeviceModel();
        } else if (platformName.equals("iOS")) {
            deviceModel = new IOSDeviceConfiguration().getIOSDeviceProductTypeAndVersion();
        }
        captureScreenShot(1, className, screenShotName, deviceModel);
    }


    private String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }


    private void screenShotAndFrame(int status,
                                    File scrFile, String methodName,
                                    String className, String model, String platform, String deviceModel) {
        String failedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + DeviceManager.getDeviceUDID()
                        + "/" + className + "/"
                        + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_failed" + ".jpeg";
        String capturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + DeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_results.jpeg";
        String framedCapturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + DeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/" + model + "_"
                        + methodName + "_results_framed.jpeg";
        String framedFailedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + DeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/" + model
                        + "_failed_" + methodName + "_framed.jpeg";

        try {
            File framePath =
                    new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
            if (status == ITestResult.FAILURE) {
                FileUtils.copyFile(scrFile, new File(failedScreen.trim()));
            } else {
                FileUtils.copyFile(scrFile, new File(capturedScreen.trim()));
            }

            File[] files1 = framePath.listFiles();
            if (framePath.exists()) {
                for (int i = 0; i < files1.length; i++) {
                    if (files1[i].isFile()) { //this line weeds out other directories/folders
                        System.out.println(files1[i]);

                        Path p = Paths.get(files1[i].toString());
                        String fileName = p.getFileName().toString().toLowerCase();
                        if (model.toLowerCase()
                                .contains(fileName.split(".png")[0].toLowerCase())) {
                            try {
                                if (status == ITestResult.FAILURE) {
                                    String screenToFrame = failedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedFailedScreen);
                                    deleteFile(screenToFrame);
                                } else {
                                    String screenToFrame = capturedScreen;
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            framedCapturedScreen);
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

    private void deleteFile(String screenToFrame) {
        File fileToDelete = new File(screenToFrame);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }
}
