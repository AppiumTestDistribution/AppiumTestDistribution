package com.appium.utils;

import com.appium.manager.AppiumDeviceManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.report.factory.TestStatusManager;
import org.testng.ITestResult;

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
                throw new RuntimeException("Invalid status");
        }
    }


    public void sendResultsToAtdService(ITestResult testResult, String methodName,
                                        String testStatus, String url) {
        String reportEventJson;
        try {
            reportEventJson = new TestStatusManager()
                    .getReportEventJson(AppiumDeviceManager.getAppiumDevice(),
                            testStatus,
                            methodName, getStatus(testResult));
            new Api().post(url, reportEventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
