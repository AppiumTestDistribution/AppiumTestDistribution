package com.appium.webtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.junit.Assert.assertNotNull;

public class LoginTestFailure extends com.test.site.UserBaseTest {


    @Test public void loginTestFailure() throws InterruptedException {
        getDriver().get("http://www.thoughtworks.com");
        WebElement idElement = getDriver().findElement(By.id("mobile-menu-titleee"));
        assertNotNull(idElement);
        //elementHighlight(idElement);
        idElement.click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='mobile-menu']/li[6]/a")));
        WebElement commentElement =
            getDriver().findElement(By.xpath(".//*[@id='mobile-menu']/li[6]/a"));
        //elementHighlight(commentElement);
        assertNotNull(commentElement);
        commentElement.click();
        Thread.sleep(5000);
        WebElement contact_us =
            getDriver().findElement(By.xpath(".//*[@id='footer-menu']/div/ul/li[9]/a"));
        contact_us.click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='contact-us-vertical']")));

    }

}
