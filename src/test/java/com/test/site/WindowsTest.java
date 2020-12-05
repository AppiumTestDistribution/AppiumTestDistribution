package com.test.site;

import io.appium.java_client.MobileElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class WindowsTest extends UserBaseTest {

    @Test
    public void sessionTest() {
        List<MobileElement> edit = driver.findElementsByClassName("Edit");
        Assert.assertNotNull(edit);
    }
}
