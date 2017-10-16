package com.appium.utils;

import com.appium.entities.MobilePlatform;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.DeviceManager;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    JSONArray screenShotArray = new JSONArray();
    JSONObject screenshotDetails = new JSONObject();
    JSONObject logs = new JSONObject();
    ;

    public static HashMap<String, String> syncal =
            new HashMap<>();

//    public Map<String, String> getSynmap() {
//        return synmap;
//    }

    public static Map<String, String> synmap
            = Collections.synchronizedMap(syncal);
    ;

    public ScreenShotManager() {
        imageUtils = new ImageUtils();
    }

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

    public String captureScreenShot(int status, String className,
                                    String methodName)
            throws IOException, InterruptedException {

        String getDeviceModel = null;
        System.out.println("Current Running Thread Status"
                + AppiumDriverManager.getDriver().getSessionId());
        File scrFile = AppiumDriverManager.getDriver()
                .getScreenshotAs(OutputType.FILE);
        screenShotNameWithTimeStamp = currentDateAndTime();
        if (DeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
            getDeviceModel = screenShotNameWithTimeStamp;
            screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                    "android");
        } else if (DeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
            getDeviceModel = screenShotNameWithTimeStamp;
            screenShotAndFrame(status, scrFile, methodName, className, getDeviceModel,
                    "iOS");
        }
        return getDeviceModel;
    }

    public void captureScreenShot(String screenShotName)
            throws InterruptedException, IOException {
        String json = null;
        String className = new Exception().getStackTrace()[1].getClassName();
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        JSONObject jsonObj1 = new JSONObject();

        String s = captureScreenShot(1, className, methodName);
        new File(System.getProperty("user.dir")
                + "/target/" + screenShotName + getCapturedScreen());
//        jsonObj.put(className + "." + methodName, screenShotName + s);
//        screenShots.put(jsonObj);

        if (screenshotDetails.length() <= 0) {
            screenshotDetails = getScreenshotDetails();
        }
        logs.put(screenShotName, System.getProperty("user.dir") + "/target/" + s);
        //screenshotDetails.remove("screens");
        screenshotDetails.put("screens", logs);
        if (syncal.containsKey(className+methodName)) {
            syncal.put(className+methodName,
                    screenshotDetails.toString());
        }
        syncal.put(className+methodName,
                screenshotDetails.toString());
    }

    private JSONObject getScreenshotDetails() {
        String className = new Exception().getStackTrace()[2].getClassName();
        String methodName = new Exception().getStackTrace()[2].getMethodName();
//        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj1 = new JSONObject();
//        jsonObj.put(className + "." + methodName, screenShotName + s);
//        screenShots.put(jsonObj);
        jsonObj1.put("class_name", className);
        jsonObj1.put("method_name", methodName);
        try {
            jsonObj1.put("model", new DeviceManager().getDeviceModel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonObj1.put("id", DeviceManager.getDeviceUDID());
        jsonObj1.put("version", new DeviceManager().getDeviceVersion());
        jsonObj1.put("platform", DeviceManager.getMobilePlatform());

        return jsonObj1;
    }

    private String currentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        return now.truncatedTo(ChronoUnit.SECONDS).format(dtf);
    }


    private void screenShotAndFrame(int status,
                                    File scrFile, String methodName,
                                    String className, String model,
                                    String platform) {
        setFailedScreen(
                "screenshot/" + platform + "/" + DeviceManager.getDeviceUDID()
                        + "/" + className + "/"
                        + methodName + "/"
                        + screenShotNameWithTimeStamp + "_"
                        + methodName + "_failed" + ".jpeg");
        setCapturedScreen(
                "screenshot/" + platform + "/" + DeviceManager.getDeviceUDID()
                        + "/" + className
                        + "/" + methodName + "/"
                        + screenShotNameWithTimeStamp + "_"
                        + methodName + "_results.jpeg");

        setFramedCapturedScreen("screenshot/" + platform + "/" + DeviceManager.getDeviceUDID()
                + "/" + className
                + "/" + methodName + "/" + model + "_"
                + methodName + "_results_framed.jpeg");
        setFramedFailedScreen(
                "screenshot/" + platform + "/" + DeviceManager.getDeviceUDID()
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
                                                    + "/target/" + getFramedCapturedScreen());
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
