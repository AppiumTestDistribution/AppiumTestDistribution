package com.test.site;


import com.annotation.values.Author;
import com.annotation.values.Description;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.thoughtworks.device.SimulatorManager;
import org.junit.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;


@Description("This Test validates swipe scenarios")
public class HomePageTest2 {


    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne1() throws Exception {
        throw new SkipException("Skipping HomepageTest");
    }

    @Test(groups = "smoke", description = "Testing Skips")
    @Author(name = "AnsonLiao")
    public void testMethodOne2() throws Exception {
        Assert.assertTrue(false);
    }

    @Test(groups = "smoke", description = "Testing Skips-- true")
    @Author(name = "AnsonLiao")
    public void testMethodOne3() throws Exception {
        Assert.assertTrue(true);
    }

}
