package com.test.site;

import com.appium.manager.AppiumDeviceManager;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DragnDropTest extends UserBaseTest {


    @BeforeMethod
    public void beforeMethod() {

    }


    @AfterMethod
    public void afterMethod() {

    }


    @Test
    public void dragNDrop() {
        login("login").click();
        waitForElement("dragAndDrop").click();

    }

    @Test
    public void dragNDropNew() {
        login("login").click();
        waitForElement("dragAndDrop").click();

    }
}
