package com.appium.utils;

import com.annotation.values.RetryCount;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Retry implements IRetryAnalyzer {
    private int retryCount = 0;
    private int maxRetryCount;
    public static Properties prop = new Properties();

    public boolean retry(ITestResult result) {
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Method[] methods = result.getInstance().getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals(result.getMethod().getMethodName())) {
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
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Test Failed");
            if (retryCount == maxRetryCount) {
                System.out.println("Log report");
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
