package com.appium.executor;

import static com.appium.utils.FigletHelper.figlet;

import com.appium.filelocations.FileLocations;
import com.appium.utils.ConfigFileManager;
import com.appium.manager.DeviceAllocationManager;
import com.appium.manager.AppiumDevice;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jakewharton.fliptables.FlipTableConverters;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.collections.Lists;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class MyTestExecutor {

    private final ConfigFileManager prop;
    private final DeviceAllocationManager deviceAllocationManager;
    private ArrayList<String> items = new ArrayList<String>();
    private ArrayList<String> listeners = new ArrayList<>();
    private ArrayList<String> groupsInclude = new ArrayList<>();
    private ArrayList<String> groupsExclude = new ArrayList<>();

    public MyTestExecutor() throws Exception {
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        prop = ConfigFileManager.getInstance();
    }

    public boolean runMethodParallelAppium(List<String> test, String pack, int devicecount,
                                           String executionType) throws Exception {
        Set<Method> resources = getMethods(pack);
        boolean hasFailure;
        dryRunTestInfo(resources);
        String runnerLevel = System.getenv("RUNNER_LEVEL") != null
                ? System.getenv("RUNNER_LEVEL") : prop.getProperty("RUNNER_LEVEL");

        if (executionType.equalsIgnoreCase("distribute")) {
            if (runnerLevel != null
                    && runnerLevel.equalsIgnoreCase("class")) {
                constructXmlSuiteForDistribution(test, createTestsMap(resources),
                        devicecount);
            } else {
                constructXmlSuiteForDistributionMethods(test, createTestsMap(resources),
                        devicecount);
            }

            hasFailure = runMethodParallel();
        } else {
            constructXmlSuiteForParallel(pack, test, createTestsMap(resources), devicecount,
                    deviceAllocationManager.getDevices());
            hasFailure = runMethodParallel();
        }
        System.out.println("Finally complete");
        figlet("Test Completed");
        return hasFailure;
    }

    private Set<Method> getMethods(String pack) throws MalformedURLException {
        URL newUrl;
        List<URL> newUrls = new ArrayList<>();
        Collections.addAll(items, pack.split("\\s*,\\s*"));
        int a = 0;
        Collection<URL> urls = ClasspathHelper.forPackage(items.get(a));
        Iterator<URL> iter = urls.iterator();

        URL url = null;

        while (iter.hasNext()) {
            url = iter.next();
            if (url.toString().contains("test-classes")) {
                break;
            }
        }
        for (String item : items) {
            newUrl = new URL(url.toString() + item.replaceAll("\\.", "/"));
            newUrls.add(newUrl);
            a++;

        }
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(newUrls)
                .setScanners(new MethodAnnotationsScanner()));
        return reflections.getMethodsAnnotatedWith(Test.class);
    }

    private void dryRunTestInfo(Set<Method> resources) {
        if (System.getProperty("dry-run") != null) {
            Multimap<String, HashMap> dryRunResults = ArrayListMultimap.create();
            resources.forEach(method -> {
                HashMap<String, String> methodAndGroups = new HashMap<>();
                String className = method.getDeclaringClass().getSimpleName();
                String methodName = method.getName();
                String[] groups = method.getAnnotation(Test.class).groups();
                methodAndGroups.put(methodName, Arrays.toString(groups));
                dryRunResults.put(className, methodAndGroups);
            });

            List<TestNGTestInfo> testInfo = new ArrayList<>();
            for (Map.Entry<String, HashMap> stringObjectEntry : dryRunResults.entries()) {
                stringObjectEntry.getValue().forEach((key, value) ->
                        testInfo.add(new TestNGTestInfo(stringObjectEntry.getKey(),
                                key.toString(), value.toString())));
            }
            System.out.println(FlipTableConverters.fromIterable(testInfo, TestNGTestInfo.class));
            System.exit(1);
        }

    }


    public boolean runMethodParallel() {
        TestNG testNG = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add(System.getProperty("user.dir") + FileLocations.PARALLEL_XML_LOCATION);
        testNG.setTestSuites(suites);
        testNG.run();
        return testNG.hasFailure();
    }

    public XmlSuite constructXmlSuiteForParallel(String pack, List<String> testcases,
                                                 Map<String, List<Method>> methods,
                                                 int deviceCount,
                                                 List<AppiumDevice> deviceSerail) {
        ArrayList<String> listeners = new ArrayList<>();
        listeners.add("com.appium.manager.AppiumParallelTestListener");
        listeners.add("com.appium.utils.RetryListener");
        include(listeners, "LISTENERS");
        include(groupsInclude, "INCLUDE_GROUPS");
        include(groupsExclude, "EXCLUDE_GROUPS");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setDataProviderThreadCount(deviceCount);
        suite.setParallel(ParallelMode.TESTS);
        suite.setVerbose(2);
        suite.setListeners(listeners);
        if (prop.getProperty("LISTENERS") != null) {
            suite.setListeners(listeners);
        }
        for (int i = 0; i < deviceCount; i++) {
            XmlTest test = new XmlTest(suite);
            test.setName("TestNG Test" + i);
            test.setPreserveOrder("false");
            test.addParameter("device", deviceSerail.get(i).getDevice().getUdid());
            test.addParameter("hostName", deviceSerail.get(i).getHostName());
            test.setIncludedGroups(groupsInclude);
            test.setExcludedGroups(groupsExclude);
            List<XmlClass> xmlClasses = new ArrayList<>();
            writeXmlClass(testcases, methods, xmlClasses);
            test.setXmlClasses(xmlClasses);
        }
        writeTestNGFile(suite);
        return suite;
    }

    private List<XmlClass> writeXmlClass(List<String> testcases, Map<String,
            List<Method>> methods, List<XmlClass> xmlClasses) {
        for (String className : methods.keySet()) {
            if (className.contains("Test")) {
                if (testcases.size() == 0) {
                    xmlClasses.add(createClass(className));
                } else {
                    for (String s : testcases) {
                        for (int j = 0; j < items.size(); j++) {
                            String testName = items.get(j).concat("." + s).toString();
                            if (testName.equals(className)) {
                                xmlClasses.add(createClass(className));
                            }
                        }
                    }
                }

            }
        }
        return xmlClasses;
    }

    public XmlSuite constructXmlSuiteForDistribution(List<String> tests,
                                                     Map<String, List<Method>> methods,
                                                     int deviceCount) {
        include(listeners, "LISTENERS");
        include(groupsInclude, "INCLUDE_GROUPS");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.CLASSES);
        suite.setVerbose(2);
        listeners.add("com.appium.manager.AppiumParallelMethodTestListener");
        listeners.add("com.appium.utils.RetryListener");
        suite.setListeners(listeners);
        if (prop.getProperty("LISTENERS") != null) {
            suite.setListeners(listeners);
        }
        XmlTest test = new XmlTest(suite);
        test.setName("TestNG Test");
        test.addParameter("device", "");
        include(groupsExclude, "EXCLUDE_GROUPS");
        test.setIncludedGroups(groupsInclude);
        test.setExcludedGroups(groupsExclude);
        List<XmlClass> xmlClasses = new ArrayList<>();
        writeXmlClass(tests, methods, xmlClasses);
        test.setXmlClasses(xmlClasses);
        writeTestNGFile(suite);
        return suite;
    }


    public XmlSuite constructXmlSuiteForDistributionMethods(List<String> tests,
                                                            Map<String, List<Method>> methods,
                                                            int deviceCount) {
        include(listeners, "LISTENERS");
        include(groupsInclude, "INCLUDE_GROUPS");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setDataProviderThreadCount(deviceCount);
        suite.setVerbose(2);
        suite.setParallel(ParallelMode.METHODS);
        listeners.add("com.appium.manager.AppiumParallelMethodTestListener");
        listeners.add("com.appium.utils.RetryListener");
        suite.setListeners(listeners);
        if (prop.getProperty("LISTENERS") != null) {
            suite.setListeners(listeners);
        }
        List<XmlClass> xmlClasses = new ArrayList<>();
        xmlClasses = writeXmlClass(tests, methods, xmlClasses);
        XmlTest test = new XmlTest(suite);
        test.setName("TestNG Test");
        test.addParameter("device", "");
        include(groupsExclude, "EXCLUDE_GROUPS");
        test.setIncludedGroups(groupsInclude);
        test.setExcludedGroups(groupsExclude);
        List<XmlClass> writeXml = new ArrayList<>();
        for (int i = 0; i < xmlClasses.size(); i++) {
            writeXml.add(new XmlClass(xmlClasses.get(i).getName()));
            test.setClasses(writeXml);
        }
        writeTestNGFile(suite);
        return suite;
    }

    private void writeTestNGFile(XmlSuite suite) {
        try {
            FileWriter writer = new FileWriter(new File(
                    System.getProperty("user.dir") + FileLocations.PARALLEL_XML_LOCATION));
            writer.write(suite.toXml());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void include(ArrayList<String> groupsInclude, String include) {
        if (prop.getProperty(include) != null) {
            Collections.addAll(groupsInclude, prop.getProperty(include).split("\\s*,\\s*"));
        } else if (System.getenv(include) != null) {
            Collections.addAll(groupsInclude, System.getenv(include).split("\\s*,\\s*"));
        }
    }


    private XmlClass createClass(String className) {
        XmlClass clazz = new XmlClass();
        clazz.setName(className);
        //clazz.setIncludedMethods(constructIncludes(methods));
        return clazz;
    }


    private List<XmlInclude> constructIncludes(List<Method> methods) {
        List<XmlInclude> includes = new ArrayList<>();
        for (Method m : methods) {
            includes.add(new XmlInclude(m.getName()));
        }
        return includes;
    }

    public Map<String, List<Method>> createTestsMap(Set<Method> methods) {
        Map<String, List<Method>> testsMap = new HashMap<>();
        methods.stream().forEach(method -> {
            List<Method> methodsList = testsMap.computeIfAbsent(
                    method.getDeclaringClass().getPackage().getName()
                            + "." + method.getDeclaringClass()
                            .getSimpleName(), k -> new ArrayList<>());
            methodsList.add(method);
        });
        return testsMap;
    }

    private void deleteOutputDirectory() {
        File delete_output = new File(System.getProperty("user.dir")
                + "/src/test/java/output/");
        File[] files = delete_output.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    public XmlSuite constructXmlSuiteForParallelCucumber(
            int deviceCount, List<AppiumDevice> deviceSerail) {
        ArrayList<String> listeners = new ArrayList<>();
        listeners.add("com.cucumber.listener.CucumberListener");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.TESTS);
        suite.setVerbose(2);
        suite.setListeners(listeners);
        for (int i = 0; i < deviceCount; i++) {
            XmlTest test = new XmlTest(suite);
            test.setName("TestNG Test" + i);
            test.setPreserveOrder("false");
            test.addParameter("device", deviceSerail.get(i).getDevice().getUdid());
            test.setPackages(getPackages());
        }
        File file = new File(System.getProperty("user.dir") + FileLocations.PARALLEL_XML_LOCATION);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(suite.toXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suite;
    }

    public XmlSuite constructXmlSuiteDistributeCucumber(
            int deviceCount) {
        ArrayList<String> listeners = new ArrayList<>();
        listeners.add("com.cucumber.listener.CucumberListener");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.CLASSES);
        suite.setVerbose(2);
        suite.setListeners(listeners);
        XmlTest test = new XmlTest(suite);
        test.setName("TestNG Test");
        test.addParameter("device", "");
        test.setPackages(getPackages());
        File file = new File(System.getProperty("user.dir") + FileLocations.PARALLEL_XML_LOCATION);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            bw.write(suite.toXml());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suite;
    }

    private static List<XmlPackage> getPackages() {
        List<XmlPackage> allPackages = new ArrayList<>();
        XmlPackage eachPackage = new XmlPackage();
        eachPackage.setName("output");
        allPackages.add(eachPackage);
        return allPackages;
    }

}
