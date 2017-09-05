package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ScreenShotManager;
import org.openqa.selenium.By;
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

//    @Test(groups = "smoke", description = "Testing Skips")
//    @Author(name = "AnsonLiao")
//    public void testMethodOne3() throws Exception {
//        MyClass myClass = new MyClass();
//        AppiumDriverManager.getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
//        AppiumDriverManager.getDriver().findElement(By.id("com.android2.calculator3:id/digit2")).click();
//        AppiumDriverManager.getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
//        new ScreenShotManager().captureScreenShot("Second Test Screen");
//        Assert.assertEquals(myClass.sum(2, 4), 7,"Runing in Thread"
//                + Thread.currentThread().getId());
//    }
//
//    @DataProvider
//    public static Object[][] getTOCs() {
//        return new Object[][]{
//                {"a"},
//                {"b"}
//        };
//    }

}
