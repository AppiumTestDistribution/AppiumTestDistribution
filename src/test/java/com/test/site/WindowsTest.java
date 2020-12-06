package com.test.site;

import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WindowsTest extends UserBaseTest {

    @Test
    public void sessionTest() {
        System.out.println("------"+ getDriver());
        System.out.println("******" + getDriver().getSessionDetails().toString());
        System.out.println("@@@@@@@" + getDriver().getPageSource());
        final RemoteWebElement edit = getDriver().findElementByClassName("Edit");
        Assert.assertNotNull(edit);
    }
}
