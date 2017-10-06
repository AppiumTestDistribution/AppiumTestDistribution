package com.appium.utils;

import com.annotation.values.RetryCount;
import com.appium.manager.ConfigFileManager;
import com.appium.manager.ReportManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Retry implements IRetryAnalyzer {
    private int maxRetryCount;
    private ConfigFileManager prop;
    private Map<String, Integer> retryCounts = new HashMap<String, Integer>();

    public Retry() {
        try {
            prop = ConfigFileManager.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        int counter = 0;
        int retryCountForTest = 0;
        String methodName = iTestResult.getMethod().getMethodName();
        Object[] obj = iTestResult.getParameters();
        Method[] methods = iTestResult.getInstance().getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals(iTestResult.getMethod().getMethodName())) {
                if (m.isAnnotationPresent(RetryCount.class)) {
                    RetryCount ta = m.getAnnotation(RetryCount.class);
                    maxRetryCount = ta.maxRetryCount();
                } else {
                    try {
                        maxRetryCount = Integer.parseInt(prop.getProperty("MAX_RETRY_COUNT"));
                    } catch (Exception e) {
                        maxRetryCount = 0;
                    }
                }
            }
        }

        while (obj.length != counter) {
            methodName = methodName + "_" + obj[counter];
            counter ++;
        }

        if (retryCounts.containsKey(methodName)) {
            retryCountForTest = retryCounts.get(methodName);
            retryCountForTest++;
        }

        if (!iTestResult.isSuccess() && retryCountForTest < maxRetryCount) {
            System.out.println(methodName
                    + " execution failed in count: " + retryCountForTest + "\n");
            retryCounts.put(methodName, retryCountForTest);
            return true;
        } else if (!iTestResult.isSuccess() && retryCountForTest == maxRetryCount) {
            System.out.println(methodName
                    + " execution failed in count: " + maxRetryCount + "\n");
            return false;
        }
        return false;
    }
}
