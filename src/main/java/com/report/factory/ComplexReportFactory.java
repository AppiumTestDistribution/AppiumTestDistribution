package com.report.factory;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ComplexReportFactory {

    public static ExtentReports reporter;
    public static Map<Long, String> threadToExtentTestMap = new HashMap<>();
    public static Map<String, ExtentTest> nameToTestMap = new HashMap<>();
    private static String filenameOfReport = System.getProperty("user.dir") + "/index.html";
    public static Properties prop = new Properties();
    public static InputStream input = null;

    @SuppressWarnings("unchecked") private synchronized static ExtentReports getExtentReport() {
        if (reporter == null) {
            // you can get the file name and other parameters here from a
            // config file or global variables
            reporter = new ExtentReports(filenameOfReport, true, DisplayOrder.NEWEST_FIRST);
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
            sysInfo.put("Selenium Java Version", "2.48.2");
            sysInfo.put("Environment", "Prod");
            sysInfo.put("AppiumVersion", "3.3.0");
            sysInfo.put("RunnerMode", prop.getProperty("RUNNER").toUpperCase());

            reporter.addSystemInfo(sysInfo);
        }
        return reporter;
    }

    public synchronized static ExtentTest getTest(String testName, String testDescription) {

        // if this test has already been created return
        if (!nameToTestMap.containsKey(testName)) {
            Long threadID = Thread.currentThread().getId();
            ExtentTest test = getExtentReport().startTest(testName, testDescription);
            nameToTestMap.put(testName, test);
            threadToExtentTestMap.put(threadID, testName);
        }
        return nameToTestMap.get(testName);
    }

    public synchronized static ExtentTest getTest(String testName) {
        return getTest(testName, "");
    }

    /*
     * At any given instance there will be only one test running in a thread. We
     * are already maintaining a thread to extentest map. This method should be
     * used after some part of the code has already called getTest with proper
     * testcase name and/or description.
     *
     * Reason: This method is not for creating test but getting an existing test
     * reporter. sometime you are in a utility function and want to log
     * something from there. Utility function or similar code sections are not
     * bound to a test hence we cannot get a reporter via test name, unless we
     * pass test name in all method calls. Which will be an overhead and a rigid
     * design. However, there is one common association which is the thread ID.
     * When testng executes a test it puts it launches a new thread, assuming
     * test level threading, all tests will have a unique thread id hence call
     * to this function will return the right extent test to use
     */
    public synchronized static ExtentTest getTest() {
        Long threadID = Thread.currentThread().getId();

        if (threadToExtentTestMap.containsKey(threadID)) {
            String testName = threadToExtentTestMap.get(threadID);
            return nameToTestMap.get(testName);
        }
        //system log, this shouldn't happen but in this crazy times if it did happen log it.
        return null;
    }

    public synchronized static void closeTest(String testName) {

        if (!testName.isEmpty()) {
            ExtentTest test = getTest(testName);
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest(ExtentTest test) {
        if (test != null) {
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest() {
        ExtentTest test = getTest();
        closeTest(test);
    }

    public synchronized static void closeReport() {
        if (reporter != null) {
            reporter.flush();
            reporter.close();
        }
    }

}
