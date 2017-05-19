package com.appium.manager;

import io.appium.java_client.AppiumDriver;

/**
 * Created by saikrisv on 17/05/17.
 */
public class DriverManager {
    private static ThreadLocal<AppiumDriver> appiumDriverThreadLocal
            = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return appiumDriverThreadLocal.get();
    }

    static void setWebDriver(AppiumDriver driver) {
        appiumDriverThreadLocal.set(driver);
    }
}
