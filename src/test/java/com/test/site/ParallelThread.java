package com.test.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.test.base.AndroidDeviceConfiguration;

public class ParallelThread {
	protected static int deviceCount;
	static Map<String, String> devices = new HashMap<String, String>();
	static AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();

	@SuppressWarnings({ "rawtypes" })
	public static void main(String[] args) throws Exception {
		devices = deviceConf.getDevices();
		deviceCount = devices.size() / 3;
		System.out.println("Total Number of devices detected::" + deviceCount);
		System.out.println("starting running tests in threads");
		ExecutorService executorService = Executors.newFixedThreadPool(deviceCount);
		List<Class> testCases = new ArrayList<Class>();
		testCases.add(HomePageTest1.class);
		testCases.add(HomePageTest2.class);
		testCases.add(HomePageTest3.class);
		testCases.add(HomePageTest4.class);

		for (final Class testFile : testCases) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					runTestCase(testFile);
				}
			});
		}

		System.out.println("ending");

	}

	@SuppressWarnings("rawtypes")
	private static void runTestCase(Class testFile) {
		Result result = JUnitCore.runClasses(testFile);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}
	
}
