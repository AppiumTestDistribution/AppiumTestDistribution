package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ScreenShotManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSTouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@Description("This Test validates swipe scenarios")
public class HomePageTest2 {


    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne1() throws Exception {
        Thread.sleep(5000);
        AppiumDriverManager.getDriver().findElementByAccessibilityId("login").click();
        AppiumDriverManager.getDriver().findElementByAccessibilityId("doubleTap").click();
        Thread.sleep(2000);
        AppiumDriverManager.getDriver().quit();
    }

    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne2() throws Exception {
        Thread.sleep(5000);
        AppiumDriverManager.getDriver().findElementByAccessibilityId("login").click();
        AppiumDriverManager.getDriver().findElementByAccessibilityId("doubleTap").click();
        Thread.sleep(2000);
        AppiumDriverManager.getDriver().quit();
    }


}
