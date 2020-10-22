package com.appium.executor;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import java.lang.reflect.Method;
import java.util.*;

public class ATDExecutorTest {
    ATDExecutor ATDExecutor;

    @BeforeSuite
    public void setUp() {
        ATDExecutor = new ATDExecutor();
    }

    @Test
    public void constructXmlSuiteForClassLevelDistributionRunnerTest() {
        Set<Method> methods = new HashSet<>();
        ArrayList<String> devices = new ArrayList<>();
        devices.add("192.168.0.1");
        devices.add("192.168.0.2");
        devices.add("192.168.0.3");
        devices.add("192.168.0.4");
        Method[] thizMethods = ATDExecutorTest.class.getMethods();
        Collections.addAll(methods, thizMethods);

        Method[] otherMethods = OtherTests.class.getMethods();
        Collections.addAll(methods, otherMethods);

        Method[] otherMethods1 = OtherTests1.class.getMethods();
        Collections.addAll(methods, otherMethods1);
        List<String> tc = new ArrayList<>();

        String suiteName = "TestNG Forum";
        String category = "TestNG Test";

        XmlSuite xmlSuite = ATDExecutor.constructXmlSuiteForClassLevelDistributionRunner(
                        tc, ATDExecutor.getTestMethods(methods),
                        suiteName, category,
                        devices.size());
        System.out.println("xml:" + xmlSuite.toXml());
        Assert.assertTrue(true);
    }

    @Test
    public void constructXmlSuiteForMethodLevelDistributionRunnerTest() {
        Set<Method> methods = new HashSet<>();
        ArrayList<String> devices = new ArrayList<>();
        devices.add("192.168.0.1");
        devices.add("192.168.0.2");
        devices.add("192.168.0.3");
        devices.add("192.168.0.4");
        Method[] thizMethods = ATDExecutorTest.class.getMethods();
        Collections.addAll(methods, thizMethods);

        Method[] otherMethods = OtherTests.class.getMethods();
        Collections.addAll(methods, otherMethods);

        Method[] otherMethods1 = OtherTests1.class.getMethods();
        Collections.addAll(methods, otherMethods1);
        List<String> testCases = new ArrayList<>();

        String suiteName = "TestNG Forum";
        String category = "TestNG Test";

        XmlSuite xmlSuite = ATDExecutor.constructXmlSuiteForMethodLevelDistributionRunner(
                        testCases, ATDExecutor.getTestMethods(methods),
                        suiteName, category,
                        devices.size());
        System.out.println("xml:" + xmlSuite.toXml());
        Assert.assertTrue(true);
    }

    @Test
    public void constructXmlSuiteForParallelRunnerTest() {
        Set<Method> methods = new HashSet<>();
        ArrayList<String> devices = new ArrayList<>();
        devices.add("192.168.0.1");
        devices.add("192.168.0.2");
        devices.add("192.168.0.3");
        devices.add("192.168.0.4");
        Method[] thizMethods = ATDExecutorTest.class.getMethods();
        Collections.addAll(methods, thizMethods);

        Method[] otherMethods = OtherTests.class.getMethods();
        Collections.addAll(methods, otherMethods);

        Method[] otherMethods1 = OtherTests1.class.getMethods();
        Collections.addAll(methods, otherMethods1);
        List<String> testCases = new ArrayList<>();

        String suiteName = "TestNG Forum";
        String categoryName = "TestNG Test";

        XmlSuite xmlSuite = ATDExecutor.constructXmlSuiteForParallelRunner(testCases,
                ATDExecutor.getTestMethods(methods),
                suiteName, categoryName,
                devices.size());

        System.out.println("xml:" + xmlSuite.toXml());
        Assert.assertTrue(true);
    }
}
