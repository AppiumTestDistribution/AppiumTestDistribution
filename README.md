# Appium Parallel Execution for Android

<h2>Run Appium Android test in parallel across devices connected</h2>

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/image.jpg)

Add the below dependencies in your pom.xml

```
<dependency>
	<groupId>com.github.saikrishna321</groupId>
	<artifactId>AppiumTestDistribution</artifactId>
	<version>3.0.0</version>
</dependency>
```

```
<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>
```
<h2>Sample Tests</h2>
 Clone the project (https://github.com/saikrishna321/AppiumTestDistributionExample)
<h1>Configure tests</h1>

Main Runnerclass should look as below :: 

```
public class Runner {
    
	@Test
	public void testApp() throws Exception {
		ParallelThread parallelThread = new ParallelThread();
		parallelThread.runner("com.paralle.tests");

	}
}

```

1. Extend your tests to BaseTest which is part of the dependencies, which takes care of running the appium server session in parallel threads.
    
<h3>Run Test from CommandLine</h3>
```
mvn clean -Dtest=Runner test

```   

2.Create a config.properties under your test directory, which hold the below properties 

```
APP_PATH=/Users/saikrisv/Documents/workspace/TestNGParallel/build/AndroidCalculator.apk
APP_PACKAGE=com.android2.calculator3
APP_ACTIVITY=com.android2.calculator3.Calculator
RUNNER=distribute
APPIUM_JS_PATH=/usr/local/lib/node_modules/appium/bin/appium.js

```

Note: 

* Provide the absolute path of the apk file.
* APPIUM_JS_PATH should be the location of the appium.js in yout location machine
* RUNNER option can be parallel/distribute(Parallel will run the all the tests across device- which helps to get the device coverage/ Distribute will distribute all the tests across devices)


<h3>Reports</h3>

Your should see report file generated as ExtentReport.html


