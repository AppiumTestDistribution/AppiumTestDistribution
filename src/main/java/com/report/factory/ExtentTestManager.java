package com.report.factory;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentTestManager { // new
	public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public static ExtentReports extent = ExtentManager.getInstance();

	public synchronized static ExtentTest getTest() {
		return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public synchronized static void endTest() {
		extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
	}

	public static synchronized ExtentTest startTest(String testName) {
		return startTest(testName, "", "");
	}

	@SuppressWarnings("unchecked")
	public synchronized static ExtentTest startTest(String testName, String desc, String deviceId) {
		ExtentTest test = extent.startTest(testName, desc).assignCategory(deviceId);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		@SuppressWarnings("rawtypes")
		Map sysInfo = new HashMap();
		sysInfo.put("Selenium Java Version", "2.48.2");
		sysInfo.put("Environment", "Prod");
		sysInfo.put("AppiumVersion", "3.3.0");
		extent.addSystemInfo(sysInfo);
		return test;
	}
	
	public synchronized static void logOutPut(String s) {
		extent.setTestRunnerOutput(s);
	}
}