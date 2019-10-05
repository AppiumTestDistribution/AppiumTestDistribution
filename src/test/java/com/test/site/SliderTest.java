package com.test.site;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SliderTest extends UserBaseTest {


    @Test
    public void dragNDrop() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(driver, 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }
}
