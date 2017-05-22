package com.test.site;

import com.appium.manager.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class UserBaseTest {
    public AppiumDriver<MobileElement> driver;


    public AppiumDriver<MobileElement> getDriver() {
        driver = DriverManager.getDriver();
        return driver;
    }
}
