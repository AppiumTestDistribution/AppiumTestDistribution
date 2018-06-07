package com.appium.utils;

import com.appium.manager.AppiumDeviceManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.report.factory.TestStatusManager;
import org.testng.ITestResult;

import java.util.HashMap;

public class Helpers {
    protected String getRemoteAppiumManagerPort(String host) throws Exception {
        String serverPort = CapabilityManager.getInstance()
                .getRemoteAppiumManangerPort(host);
        if (serverPort == null) {
            return "4567";
        } else {
            return serverPort;
        }
    }

    protected String getStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "Pass";
            case ITestResult.FAILURE:
                return "Fail";
            case ITestResult.SKIP:
                return "Skip";
            default:
                return "Started";
        }
    }


    public void sendResultsToAtdService(ITestResult testResult, String methodName,
                                        String testStatus, String url,
                                        HashMap<String, String> logs) {
        String reportEventJson;
        String className = testResult.getInstance().getClass().getSimpleName();
        try {
            String exception = null;
            if (getStatus(testResult).equalsIgnoreCase("fail")) {
                exception = testResult.getThrowable().getMessage();
            }
            reportEventJson = new TestStatusManager()
                    .getReportEventJson(AppiumDeviceManager.getAppiumDevice(),
                            testStatus,
                            methodName, getStatus(testResult),
                            exception, className, logs);
            new Api().post(url, reportEventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
