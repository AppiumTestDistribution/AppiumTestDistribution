package com.test.ios;

import org.testng.annotations.Test;

import com.appium.manager.ParallelThread;

public class RunnerIOS {

	@Test
	public void testApp() throws Exception{
		ParallelThread parallelThread = new ParallelThread();
		parallelThread.runner("com.test.steps");
	}

	
}
