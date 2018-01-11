package com.appium.utils;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.openqa.selenium.OutputType;
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
    private String capturedScreen;
    private String failedScreen;
    private String framedFailedScreen;
    private String framedCapturedScreen;

    public String getFramedCapturedScreen() {
        return framedCapturedScreen;
    }

    public void setFramedCapturedScreen(String framedCapturedScreen) {
        this.framedCapturedScreen = framedCapturedScreen;
    }

    public String getCapturedScreen() {
        return capturedScreen;
    }

    public void setCapturedScreen(String capturedScreen) {
        this.capturedScreen = capturedScreen;
    }

    public String getFramedFailedScreen() {
        return framedFailedScreen;
    }

    public void setFramedFailedScreen(String framedFailedScreen) {
        this.framedFailedScreen = framedFailedScreen;
    }

    public String getFailedScreen() {
        return failedScreen;
    }

    public void setFailedScreen(String failedScreen) {
        this.failedScreen = failedScreen;
    }


    public ScreenShotManager() {
        imageUtils = new ImageUtils();
    }

    public String captureScreenShot(int status, String className,
                                    String methodName, String deviceModel)
            throws IOException, InterruptedException {

        String getDeviceModel = null;
        if (AppiumDriverManager.getDriver().getSessionId() != null) {
            System.out.println("Current Running Thread Status"
                    + AppiumDriverManager.getDriver().getSessionId());
            File scrFile = AppiumDriverManager.getDriver()
                    .getScreenshotAs(OutputType.FILE);
            screenShotNameWithTimeStamp = currentDateAndTime();
            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
                getDeviceModel = screenShotNameWithTimeStamp + deviceModel;
                screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                        "android", deviceModel);
            } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
                getDeviceModel = screenShotNameWithTimeStamp + deviceModel;
                screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                        "iOS", deviceModel);
            }
        }
        return getDeviceModel;
    }

    public void captureScreenShot(String screenShotName)
            throws InterruptedException, IOException {
        String className = new Exception().getStackTrace()[1].getClassName();
        String deviceModel = null;
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            deviceModel = new AndroidDeviceConfiguration().getDeviceModel();
        } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
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
                                    String className, String model,
                                    String platform, String deviceModel) {
        setFailedScreen(
                  "screenshot/" + platform + "/" + AppiumDeviceManager.getDeviceUDID()
                        + "/" + className + "/"
                        + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_failed" + ".jpeg");
        setCapturedScreen(
                "screenshot/" + platform + "/" + AppiumDeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/"
                        + screenShotNameWithTimeStamp + deviceModel + "_"
                        + methodName + "_results.jpeg");

        setFramedCapturedScreen("screenshot/" + platform + "/" + AppiumDeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/" + model + "_"
                        + methodName + "_results_framed.jpeg");
        setFramedFailedScreen(
                "screenshot/" + platform + "/" + AppiumDeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/" + model
                        + "_failed_" + methodName + "_framed.jpeg");

        try {
            File framePath =
                    new File(System.getProperty("user.dir")
                            + "/src/test/resources/frames/");
            if (status == ITestResult.FAILURE) {
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
                        + "/target/" + getFailedScreen().trim()));
            } else {
                FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
                        + "/target/" + getCapturedScreen().trim()));
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
                                    String screenToFrame = System.getProperty("user.dir")
                                            + "/target/" + getFailedScreen();
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            System.getProperty("user.dir")
                                                    + "/target/" + getFramedFailedScreen());
                                    deleteFile(screenToFrame);
                                } else {
                                    String screenToFrame = System.getProperty("user.dir")
                                            + "/target/" + getCapturedScreen();
                                    imageUtils.wrapDeviceFrames(files1[i].toString(), screenToFrame,
                                            System.getProperty("user.dir")
                                                    + "/target/" +  getFramedCapturedScreen());
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
