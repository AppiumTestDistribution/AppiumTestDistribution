package com.test.site;



import com.annotation.values.Author;
import org.openqa.selenium.By;
import org.testng.annotations.Test;


public class HomePageTest4 extends UserBaseTest {


    @Test
    @Author(name = "Krishna")public void testMethodFour4() throws Exception {

        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        Thread.sleep(3000);
        captureScreenShot("TestMethod4");
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit5")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit99")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
    }

}
