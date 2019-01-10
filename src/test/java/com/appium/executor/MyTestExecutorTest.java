package com.appium.executor;



import com.appium.filelocations.FileLocations;
import com.appium.device.HostMachineDeviceManager;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTestExecutorTest {
    MyTestExecutor ex1 ;

    public MyTestExecutorTest() throws Exception {
        ex1 = new MyTestExecutor();
    }

    public void testXmlSuiteCreation() {
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

        String suiteName = "TestNG Forum";
        String category = "TestNG Test";

        XmlSuite xmlSuite =
                ex1.constructXmlSuiteForDistribution(
                        tc, ex1.createTestsMap(methods),
                        suiteName, category,
                        devices.size());
        System.out.println("xml:" + xmlSuite.toXml());
        Assert.assertTrue(true);
    }


    @Test public void testXmlSuiteCreationForMethodParallelCucumber() throws IOException {
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

        String suiteName = "TestNG Forum";
        String category = "TestNG Test";

        XmlSuite xmlSuite =
                ex1.constructXmlSuiteForDistributionMethods(
                        tc, ex1.createTestsMap(methods),
                        suiteName, category,
                        devices.size());
        System.out.println("xml:" + xmlSuite.toXml());
        Assert.assertTrue(true);
    }

    @Test public void testXmlSuiteCreationForMethodParallel() throws IOException {
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

        String suiteName = "TestNG Forum";
        String category = "TestNG Test";

        XmlSuite xmlSuite = ex1.constructXmlSuiteForParallel("com.appium.executor",
                tc, ex1.createTestsMap(methods),
                suiteName, category,
                devices.size(), HostMachineDeviceManager.getInstance()
                        .getDevicesByHost().getAllDevices());

        System.out.println("xml:" + xmlSuite.toXml());
        Assert.assertTrue(true);
    }

    public void testXmlSuiteCreationCucumber() throws IOException {
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
        XmlSuite xmlSuite =
            ex1.constructXmlSuiteForParallelCucumber(devices.size(),
                    HostMachineDeviceManager.getInstance().getDevicesByHost().getAllDevices());
        System.out.println("xml:" + xmlSuite.toXml());
        File file = new File(System.getProperty("user.dir") + FileLocations.PARALLEL_XML_LOCATION);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(xmlSuite.toXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }
}
