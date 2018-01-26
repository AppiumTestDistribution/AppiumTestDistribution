package com.test.site;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import java.time.Duration;

public class SliderTest extends  UserBaseTest {

    @Test
    public void sliderTest() {
        login("login").click();
        waitForElement("slider1").click();
        MobileElement slider = (MobileElement) waitForElement("slider");
        Dimension size = slider.getSize();
        TouchAction swipe = new TouchAction(driver).press(ElementOption.element(slider,
                0, size.height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(ElementOption.element(slider, size.width / 2,
                        size.height / 2)).release();
        swipe.perform();
    }
}
