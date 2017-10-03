package com.appium.manager;

import com.annotation.values.Description;
import com.annotation.values.SkipIf;
import com.report.factory.ExtentManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public final class AppiumParallelTestListener
        implements IClassListener, IInvokedMethodListener, ISuiteListener {

    private ReportManager reportManager;
    private DeviceAllocationManager deviceAllocationManager;
    public AppiumServerManager appiumServerManager;
    public String testDescription = "";
    private AppiumDriverManager appiumDriverManager;

    List<String> syncal =
            Collections.synchronizedList(new ArrayList<String>());

    public static Map<String, String> userLogs =
            Collections.synchronizedMap(new HashMap<String, String>());

    public AppiumParallelTestListener() throws Exception {
        try {
            reportManager = new ReportManager();
            appiumServerManager = new AppiumServerManager();
            deviceAllocationManager = DeviceAllocationManager.getInstance();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            appiumDriverManager.startAppiumDriverInstance();
            reportManager.startLogResults(method.getTestMethod().getMethodName(),
                    testResult.getTestClass().getRealClass().getSimpleName());
            SkipIf skip =
                    method.getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod().getAnnotation(SkipIf.class);
            if (skip != null) {
                String info = skip.platform();
                if (AppiumDriverManager.getDriver().toString()
                        .split("\\(")[0].trim().toString().contains(info)) {
                    System.out.println("skipping test");
                    throw new SkipException("Skipped because property was set to :::" + info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        JSONObject json = new JSONObject();
        json.put("id", DeviceManager.getDeviceUDID());
        json.put("version", new DeviceManager().getDeviceVersion());
        json.put("platform", DeviceManager.getMobilePlatform());
        //json.put("resolution", DeviceManager.getMobilePlatform());
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
                reportManager.setAuthorName(method);
                HashMap<String, String> getLogDetails = reportManager.endLogTestResults(testResult);
                JSONObject status = getStatus(json, getExecutionStatus(testResult),
                        String.valueOf(testResult.getThrowable()),
                        getLogDetails, method.getTestMethod().getMethodName(),
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

    public JSONObject getStatus(JSONObject json, String status, String error,
                                HashMap<String, String> deviceLogsPath,
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
        deviceLogsPath.forEach((key, value) -> {
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

    /*
    Document to make codacy happy
    */
    @Override
    public void onStart(ISuite iSuite) {
        try {
            appiumServerManager.startAppiumServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sync(String message) {
        //Adding elements to synchronized ArrayList
        syncal.add(message);
    }


    @Override
    public void onFinish(ISuite iSuite) {
        try {
            appiumServerManager.stopAppiumServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            userLogs.put("Appium", "1.6.6.beta4");
            //jsonObject.put("userMetaData",userLogs);
            file.write(new JSONObject().put("report", jsonReport).toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getSummaryDetails(Map<String, ISuiteResult> results) {

        Object firstSuite = results.keySet().toArray()[0];
        ISuiteResult result = results.get(firstSuite);
        ITestContext testContext = result.getTestContext();
        int passedTests = testContext.getPassedTests().getAllResults().size();
        int failedTests = testContext.getFailedTests().getAllResults().size();
        int skippedTests = testContext.getSkippedTests().getAllResults().size();
        String suiteName = testContext.getName();
        //String includedGroups = testContext.getIncludedGroups().toString();

        Date startDate = testContext.getStartDate();
        Date endDate = testContext.getEndDate();
        long duration = endDate.getTime() - startDate.getTime();
        JSONObject jsonSummary = new JSONObject();
        jsonSummary.put("suite_name", suiteName);
        //jsonSummary.put("Included Groups", includedGroups);
        jsonSummary.put("passed", passedTests);
        jsonSummary.put("failed", failedTests);
        jsonSummary.put("skipped", skippedTests);
        jsonSummary.put("duration", duration / 1000 + " seconds");

        return jsonSummary;
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

    @Override
    public void onBeforeClass(ITestClass testClass) {
        try {
            String device = testClass.getXmlClass().getAllParameters().get("device").toString();
            String className = testClass.getRealClass().getSimpleName();
            deviceAllocationManager.allocateDevice(device,
                    deviceAllocationManager.getNextAvailableDeviceId());
            if (getClass().getAnnotation(Description.class) != null) {
                testDescription = getClass().getAnnotation(Description.class).value();
            }
            reportManager.createParentNodeExtent(className,
                    testDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAfterClass(ITestClass iTestClass) {
        ExtentManager.getExtent().flush();
        deviceAllocationManager.freeDevice();
    }
}
