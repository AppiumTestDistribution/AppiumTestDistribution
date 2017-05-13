package com.test.site;



import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.utils.ScreenShotManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


@Description("This Test validates swipe scenarios")
public class HomePageTest2
    extends UserBaseTest {

    @Test(groups = {"smoke"},description = "Testing")
    public void testMethodOne_2() throws Exception {
        MyClass c = new MyClass();
        new ScreenShotManager().captureScreenShot(driver,"Second Test Screen");
        Assert.assertEquals(c.sum(2, 3), 6);
    }
}
