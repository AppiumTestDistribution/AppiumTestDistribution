package com.test.site;

import io.appium.java_client.AppiumBy;
import org.testng.annotations.Test;



@Test(groups = { "end-to-end-test" })
public class SliderTest extends UserBaseTest {
    @Test
    public void dragNDrop() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        waitForElement("dragMe").click();
    }
}
