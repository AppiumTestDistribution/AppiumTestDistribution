package com.test.site;

import com.annotation.values.Author;
import com.annotation.values.RetryCount;

import com.annotation.values.SkipIf;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class SliderTest extends UserBaseTest {



    @Author(name = "Srinivasan Sekar")
    @SkipIf(platform = "ios")
    @Test
    public void failureTest() {
        Assert.assertTrue(false);
    }

    @Test
    public void passTest() {
        Assert.assertTrue(true);
    }

    @Test
    public void skipTest() {
        throw new SkipException("Skipped because property was set to :::");
    }


    @Test(description = "Yo Man, its description")
    @RetryCount(maxRetryCount = 3)
    public void customDescriptionAndMaxRetriesTest() {
        Assert.assertTrue(false);
    }
}
