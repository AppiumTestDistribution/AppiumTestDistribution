package com.test.site;



import com.annotation.values.Author;
import com.annotation.values.Description;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.By;
import org.testng.annotations.Test;


@Description("This Test validates swipe scenarios")
public class HomePageTest2
    extends UserBaseTest {

    @Test
    @Author(name = "AnsonLiao,Sai") public void testMethodOne_2() throws Exception {
        System.out.println(
            "ThreadName: " + Thread.currentThread().getName() + Thread.currentThread()
                .getStackTrace()[1].getClassName());
        Thread.sleep(3000);
        getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
        getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
        MobileElement el = getDriver().findElement(By.id("com.android2.calculator3:id/equal"));
        el.swipe(SwipeElementDirection.LEFT, 10000);
    }
}
