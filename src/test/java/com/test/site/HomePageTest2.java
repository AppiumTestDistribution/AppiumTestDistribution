package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ScreenShotManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


@Description("This Test validates swipe scenarios")
public class HomePageTest2 {


    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne1() throws Exception {
        ScreenShotManager screenShotManager = new ScreenShotManager();
        Thread.sleep(5000);
        AppiumDriverManager.getDriver().findElement(
                By.id("com.android2.calculator3:id/cling_dismiss")).click();
        screenShotManager.captureScreenShot("first" );
        screenShotManager.captureScreenShot("second" );
        AppiumDriverManager.getDriver()
                .findElementByAccessibilityId("doubleTap").click();
        Thread.sleep(2000);
        AppiumDriverManager.getDriver().quit();
    }

    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne2() throws Exception {
        ScreenShotManager screenShotManager = new ScreenShotManager();
        Thread.sleep(5000);
        AppiumDriverManager.getDriver().findElement(
                By.id("com.android2.calculator3:id/cling_dismiss")).click();
        screenShotManager.captureScreenShot("Third" );
        screenShotManager.captureScreenShot("Fourth" );
        AppiumDriverManager.getDriver()
                .findElementByAccessibilityId("doubleTap").click();
        Thread.sleep(2000);
        AppiumDriverManager.getDriver().quit();
    }

}
