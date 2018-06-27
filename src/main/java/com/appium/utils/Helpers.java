package com.appium.utils;

import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.ConfigFileManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.report.factory.TestStatusManager;
import org.testng.ITestResult;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
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
                return "Unknown";
        }
    }


    public void sendResultsToAtdService(ITestResult testResult,
                                        String testStatus, String url,
                                        HashMap<String, String> logs) {
        String reportEventJson;
        try {
            reportEventJson = new TestStatusManager()
                    .getReportEventJson(AppiumDeviceManager.getAppiumDevice(),
                            testStatus,
                            testResult, logs);
            new Api().post(url, reportEventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String getHostMachineIpAddress() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));
        return socket.getLocalAddress().toString()
                .replace("/", "");
    }

    protected static String getAppiumServerVersion() throws IOException {
        String appiumVersion = null;
        try {
            appiumVersion = new CommandPrompt().runCommand("appium -v")
                    .replaceAll("\n", "");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return appiumVersion;
    }
}
