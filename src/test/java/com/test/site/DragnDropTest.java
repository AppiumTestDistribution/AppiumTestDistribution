package com.test.site;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class DragnDropTest extends  UserBaseTest {

    @Test
    public void dragNDrop() {
        login("login").click();
        driver.findElementByAccessibilityId("dragAndDrop").click();
        MobileElement dragMe = (MobileElement) new WebDriverWait(driver, 30).until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));
        System.out.println("Element Source X-Co-ordinates::" + dragMe.getSize().getWidth() / 2
                + "Element Source Y-Co-ordinates::" + dragMe.getSize().getHeight() / 2);
        MobileElement dropzone = driver.findElementByAccessibilityId("dropzone");

        System.out.println("Element Destination X-Co-ordinates::" + dropzone.getSize().getWidth() / 2
                + "Element Destination Y-Co-ordinates::" + dropzone.getSize().getHeight() / 2);


        new TouchAction(driver).press(ElementOption.element(dragMe,dragMe.getSize().getWidth() / 2,dragMe.getSize().getHeight() / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).moveTo(ElementOption.element(dropzone,dropzone.getSize().getWidth() / 2
                        ,dragMe.getSize().getHeight() / 2)).release().perform();

    }
}
