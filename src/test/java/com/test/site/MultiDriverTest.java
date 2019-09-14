package com.test.site;

import com.annotation.values.MultiATDDriver;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.ScreenShotManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class MultiDriverTest {

    @Test
    @MultiATDDriver(devices =  2)
    public void multiDriverTest() {
        List<AppiumDriver<MobileElement>> drivers = AppiumDriverManager.getDrivers();
        SessionId sessionId = drivers.get(0).getSessionId();
        SessionId sessionId1 = drivers.get(1).getSessionId();
        new ScreenShotManager().captureScreenShot("LoginPage");
        Assert.assertNotEquals(sessionId, sessionId1);
    }
}
