package com.appium.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.TestNG;

public class ParallelThread {
	protected static int deviceCount;
	static Map<String, String> devices = new HashMap<String, String>();
	static AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();
	BaseTest baseTest = new BaseTest();
	List<Class> testcases;

	@SuppressWarnings({ "rawtypes" })

	public void runner(String pack) throws Exception {
		devices = deviceConf.getDevices();
		deviceCount = devices.size() / 3;
		System.out.println("Total Number of devices detected::" + deviceCount);
		System.out.println("starting running tests in threads");
		ExecutorService executorService = Executors.newFixedThreadPool(deviceCount);

		testcases = new ArrayList<Class>();

		// final String pack = "com.paralle.tests"; // Or any other package
		PackageUtil.getClasses(pack).stream().forEach(s -> {
			if (s.toString().contains("Test")) {
				System.out.println("forEach: " + testcases.add((Class) s));
			}
		});

		for (final Class testFile : testcases) {
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
		System.out.println("Merging all junit xml and converting to JSON");
		baseTest.convertXmlToJSon();
		System.out.println("ending");

	}

	// @SuppressWarnings("rawtypes")
	// private static void runTestCase(Class testFile) {
	// Result result = JUnitCore.runClasses(testFile);
	// for (Failure failure : result.getFailures()) {
	// System.out.println(failure.toString());
	// }
	// }

	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		test.run();
	}

}
