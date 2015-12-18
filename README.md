# AppiumTestDistribution

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
RUNNER="distribute" APP_PATH="/Users/saikrisv/Documents/workspace/TestNGParallel/build/AndroidCalculator.apk" APP_PACKAGE="com.android2.calculator3" APP_ACTIVITY="com.android2.calculator3.Calculator" mvn clean -Dtest=Runner test

* Please make sure you give the absolute path of the apk
* RUNNER can be set with parallel and distribute.

```   
<h3>Reports</h3>

Your should see report file generated as ExtentReport.html


