package com.appium.utils;

import com.annotation.values.RetryCount;
import com.appium.manager.ConfigFileManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Retry implements IRetryAnalyzer {
    private ConfigFileManager prop;
    private AtomicInteger COUNTER;

    public Retry() {
        prop = ConfigFileManager.getInstance();
        COUNTER = new AtomicInteger(0);
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        int maxRetryCount;
        RetryCount annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);
        if(annotation != null) {
            maxRetryCount = annotation.maxRetryCount();
        } else {
            try {
                maxRetryCount = Integer.parseInt(prop.getProperty("MAX_RETRY_COUNT"));
            } catch (Exception e) {
                maxRetryCount = 0;
            }
        }
        return !iTestResult.isSuccess() && COUNTER.incrementAndGet() < maxRetryCount;
    }
}
