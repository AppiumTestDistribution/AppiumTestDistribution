---
title: TestNG
---

1. Main Runnerclass should look as below:

```java
/** Run lists of tests from package **/
public class Runner {

    @Test
    public void testApp() throws Exception {
        ATDRunner atdRunner = new ATDRunner();
        //atdRunner.runner(package_name_where_test_located);
        atdRunner.runner("com.paralle.tests");
    }
}

/** Run lists of tests **/
@Test
public void testApp() throws Exception {
  ATDRunner parallelThread = new ATDRunner();
  List<String> tests = new ArrayList<>();
  tests add("HomePageTest2");
  tests add("HomePageTest3");
  parallelThread.runner("com.test.site",tests);
}

/** Run lists of tests from mulitple packages **/
@Test
public void testApp() throws Exception {
  ATDRunner parallelThread = new ATDRunner();
  List<String> tests = new ArrayList<>();
  tests.add("HomePageTest2");
  tests.add("HomePageTest3");
  parallelThread.runner("com.test.site,com.ios.test",tests);
}

/** Run test against certain devices connected **/
command to start runner
UDIDS=udid1,udid2,udid3 mvn clean -Dtest=RunnerCukes test

public class RunnerCukes {
    @Test
    public void testCukesRunner() throws Exception {
        String udidParam = System.getProperty("udid");
        String[] parts = udidParam.split(",");
        List<String> deviceList = Arrays.asList(parts);
        ParallelThread cucumberParallelThread = new ParallelThread(deviceList);
        boolean hasFailures = cucumberParallelThread.runner("output");
        Assert.assertFalse(hasFailures, "Testcases have failed in parallel execution");
    }
}
```

2. Create `config.properties` file under your test directory, which should have below properties.

   ```
   RUNNER=distribute
   FRAMEWORK=testng/cucumber
   LISTENERS=listerner2,listerner2 (user can add custom listeners here, comma separated)
   MAX_RETRY_COUNT=2 (Provide any retry count on failures, this is applied to all tests globally)


   ## Default path to capability json is root/caps/capabilities.json if the location of the capabilities.json is changed make sure you mention as below
   CAPS=relative/absolute
   SUITE_NAME=testApp
   CATEGORY=sanity
   ```

3. If you want to specify android/iOS capabilities
   (https://github.com/saikrishna321/PageObjectPatternAppium/tree/master/caps)

### Run Test from CommandLine

```
Platform="android/ios/both" mvn clean -Dtest=Runner test
```

Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)

If your application is cross-platform and you end up building a PageObjectPattern Framework. Then you can also run those tests across Android and iOS devices connected to the same Mac OSX Host.

(e.g. If you have 3 Android and 3 iOS devices connected to the same machine, you will be able to execute these tests parallel on both platforms)
