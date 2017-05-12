package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AndroidDeviceConfiguration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
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

    public String captureScreenShot(int status, String className,
                                    String methodName, AppiumDriver<MobileElement> driver,
                                    String deviceModel,
                                    String device_udid) throws IOException, InterruptedException {
        String context = driver.getContext();
        String getDeviceModel = null;
        boolean contextChanged = false;
        if ("Android".equals(driver.getSessionDetails().get("platformName").toString()) && !context
                .equals("NATIVE_APP")) {
            driver.context("NATIVE_APP");
            contextChanged = true;
        }
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        if (contextChanged) {
            driver.context(context);
        }
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (driver.getSessionDetails().get("platformName").toString().equals("Android")) {
            getDeviceModel = screenShotNameWithTimeStamp + deviceModel;
            screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                    "android", device_udid, deviceModel);
        } else if (driver.getSessionDetails().get("platformName").toString().equals("iOS")) {
            getDeviceModel = screenShotNameWithTimeStamp + deviceModel;
            screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                    "iOS", device_udid, deviceModel);
        }
        return getDeviceModel;
    }

    public void captureScreenShot(AppiumDriver driver, String screenShotName)
            throws InterruptedException, IOException {
        String className = new Exception().getStackTrace()[1].getClassName();
        String udid = driver.getSessionDetails().get("udid").toString();
        String platformName = driver.getSessionDetails().get("platformName").toString();
        String deviceModel = null;
        if (platformName.equals("Android")) {
            deviceModel = new AndroidDeviceConfiguration().getDeviceModel(udid);
        } else if (platformName.equals("iOS")) {
            deviceModel = new IOSDeviceConfiguration().getIOSDeviceProductTypeAndVersion(udid);
        }
        captureScreenShot(1, className, screenShotName,
                driver, deviceModel, udid);
    }


    private String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }


    private void screenShotAndFrame(int status,
                                    File scrFile, String methodName,
                                    String className, String model, String platform,
                                    String device_udid, String deviceModel) {
        String failedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        + "/" + className + "/"
                        + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_failed" + ".png";
        String capturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        + "/" + className
                        + "/" + methodName + "/" + methodName
                        + ".png";
        String framedCapturedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        + "/" + className
                        + "/" + methodName + "/" + model + "_"
                        + methodName + "_results.png";
        String framedFailedScreen =
                System.getProperty("user.dir") + "/target/screenshot/"
                        + platform + "/" + device_udid
                        + "/" + className
                        + "/" + methodName + "/" + model
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
