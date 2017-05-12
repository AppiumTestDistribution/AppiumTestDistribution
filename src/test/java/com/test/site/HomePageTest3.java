package com.test.site;

import com.annotation.values.Author;
import com.annotation.values.RetryCount;
import com.appium.utils.Retry;
import com.appium.utils.ScreenShotManager;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.io.IOException;


public class HomePageTest3 extends UserBaseTest {


    @Test(retryAnalyzer = Retry.class)
    @Author(name = "AnsonLiao")
    @RetryCount(maxRetryCount = 2) public void testMethodOne_3()
        throws InterruptedException, IOException {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        Thread.sleep(3000);
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        new ScreenShotManager().captureScreenShot(driver,"LoginPage--1");
        getDriver().findElement(By.id("com.android2.calculator3:id/digit4")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        new ScreenShotManager().captureScreenShot(driver,"LoginPage");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
    }

    @Test
    public void testMethodOne_4()
            throws InterruptedException, IOException {

        System.out.println(
                "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                        .getStackTrace()[1].getClassName());
        Thread.sleep(3000);
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit4")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        new ScreenShotManager().captureScreenShot(driver,"LoginPage1");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }
}
