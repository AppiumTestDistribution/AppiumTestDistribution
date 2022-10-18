package com.test.site;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "end-to-end-test" })
public class WindowsTest extends UserBaseTest {

    @Test
    public void sessionTest() {
        System.out.println("------" + getDriver());
        System.out.println("@@@@@@@" + getDriver().getPageSource());
        final WebElement edit = getDriver().findElement(AppiumBy.className("Edit"));
        Assert.assertNotNull(edit);
    }
}
