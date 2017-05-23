package com.appium.executor;

import com.appium.cucumber.report.HtmlReporter;
import com.appium.manager.ConfigFileManager;
import com.appium.manager.DeviceAllocationManager;
import com.appium.manager.PackageUtil;
import com.appium.manager.ParallelThread;
import com.appium.utils.ImageUtils;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.testng.TestNG;
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
import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MyTestExecutor {
    private final ConfigFileManager prop;
    private final DeviceAllocationManager deviceAllocationManager;
    List<Thread> threads = new ArrayList<Thread>();
    public List<Class> testcases = new ArrayList<>();
    public HtmlReporter reporter = new HtmlReporter();
    public ArrayList<String> items = new ArrayList<String>();
    private ArrayList<String> listeners = new ArrayList<>();
    private ArrayList<String> groupsInclude = new ArrayList<>();
    private ArrayList<String> groupsExclude = new ArrayList<>();

    public MyTestExecutor() throws IOException {
        deviceAllocationManager = DeviceAllocationManager.getInstance();
        prop = ConfigFileManager.getInstance();
    }

    @SuppressWarnings("rawtypes")

    public boolean[] distributeTests(int deviceCount) {
        final boolean[] hasFailures = {false};
        try {
            PackageUtil.getClasses("output").stream().forEach(s -> {
                if (s.toString().contains("IT")) {
                    System.out.println("forEach: " + testcases.add((Class) s));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        final ExecutorService[] executorService = {Executors.newFixedThreadPool(deviceCount)};
        for (final Class testFile : testcases) {
            executorService[0].submit(new Runnable() {
                public void run() {
                    System.out.println("Running test file: " + testFile.getName());
                    hasFailures[0] = testRunnerTestNg(testFile);
                }
            });
        }
        executorService[0].shutdown();
        try {
            executorService[0].awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deleteOutputDirectory();
        try {
            reporter.generateReports();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ending");
        return hasFailures;
    }

    public boolean runMethodParallelAppium(List<String> test, String pack, int devicecount,
                                           String executionType) throws Exception {
        URL newUrl = null;
        List<URL> newUrls = new ArrayList<>();
        Collections.addAll(items, pack.split("\\s*,\\s*"));
        int a = 0;
        Collection<URL> urls = ClasspathHelper.forPackage(items.get(a));
        Iterator<URL> iter = urls.iterator();
        URL url = iter.next();
        urls.clear();
        for (int i = 0; i < items.size(); i++) {
            newUrl = new URL(url.toString() + items.get(i).replaceAll("\\.", "/"));
            newUrls.add(newUrl);
            a++;

        }
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(newUrls)
            .setScanners(new MethodAnnotationsScanner()));
        Set<Method> resources =
            reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
        boolean hasFailure;
        if (executionType.equalsIgnoreCase("distribute")) {
            constructXmlSuiteForDistribution(pack, test, createTestsMap(resources),
                    devicecount);
            hasFailure = runMethodParallel();
        } else {
            constructXmlSuiteForParallel(pack, test, createTestsMap(resources), devicecount,
                    deviceAllocationManager.getDevices());
            hasFailure = runMethodParallel();
        }
        System.out.println("Finally complete");
        ParallelThread.figlet("Test Completed");
        ImageUtils.creatResultsSet();
        //ImageUtils.createJSonForHtml();
        return hasFailure;
    }

    public boolean testRunnerTestNg(@SuppressWarnings("rawtypes") Class arg) {
        TestNG test = new TestNG();
        test.setTestClasses(new Class[] {arg});
        System.out.println("Into TestNGRunner");
        test.run();
        return test.hasFailure();

    }

    public boolean runMethodParallel() {
        TestNG testNG = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add(System.getProperty("user.dir") + "/target/parallel.xml");
        testNG.setTestSuites(suites);
        testNG.run();
        return testNG.hasFailure();
    }

    public XmlSuite constructXmlSuiteForParallel(String pack, List<String> testcases,
        Map<String, List<Method>> methods, int deviceCount, ArrayList<String> deviceSerail) {
        ArrayList<String> listeners = new ArrayList<>();
        listeners.add("com.appium.manager.AppiumParallelTest");
        listeners.add("com.appium.utils.RetryListener");
        include(listeners, "LISTENERS");
        include(groupsInclude, "INCLUDE_GROUPS");
        include(groupsExclude, "EXCLUDE_GROUPS");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.TESTS);
        suite.setVerbose(2);
        suite.setListeners(listeners);
//        if (prop.getProperty("LISTENERS") != null) {
//            suite.setListeners(listeners);
//        }
        for (int i = 0; i < deviceCount; i++) {
            XmlTest test = new XmlTest(suite);
            test.setName("TestNG Test" + i);
            test.setPreserveOrder("false");
            test.addParameter("device", deviceSerail.get(i));
            test.setIncludedGroups(groupsInclude);
            test.setExcludedGroups(groupsExclude);
            List<XmlClass> xmlClasses = new ArrayList<>();
            writeXmlClass(testcases, methods, xmlClasses);
            test.setXmlClasses(xmlClasses);
        }
        System.out.println(suite.toXml());
        writeTestNGFile(suite);
        return suite;
    }

    public void writeXmlClass(List<String> testcases, Map<String,
            List<Method>> methods, List<XmlClass> xmlClasses) {
        for (String className : methods.keySet()) {
            if (className.contains("Test")) {
                if (testcases.size() == 0) {
                    xmlClasses.add(createClass(className, methods.get(className)));
                } else {
                    for (String s : testcases) {
                        for (int j = 0; j < items.size(); j++) {
                            String testName = items.get(j).concat("." + s).toString();
                            if (testName.equals(className)) {
                                xmlClasses.add(createClass(className, methods.get(className)));
                            }
                        }
                    }
                }

            }
        }
    }

    public XmlSuite constructXmlSuiteForDistribution(String pack, List<String> tests,
        Map<String, List<Method>> methods, int deviceCount) {
        include(listeners, "LISTENERS");
        include(groupsInclude, "INCLUDE_GROUPS");
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.CLASSES);
        suite.setVerbose(2);
        listeners.add("com.appium.manager.AppiumParallelTest");
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

    private void writeTestNGFile(XmlSuite suite) {
        try {
            FileWriter writer = new FileWriter(new File(
                    System.getProperty("user.dir") + "/target/parallel.xml"));
            writer.write(suite.toXml());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void include(ArrayList<String> groupsInclude, String include) {
        if (prop.getProperty(include) != null) {
            Collections.addAll(groupsInclude, prop.getProperty(include).split("\\s*,\\s*"));
        } else if (System.getenv(include) != null) {
            Collections.addAll(groupsInclude, System.getenv(include).split("\\s*,\\s*"));
        }
    }


    private XmlClass createClass(String className, List<Method> methods) {
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
            List<Method> methodsList = testsMap.get(
                method.getDeclaringClass().getPackage().getName() + "." + method.getDeclaringClass()
                    .getSimpleName());
            if (methodsList == null) {
                methodsList = new ArrayList<>();
                testsMap.put(method.getDeclaringClass().getPackage().getName() + "." + method
                    .getDeclaringClass().getSimpleName(), methodsList);
            }
            methodsList.add(method);
        });
        return testsMap;
    }

    public void runTestCase(Class testCase) {
        Result result = JUnitCore.runClasses(testCase);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

    public void deleteOutputDirectory() {
        File delete_output = new File(System.getProperty("user.dir") + "/src/test/java/output/");
        File[] files = delete_output.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    public XmlSuite constructXmlSuiteForParallelCucumber(
        int deviceCount, ArrayList<String> deviceSerail) {
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.TESTS);
        suite.setVerbose(2);
        for (int i = 0; i < deviceCount; i++) {
            XmlTest test = new XmlTest(suite);
            test.setName("TestNG Test" + i);
            test.setPreserveOrder("false");
            test.addParameter("device", deviceSerail.get(i));
            test.setPackages(getPackages());
        }
        File file = new File(System.getProperty("user.dir") + "/target/parallelCucumber.xml");
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
            int deviceCount, ArrayList<String> deviceSerail) {
        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
        suite.setParallel(ParallelMode.CLASSES);
        suite.setVerbose(2);
        XmlTest test = new XmlTest(suite);
        test.setName("TestNG Test");
        test.addParameter("device", "");
        test.setPackages(getPackages());
        File file = new File(System.getProperty("user.dir") + "/target/parallelCucumber.xml");
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

    public static List<XmlPackage> getPackages() {
        List<XmlPackage> allPackages = new ArrayList<>();
        XmlPackage eachPackage = new XmlPackage();
        eachPackage.setName("output");
        allPackages.add(eachPackage);
        return allPackages;
    }

}
