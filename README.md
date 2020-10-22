<h1 align="center">
	<br>
	<img src="image/ATD-Logo1.png" alt="ATD">
	<br>
	<br>
	<br>
</h1>

# Appium Parallel Execution for Android and iOS on Real Devices
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/saikrishna321/AppiumTestDistribution)
[![Join the chat at https://gitter.im/saikrishna321/AppiumTestDistribution](https://badges.gitter.im/saikrishna321/AppiumTestDistribution.svg)](https://gitter.im/saikrishna321/AppiumTestDistribution?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://dev.azure.com/saikrishna321/ATD/_apis/build/status/AppiumTestDistribution.AppiumTestDistribution)](https://dev.azure.com/saikrishna321/ATD/_build/latest?definitionId=2)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/96c555bd567240999e89dba531fe9802)](https://www.codacy.com/app/saikrishna321/AppiumTestDistribution?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=saikrishna321/AppiumTestDistribution&amp;utm_campaign=Badge_Grade)
[![GitHub stars](https://img.shields.io/github/stars/saikrishna321/AppiumTestDistribution.svg?style=flat)](https://github.com/saikrishna321/AppiumTestDistribution/stargazers)
[ ![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg?style=flat )](https://github.com/saikrishna321/AppiumTestDistribution/pulls)
[![GitHub forks](https://img.shields.io/github/forks/saikrishna321/AppiumTestDistribution.svg?style=social&label=Fork)](https://github.com/kirankalyan5/AppiumTestDistribution/network)

## Thanks for support 
<h3>
	<a href= "https://saucelabs.com"><img src="image/Powered by Sauce Labs badges red.svg" alt="ATD" width="45%" align="top"></a>
	<a href= "https://www.browserstack.com"><img src="https://p14.zdusercontent.com/attachment/1015988/j4BFTOujCnXKj0HBcxYt78Bc7?token=eyJhbGciOiJkaXIiLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0..LGh31Nv7MQwbhepdV4ZYCA.6tyNmOnWvHjz6iZFtQg3kTj6-CLsKxtdWtOOYxHzOcEuxM-DBMOi4XWJKiSo6M70a9M3eOSK4zkbUAx9Zk7n0W-D6SWMbdCdwBn2bzu6J9JzdQ_LReqAVdpU_5jSNQti4zOPST__hShBNvipQPYVbNl5OMukCQJvB9pnLEm_KXZDmUxWdsvaNJWVpUzV_k1QZn6Bgle-xKkhQoIQMLVEwmed_j0uhoGAKVS4iv3NBEeBmcJfyW8f3XmIk-BRFJBKWWhV6AenD3svBL0LmhMFojX0JvA7CbNx0h9s99qY8J4.jss6MlHMDhpeBbQYvDzjqw" alt="ATD" width="45%" align="top"></a>
</h3><h3 align="left">
	<a href= "https://www.genymotion.com/"><img src="image/genymotion.png" alt="ATDGM" width="50%"></a>
</h3>

Add the below dependencies in your pom.xml (Master)

```
<dependency>
    <groupId>com.github.AppiumTestDistribution</groupId>
    <artifactId>AppiumTestDistribution</artifactId>
    <version>latest_commit</version>
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

## [Maven Release Version](https://search.maven.org/artifact/com.github.saikrishna321/AppiumTestDistribution)

   ```
    <dependency>
      <groupId>com.github.saikrishna321</groupId>
      <artifactId>AppiumTestDistribution</artifactId>
      <version>11.0.1</version>
    </dependency>
   ```

## [Prerequisites](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Prerequisites)

## ReportPortal 
    Add LISTENERS=com.epam.reportportal.testng.ReportPortalTestNGListener to config.properties to send data to report portal.
### Sample Tests
 Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)
 If your application is cross-platform and you end up  building a PageObjectPattern Framework. Then you can also run those tests across Android and iOS devices connected to the same Mac OSX Host.
 
 (e.g. If you have 3 Android and 3 iOS devices connected to the same machine, you will be able to execute these test parallel on both platforms)

## [Configure-tests-for-TestNG](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Configure-tests-for-TestNG)

## [Configure-tests-for-Cucumber](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Configure-test-for-Cucumber)

## [Configure-tests-for-Sauce](https://github.com/AppiumTestDistribution/AppiumTestDistribution/wiki/Configure-test-for-Cloud-Sauce)

## [Configure-tests-for-Browserstack](https://github.com/AppiumTestDistribution/AppiumTestDistribution/wiki/Configure-test-for-BrowserStack-cloud)

## [Configure-tests-for-GenyMotionCloud](https://github.com/AppiumTestDistribution/AppiumTestDistribution/wiki/Configure-test-for-Genymotion-cloud)

## [Customize Tests](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Customize-Tests)

## [Tips](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Tips)

## Video log Prerequisites
* Install flick
    * ```$ gem install flick```

* Install ffmpeg. [OSX](https://trac.ffmpeg.org/wiki/CompilationGuide/MacOSX)
	* ```$ brew update```
	* ```$ brew install ffmpeg```
* Install mp4box. [OSX](http://hunterford.me/compiling-mp4box-on-mac-os-x/)
	* ```$ brew install mp4box```
* Install ffmpeg. [windows]( https://ffmpeg.org/download.html#build-windows)
* Install mp4box. [windows] (https://gpac.wp.mines-telecom.fr/downloads/)

## Runner
    Videos will be logged for failure tests
    VIDEO_LOGS="true" mvn clean -Dtest=Runner test 

## Credits
Thanks to
* [@thote](https://github.com/thote) and [@jaydeepc](https://github.com/jaydeepc) for the motivation.
* [@SrinivasanTarget](https://github.com/SrinivasanTarget) for contributing on iOS parallel.
* [@anandbagmar](https://github.com/anandbagmar) for fixing cucumber support.

### Reports

Your should see report file generated as ExtentReport.html under the target folder.

### Parallel Run
[![ScreenShot](image/run.png)](https://www.youtube.com/watch?v=X2OaLAqikMI&t=57s)


## FAQ
**Q. Can I automate application which I already have installed/ downloaded from App store?**

A. With XCUITest framework, that is available from ios version 9.3, it's possible to execute Native (WebViews content would not be reachable) application using it's bundle id.

In order to get information about bundle id of pre-installed application:
* connect device with application installed;
* run ideviceinstaller -l or ideviceinstaller -l -u {udid of the device} in case if more then one device connected to the host;
* in the list - find name of the application followed by its bundle id.

After that, instead of `app` capability `bundleId` should be used with application's bundle id as a value.
Since Appium still needs WDA to be built and deployed on the real device - valid certificate and provisioning profile will be needed. More information could be found [here](https://github.com/imurchie/appium-xcuitest-driver/blob/isaac-rs/docs/real-device-config.md)

In case if test need to interact with WebViews and/or ios version has no support of XCUITest - Appium would need instruments application which is in debug mode, it will not work with any application which is not in debug mode. Read more about it [here](https://discuss.appium.io/t/appium-ios-app-testing/105/8)

**Q. Is this framework supports to run multiple IOS simulators?**

A. Yes, Currently Appium does support multiple simulators with latest 1.6.6.beta with Xcode9-beta.

**Q. Unable to instruments application or instruments crashed on start up?**

A. Below are few possible causes
* (app/ipa) is not on debug mode.
* (app) targeted to simulator will not work with real device and vice versa
* Check device's developer option in settings.

**Q. Unable to install application during automation?**

A. A quick solution would try to install the application using "ideviceinstaller -i ipa_name", if that does not work, check app is built with the valid provisioning profile.

**Q. Can I run tests on iOS app for which I have source code**(e.g:"[Wordpress](https://github.com/wordpress-mobile/WordPress-iOS.git)") **on real devices?**

A. Yes, with a Valid provisioning profile this app can be installed on your device.
(Note: The application must be signed with a valid developer certificate and your device should be added to the provisioning profile)


## Organizations :blue_heart:

ThoughtWorks             |  CeX                      |  Travelstart
:-------------------------:|:-------------------------:|:-------------------------:
[![ThoughtWorks](https://static1.squarespace.com/static/5914a98815cf7d683358255d/t/5914b14d17bffc60d36cf2a7/1494531166569/)](https://www.thoughtworks.com)  |  [![Cex](https://in.webuy.com/images/logos/CeX_Logo_Rich_black_RGB-01.png)](https://in.webuy.com/)  |  [![Travelstart](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRahsNjFIF2pQGOxKoinf4Au1gGjyMUQEArL8Mv_gyCl1Aaxuu-)](http://www.travelstart.com/)

Jio India             |  M800 | Reward Gateway
:-:|:-:|:-:
[![Jio](http://images.indianexpress.com/2016/07/reliancejio_newlogo_1.jpg)](https://www.jio.com/)  |  [![M800](https://i.stack.imgur.com/nY9CC.jpg)](http://www.m800.com/) | [![Reward](http://www.greathillpartners.com/wp-content/uploads/2016/01/reward-gateway-logo-horizontal-tagline-on-white-1.png)](https://www.rewardgateway.com/)

dahmakan|
:-------------------------:|
[<img src='https://s2-cdn.greenhouse.io/external_greenhouse_job_boards/logos/400/066/300/original/dahmakan-og-logo.jpg?1519124252' width='300' height='150'/>](https://www.dahmakan.com/)
## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
AppiumTestDistribution is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)
