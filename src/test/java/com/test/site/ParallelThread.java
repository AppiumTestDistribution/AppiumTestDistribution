package com.test.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.testng.TestNG;

import com.appium.manager.AndroidDeviceConfiguration;
import com.appium.manager.BaseTest;

public class ParallelThread {
	protected static int deviceCount;
	static Map<String, String> devices = new HashMap<String, String>();
	static AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();
	BaseTest baseTest = new BaseTest();

	@SuppressWarnings({ "rawtypes" })
	
	public void runner(List<Class> testCases) throws Exception {
		devices = deviceConf.getDevices();
		deviceCount = devices.size() / 3;
		System.out.println("Total Number of devices detected::" + deviceCount);
		System.out.println("starting running tests in threads");
		ExecutorService executorService = Executors.newFixedThreadPool(deviceCount);

		for (final Class testFile : testCases) {
			executorService.submit(new Runnable() {
				public void run() {
					System.out.println("Running test file: " + testFile.getName());
					testRunnerTestNg(testFile);

				}
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// baseTest.convertXmlToJSon();
		System.out.println("ending");

	}

	@SuppressWarnings("rawtypes")
	private static void runTestCase(Class testFile) {
		Result result = JUnitCore.runClasses(testFile);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}

	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		test.run();
	}

}
