package com.test.site;



import com.annotation.values.Author;
import com.appium.utils.ScreenShotManager;
import org.openqa.selenium.By;
import org.testng.annotations.Test;


public class HomePageTest4 extends UserBaseTest {


    @Test
    @Author(name = "Krishna")public void testMethodFour_4() throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        Thread.sleep(3000);
        new ScreenShotManager().captureScreenShot("TestSampler4");
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit5")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
    }

}
