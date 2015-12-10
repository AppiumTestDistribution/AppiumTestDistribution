package com.appium.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.TestNG;

public class Executor {
	List<Thread> threads = new ArrayList<Thread>();
	
	public void distributeTests(int deviceCount, List<Class> testcases) {
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
	}

	public void parallelTests(int deviceCount, List<Class> testCases) throws InterruptedException {
	
		for (int i = 0; i < deviceCount; i++) {
			final int x = i;
			Thread t = new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					runTests(testCases);
				}

				private void runTests(List<Class> testCases) {
					for (Class test : testCases) {
						System.out.println("*****CurrentRunningThread" + Thread.currentThread().getName() + test);
						testRunnerTestNg(test);
					}
				}
			});
			
			threads.add(t);
			t.start();
	    }
		
		for (Thread t : threads) {
			t.join();
		}
		
		System.out.println("Finally complete");
	}

	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		System.out.println("Into TestNGRunner");
		test.run();
	}

}
