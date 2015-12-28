package com.appium.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
/*
 * This class picks the devices connected 
 * and distributes across multiple thread.
 * 
 * Thanks to @Thote_Gowda(thotegowda.gr@gmail.com)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.appium.cucumber.report.HtmlReporter;
import com.appium.executor.MyTestExecutor;

public class ParallelThread {
	protected static int deviceCount;
	static Map<String, String> devices = new HashMap<String, String>();
	static AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();
	AppiumParallelTest baseTest = new AppiumParallelTest();
	HtmlReporter htmlReporter = new HtmlReporter();
	MyTestExecutor myTestExecutor = new MyTestExecutor();
	public static Properties prop = new Properties();
	public static InputStream input = null;
	List<Class> testcases;

	@SuppressWarnings({ "rawtypes" })
	public void runner(String pack) throws Exception {
		File f = new File(System.getProperty("user.dir") + "/target/appiumlogs/");
		if (!f.exists()) {
			System.out.println("creating directory: " + "Logs");
			boolean result = false;
			try {
				f.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		File adb_logs = new File(System.getProperty("user.dir") + "/target/adblogs/");
		if (!adb_logs.exists()) {
			System.out.println("creating directory: " + "ADBLogs");
			boolean result = false;
			try {
				adb_logs.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
		input = new FileInputStream("config.properties");
		prop.load(input);
		devices = deviceConf.getDevices();
		deviceCount = devices.size() / 3;
		System.out.println("Total Number of devices detected::" + deviceCount);
		System.out.println("starting running tests in threads");

		testcases = new ArrayList<Class>();

		// final String pack = "com.paralle.tests"; // Or any other package
		PackageUtil.getClasses(pack).stream().forEach(s -> {
			if (s.toString().contains("Test")) {
				System.out.println("forEach: " + testcases.add((Class) s));
			}
		});

		if (prop.getProperty("RUNNER").equalsIgnoreCase("distribute")) {
			myTestExecutor.distributeTests(deviceCount, testcases);
			// myTestExecutor.runMethodParallelAppium(pack, deviceCount);

		} else if (prop.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
			myTestExecutor.parallelTests(deviceCount, testcases);
		}

	}

}
