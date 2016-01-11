# Appium Parallel Execution for Android

[![Join the chat at https://gitter.im/saikrishna321/AppiumTestDistribution](https://badges.gitter.im/saikrishna321/AppiumTestDistribution.svg)](https://gitter.im/saikrishna321/AppiumTestDistribution?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

<h2>Run Appium Android test in parallel across devices connected</h2>

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/image.jpg)

Add the below dependencies in your pom.xml

```
<dependency>
	<groupId>com.github.saikrishna321</groupId>
	<artifactId>AppiumTestDistribution</artifactId>
	<version>3.0.3</version>
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

<h2>Prerequisites</h2>

1. Make sure Appium(1.4.16) is installed through node.
2. Make sure adb sdk is set under ANDROID_HOME.
3. Install JDK 1.8 and set under path JAVA_HOME to get this framework working.

<h2>Sample Tests</h2>
 Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)
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

1. Extend your tests to AppiumParallelTest which is part of the dependencies, which takes care of running the appium server session in parallel threads.

```
public class UserBaseTest extends AppiumParallelTest {

	@BeforeMethod()
	public void startApp(Method name) throws Exception {
		startLogResults(name.getName());
	}

	@AfterMethod()
	public void killServer(ITestResult result) {
		endLogTestResults(result);
		getDriver().resetApp();
	}

	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	@BeforeClass()
	public void beforeClass() throws Exception {
		driver = startAppiumServerInParallel(getClass().getSimpleName());
	}

	@AfterClass()
	public void afterClass() throws InterruptedException, IOException {
		System.out.println("After Class" + Thread.currentThread().getId());
		killAppiumServer();
	}
}

```
    
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
BROWSER_TYPE=chrome
APP_TYPE=native

```

Note: 

* Provide the absolute path of the apk file.
* APPIUM_JS_PATH should be the location of the appium.js in yout location machine
* RUNNER option can be parallel/distribute(Parallel will run  all the tests across device- which helps you to get the device coverage/ Distribute will distribute all the tests across devices which helps you faster execution)
* APP_TYPE should be set to "native" to run native/hybrid tests and "web" to run webtests in chrome.
* Make sure you have chrome browser installed on real devices, if not please download from playstore.
* Make sure you don't use ``` 	getDriver().resetApp()  ``` when your running your webtests.


<h3>Reports</h3>

Your should see report file generated as ExtentReport.html

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/report1.jpg)

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/report2.jpg)

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/report3.jpg)

<h3>TestRunning in Distributed Way</h3>

[![ScreenShot](http://s29.postimg.org/uln15acdz/Screen_Shot_2016_01_10_at_12_02_10_pm.png)](https://www.youtube.com/watch?v=KfMoJ6dSC3g)

## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
AppiumTestDistribution is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)
