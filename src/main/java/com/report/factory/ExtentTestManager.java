package com.report.factory;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExtentTestManager { // new
	public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	public static ExtentReports extent = ExtentManager.getInstance();
	public static Properties prop = new Properties();
	public static InputStream input = null;

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
		try {
			input = new FileInputStream("config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map sysInfo = new HashMap();
		sysInfo.put("Selenium Java Version", "2.52.0");
		sysInfo.put("Environment", "Prod");
		sysInfo.put("AppiumVersion", "3.4.0");
		sysInfo.put("RunnerMode",prop.getProperty("RUNNER").toUpperCase());
		extent.addSystemInfo(sysInfo);
		return test;
	}

	public synchronized static void logOutPut(String imgSrc,String headerName) {
		imgSrc = "<div class='sample'><h4 class='md-display-4'>"+headerName+"</h4><img src="+imgSrc + " style=\"width:100%;height:100%;\"></div>";
		extent.loadConfig(new File("/Users/saikrisv/git/AppiumTestDistribution/extent.xml"));
		extent.setTestRunnerOutput(imgSrc);
		//extent.setTestRunnerOutput(s);
	}
}