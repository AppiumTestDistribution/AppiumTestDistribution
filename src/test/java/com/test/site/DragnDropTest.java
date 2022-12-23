package com.test.site;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;


@Test(groups = { "end-to-end-test" })
public class DragnDropTest extends UserBaseTest {
    @Test
    public void dragNDrop() {
        waitForElement("login").click();
        waitForElement("dragAndDrop").click();
    }
}
