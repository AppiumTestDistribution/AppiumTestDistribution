package com.test.site;

import org.testng.TestNG;
import org.testng.annotations.Test;

import com.appium.manager.ParallelThread;

public class Runner {
	@Test
	public static void testApp() throws Exception {
		ParallelThread parallelThread = new ParallelThread();
		parallelThread.runner("com.test.site");

	}

}
