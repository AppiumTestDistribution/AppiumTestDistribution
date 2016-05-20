package com.appium.executor;

import org.junit.Test;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class MyTestExecutorTest {
    MyTestExecutor ex1 = new MyTestExecutor();

    @Test public void testXmlSuiteCreation() {
        Set<Method> methods = new HashSet<>();

        Method[] thizMethods = MyTestExecutorTest.class.getMethods();
        for (Method m : thizMethods) {
            methods.add(m);
        }

        Method[] otherMethods = OtherTests.class.getMethods();
        for (Method m : otherMethods) {
            methods.add(m);
        }

        Method[] otherMethods1 = OtherTests1.class.getMethods();
        for (Method m : otherMethods1) {
            methods.add(m);
        }
        List<String> tc = new ArrayList<>();

        XmlSuite xmlSuite =
            ex1.constructXmlSuiteForParallel("com.appium.executor", tc, ex1.createTestsMap(methods),
                3);
        System.out.println("xml:" + xmlSuite.toXml());
        assertTrue(true);
    }

    @Test public void testParallelMethods() throws Exception {
        List<String> tc = new ArrayList<>();
        ex1.runMethodParallelAppium(tc, "com.appium.executor", 3, "distribute");
    }

}
