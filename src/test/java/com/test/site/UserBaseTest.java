package com.test.site;

import com.appium.manager.AppiumDriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UserBaseTest {
    public List<AppiumDriver<MobileElement>> driver;


    public AppiumDriver<MobileElement> getDriver() {
        driver = AppiumDriverManager.getDrivers();
        return driver.get(0);
    }

    public WebElement login(String locator) {
        return waitForElement(locator);
    }

    public WebElement waitForElement(String locator) {
        return new WebDriverWait(getDriver(),30).until(ExpectedConditions
                .elementToBeClickable(MobileBy.AccessibilityId(locator)));
    }
}
