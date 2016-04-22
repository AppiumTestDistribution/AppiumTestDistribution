package com.appium.executor;

import org.junit.Test;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class MyTestExecutorTest {
	MyTestExecutor ex1 = new MyTestExecutor();

	@Test
	public void testXmlSuiteCreation() {
		Set<Method> methods = new HashSet<>();

		Method[] thizMethods = MyTestExecutorTest.class.getMethods();
		for (Method m : thizMethods) {
			methods.add(m);
		}

		Method[] otherMethods = OtherTests.class.getMethods();
		for (Method m : otherMethods) {
			methods.add(m);
		}

		XmlSuite xmlSuite = ex1.constructXmlSuiteForParallel(ex1.createTestsMap(methods),3);
		System.out.println("xml:" + xmlSuite.toXml());
		assertTrue(true);
	}

	@Test
	public void testParallelMethods() throws Exception {
		ex1.runMethodParallelAppium("com.appium.executor", 3,"distribute");
	}

}