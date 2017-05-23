package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.annotation.values.RetryCount;
import com.appium.manager.AppiumParallelTestListener;
import com.appium.utils.ScreenShotManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


@Description("This Test validates swipe scenarios")
public class HomePageTest2
        extends UserBaseTest {

    MyClass myClass;

    @Test(retryAnalyzer = AppiumParallelTestListener.class, groups = "smoke",
    description = "Testing Skips")
    @Author(name = "AnsonLiao")
    @RetryCount(maxRetryCount = 2)
    public void testMethodOne_2() throws Exception {
        myClass = new MyClass();
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit2")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
        new ScreenShotManager().captureScreenShot("Second Test Screen");
        Assert.assertEquals(myClass.sum(2, 3), 6,"Runing in Thread" + Thread.currentThread().getId());
    }
}
