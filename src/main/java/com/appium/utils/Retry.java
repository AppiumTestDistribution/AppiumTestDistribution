package com.appium.utils;

import com.annotation.values.RetryCount;
import com.appium.manager.ConfigFileManager;
import com.appium.manager.ReportManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.IOException;
import java.lang.reflect.Method;

public class Retry implements IRetryAnalyzer {
    private int retryCount = 0;
    private int maxRetryCount;
    private ConfigFileManager prop;

    public Retry() {
        try {
            prop = ConfigFileManager.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
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
        if (iTestResult.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test Failed");
            if (retryCount == maxRetryCount) {
                System.out.println("Log report");
                retryCount = 0;
                return false;
            }
            if (retryCount < maxRetryCount) {
                retryCount++;
                System.out.println(
                        "Retrying Failed Test Cases " + retryCount + "out of " + maxRetryCount);
                return true;

            }
        }
        return false;
    }
}
