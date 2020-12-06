package com.test.site;

import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class WindowsTest extends UserBaseTest {

    @Test
    public void sessionTest() {
        System.out.println("------"+ getDriver());
        System.out.println("******" + getDriver().getSessionDetails().toString());
        System.out.println("@@@@@@@" + getDriver().getPageSource());
        MobileElement edit = getDriver().findElementByClassName("Edit");
        Assert.assertNotNull(edit);
    }
}
