package com.test.site;

import static com.jayway.jsonpath.JsonPath.parse;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static java.text.MessageFormat.format;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

import com.jayway.jsonpath.DocumentContext;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AtdServiceTest {
    private static final ApiHelper API = new ApiHelper();
    private static final String URL = "http://localhost:8888";
    private static final String UUID = "emulator-5554";
    private AndroidDriver<AndroidElement> driver;

    @BeforeClass
    public void setup() {
        String response = allocate(UUID);
        DocumentContext path = parse(response);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, path.read("os")
            .toString());
        capabilities.setCapability(PLATFORM_VERSION, path.read("osVersion")
            .toString());
        capabilities.setCapability(DEVICE_NAME, path.read("name")
            .toString());
        capabilities.setCapability(APP, "https://github.com/shridharkalagi/AppiumSample/raw/master/VodQA.apk");
        capabilities.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        driver = new AndroidDriver<>(capabilities);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
        free(UUID);
    }

    @Test
    public void testLogin() {
        WebElement login = new WebDriverWait(driver, 30).until(
            ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("login")));
        AndroidTouchAction touch = new AndroidTouchAction(driver);
        touch.tap(TapOptions.tapOptions()
            .withElement(ElementOption.element(login)))
            .perform();
    }

    @SneakyThrows
    private String allocate(String uuid) {
        return API.post(format("{0}/devices/{1}/allocate", URL, uuid))
            .string();
    }

    @SneakyThrows
    private void free(String uuid) {
        API.delete(format("{0}/devices/{1}/freeDevice", URL, uuid))
            .string();
    }
}