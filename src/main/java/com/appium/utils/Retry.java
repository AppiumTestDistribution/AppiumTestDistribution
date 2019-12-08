package com.appium.utils;

import static com.appium.utils.ConfigFileManager.MAX_RETRY_COUNT;

import com.annotation.values.RetryCount;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.atomic.AtomicInteger;

public class Retry implements IRetryAnalyzer {
    private AtomicInteger COUNTER;

    public Retry() {
        COUNTER = new AtomicInteger(0);
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        int maxRetryCount;
        RetryCount annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);
        if (annotation != null) {
            maxRetryCount = annotation.maxRetryCount();
        } else {
            try {
                maxRetryCount = MAX_RETRY_COUNT.getInt();
            } catch (Exception e) {
                maxRetryCount = 0;
            }
        }
        return !iTestResult.isSuccess() && COUNTER.incrementAndGet() < maxRetryCount;
    }
}
