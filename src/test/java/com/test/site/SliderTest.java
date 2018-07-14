package com.test.site;

import com.annotation.values.RetryCount;
import com.appium.utils.ScreenShotManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;

public class SliderTest extends  UserBaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SliderTest.class);

    @Test(dataProvider = "bla-bla")
    public void sliderTest(String msg) throws IOException, InterruptedException {
        LOGGER.info(msg);
        login("lo").click();
        waitForElement("slider1").click();
        MobileElement slider = (MobileElement) waitForElement("slider");
        Dimension size = slider.getSize();
        TouchAction swipe = new TouchAction(driver).press(ElementOption.element(slider,
                0, size.height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(ElementOption.element(slider, size.width / 2,
                        size.height / 2)).release();
        new ScreenShotManager().captureScreenShot("SliderScreen");
        swipe.perform();
    }

    @DataProvider(name = "bla-bla")
    public Iterator<Object[]> params() {
        return Arrays.asList(new Object[] { "one" }, new Object[] { "two" }).iterator();
    }

    @Test
    public void slider1Test() throws IOException, InterruptedException {
        login("login").click();
        waitForElement("slider1").click();
        MobileElement slider = (MobileElement) waitForElement("slider");
        Dimension size = slider.getSize();
        TouchAction swipe = new TouchAction(driver).press(ElementOption.element(slider,
                0, size.height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(ElementOption.element(slider, size.width / 2,
                        size.height / 2)).release();
        new ScreenShotManager().captureScreenShot("SliderScreen");
        swipe.perform();
    }

    @Test
    @RetryCount(maxRetryCount = 4)
    public void slider2Test() throws IOException, InterruptedException {
        login("login").click();
        waitForElement("slider").click();
        MobileElement slider = (MobileElement) waitForElement("slider");
        Dimension size = slider.getSize();
        TouchAction swipe = new TouchAction(driver).press(ElementOption.element(slider,
                0, size.height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3)))
                .moveTo(ElementOption.element(slider, size.width / 2,
                        size.height / 2)).release();
        new ScreenShotManager().captureScreenShot("SliderScreen");
        swipe.perform();
    }
}
