package com.test.site;

import com.annotation.values.RetryCount;
import com.annotation.values.SkipIf;
import com.appium.manager.AppiumParallelTestListener;
import org.openqa.selenium.By;
import org.testng.annotations.Test;



public class HomePageTest1 extends UserBaseTest {


    @Test public void testMethodOne1() throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit2")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        //captureScreenShot("TestMethod1");
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }

    @Test public void testMethodOne6()
        throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        //captureScreenShot("TestMethod2");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();

        //getDriver().close();
    }

    @Test
    public void testMethodOne7() throws Exception {
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit33")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        //captureScreenShot("TestMethod7");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }


    @Test public void testMethodOne8()
        throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }

    @Test public void testMethodOne9()
        throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
        //captureScreenShot("TestMethod11");
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }

    @Test
    public void testMethodOne10() throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }

    @Test
    public void testMethodOne11() throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        //captureScreenShot("TestMethod1");
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
        //getDriver().close();
    }
}
