package com.appium.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class BaseTest extends TestListenerAdapter {
	protected String port;
	public AppiumDriver<MobileElement> driver;
	CommandPrompt cp = new CommandPrompt();
	AppiumManager appiumMan = new AppiumManager();
	AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
	public static Properties prop = new Properties();
	public static InputStream input = null;
	public String device_udid;
	public ExtentTest testReporter;
	AppiumDriverLocalService appiumDriverLocalService;
	int thread_device_count;
	public List<LogEntry> logEntries;
	public File logFile;
	public PrintWriter log_file_writer;

	public void testopenBroswer(String methodName) throws Exception {
		input = new FileInputStream("config.properties");
		prop.load(input);
		ArrayList<String> devices = androidDevice.getDeviceSerail();

		if (prop.getProperty("RUNNER").equalsIgnoreCase("distribute")) {
			System.out.println("*************" + Thread.currentThread().getName());
			System.out.println("******Current Thread Running*******" + Thread.currentThread().getName().split("-")[3]);
			thread_device_count = Integer.valueOf(Thread.currentThread().getName().split("-")[3]) - 1;
		} else if (prop.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
			System.out.println("******Into Parallel BaseTest*******" + Thread.currentThread().getName().split("-")[1]);
			thread_device_count = Integer.valueOf(Thread.currentThread().getName().split("-")[1]) - 1;
			System.out.println("Device received from array" + devices.get(thread_device_count));
		}

		// When tests are running in parallel then we get
		// Thread.currentThread().getName().split("-")[1] to get the device
		// array position
		device_udid = devices.get(thread_device_count);
		appiumMan.appiumServer(device_udid, methodName);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("platformVersion", "5.X");
		capabilities.setCapability("package", prop.getProperty("APP_PACKAGE"));
		capabilities.setCapability("appActivity", prop.getProperty("APP_ACTIVITY"));
		Thread.sleep(5000);
		driver = new AndroidDriver<MobileElement>(appiumMan.getAppiumUrl(), capabilities);

	}

	@AfterClass
	public void closeApp() {
		// driver.close();
		// driver.quit();
	}

	@BeforeMethod
	public void beforeMethod(Method caller) throws Exception {
		testopenBroswer(caller.getName());
		logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
		logFile = new File(System.getProperty("user.dir") + "/target/adblogs/" + caller.getName() + ".txt");
		log_file_writer = new PrintWriter(logFile);
		System.out.println(device_udid);
		String category = androidDevice.deviceModel(device_udid);
		ExtentTestManager.startTest(caller.getName(), "Mobile Appium Test",
				category + device_udid.replace(".", "_").replace(":", "_"));
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Logs:: <a href=" + System.getProperty("user.dir")
					+ "/target/appiumlogs/" + result.getMethod().getMethodName() + ".txt" + ">AppiumServerLogs</a>");
			log_file_writer.println(logEntries);
			log_file_writer.flush();
			ExtentTestManager.getTest().log(LogStatus.INFO, "ADBLogs:: <a href=" + System.getProperty("user.dir")
					+ "/target/adblogs/" + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
			System.out.println(driver.getSessionId() + ": Saving device log - Done.");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");
			ExtentTestManager.getTest().log(LogStatus.INFO, "Logs::<a href=" + System.getProperty("user.dir")
					+ "/target/appiumlogs/" + result.getMethod().getMethodName() + ".txt" + ">AppiumServerLogs</a>");
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/" + device_udid
						+ result.getMethod().getMethodName() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ExtentTestManager.getTest().log(LogStatus.INFO,
					"Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir")
							+ "/target/" + device_udid + result.getMethod().getMethodName() + ".png"));
			log_file_writer.println(logEntries);
			log_file_writer.flush();
			ExtentTestManager.getTest().log(LogStatus.INFO, "ADBLogs:: <a href=" + System.getProperty("user.dir")
					+ "/target/adblogs/" + result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
			System.out.println(driver.getSessionId() + ": Saving device log - Done.");
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped");
		}

		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
		driver.closeApp();
		appiumMan.destroyAppiumNode();
		// driver.quit();
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
