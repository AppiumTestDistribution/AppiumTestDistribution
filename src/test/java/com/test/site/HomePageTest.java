package com.test.site;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.test.base.BaseTest;

public class HomePageTest extends BaseTest{
	
	public static final String PASSWORD = "org.wordpress.android:id/nux_password";
	public static final String USERNAME = "org.wordpress.android:id/nux_username";
	
	@Test

    public void testMethodOne_1() throws Exception  {
		waitForElementClickable(By.id("com.android2.calculator3:id/cling_dismiss"), 30);
		//WebElement el= driver.findElement(By.id("com.android2.calculator3:id/cling_dismiss"));
		//highlightElement(driver, el);
		driver.findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		driver.findElement(By.id("com.android2.calculator3:id/digit1")).click();
		driver.findElement(By.id("com.android2.calculator3:id/plus")).click();
		driver.findElement(By.id("com.android2.calculator3:id/digit9")).click();
		driver.findElement(By.id("com.android2.calculator3:id/equal")).click();	
		//driver.close();
	}
	
	public WebElement driver(By by) {
		return driver.findElement(by);

	}

	public void waitForElementClickable(By by, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.elementToBeClickable(by));

	}
 
}
