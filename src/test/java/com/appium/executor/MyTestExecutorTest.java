package com.appium.executor;

import org.junit.Test;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class MyTestExecutorTest {

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

        MyTestExecutor ex1 = new MyTestExecutor();
        XmlSuite xmlSuite = ex1.constructXmlSuite(methods);
        System.out.println("xml:" + xmlSuite.toXml());
        assertTrue(true);
    }

}