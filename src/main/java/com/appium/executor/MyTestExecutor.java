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
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

public class MyTestExecutor {
	List<Thread> threads = new ArrayList<Thread>();

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("rawtypes")
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


	public void runMethodParallelAppium(String pack, int devicecount,String executionType) throws Exception {
		Collection<URL> urls = ClasspathHelper.forPackage(pack);
		Iterator<URL> iter = urls.iterator();
		URL url = iter.next();
		urls.clear();
		URL newUrl = new URL(url.toString() + pack.replaceAll("\\.", "/"));
		List<URL> newUrls = Lists.newArrayList(newUrl);
		Reflections reflections = new Reflections(
				new ConfigurationBuilder().setUrls(newUrls).setScanners(new MethodAnnotationsScanner()));
		Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);

		if(executionType.equalsIgnoreCase("distribute")){
			runMethodParallel(constructXmlSuiteForDistribution(createTestsMap(resources), devicecount), devicecount);
		}else{
			runMethodParallel(constructXmlSuiteForParallel(createTestsMap(resources), devicecount), devicecount);
		}
		System.out.println("Finally complete");
	}

	public static void testRunnerTestNg(@SuppressWarnings("rawtypes") Class arg) {
		TestNG test = new TestNG();
		test.setTestClasses(new Class[]{arg});
		System.out.println("Into TestNGRunner");
		test.run();
	}

	public void runMethodParallel(XmlSuite suite, int threadCount) {
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(asList(suite));
		System.out.println(suite.toXml());
		testNG.run();
	}
	
	public XmlSuite constructXmlSuiteForParallel(Map<String, List<Method>> methods,int deviceCount) {
		XmlSuite suite = new XmlSuite();
		suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.TESTS);
		suite.setVerbose(2);
		for(int i=0;i<deviceCount;i++){
			XmlTest test = new XmlTest(suite);
			test.setName("TestNG Test"+i);
			test.setPreserveOrder("false");
			List<XmlClass> xmlClasses = new ArrayList<>();
			for (String className : methods.keySet()) {
				if (className.contains("Test")) {
					xmlClasses.add(createClass(className, methods.get(className)));
				}
			}
			test.setXmlClasses(xmlClasses);
		}
		return suite;
	}

	public XmlSuite constructXmlSuiteForDistribution(Map<String, List<Method>> methods,int deviceCount) {
		XmlSuite suite = new XmlSuite();
		suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.CLASSES);
		suite.setVerbose(2);
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
		//clazz.setIncludedMethods(constructIncludes(methods));
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
			List<Method> methodsList = testsMap.get(method.getDeclaringClass().getPackage().getName() + "." + method.getDeclaringClass().getSimpleName());
			if (methodsList == null) {
				methodsList = new ArrayList<>();
				testsMap.put(method.getDeclaringClass().getPackage().getName() + "." + method.getDeclaringClass().getSimpleName(), methodsList);
			}
			methodsList.add(method);
		});
		return testsMap;
	}

}
