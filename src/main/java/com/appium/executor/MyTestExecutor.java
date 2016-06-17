package com.appium.executor;

import com.appium.cucumber.report.HtmlReporter;
import com.appium.manager.PackageUtil;
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
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class MyTestExecutor {
    List<Thread> threads = new ArrayList<>();
    public static Properties prop = new Properties();
    public List<Class> testCases = new ArrayList<>();
    public HtmlReporter reporter = new HtmlReporter();
    public ArrayList<String> items = new ArrayList<>();

    @SuppressWarnings("rawtypes") public void distributeTests(int deviceCount) {
        printClasses();
        ExecutorService executorService = Executors.newFixedThreadPool(deviceCount);
        for (final Class testFile : testCases) {
            executorService.submit((Runnable) () -> {
                System.out.println("Running test file: " + testFile.getName());
                runTestCase(testFile);

            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
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
    }

    @SuppressWarnings("rawtypes") public void parallelTests(int deviceCount)
        throws InterruptedException {
        printClasses();
        for (int i = 0; i < deviceCount; i++) {
            Thread t = new Thread(new Runnable() {

                public void run() {
                    // TODO Auto-generated method stub
                    runTests(testCases);
                }

                private void runTests(List<Class> testCases) {
                    for (Class test : testCases) {
                        System.out.println(
                            "*****CurrentRunningThread" + Thread.currentThread().getId() + test);
                        runTestCase(test);
                    }
                }
            });

            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        deleteOutputDirectory();
        try {
            reporter.generateReports();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finally complete");
    }


    public void runMethodParallelAppium(List<String> test, String pack, int deviceCount,
        String executionType) throws Exception {
        URL newUrl;
        List<URL> newUrls = new ArrayList<>();
        Collections.addAll(items, pack.split("\\s*,\\s*"));
        if (items.size() == 1) {
            Collection<URL> urls = ClasspathHelper.forPackage(items.get(0));
            Iterator<URL> iter = urls.iterator();
            URL url = iter.next();
            urls.clear();
            newUrl = new URL(url.toString() + items.get(0).replaceAll("\\.", "/"));
            newUrls = Lists.newArrayList(newUrl);
        } else if (items.size() > 1) {
            int a = 0;
            Collection<URL> urls = ClasspathHelper.forPackage(items.get(a));
            Iterator<URL> iter = urls.iterator();
            URL url = iter.next();
            urls.clear();
            for (String item : items) {
                newUrl = new URL(url.toString() + item.replaceAll("\\.", "/"));
                newUrls.add(newUrl);
                a++;
            }
        }


        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(newUrls)
            .setScanners(new MethodAnnotationsScanner()));
        Set<Method> resources =
            reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
        if (executionType.equalsIgnoreCase("distribute")) {
            runMethodParallel(
                constructXmlSuiteForDistribution(pack, test, createTestsMap(resources),
                    deviceCount), deviceCount);
        } else {
            runMethodParallel(
                constructXmlSuiteForParallel(pack, test, createTestsMap(resources), deviceCount),
                deviceCount);
        }
        System.out.println("Finally complete");
        ImageUtils.createResultsSet();
    }

    public static void testRunnerTestNg(@SuppressWarnings("rawtypes") Class arg) {
        TestNG test = new TestNG();
        test.setTestClasses(new Class[] {arg});
        System.out.println("Into TestNGRunner");
        test.run();
    }

    public void runMethodParallel(XmlSuite suite, int threadCount) {
        TestNG testNG = new TestNG();
        testNG.setXmlSuites(asList(suite));
        System.out.println(suite.toXml());
        testNG.run();
    }

    public XmlSuite constructXmlSuiteForParallel(String pack, List<String> testCases,
        Map<String, List<Method>> methods, int deviceCount) {
        ArrayList<String> listeners = new ArrayList<>();
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        XmlSuite suite = new XmlSuite();
        listeners.add("com.appium.manager.AppiumParallelTest");
        listeners.add("com.appium.utils.RetryListener");
        if (prop.getProperty("LISTENERS") != null) {
            Collections.addAll(listeners, prop.getProperty("LISTENERS").split("\\s*,\\s*"));
        }

        suite.setName("TestNG Forum");
        suite.setThreadCount(deviceCount);
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
            List<XmlClass> xmlClasses = new ArrayList<>();
            for (String className : methods.keySet()) {
                if (className.contains("Test")) {
                    if (testCases.size() == 0) {
                        xmlClasses.add(createClass(className, methods.get(className)));
                    } else {
                        for (String s : testCases) {
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
            test.setXmlClasses(xmlClasses);
        }
        return suite;
    }

    public XmlSuite constructXmlSuiteForDistribution(String pack, List<String> tests,
        Map<String, List<Method>> methods, int deviceCount) {
        ArrayList<String> listeners = new ArrayList<>();
        try {
            prop.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (prop.getProperty("LISTENERS") != null) {
            Collections.addAll(listeners, prop.getProperty("LISTENERS").split("\\s*,\\s*"));
        }
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
        List<XmlClass> xmlClasses = new ArrayList<>();
        for (String className : methods.keySet()) {
            if (className.contains("Test")) {
                if (tests.size() == 0) {
                    xmlClasses.add(createClass(className, methods.get(className)));
                } else {
                    for (String s : tests) {
                        for (int i = 0; i < items.size(); i++) {
                            String testName = items.get(i).concat("." + s).toString();
                            if (testName.equals(className)) {
                                xmlClasses.add(createClass(className, methods.get(className)));
                            }
                        }
                    }
                }

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
        return methods.stream().map(m -> new XmlInclude(m.getName())).collect(Collectors.toList());
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
        assert files != null;
        for (File file : files) {
            file.delete();
        }
    }

    public void printClasses(){
        try {
            PackageUtil.getClasses("output").stream().forEach(s -> {
                if (s.toString().contains("IT")) {
                    System.out.println("forEach: " + testCases.add((Class) s));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
