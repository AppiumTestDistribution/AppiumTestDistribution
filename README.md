# Appium Parallel Execution for Android and iOS on Real Devices

[![Join the chat at https://gitter.im/saikrishna321/AppiumTestDistribution](https://badges.gitter.im/saikrishna321/AppiumTestDistribution.svg)](https://gitter.im/saikrishna321/AppiumTestDistribution?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/saikrishna321/AppiumTestDistribution.svg?branch=master)](https://travis-ci.org/saikrishna321/AppiumTestDistribution/builds/)

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/conect1-anim.gif)

Add the below dependencies in your pom.xml

```
<dependency>
	<groupId>com.github.saikrishna321</groupId>
	<artifactId>AppiumTestDistribution</artifactId>
	<version>4.0.1</version>
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

##Prerequisites

###Android:
1. Make sure Appium(1.4.16) is installed through node and if using the latest appium v1.5 make sure you have it build from the source.
2. Make sure adb sdk is set under ANDROID_HOME.
3. Install JDK 1.8 and set under path JAVA_HOME to get this framework working.
4. Device's developer option should be enabled

### IOS (Real device):
1. A Mac machine must have XCode
2. libimobiledevice and ideviceinstaller need to be installed
e.g `brew install libimobiledevice libplist libtasn1 usbmuxd openssl ideviceinstaller`
3. Developer option must be enable in attached iOS device (Settings>Developer>Enable UI Automation)
4. Appium instruments only on debug application (*.app or *.ipa), any application in release mode will not work

### Sample Tests
 Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)
 If you're application is cross-platform  and you end up  building a PageObjectPattern Framework then can run the tests across android and iOS devices connected in the same Mac OSX Host.
 (For ex: 3 android devices and 3 iOS devices connected to the same machine, you can trigger the test parallel on both platforms)

##Configure tests

Main Runnerclass should look as below :: 

```
public class Runner {
    
	@Test
	public void testApp() throws Exception {
		ParallelThread parallelThread = new ParallelThread();
		//parallelThread.runner(package_name_where_test_located);
		parallelThread.runner("com.paralle.tests");

	}
}

```

1. Extend your tests to AppiumParallelTest which is part of the dependencies, which takes care of running the appium server session in parallel threads.

```
public class UserBaseTest extends AppiumParallelTest {

	@BeforeMethod()
	public void startApp(Method name) throws Exception {
 		driver = startAppiumServerInParallel(name.getName());
        startLogResults(name.getName());
	}

	@AfterMethod()
	public void killServer(ITestResult result) {
		endLogTestResults(result);
		getDriver().quit();
	}

	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	@BeforeClass()
	public void beforeClass() throws Exception {
		 startAppiumServer(getClass().getSimpleName());
	}

	@AfterClass()
	public void afterClass() throws InterruptedException, IOException {
		killAppiumServer();
	}
}

```
    
###Run Test from CommandLine
```
mvn clean -Dtest=Runner test

```   

2.Create a config.properties under your test directory, which hold the below properties

```
APP_PACKAGE=com.android2.calculator3
APP_ACTIVITY=com.android2.calculator3.Calculator
RUNNER=distribute

## For appium 1.5.X users (If appium installed using npm)
APPIUM_JS_PATH=/usr/local/lib/node_modules/appium/build/lib/main.js

## For appium 1.4.13 users (GUI)
APPIUM_JS_PATH=/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js

## For appium 1.4.16 users (Non-GUI -- Installed using npm)
APPIUM_JS_PATH=/usr/local/lib/node_modules/appium/bin/appium.js

BROWSER_TYPE=chrome
APP_TYPE=NA
BUNDLE_ID=
IOS_APP_PATH=absolute path to .ipa
ANDROID_APP_PATH=absoulte path to .apk

```

##Tips: 

* Provide the absolute path of the .apk and .ipa file.
* APPIUM_JS_PATH should be the location of the appium.js in your local machine.If your using the latest Appium v1.5 please make sure
  you have the source build locally. Refer the Bug(https://github.com/appium/appium/issues/6202)
* RUNNER option can be parallel/distribute(Parallel will run all the tests across device- which helps you to get the device coverage/ Distribute will distribute all the tests across devices which helps you faster execution)
* APP_TYPE should be set to "web" to run webtests on chrome in android, if running native/hybrid test, set APP_TYPE="NA".
* Make sure you have chrome browser installed on android real devices, if not please download from playstore.
* Make sure you don't use ``` 	getDriver().resetApp()  ``` when your running your webtests.
* On Test Failures device frame will be added to screenshot captured during execution,provided you have the frames inside the resources folder.please download the frames(https://github.com/saikrishna321/DeviceFrames) and place them under resources folder. (For ex: /src/test/resources/frames/)

<h3>Sample ImagesFramed</h3>
https://github.com/saikrishna321/AppiumTestDistribution/tree/master/image/device_frame_example)

* Specific test method can be skipped on specific platform(AndroidDriver/IOSDriver) when running tests Concurrently on the same OSX Host.
	```
	@Test
 	@SkipIf(platform = "AndroidDriver")
	 	public void testMethodOne_8() throws Exception  {}

	@Test
 	@SkipIf(platform = "IOSDriver")
	 	public void testMethodOne_9() throws Exception  {}
	 
	```
* Retry Annotation on test failures.

	```
	@Test(retryAnalyzer=Retry.class)
	@RetryCount(maxRetryCount=2)
	public void testMethodOne_8() throws Exception  {}
	
	```

##Device Art for Android
* Nexus 4
* Nexus 5
* Nexus 6
* Nexus 6P
* Nexus 5x
* Yet to add S3,S4,S5 and S6

##Device Art for iPhone

* iPhone 5c
* iPhone 5s
* iPhone 6
* iPhone 6 Plus
* iPhone 6s
* iPhone 6s Plus
* iPod touch 5G

##Credits
Thanks to
[@SrinivasanTarget](https://github.com/SrinivasanTarget) [@thote](https://github.com/thote) and [@jaydeepc](https://github.com/jaydeepc)

###Reports

Your should see report file generated as ExtentReport.html under the target folder.

###TestRunning in Distributed Way

[![ScreenShot](http://s29.postimg.org/uln15acdz/Screen_Shot_2016_01_10_at_12_02_10_pm.png)](https://www.youtube.com/watch?v=KfMoJ6dSC3g)

##WIP
iOS WebTest using Safari

##FAQ
Q. Is this framework supports to run multiple IOS simulators?

A. No, multiple simulator will be supported in appium's future releases.

Q. Unable to instruments application or instruments crashed on start up?

A. Below are few possible causes
* (app/ipa) should be in debug mode
* (app) targeted to emulator will not be work with real device and vice versa
* Check device's developer option

Q. Unable to install application during automation?

A. a quick would be try to install application using "ideviceinstaller -i ipa_name", if that does not work, check app is built with valid provisioning profile.

Q. Can i run any test app (e.g:"Wordpress") on real devices?

A. Yes, with Valid provisioning profile this app can be installed to your device
(Note: Here, application needs to be signed by valid developer certificate)


## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
AppiumTestDistribution is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)
