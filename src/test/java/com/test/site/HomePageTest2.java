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


    @Test(groups = "smoke")
    @Author(name = "AnsonLiao")
    public void testMethodOne11() throws Exception {
       Assert.assertTrue(false);
    }

    @Test(groups = "smoke")
    @Author(name = "AnsonLiao")
    public void testMethodOne2() throws Exception {
        Assert.assertTrue(true);
    }


    @Test(groups = "smoke")
    @Author(name = "AnsonLiao")
    public void testMethodOne3() throws Exception {
        throw new SkipException("testing");
    }

}
