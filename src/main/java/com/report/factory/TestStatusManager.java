package com.report.factory;

import static com.appium.utils.ConfigFileManager.RUNNER;

import com.appium.device.DevicesByHost;
import com.appium.manager.AppiumDevice;
import com.appium.utils.FileFilterParser;
import com.appium.utils.Helpers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TestStatusManager extends Helpers {
    public String getReportEventJson(AppiumDevice appiumDevice,
                                     String testStatus, ITestResult iTestResult,
                                     HashMap<String, String> logs)
        throws JsonProcessingException {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
        JSONObject test = new JSONObject();
        JSONArray dataProvider = new JSONArray();
        test.put("status", testStatus);
        test.put("testresult", getStatus(iTestResult));
        test.put("testMethodName", iTestResult.getMethod().getMethodName());
        for (Object parameter : iTestResult.getParameters()) {
            dataProvider.put(parameter);
        }


        test.put("dataProviderValue", dataProvider);
        if (getStatus(iTestResult).equalsIgnoreCase("fail")) {
            test.put("testException", iTestResult.getThrowable().getMessage());
        }
        test.put("testClassName", iTestResult.getInstance()
            .getClass().getSimpleName());
        if (logs.size() > 0) {
            test.put("adbLogs", logs.get("adbLogs"));
            test.put("screenShotFailure", logs.get("screenShotFailure"));
        }
        if (testStatus.equalsIgnoreCase("Completed")) {
            String endTime = dateFormat.format(new Date(iTestResult.getEndMillis()));
            String startTime = dateFormat.format(new Date(iTestResult.getStartMillis()));
            test.put("endTime", endTime);
            test.put("startTime", startTime);
            test.put("screenPath", new FileFilterParser()
                .getScreenShotPaths(appiumDevice.getDevice().getUdid(), iTestResult));
        }
        String deviceDetails = new ObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(appiumDevice);
        JSONArray device = new JSONArray();
        device.put(deviceDetails);
        test.put("deviceinfo", deviceDetails.replace("/\r?\n|\r/g", ""));
        return test.toString();
    }

    public String envInfo(int deviceSize) throws IOException {
        JSONObject env = new JSONObject();
        env.put("SeleniumVersion", "3.14.X");
        env.put("AppiumServer", getAppiumServerVersion());
        env.put("Runner", RUNNER.get());
        env.put("AppiumClient", "7.0.0");
        env.put("TotalDevices", deviceSize);
        return env.toString();
    }

    public String appiumLogs(DevicesByHost devicesByHost) {
        JSONObject logs = new JSONObject();
        devicesByHost.getAllHosts().forEach(host -> {
            if (host.equals("127.0.0.1")) {
                try {
                    logs.put(host, "http://" + getHostMachineIpAddress() + ":"
                        + getRemoteAppiumManagerPort(host)
                        + "/appiumlogs/appium_logs.txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    logs.put(host, "http://" + getHostMachineIpAddress() + ":"
                        + getRemoteAppiumManagerPort(host)
                        + "/appium/logs");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        return logs.toString();
    }
}
