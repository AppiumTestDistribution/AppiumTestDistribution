package com.report.factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.Reporter;

import java.util.Properties;

public class ExtentTestManager { // new
    public static Properties prop = new Properties();

    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    public static ExtentReports extent = ExtentManager.getExtent();
    private static ExtentTest test;

    public synchronized static ExtentTest getTest() {
        return extentTest.get();
    }

    public synchronized static ExtentTest createTest(String name, String description,
        String deviceId) {
        test = extent.createTest(name, description).assignCategory(deviceId);
        extentTest.set(test);
        return getTest();
    }

    public synchronized static ExtentTest createTest(String name, String description) {
        return createTest(name, description, String.valueOf(Thread.currentThread().getId()));
    }

    public synchronized static ExtentTest createTest(String name) {
        return createTest(name, "Sample Test");
    }

    public synchronized static void logOutPut(String imgSrc, String headerName) {
        String imgPath = "<div class='col l4 m6 s12'>"
            + "<div class='card-panel'><h4 class='md-display-4'>"
            + headerName + "</h4><img src=" + imgSrc
            + " style=\"width:100%;height:100%;\"></div></div>";
        extent.setTestRunnerOutput(imgPath);

    }

    public synchronized static void logVideo(String videoSrc, String headerName) {
        String videoPath = "<div class='col l4 m6 s12'>"
            + "<div class='card-panel'><h4 class='md-display-4'>"
            + headerName + "</h4><video width=\"320\" height=\"240\" controls>"
            + "<source src="
            + videoSrc + " style=\"width:100%;height:100%;\" type=\"video/mp4\">"
            + "</video></div></div>";
        extent.setTestRunnerOutput(videoPath);
    }


    public synchronized static void logger(String message) {
        Reporter.log(message + "<br>", true);
        getTest().log(Status.INFO, message + "<br>");
    }
}
