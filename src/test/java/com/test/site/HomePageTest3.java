package com.test.site;

import com.annotation.values.Author;
import com.annotation.values.RetryCount;
import com.appium.manager.AppiumDriverManager;
import com.appium.utils.ScreenShotManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;


public class HomePageTest3  {

    public AppiumDriver<MobileElement> driver;


    public AppiumDriver<MobileElement> getDriver() {
        driver = AppiumDriverManager.getDriver();
        return driver;
    }

    @Test
    @Author(name = "AnsonLiao")
    @RetryCount(maxRetryCount = 2) public void testMethodHomePage3()
        throws InterruptedException, IOException {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        new ScreenShotManager().captureScreenShot("LoginPage--1");
        getDriver().findElement(By.id("com.android2.calculator3:id/digit4")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        new ScreenShotManager().captureScreenShot("LoginPage");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
    }

    @Test
    public void testMethodHomePage3Two()
            throws InterruptedException, IOException {

        System.out.println(
                "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                        .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit4")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        new ScreenShotManager().captureScreenShot("LoginPage1");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }
}
