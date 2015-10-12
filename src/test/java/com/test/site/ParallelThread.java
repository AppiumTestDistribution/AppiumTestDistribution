package com.test.site;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.FormatterElement;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.testng.TestNG;
import org.testng.annotations.Test;

import com.test.base.AndroidDeviceConfiguration;

public class ParallelThread {
	protected static int deviceCount;
	static Map<String, String> devices = new HashMap<String, String>();
	static AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();

	@SuppressWarnings({ "rawtypes" })
	@Test
	public void testTrigger() throws Exception {
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
		testCases.add(HomePageTest5.class);

		for (final Class testFile : testCases) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					testRunnerTestNg(testFile);
					System.out.println("test file: " + testFile.getName());
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

	@SuppressWarnings("rawtypes")
	private static void runTestCase(Class testFile) {
		Result result = JUnitCore.runClasses(testFile);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}
	
	public static void getTest(String classTestFile){
	    String pathToReports = "/Users/saikrisv/Documents/workspace/TestNGParallel";
	    Project project = new Project();

	    try {
	        new File(pathToReports).mkdir();
	        JUnitTask task = new JUnitTask();

	        project.setProperty("java.io.tmpdir",pathToReports);
	        task.setProject(project);

	        FormatterElement.TypeAttribute type = new FormatterElement.TypeAttribute();
	        type.setValue("xml");

	        FormatterElement formater = new FormatterElement();   
	        formater.setType(type);
	        task.addFormatter(formater);
            //HomePageTest1.class.getName()
	        JUnitTest test = new JUnitTest(classTestFile);
	        test.setTodir(new File(pathToReports));
	        task.addTest(test);         
	        task.execute(); 
	    } catch (Exception e) {
	    }

	}
	
	
	public void testRunnerTestNg(Class arg){
		 TestNG test=new TestNG();
	       test.setTestClasses(new Class[]{arg});
	       test.run();
	}

}
