package com.report.factory;

import com.appium.manager.ConfigFileManager;
import com.appium.utils.AppiumDevice;
import com.appium.utils.DevicesByHost;
import com.appium.utils.Helpers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class TestStatusManager extends Helpers {
    public String getReportEventJson(AppiumDevice appiumDevice,
                                     String testStatus, String testCaseName,
                                     String testResult, String testException,
                                     String testClassName, HashMap<String, String> logs)
            throws JsonProcessingException {
        LocalTime localTime = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS);
        JSONObject test = new JSONObject();
        test.put("status", testStatus);
        test.put("testresult", testResult);
        test.put("testMethodName", testCaseName);
        test.put("testException", testException);
        test.put("testClassName", testClassName);
        if (logs.size() > 0) {
            test.put("adbLogs", logs.get("adbLogs"));
            test.put("screenShotFailure", logs.get("screenShotFailure"));
        }
        if (testStatus.equalsIgnoreCase("Completed")) {
            test.put("endTime", localTime);
        } else {
            test.put("startTime", localTime);
        }
        String deviceDetails = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(appiumDevice);
        JSONArray device = new JSONArray();
        device.put(deviceDetails);
        test.put("deviceinfo", JSON.parse(deviceDetails.replace("/\r?\n|\r/g", "")));
        return test.toString();
    }

    public String envInfo(int deviceSize) throws IOException {
        JSONObject env = new JSONObject();
        env.put("SeleniumVersion", "3.12.0");
        env.put("AppiumServer", getAppiumServerVersion());
        env.put("Runner", ConfigFileManager.getInstance().getProperty("RUNNER"));
        env.put("AppiumClient", "6.0.0");
        env.put("Total Devices", deviceSize);
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
