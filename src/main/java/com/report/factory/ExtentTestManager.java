package com.report.factory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExtentTestManager { // new
    public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

    public static ExtentReports extent = ExtentManager.getInstance();
    public static Properties prop = new Properties();
    public static InputStream input = null;

    public synchronized static ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public synchronized static void endTest() {
        extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "", "");
    }

    @SuppressWarnings("unchecked")
    public synchronized static ExtentTest startTest(String testName, String desc, String deviceId) {
        ExtentTest test = extent.startTest(testName, desc).assignCategory(deviceId);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        try {
            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map sysInfo = new HashMap();
        sysInfo.put("Selenium Java Version", "2.53.0");
        sysInfo.put("Environment", "Prod");
        sysInfo.put("AppiumVersion", "4.0.0");
        sysInfo.put("RunnerMode", prop.getProperty("RUNNER").toUpperCase());
        extent.addSystemInfo(sysInfo);
        return test;
    }

    public synchronized static void logOutPut(String imgSrc, String headerName) {
        imgSrc = "<div class='col l4 m6 s12'><div class='card-panel'><h4 class='md-display-4'>"
            + headerName + "</h4><img src=" + imgSrc
            + " style=\"width:100%;height:100%;\"></div></div>";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        extent.loadConfig(classLoader.getResource("extent.xml"));
        //extent.setTestRunnerOutput(imgSrc);
        //extent.setTestRunnerOutput(s);
    }

    public synchronized static void logVideo(String videoSrc, String headerName) {
        videoSrc = "<div class='col l4 m6 s12'><div class='card-panel'><h2 class='md-display-4'>"
            + headerName + "</h2><video width=\"320\" height=\"240\" controls>"
            + "<source src=" + videoSrc
            + " style=\"width:100%;height:100%;\" type=\"video/mp4\">"
            + "</video></div></div>";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        extent.loadConfig(classLoader.getResource("extent.xml"));
        extent.setTestRunnerOutput(videoSrc);
        //extent.setTestRunnerOutput(s);
    }


    public synchronized static void loadConfig() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        extent.loadConfig(classLoader.getResource("extent.xml"));
    }

    public synchronized static void logger(String message) {
        Reporter.log(message + "<br>", true);
        getTest().log(LogStatus.INFO, message + "<br>");
    }
}
