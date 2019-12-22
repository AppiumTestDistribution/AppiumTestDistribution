package com.test.site;

import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DragnDropTest extends UserBaseTest {
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before method in DragnDropTest.");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After method in DragnDropTest.");
    }

    @Test
    public void dragNDrop() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(driver, 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }
}
