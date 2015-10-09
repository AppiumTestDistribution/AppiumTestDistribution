package com.test.base;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class BaseTest extends AvailabelPorts {
	protected String port;
	public AppiumDriver<MobileElement> driver;
	CommandPrompt cp = new CommandPrompt();
	AppiumManager appiumMan = new AppiumManager();
	@Before
	public void openBroswer() throws Exception {
		String[] devices = { "192.168.56.101:5555", "192.168.56.102:5555" };

		// String appium_ports = getPort();
		// String bootstrap_ports = getPort();
		 String device_udid=pickRandomUUID(devices);
		// String command = "appium --session-override -p " + appium_ports + "
		// -U "+device_udid + " -bp " + bootstrap_ports + " >/dev/null 2>&1 & ";
		// System.out.println(command);
		// cp.runCommand(command);
		port = appiumMan.startAppium(device_udid);
		
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("platformVersion", "5.X");
		capabilities.setCapability("app", System.getProperty("user.dir") + "/build/AndroidCalculator.apk");
		capabilities.setCapability("package", "com.android2.calculator3");
		capabilities.setCapability("appActivity", "com.android2.calculator3.Calculator");
		//capabilities.setCapability("udid", devices);
		System.out.println("http://127.0.0.1:" + port + "/wd/hub" + device_udid);
		Thread.sleep(3000);
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"),
				capabilities);

	}

	@After
	public void closeBrowser() throws IOException {
		driver.quit();
	}

	public String pickRandomUUID(String[] uuidArray) {
		String uuid = "";
		Random random = new Random();
		int index = random.nextInt(uuidArray.length);
		uuid = uuidArray[index];
		return uuid;
	}

	public void waitForElement(By id, int time) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable((id)));
	}

}
