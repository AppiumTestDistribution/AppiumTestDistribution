package com.test.site;

import com.annotation.values.Author;
import com.annotation.values.RetryCount;

import com.annotation.values.SkipIf;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class SliderTest extends  UserBaseTest {

    @DataProvider(name = "bla-bla")
    public Iterator<Object[]> params() {
        return Arrays.asList(new Object[] { "one" }, new Object[] { "two" }).iterator();
    }

    @Test(dataProvider = "bla-bla")
    public void dataProviderTest(String msg) throws IOException, InterruptedException {
        System.out.println(msg);
        Assert.assertTrue(true);
    }

    @Test
    @Author(name = "Srinivasan Sekar")
    @SkipIf(platform = "ios")
    public void failureTest() throws IOException, InterruptedException {
        Assert.assertTrue(false);
    }

    @Test
    public void passTest() throws IOException, InterruptedException {
        Assert.assertTrue(true);
    }

    @Test(description = "Yo Man, its description")
    @RetryCount(maxRetryCount = 3)
    public void customDescriptionAndMaxRetriesTest() throws IOException, InterruptedException {
        Assert.assertTrue(false);
    }
}
