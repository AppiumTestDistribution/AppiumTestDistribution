package com.test.base;

import java.net.URL;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BaseTest extends AvailabelPorts {
	protected String port;
	public AppiumDriver<MobileElement> driver;
	CommandPrompt cp = new CommandPrompt();
	AppiumManager appiumMan = new AppiumManager();
	AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();

	@BeforeClass
	public void testopenBroswer() throws Exception {
		ArrayList<String> devices=androidDevice.getDeviceSerail();
		System.out.println("*************" + Thread.currentThread().getName().split("-")[3]);
		int thread_device_count=Integer.valueOf(Thread.currentThread().getName().split("-")[3]) - 1;
		String device_udid = devices.get(thread_device_count);
		port = appiumMan.startAppium(device_udid);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("platformVersion", "5.X");
		capabilities.setCapability("app", System.getProperty("user.dir") + "/build/AndroidCalculator.apk");
		capabilities.setCapability("package", "com.android2.calculator3");
		capabilities.setCapability("appActivity", "com.android2.calculator3.Calculator");
		System.out.println("http://127.0.0.1:" + port + "/wd/hub" + device_udid);
		Thread.sleep(5000);
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);

	}

	@AfterClass
	public void testcloseBrowser() throws Exception {
		driver.quit();
		//androidDevice.stopADB();

	}

	public void waitForElement(By id, int time) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable((id)));
	}

}
