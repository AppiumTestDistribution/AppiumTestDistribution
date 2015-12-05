package com.test.site;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.TestNG;
import org.testng.annotations.Test;

import com.appium.manager.BaseTest;

public class sample {

	@Test
	public void testReports() {
		// TODO Auto-generated method stub
		List<Class> testCases = new ArrayList<Class>();
		testCases.add(HomePageTest1.class);
		testCases.add(HomePageTest2.class);
		testCases.add(HomePageTest3.class);
		
		
		int devices = 1;
		for (int i = 0; i < devices; i++) {
			final int x = i;
			new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					runTests(testCases);
				}

				private void runTests(List<Class> testCases) {
					for (Class test : testCases) {
						System.out.println(Thread.currentThread().getName() + test);
						testRunnerTestNg(test);
					}
				}

			}).start();
		}
		
//		ExecutorService executorService = Executors.newFixedThreadPool(2);
//		for (final Class testFile : testCases) {
//			executorService.submit(new Runnable() {
//				public void run() {
//					System.out.println("Running test file: " + testFile.getName());
//					testRunnerTestNg(testFile);
//
//				}
//			});
//		}
//		executorService.shutdown();
//		try {
//			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		// baseTest.convertXmlToJSon();
//		System.out.println("ending");
	}
	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		test.run();
	}
}
