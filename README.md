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
1. If you have installed Appium from [npm](https://www.npmjs.com/package/appium) make sure you have version 1.4.16. If you wish to use latest version of Appium(e.g. v1.5) you need to build it from the source.

	e.g. `npm -g install appium@1.14.14` or [Build Appium from source](https://discuss.appium.io/t/appium-1-5-beta-release/6058/35).
2. Android SDK is installed and ANDROID_HOME is set as environment variable.
3. JDK 1.8 is installed and JAVA_HOME is set as environment variable.
4. Developer option is enabled on the attached device and must be authorized.

### IOS (Real device):
1. Mac machine with XCode and command line tools installed. XCode version >= 6.0, 7.1.1 recommended.
2. `libimobiledevice` and `ideviceinstaller` needs to be installed.

	e.g. `brew install libimobiledevice libplist libtasn1 usbmuxd openssl ideviceinstaller`
3. Developer option must be enable in attached iOS device (Settings>Developer>Enable UI Automation)
4. You must have a debug build of the application you wish to test.

### Sample Tests
 Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)
 If you're application is cross-platform  and you end up  building a PageObjectPattern Framework then can run the tests across android and iOS devices connected in the same Mac OSX Host.
 (For ex: 3 android devices and 3 iOS devices connected to the same machine, you can trigger the test parallel on both platforms)

##Configure tests

1. Main Runnerclass should look as below ::

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
2. Extend your tests to AppiumParallelTest ::( It is part of the dependencies and will take care of running the Appium server session in parallel threads).

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

3. Create `config.properties` file under your test directory, which should have below properties.

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
	IOS_APP_PATH=<absolute path to .app/.ipa>
	ANDROID_APP_PATH=<absoulte path to .apk>
	```

###Run Test from CommandLine

```
mvn clean -Dtest=Runner test
```
##Custmize Tests:

* Specific test method can be skipped based on platform (AndroidDriver/IOSDriver) when running tests Concurrently on the same OSX Host.
	```
	@Test
	@SkipIf(platform = "AndroidDriver")
	public void testMethodOne_8() throws Exception  {}

	@Test
	@SkipIf(platform = "IOSDriver")
	public void testMethodOne_9() throws Exception  {}
	```
* Retry Annotation on test failures. The test would be re-executed specifid number of time if it fails.
	```
	@Test(retryAnalyzer=Retry.class)
	@RetryCount(maxRetryCount=2)
	public void testMethodOne_8() throws Exception  {}
	```

##Tips:

* Provide the absolute path of the .apk and .ipa file.
* APPIUM_JS_PATH should be the location of the appium.js in your local machine.If your using the latest Appium v1.5 please make sure
  you have the source build locally. Refer the Bug(https://github.com/appium/appium/issues/6202)
* RUNNER option can be parallel/distribute(Parallel will run all the tests across device- which helps you to get the device coverage/ Distribute will distribute all the tests across devices which helps you faster execution)
* APP_TYPE should be set to "web" to run webtests on chrome in android, if running native/hybrid test, set APP_TYPE="NA".
* Make sure you have chrome browser installed on android real devices, if not download from Playstore.
* Make sure you don't use ```getDriver().resetApp()``` when you are running your webtests.
* On Test Failures device frame will be added to screenshot captured during execution, provided you have the frames inside the resources folder. Please download the frames(https://github.com/saikrishna321/DeviceFrames) and place them under resources folder. (e.g. /src/test/resources/frames/)

<h3>Sample ImagesFramed</h3>
https://github.com/saikrishna321/AppiumTestDistribution/tree/master/image/device_frame_example)

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
Q. Can I automate application which I already have installed/ downloaded from App store?

A.No, Appium can only instruments application which are in debug mode, it will not work with any application build which is not in degug mode. Read more about it [here](https://discuss.appium.io/t/appium-ios-app-testing/105/8)

Q. Is this framework supports to run multiple IOS simulators?

A. No, Currently Appium does not supports multiple simulator, it  will be supported in Appium's future releases.

Q. Unable to instruments application or instruments crashed on start up?

A. Below are few possible causes
* (app/ipa) is not on debug mode.
* (app) targeted to simulator will not work with real device and vice versa
* Check device's developer option in settings.
*

Q. Unable to install application during automation?

A. A quick would be try to install application using "ideviceinstaller -i ipa_name", if that does not work, check app is built with valid provisioning profile.

Q. Can I run any test on iOS app for which I have source code(e.g:"[Wordpress](https://github.com/wordpress-mobile/WordPress-iOS.git)") on real devices?

A. Yes, with a Valid provisioning profile this app can be installed on your device.
(Note: The application must be signed with a valid developer certificate an your device should be added to the provisioning profile)


## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
AppiumTestDistribution is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)
