package com.appium.executor;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

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
	
	public void runMethodParallelAppium(String pack,int devicecount) throws Exception {

		// getClassPackage("com.test.parallel");

		Collection<URL> urls = ClasspathHelper.forPackage(pack);
		Iterator<URL> iter = urls.iterator();
		URL url = iter.next();
		urls.clear();
		URL newUrl = new URL(url.toString() + pack.replaceAll("\\.", "/"));
		List<URL> newUrls = Lists.newArrayList(newUrl);
		Reflections reflections = new Reflections(
				new ConfigurationBuilder().setUrls(newUrls).setScanners(new MethodAnnotationsScanner()));
		Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);

		ExecutorService executorService = Executors.newFixedThreadPool(devicecount);

		for (Method met : resources) {
			executorService.submit(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (met.getDeclaringClass().getSimpleName().contains("Test")) {
						System.out.println("*****CurrentRunningThread" + Thread.currentThread().getName()
								+ met.getDeclaringClass() + "***MethodName****" + met.getName().toString());
						runMethodParallel(met.getDeclaringClass(), met.getName().toString());
					}

				}
			});
		}
		
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finally complete");

	}


	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[] { arg });
		System.out.println("Into TestNGRunner");
		test.run();
	}
	
	@SuppressWarnings("rawtypes")
	public static void runMethodParallel(Class classes, String method) {
		System.out.println("running test: " + classes.getSimpleName() + ":" + method);
		TestNG testNG = new TestNG();
		testNG.setVerbose(2);
		XmlSuite suite = new XmlSuite();
		suite.setName("TestNG Forum");
		XmlTest test = new XmlTest(suite);
		test.setName("TestNG Test");
		XmlClass clazz = new XmlClass();
		// Since DemoClassWithManyMethods is a nested class, we have to use "$"
		// symbol, else we could have just used
		// getCanonicalName() alone

		// clazz.setName(MethodRunner.class.getCanonicalName() + "$" +
		// classes.getSimpleName());
		clazz.setName(classes.getCanonicalName());
		// clazz.setClass(classes);
		XmlInclude include = new XmlInclude(method);

		include.setXmlClass(clazz);
		clazz.setIncludedMethods(Arrays.asList(include));
		test.setXmlClasses(Arrays.asList(clazz));
		testNG.setXmlSuites(Arrays.asList(suite));
		testNG.run();
	}

}
