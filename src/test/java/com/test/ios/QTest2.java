package com.test.ios;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.test.site.UserBaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.IOSServerFlag;

public class QTest2 extends UserBaseTest{

//	private IOSDriver<MobileElement> driver;
//	private AppiumDriverLocalService service;
//	CommandPrompt cp = new CommandPrompt();
//	
//	AvailabelPorts avlport = new AvailabelPorts();
//	@BeforeClass(alwaysRun = true)
//	public void startApp() throws Exception {
//		System.out.println("Starting Appium Server 2");
//		File classPathRoot = new File(System.getProperty("user.dir"));
//		int port = avlport.getPort();
//		int bootstrapPort=avlport.getPort();
//		AppiumServiceBuilder builder = new AppiumServiceBuilder()
//				.withAppiumJS(new File(
//						"/usr/local/lib/node_modules/appium/build/lib/main.js"))
//				.withArgument(GeneralServerFlag.LOG_LEVEL, "info")
//				.withArgument(IOSServerFlag.USE_NATIVE_INSTRUMENTS)
//				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, Integer.toString(bootstrapPort))
//				.withArgument(GeneralServerFlag.TEMP_DIRECTORY,
//						new File(String.valueOf(classPathRoot)).getAbsolutePath() + "/target/" + "tmp_" + port)
//				.withArgument(GeneralServerFlag.SESSION_OVERRIDE).usingPort(port);
//		service = builder.build();
//		service.start();
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setCapability("deviceName", "iPod touch");
////		capabilities.setCapability("autoLaunch", true);
//		capabilities.setCapability("newCommandTimeout", 90);
//		capabilities.setCapability("udid", "cb5168c0b1397f21189d46532e86147aa122660b");
//		capabilities.setCapability("bundleId", "com.tesco.grocery");
//		capabilities.setCapability("noReset", true);
//		driver = new IOSDriver<MobileElement>(service.getUrl(),capabilities);
//		Thread.sleep(10000L);
//	}
//
//	@AfterClass()
//	public void killServer() throws InterruptedException, IOException {
//		driver.quit();
//		service.stop();
//	}
	
//	@BeforeClass
//	public void method() throws Exception {
//		System.out.println("QTest1ThreadName: " + Thread.currentThread().getName());
//		Thread.sleep(3000);
//	}
	
	@Test
	public void methodtwo() throws Exception {
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@name='Store locator']")).sendKeys(Keys.ENTER);
		Thread.sleep(3000);
		driver.hideKeyboard();
		driver.getKeyboard().pressKey(Keys.ENTER);
//		driver.prx§
	}
	
//	@Test
//	public void methodthree() throws Exception {
//		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='Special offers']")).click();
//		Thread.sleep(3000);
//
//	}
//	
//	@Test
//	public void methodfour() throws Exception {
//		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='Browse all groceries']")).click();
//		Thread.sleep(3000);
//	}
//	
//	@Test
//	public void methodfive() throws Exception {
//		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='Discover the finest range']")).click();
//		Thread.sleep(3000);
//	}
//	
//	@Test
//	public void methodsix() throws Exception {
//		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='Add your Clubcard']")).click();
//		Thread.sleep(3000);
//	}
	
//	@Test
//	public void methodseven() throws Exception {
//		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='Browse all groceries']")).click();
//		Thread.sleep(3000);
//	}
	
//	@Test
//	public void methodeight() throws Exception {
//		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='action_bar_up_navigation'][1]")).click();
//		Thread.sleep(3000);
//		driver.findElement(By.xpath("//*[@name='Home']")).click();
//		Thread.sleep(3000);
//	}
}
