package com.test.site;

import com.appium.manager.AppiumDriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserBaseTest {
    public AppiumDriver driver;


    public AppiumDriver getDriver() {
        driver = AppiumDriverManager.getDriver();
        return driver;
    }

    public WebElement login(String locator) {
        return waitForElement(locator);
    }

    public WebElement waitForElement(String locator) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(30)).until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId(locator)));
    }
}
