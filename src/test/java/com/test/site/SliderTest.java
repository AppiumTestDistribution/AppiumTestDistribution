package com.test.site;

import com.annotation.values.Author;
import com.annotation.values.RetryCount;

import com.annotation.values.SkipIf;
import com.appium.manager.AppiumDeviceManager;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class SliderTest extends UserBaseTest {



    @BeforeMethod
    public void beforeMethod() {
        System.out.println("In Before Slider Method"
            + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
    }


    @AfterMethod
    public void afterMethod() {
        System.out.println("In After Slider Method"
            + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid());
    }


    @Test
    public void dragNDrop() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(driver, 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }

    @Test
    public void dragNDropNew() {
        login("login").click();
        waitForElement("dragAndDrop").click();
        new WebDriverWait(driver, 30)
            .until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));

    }
}
