package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ScreenShotManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


@Description("This Test validates swipe scenarios")
public class HomePageTest2 {

    public MyClass myClass;

    public AppiumDriver<MobileElement> driver;


    public AppiumDriver<MobileElement> getDriver() {
        driver = AppiumDriverManager.getDriver();
        return driver;
    }

    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne2() throws Exception {
        myClass = new MyClass();
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit2")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        new ScreenShotManager().captureScreenShot("Second Test Screen");
        Assert.assertEquals(myClass.sum(2, 3), 6,"Runing in Thread"
                + Thread.currentThread().getId());
    }
}
