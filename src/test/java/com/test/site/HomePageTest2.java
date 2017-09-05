package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.manager.AppiumDriverManager;
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
