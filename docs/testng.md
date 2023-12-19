---
title: TestNG
---

### Main Runnerclass should look as below:

```java
/** Run the test based on groups **/
public class Runner {

   @Test
   public void testApp() throws Exception {
      ATDRunner atdRunner = new ATDRunner();
      //atdRunner.runner(package_name_where_test_located);
      atdRunner.runner("com.parallel.tests");
   }
}

/** Run specific list of test cases present in different test classes **/
In the below example, testcase 'verifyHeaderAndFooter' present in HomePage class and testcase 'verfiyMyProfileDetails' present in ProfilePage class has been included for execution. Here testCase is a argument passed through system property.

@Test
public void testApp() throws Exception {
        ATDRunner parallelThread = new ATDRunner();
        List<String> testCases = new ArrayList<>();
        testCases.add("verifyHeaderAndFooter");
        testCases.add("verfiyProfileDetails");
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

```

### Set config properties

Create `config.properties` file under your test directory, which should have below properties.

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

### Set capabilities
If you want to specify android/iOS capabilities
   (https://github.com/saikrishna321/PageObjectPatternAppium/tree/master/caps)

### Run Test from CommandLine

```
Platform="android/ios" mvn clean -Dtest=Runner test
```
