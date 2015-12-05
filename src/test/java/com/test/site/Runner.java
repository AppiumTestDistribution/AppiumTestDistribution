package com.test.site;

import org.testng.TestNG;
import org.testng.annotations.Test;

import com.appium.manager.ParallelThread;

public class Runner {

	public static void main(String[] arg) throws Exception {
		ParallelThread parallelThread = new ParallelThread();
		parallelThread.runner("com.test.site");

	}

	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		test.run();
	}
}
