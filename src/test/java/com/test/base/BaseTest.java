package com.test.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.screen.failure.OnFailure;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import ru.yandex.qatools.allure.annotations.Attachment;

public class BaseTest extends AvailabelPorts{
	protected String port;
	public AppiumDriver<MobileElement> driver;
	CommandPrompt cp = new CommandPrompt();
	AppiumManager appiumMan = new AppiumManager();
	AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
	@BeforeClass
	public void testopenBroswer() throws Exception {
		ArrayList<String> devices = androidDevice.getDeviceSerail();
		System.out.println("*************" + Thread.currentThread().getName().split("-")[3]);
		int thread_device_count = Integer.valueOf(Thread.currentThread().getName().split("-")[3]) - 1;
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
		// androidDevice.stopADB();

	}
	

	@Attachment
	public byte[] makeScreenshot() {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}
	
	public void waitForElement(By id, int time) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable((id)));
	}

	@SuppressWarnings("unused")
	public void convertXmlToJSon() throws IOException {
		String fileName = "report.json";
		BufferedWriter bufferedWriter = null;
		try {
			int i = 1;
			FileWriter fileWriter;
			int dir_1 = new File(System.getProperty("user.dir") + "/test-output/junitreports").listFiles().length;

			List textFiles = new ArrayList();
			File dir = new File(System.getProperty("user.dir") + "/test-output/junitreports");

			for (File file : dir.listFiles()) {
				if (file.getName().contains(("Test"))) {
					System.out.println(file);

					fileWriter = new FileWriter(fileName, true);
					InputStream inputStream = new FileInputStream(file);
					StringBuilder builder = new StringBuilder();
					int ptr = 0;
					while ((ptr = inputStream.read()) != -1) {
						builder.append((char) ptr);
					}

					String xml = builder.toString();
					JSONObject jsonObj = XML.toJSONObject(xml);

					// Always wrap FileWriter in BufferedWriter.
					bufferedWriter = new BufferedWriter(fileWriter);

					// Always close files.
					String jsonPrettyPrintString = jsonObj.toString(4);
					// bufferedWriter.write(jsonPrettyPrintString);
					if (i == 1) {
						bufferedWriter.append("[");
					}
					bufferedWriter.append(jsonPrettyPrintString);
					if (i != dir_1) {
						bufferedWriter.append(",");
					}

					if (i == dir_1) {
						bufferedWriter.append("]");
					}

					bufferedWriter.newLine();
					bufferedWriter.close();
					i++;
				}
			}
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
