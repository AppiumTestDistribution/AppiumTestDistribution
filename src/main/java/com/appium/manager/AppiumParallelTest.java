package com.appium.manager;

import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.CommandPrompt;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.report.factory.ExtentManager;
import com.report.factory.ExtentTestManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class AppiumParallelTest extends TestListenerAdapter {
	protected String port;
	public AppiumDriver<MobileElement> driver;
	CommandPrompt cp = new CommandPrompt();
	public AppiumManager appiumMan = new AppiumManager();


	public InputStream input = null;
	public String device_udid;
	public ExtentTest testReporter;
	public AppiumDriverLocalService appiumDriverLocalService;
	int thread_device_count;
	public List<LogEntry> logEntries;
	public File logFile;
	public PrintWriter log_file_writer;
	public DesiredCapabilities capabilities = new DesiredCapabilities();
	public String category;

  private static ArrayList<String> devices;
  public static Properties prop = new Properties();
  private static IOSDeviceConfiguration iosDevice = new IOSDeviceConfiguration();
  private static AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
  private static ConcurrentHashMap<String, Boolean> deviceMapping = new ConcurrentHashMap<String, Boolean>();
	static {
    try {
      prop.load(new FileInputStream("config.properties"));
      if (prop.getProperty("PLATFORM").equalsIgnoreCase("android")) {
        devices = androidDevice.getDeviceSerail();
      } else if (prop.getProperty("PLATFORM").equalsIgnoreCase("ios")) {
        devices = iosDevice.getIOSUDID();
        iosDevice.setIOSWebKitProxyPorts();
      }
      for (String device : devices) {
        deviceMapping.put(device, true);
      }

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Failed to initialize framework");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Failed to initialize framework");
    }
  }

	public static synchronized String getNextAvailableDeviceId() {
		ConcurrentHashMap.KeySetView<String, Boolean> devices = deviceMapping.keySet();
		for (String device: devices) {
			if (deviceMapping.get(device) == true) {
				deviceMapping.put(device, false);
				return device;
			}
		}
		return null;
	}


  public static void freeDevice(String deviceId) {
    deviceMapping.put(deviceId, true);
  }

	public synchronized AppiumServiceBuilder startAppiumServer(String methodName) throws Exception {
    device_udid = getNextAvailableDeviceId();
    if (device_udid == null) {
      System.out.println("No devices are free to run test or Failed to run test");
      return null;
    }

		if (prop.getProperty("PLATFORM").equalsIgnoreCase("ios")) {
			category = iosDevice.getDeviceName(device_udid).replace("\n", " ");
		} else {
			category = androidDevice.deviceModel(device_udid);
		}
		System.out.println(category + device_udid.replace(".", "_").replace(":", "_"));
		ExtentTestManager.startTest(methodName, "Mobile Appium Test",
				category + device_udid.replace(".", "_").replace(":", "_"));
		ExtentTestManager.getTest().log(LogStatus.INFO, "AppiumServerLogs", "<a href=" + System.getProperty("user.dir")
				+ "/target/appiumlogs/" + device_udid + "__" + methodName + ".txt" + ">Logs</a>");
		if (prop.getProperty("PLATFORM").equalsIgnoreCase("android")) {
			return appiumMan.appiumServer(device_udid, methodName);
		} else if (prop.getProperty("PLATFORM").equalsIgnoreCase("ios")) {
			iosDevice.startIOSWebKit(device_udid);
			return appiumMan.appiumServerIOS(device_udid, methodName);

		}
		return null;
	}

	// @BeforeMethod
	public synchronized AppiumDriver<MobileElement> startAppiumServerInParallel(String methodName) throws Exception {

		if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
			androidWeb();
		} else if (prop.getProperty("APP_TYPE").equalsIgnoreCase("androidnative")) {
			androidNative();
		} else if (prop.getProperty("APP_TYPE").equalsIgnoreCase("iosnative")) {
			iosNative();
		}

		Thread.sleep(5000);
		if (prop.getProperty("APP_TYPE").equalsIgnoreCase("androidnative")
				|| prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
			driver = new AndroidDriver<MobileElement>(appiumMan.getAppiumUrl(), capabilities);
		} else if (prop.getProperty("APP_TYPE").equalsIgnoreCase("iosnative")) {
			driver = new IOSDriver<MobileElement>(appiumMan.getAppiumUrl(), capabilities);
		}

		return driver;
	}

	public void startLogResults(String methodName) throws FileNotFoundException {
		if (prop.getProperty("APP_TYPE").equalsIgnoreCase("androidnative")) {
			logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
			logFile = new File(
					System.getProperty("user.dir") + "/target/adblogs/" + device_udid + "__" + methodName + ".txt");
			log_file_writer = new PrintWriter(logFile);
		}
		System.out.println(device_udid);
		/*
		 * String category = androidDevice.deviceModel(device_udid);
		 * ExtentTestManager.startTest(methodName, "Mobile Appium Test",
		 * category + device_udid.replace(".", "_").replace(":", "_"));
		 */
	}

	// @AfterMethod
	public void endLogTestResults(ITestResult result) {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, result.getMethod().getMethodName(), "Pass");
			/*
			 * ExtentTestManager.getTest().log(LogStatus.INFO, "Logs:: <a href="
			 * + System.getProperty("user.dir") + "/target/appiumlogs/" +
			 * device_udid + "__" + result.getMethod().getMethodName() + ".txt"
			 * + ">AppiumServerLogs</a>");
			 */
			if (prop.getProperty("APP_TYPE").equalsIgnoreCase("androidnative")) {
				log_file_writer.println(logEntries);
				log_file_writer.flush();
				ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
						"ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/" + device_udid + "__"
								+ result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
				System.out.println(driver.getSessionId() + ": Saving device log - Done.");
			}

		}
		if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName(), result.getThrowable());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/target/" + device_udid
						+ result.getMethod().getMethodName() + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
					"Snapshot below: " + ExtentTestManager.getTest().addScreenCapture(System.getProperty("user.dir")
							+ "/target/" + device_udid + result.getMethod().getMethodName() + ".png"));
			if (prop.getProperty("APP_TYPE").equalsIgnoreCase("androidnative")) {
				log_file_writer.println(logEntries);
				log_file_writer.flush();
				ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName(),
						"ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/" + device_udid + "__"
								+ result.getMethod().getMethodName() + ".txt" + ">AdbLogs</a>");
				System.out.println(driver.getSessionId() + ": Saving device log - Done.");
			}

		}
		if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped");
		}

	}

	public synchronized void killAppiumServer() throws InterruptedException, IOException {
		System.out.println("**************ClosingAppiumSession****************");
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
		if (prop.getProperty("APP_TYPE").equalsIgnoreCase("androidnative")) {
			System.out.println("Closing Session::" + driver.getSessionId());
			driver.closeApp();
		} else if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
			driver.quit();
		}
		appiumMan.destroyAppiumNode();
		iosDevice.destroyIOSWebKitProxy();

    freeDevice(device_udid);
	}

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}

	public void waitForElement(By id, int time) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable((id)));
	}

	public void resetAppData() throws InterruptedException, IOException {
		androidDevice.clearAppData(device_udid, prop.getProperty("APP_PACKAGE"));
	}

	public void closeOpenApp() throws InterruptedException, IOException {
		androidDevice.closeRunningApp(device_udid, prop.getProperty("APP_PACKAGE"));
	}

	public void androidNative() {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.X");
		if (Integer.parseInt(androidDevice.deviceOS(device_udid)) <= 16) {
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
		}
		capabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("APP_PATH"));
		capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, prop.getProperty("APP_PACKAGE"));
		capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, prop.getProperty("APP_ACTIVITY"));
		if (prop.getProperty("APP_WAIT_ACTIVITY") != null) {
			capabilities.setCapability(MobileCapabilityType.APP_WAIT_ACTIVITY, prop.getProperty("APP_WAIT_ACTIVITY"));
		}
	}

	public void androidWeb() {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.X");
		// If you want the tests on real device, make sure chrome browser is
		// installed
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, prop.getProperty("BROWSER_TYPE"));
		capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);
		capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
	}

	public void iosNative() {
		// TODO Auto-generated method stub
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
		capabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("APP_PATH"));
		capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);
		capabilities.setCapability("bundleId", prop.getProperty("BUNDLE_ID"));
		capabilities.setCapability("autoAcceptAlerts", true);
		capabilities.setCapability("browserName", "");
	}

	public void deleteAppIOS(String bundleID) throws InterruptedException, IOException {
		iosDevice.unInstallApp(device_udid, bundleID);
	}

	public void installAppIOS(String appPath) throws InterruptedException, IOException {
		iosDevice.installApp(device_udid, appPath);
	}

	public Boolean checkAppIsInstalled(String bundleID) throws InterruptedException, IOException {
		return iosDevice.checkIfAppIsInstalled(bundleID);
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
