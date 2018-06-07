package com.report.factory;

import com.appium.utils.AppiumDevice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TestStatusManager {
    public String getReportEventJson(AppiumDevice appiumDevice,
                                     String testStatus, String testCaseName,
                                     String testResult, String testException,
                                     String testClassName)
            throws JsonProcessingException {
        LocalTime localTime = ZonedDateTime.now().toLocalTime().truncatedTo(ChronoUnit.SECONDS);
        JSONObject test = new JSONObject();
        test.put("status", testStatus);
        test.put("testresult", testResult);
        test.put("testcasename", testCaseName);
        test.put("testException", testException);
        test.put("testClassName", testClassName);
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
}
