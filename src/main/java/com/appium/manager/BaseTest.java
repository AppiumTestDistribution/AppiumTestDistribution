package com.appium.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import ru.yandex.qatools.allure.annotations.Attachment;

public class BaseTest extends TestListenerAdapter {
	protected String port;
	public AppiumDriver<MobileElement> driver;
	CommandPrompt cp = new CommandPrompt();
	AppiumManager appiumMan = new AppiumManager();
	AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
	public static Properties prop = new Properties();
	public static InputStream input = null;
	public static String device_udid;
	public ExtentTest testReporter;
	AppiumDriverLocalService appiumDriverLocalService;
	
	//@BeforeClass
	public void testopenBroswer() throws Exception {	
		input = new FileInputStream("config.properties");
		prop.load(input);
		ArrayList<String> devices = androidDevice.getDeviceSerail();
		System.out.println("*************" + Thread.currentThread().getName().split("-")[3]);
		int thread_device_count = Integer.valueOf(Thread.currentThread().getName().split("-")[3]) - 1;
		device_udid = devices.get(thread_device_count);
		appiumMan.appiumServer(device_udid);
       //Replace the appium start programatically	
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("platformVersion", "5.X");
		//capabilities.setCapability("app", System.getProperty("user.dir") + "/build/" + prop.getProperty("appname"));
		capabilities.setCapability("package", prop.getProperty("package"));
		capabilities.setCapability("appActivity", prop.getProperty("appActivity"));
		Thread.sleep(5000);
		//driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
		driver = new AndroidDriver<MobileElement>(appiumMan.getAppiumUrl(), capabilities);

	}

	@AfterClass
	public void closeApp() {
		driver.close();
		//driver.quit();
	}

	@BeforeMethod
	public void beforeMethod(Method caller) throws Exception {
	    testopenBroswer();
		ExtentTestManager.startTest(caller.getName(), "This is a simple test.");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");

		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped");
		}

		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
		driver.quit();
		appiumMan.destroyAppiumNode();
		//driver.quit();
	}

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	@AfterSuite
	public void afterSuite() {
		ExtentManager.getInstance().flush();
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
