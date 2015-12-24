package com.appium.executor;

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

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.testng.xml.XmlSuite.ParallelMode.METHODS;

public class MyTestExecutor {
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

	public void runMethodParallelAppium(String pack, int devicecount) throws Exception {
		Collection<URL> urls = ClasspathHelper.forPackage(pack);
		Iterator<URL> iter = urls.iterator();
		URL url = iter.next();
		urls.clear();
		URL newUrl = new URL(url.toString() + pack.replaceAll("\\.", "/"));
		List<URL> newUrls = Lists.newArrayList(newUrl);
		Reflections reflections = new Reflections(
				new ConfigurationBuilder().setUrls(newUrls).setScanners(new MethodAnnotationsScanner()));
		Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);

		runMethodParallel(constructXmlSuite(createTestsMap(resources)), devicecount);

		System.out.println("Finally complete");
	}

	public static void testRunnerTestNg(Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[]{arg});
		System.out.println("Into TestNGRunner");
		test.run();
	}
	
	@SuppressWarnings("rawtypes")
	public void runMethodParallel(XmlSuite suite, int threadCount) {
		TestNG testNG = new TestNG();
		testNG.setVerbose(2);
		testNG.setThreadCount(threadCount);
		testNG.setParallel(METHODS);
		testNG.setXmlSuites(asList(suite));
		testNG.run();
	}

	public XmlSuite constructXmlSuite(Map<String, List<Method>> methods) {
		XmlSuite suite = new XmlSuite();
		suite.setName("TestNG Forum");

		XmlTest test = new XmlTest(suite);
		test.setName("TestNG Test");

		List<XmlClass> xmlClasses = new ArrayList<>();
		for (String className : methods.keySet()) {
			if (className.contains("Test")) {
				xmlClasses.add(createClass(className, methods.get(className)));
			}
		}
		test.setXmlClasses(xmlClasses);
		return suite;
	}

	private XmlClass createClass(String className, List<Method> methods) {
		XmlClass clazz = new XmlClass();
		clazz.setName(className);
		clazz.setIncludedMethods(constructIncludes(methods));
		return clazz;
	}

	private List<XmlInclude> constructIncludes(List<Method> methods) {
		List<XmlInclude> includes = new ArrayList<>();
		for(Method m : methods) {
			includes.add(new XmlInclude(m.getName()));
		}
		return includes;
	}

	public Map<String, List<Method>> createTestsMap(Set<Method> methods) {
		Map<String, List<Method>> testsMap = new HashMap<>();
		methods.stream().forEach(method -> {
			List<Method> methodsList = testsMap.get(method.getDeclaringClass().getSimpleName());
			if (methodsList == null) {
				methodsList = new ArrayList<>();
				testsMap.put(method.getDeclaringClass().getSimpleName(), methodsList);
			}
			methodsList.add(method);
		});
		return testsMap;
	}
}
