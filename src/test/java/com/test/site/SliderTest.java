package com.test.site;

import com.annotation.values.MultiATDDriver;
import io.appium.java_client.MobileBy;
import org.junit.After;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SliderTest extends UserBaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        System.out.println("Something");
    }

    @AfterMethod(alwaysRun = true)
    public void setDown() {
        System.out.println("Something");
    }

    @Test
    public void dragNDropNew() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(getDriver(), 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }

    @Test
    public void dragNDropNew1() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(getDriver(), 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }
}
