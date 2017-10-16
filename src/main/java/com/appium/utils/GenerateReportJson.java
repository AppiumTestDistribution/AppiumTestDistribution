package com.appium.utils;


import com.appium.manager.AppiumDriverManager;
import com.appium.manager.DeviceManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GenerateReportJson {

    AppiumDriverManager appiumDriverManager;
    public static List<String> syncal =
            Collections.synchronizedList(new ArrayList<String>());

    public static Map<String, String> userLogs =
            Collections.synchronizedMap(new HashMap<String, String>());

    public GenerateReportJson() throws Exception {
        appiumDriverManager = new AppiumDriverManager();
    }

    public JSONObject getStatus(JSONObject json, HashMap<String, String> deviceLogs,
                                String status, String error,
                                String methodname, String classname,
                                long startTime, long endTime, long totalTime) {
        JSONObject jsonObj = new JSONObject();
        JSONObject logs = new JSONObject();
        jsonObj.put("results", status);
        jsonObj.put("methodname", methodname);
        jsonObj.put("classname", classname);
        jsonObj.put("exceptiontrace", error);
        jsonObj.put("starttime", startTime);
        jsonObj.put("endtime", endTime);
        json.put("totaltime", totalTime);
        deviceLogs.forEach((key, value) -> {
            if ("videoLogs".equals(key)
                    || "screenShotFailure".equals(key)) {
                if (new File(value).exists()) {
                    logs.put(key, value);
                }
            }
            logs.put(key, value);
        });
        jsonObj.put("logs", logs);
        json.put("testDetails", jsonObj);
        return json;
    }

    public JSONObject getResultsSummary(Map<String, ISuiteResult> results) {
        Object firstSuite = results.keySet().toArray()[0];
        ISuiteResult result = results.get(firstSuite);
        ITestContext testContext = result.getTestContext();
        int passedTests = testContext.getPassedTests().getAllResults().size();
        int failedTests = testContext.getFailedTests().getAllResults().size();
        int skippedTests = testContext.getSkippedTests().getAllResults().size();
        String suiteName = testContext.getName();
        //String includedGroups = testContext.getIncludedGroups().toString();

        JSONObject jsonSummary = new JSONObject();
        jsonSummary.put("suite_name", suiteName);
        //jsonSummary.put("Included Groups", includedGroups);
        jsonSummary.put("passed", passedTests);
        jsonSummary.put("failed", failedTests);
        jsonSummary.put("skipped", skippedTests);
        jsonSummary.put("duration", Duration.of(testContext.getEndDate().getTime()
                - testContext.getStartDate().getTime(), ChronoUnit.MILLIS).getSeconds());

        return jsonSummary;
    }

    public void finalReportCreation(ISuite iSuite) {
        FileWriter file = null;
        try {
            file = new FileWriter(System.getProperty("user.dir")
                    + "/target/finalReport.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonReport = new JSONObject();
        JSONArray jsonTest = new JSONArray();
        JSONParser parser = new JSONParser();

        for (int i = 0; i < syncal.size(); i++) {
            try {
                jsonTest.put(parser.parse(syncal.get(i)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Map<String, ISuiteResult> results = iSuite.getResults();
        JSONObject summaryDetails = getSummaryDetails(results);
        summaryDetails.put("num_tests", iSuite.getAllInvokedMethods().size());

        jsonReport.put("summary", summaryDetails);
        jsonReport.put("tests", jsonTest);
        JSONParser jsonParser = new JSONParser();
        try {
            jsonReport.put("Screenshots", jsonParser.parse(ScreenShotManager.synmap.get("captureScreenShot")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            userLogs.put("Appium", "1.6.6.beta4");
            jsonReport.put("userMetaData", userLogs);
            file.write(new JSONObject().put("report", jsonReport).toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTestResultsAndQuitDriver(IInvokedMethod method, ITestResult testResult) {
        ScreenShotManager screenShotManager = new ScreenShotManager();
        HashMap<String, String> logs = new HashMap<>();
        JSONObject json = new JSONObject();
        json.put("id", DeviceManager.getDeviceUDID());
        json.put("version", new DeviceManager().getDeviceVersion());
        json.put("platform", DeviceManager.getMobilePlatform());
        try {
            json.put("model", new DeviceManager().getDeviceModel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (testResult.getStatus() == ITestResult.SUCCESS
                    || testResult.getStatus() == ITestResult.FAILURE) {
                if (testResult.getStatus() == ITestResult.FAILURE) {
                    screenShotManager.captureScreenShot(2,
                            testResult.getClass().getSimpleName(),
                            testResult.getMethod().getMethodName());
                    String screenShotFailure;
                    if (new File(System.getProperty("user.dir")
                            + "/target/" + screenShotManager.getFailedScreen()).exists()) {
                        screenShotFailure = screenShotManager.getFailedScreen();
                        logs.put("screenShotFailure", screenShotFailure);
                    } else if (new File(System.getProperty("user.dir")
                            + "/target/" + screenShotManager.getFramedFailedScreen()).exists()) {
                        screenShotFailure = screenShotManager.getFramedFailedScreen();
                        logs.put("screenShotFailure", screenShotFailure);
                    }
                }
                String testDescription;
                String description = method.getTestMethod().getMethod().getAnnotation(Test.class).description();
                if (description.isEmpty()) {
                    testDescription = method.getTestMethod().getMethodName();
                } else {
                    testDescription = description;
                }
                JSONObject status = getStatus(json, logs,
                        getExecutionStatus(testResult),
                        String.valueOf(testResult.getThrowable()),
                        testDescription,
                        testResult.getInstance().getClass().getSimpleName(),
                        Duration.of(testResult.getStartMillis(), ChronoUnit.MILLIS).getSeconds(),
                        Duration.of(testResult.getEndMillis(), ChronoUnit.MILLIS).getSeconds(),
                        Duration.of(testResult.getEndMillis() - testResult.getStartMillis(),
                                ChronoUnit.MILLIS).getSeconds());

                sync(status.toString());
            }
            if (method.isTestMethod()) {
                appiumDriverManager.stopAppiumDriver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getExecutionStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "Pass";
            case ITestResult.FAILURE:
                return "Fail";
            case ITestResult.SKIP:
                return "Skip";
            default:
                throw new RuntimeException("Invalid status");
        }
    }

    private JSONObject getSummaryDetails(Map<String, ISuiteResult> results) {
        return getResultsSummary(results);
    }

    public void sync(String message) {
        //Adding elements to synchronized ArrayList
        syncal.add(message);
    }

}