package com.appium.executor;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.io.IOUtils.toInputStream;
import static org.testng.Assert.assertTrue;

public class ATDExecutorTest {
    ATDExecutor ATDExecutor;
    DocumentBuilder db;
    Set<Method> methods;
    String SUITE_NAME = "TestNG Forum";
    String CATEGORY_NAME = "TestNG Test";

    @BeforeSuite
    public void setUp() {
        String cloudName = "Local";
        /*appiumDeviceList.add(new AppiumDevice(new Device(), "10.10.10.10", cloudName));
        appiumDeviceList.add(new AppiumDevice(new Device(), "10.10.10.10", cloudName));
        appiumDeviceList.add(new AppiumDevice(new Device(), "10.10.10.10", cloudName));
        appiumDeviceList.add(new AppiumDevice(new Device(), "10.10.10.10", cloudName));*/

        //deviceAllocationManager = Mockito.mock(Device.class);
        // ATDExecutor = new ATDExecutor(deviceAllocationManager);
    }

    @BeforeMethod
    public void clear() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setCoalescing(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setIgnoringComments(true);
        db = documentBuilderFactory.newDocumentBuilder();

        methods = new HashSet<>();
        Method[] thisMethods = ATDExecutorTest.class.getMethods();
        Collections.addAll(methods, thisMethods);

        Method[] otherMethods = OtherTests.class.getMethods();
        Collections.addAll(methods, otherMethods);

        Method[] otherMethods1 = OtherTests1.class.getMethods();
        Collections.addAll(methods, otherMethods1);
    }

    @Test
    public void constructXmlSuiteForClassLevelDistributionRunnerTest() throws IOException,
            SAXException {
        XmlSuite xmlSuite = ATDExecutor.constructXmlSuiteForClassLevelDistributionRunner(
                new ArrayList<>(), ATDExecutor.getTestMethods(methods),
                SUITE_NAME, CATEGORY_NAME,4);

        Document document1 = db.parse(
                new File("./src/test/resources/XMLSuiteForClassLevelDistribution.xml"));
        document1.normalizeDocument();

        Document document2 = db.parse(toInputStream((xmlSuite.toXml())));
        document2.normalizeDocument();

        assertTrue(document1.isEqualNode(document2));
    }

    @Test
    public void constructXmlSuiteForMethodLevelDistributionRunnerTest() throws IOException,
            SAXException {
        XmlSuite xmlSuite = ATDExecutor.constructXmlSuiteForMethodLevelDistributionRunner(
                new ArrayList<>(), ATDExecutor.getTestMethods(methods),
                SUITE_NAME, CATEGORY_NAME, 4);

        Document document1 = db.parse(
                new File("./src/test/resources/XMLSuiteForMethodLevelDistribution.xml"));
        document1.normalizeDocument();

        Document document2 = db.parse(toInputStream((xmlSuite.toXml())));
        document2.normalizeDocument();

        assertTrue(document1.isEqualNode(document2));
    }

    @Test
    public void constructXmlSuiteForParallelRunnerTest() throws IOException,
            SAXException {
        XmlSuite xmlSuite = ATDExecutor.constructXmlSuiteForParallelRunner(new ArrayList<>(),
                ATDExecutor.getTestMethods(methods),
                SUITE_NAME, CATEGORY_NAME, 4);

        Document document1 = db.parse(
                new File("./src/test/resources/XMLSuiteForParallelRunner.xml"));
        document1.normalizeDocument();

        Document document2 = db.parse(toInputStream((xmlSuite.toXml())));
        document2.normalizeDocument();

        assertTrue(document1.isEqualNode(document2));
    }
}
