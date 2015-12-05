package com.appium.manager;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.TestNG;

import com.appium.cucumber.report.HtmlReporter;

public class ParallelThread {
	protected static int deviceCount;
	static Map<String, String> devices = new HashMap<String, String>();
	static AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();
	BaseTest baseTest = new BaseTest();
	HtmlReporter htmlReporter = new HtmlReporter();
	public static Properties prop = new Properties();
	public static InputStream input = null;
	List<Class> testcases;

	@SuppressWarnings({ "rawtypes" })

	public void runner(String pack) throws Exception {
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

		if (prop.getProperty("runner").equalsIgnoreCase("distribute")) {
			ExecutorService executorService = Executors.newFixedThreadPool(deviceCount);
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
			System.out.println("ending");
		} else if(prop.getProperty("runner").equalsIgnoreCase("parallel")){
			for (int i=0;i<deviceCount;i++){
				final int x = i;
				new Thread(new Runnable(){

					public void run() {
						// TODO Auto-generated method stub
					     runTests(testcases);
					}

					private void runTests(List<Class> testCasesParallel) {
						for (Class test : testCasesParallel) {
							 System.out.println("***Into Parallel Runner**"+Thread.currentThread().getName()+test);
							 testRunnerTestNg(test);
						}
					}
					
				}).start();
			}
		}

	}

	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		System.out.println("Into TestNGRunner");
		test.run();
	}

}
