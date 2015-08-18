package com.test.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {
	public static WebDriver driver;

	@BeforeTest
	@Parameters("browser")
	public void openBroswer(String browser) {

		/*
		 * Comparing the value of parameter name if this is FF then It would
		 * launch Firefox and script that would run is as follows
		 */
		if (browser.equalsIgnoreCase("FF")) {
			System.out.println("Firefox driver would be used");
			driver = new FirefoxDriver();
		} else {
			System.out.println("Ie webdriver would be used");
			driver = new ChromeDriver();
		}
	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();

	}

}
