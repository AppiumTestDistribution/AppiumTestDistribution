package com.appium.utils;

import com.appium.manager.ConfigFileManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Retry implements IRetryAnalyzer {
    public int maxRetryCount;
    private ConfigFileManager prop;
    private AtomicInteger COUNTER;

    public Retry() {
        try {
            prop = ConfigFileManager.getInstance();
            COUNTER = new AtomicInteger(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        maxRetryCount = Integer.parseInt(prop.getProperty("MAX_RETRY_COUNT"));
        return !result.isSuccess() && COUNTER.incrementAndGet() < maxRetryCount;
    }
}
