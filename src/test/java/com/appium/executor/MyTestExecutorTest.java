package com.appium.executor;


import static junit.framework.TestCase.assertTrue;

import org.junit.Test;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTestExecutorTest {
    MyTestExecutor ex1 = new MyTestExecutor();

    @Test public void testXmlSuiteCreation() {
        Set<Method> methods = new HashSet<>();
        ArrayList<String> devices = new ArrayList<>();
        devices.add("192.168.0.1");
        devices.add("192.168.0.2");
        devices.add("192.168.0.3");
        devices.add("192.168.0.4");
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
            ex1.constructXmlSuiteForDistribution("com.appium.executor",
                tc, ex1.createTestsMap(methods),
                devices.size());
        System.out.println("xml:" + xmlSuite.toXml());
        assertTrue(true);
    }
}
