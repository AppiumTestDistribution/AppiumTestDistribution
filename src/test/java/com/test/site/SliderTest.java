package com.test.site;

import com.annotation.values.MultiATDDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SliderTest extends UserBaseTest {


    @Test
    public void dragNDropNew() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(getDriver(), 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }
}
