package com.appium.webtest;

import org.testng.annotations.Test;

import com.appium.manager.ParallelThread;

public class RunnerWeb {

	
	@Test
	public void runWebTests() throws Exception{
		ParallelThread parallelThread = new ParallelThread();
		parallelThread.runner("com.appium.webtest");
	}
}
