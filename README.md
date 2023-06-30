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
[![CircleCI](https://dl.circleci.com/status-badge/img/gh/AppiumTestDistribution/AppiumTestDistribution/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/AppiumTestDistribution/AppiumTestDistribution/tree/main)
[![GitHub stars](https://img.shields.io/github/stars/saikrishna321/AppiumTestDistribution.svg?style=flat)](https://github.com/saikrishna321/AppiumTestDistribution/stargazers)
[ ![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg?style=flat )](https://github.com/saikrishna321/AppiumTestDistribution/pulls)
[![GitHub forks](https://img.shields.io/github/forks/saikrishna321/AppiumTestDistribution.svg?style=social&label=Fork)](https://github.com/kirankalyan5/AppiumTestDistribution/network)

[![CircleCI](https://dl.circleci.com/insights-snapshot/gh/AppiumTestDistribution/AppiumTestDistribution/main/build/badge.svg?window=24h)](https://app.circleci.com/insights/github/AppiumTestDistribution/AppiumTestDistribution/workflows/build/overview?branch=main&reporting-window=last-24-hours&insights-snapshot=true)

## Thanks for support 
<h3>
	<a href= "https://saucelabs.com"><img src="image/Powered by Sauce Labs badges red.svg" alt="ATD" width="45%" align="top"></a>
<a href= "https://www.browserstack.com"><img src="https://maddyness-uk.twic.pics/2021/06/Screenshot-2021-06-21-at-20.14.46.png?twic=v1/resize=630" alt="ATD" width="45%" align="top"></a>
     
   <a href= "https://www.lambdatest.com"><img src="https://raw.githubusercontent.com/AppiumTestDistribution/appium-device-farm/main/assets/lt.png?twic=v1/resize=630" alt="ATD" width="45%" align="top"></a>
</h3>

Add the below dependencies in your pom.xml (Master)

### Please make sure you have installed Appium v2.0 and [appium-device-farm](https://github.com/AppiumTestDistribution/appium-device-farm) plugin
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
      <version>13.1.0</version>
    </dependency>
   ```

## [Prerequisites](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Prerequisites)

## ReportPortal 
    Add LISTENERS=com.epam.reportportal.testng.ReportPortalTestNGListener to config.properties to send data to report portal.
### Sample Tests
 Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)
 If your application is cross-platform and you end up  building a PageObjectPattern Framework. Then you can also run those tests across Android and iOS devices connected to the same Mac OSX Host.
 
 (e.g. If you have 3 Android and 3 iOS devices connected to the same machine, you will be able to execute these tests parallel on both platforms)

## [Configure-tests-for-TestNG](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Configure-tests-for-TestNG)

## [Configure-tests-for-Cucumber](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Configure-test-for-Cucumber)

## [Configure-tests-for-Sauce](https://github.com/AppiumTestDistribution/AppiumTestDistribution/wiki/Configure-test-for-Cloud-Sauce)

## [Configure-tests-for-Browserstack](https://github.com/AppiumTestDistribution/AppiumTestDistribution/wiki/Configure-test-for-BrowserStack-cloud)

## [Configure-tests-for-LambdaTest](https://github.com/AppiumTestDistribution/AppiumTestDistribution/wiki/Configure-test-for-LambdaTest-cloud)

## [Customize Tests](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Customize-Tests)

## [Tips](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Tips)

## Video log Prerequisites

* Install ffmpeg. [OSX](https://trac.ffmpeg.org/wiki/CompilationGuide/MacOSX)
	* ```$ brew update```
	* ```$ brew install ffmpeg```
    * `
* Install ffmpeg. [windows]( https://ffmpeg.org/download.html#build-windows)

## Runner
    Videos will be logged for failure tests
    VIDEO_LOGS="true" mvn clean -Dtest=Runner test 

## Credits
Thanks to
* [@thote](https://github.com/thote) and [@jaydeepc](https://github.com/jaydeepc) for the motivation.
* [@SrinivasanTarget](https://github.com/SrinivasanTarget) for contributing on iOS parallel.
* [@anandbagmar](https://github.com/anandbagmar) for fixing cucumber support.

### Parallel Run
[![ScreenShot](image/run.png)](https://www.youtube.com/watch?v=X2OaLAqikMI&t=57s)


## FAQ
**Q. Can I automate application which I already have installed/downloaded from App store?**

A. With XCUITest framework, that is available from iOS version 9.3, it's possible to execute Native (WebViews content would not be reachable) application using its bundle id.

In order to get information about bundle id of pre-installed application:
* connect device with application installed;
* run ideviceinstaller -l or ideviceinstaller -l -u {udid of the device} in case more than one device connected to the host;
* in the list - find name of the application followed by its bundle id.

After that, instead of `app` capability `bundleId` should be used with application's bundle id as a value.
Since Appium still needs WDA to be built and deployed on the real device - valid certificate and provisioning profile will be needed. More information could be found [here](https://github.com/imurchie/appium-xcuitest-driver/blob/isaac-rs/docs/real-device-config.md)

In case the tests need to interact with WebViews and/or iOS version has no support of XCUITest - Appium would need instruments application which is in debug mode. It will not work with any application which is not in debug mode. Read more about it [here](https://discuss.appium.io/t/appium-ios-app-testing/105/8)

**Q. Does this framework support running multiple iOS simulators?**

A. Yes, currently Appium does support multiple simulators with latest 1.6.6.beta with Xcode9-beta.

**Q. Unable to instrument application or instruments crashed on start up?**

A. Below are few possible causes
* (app/ipa) is not on debug mode.
* (app) targeted to simulator will not work with real device and vice versa
* Check device's developer option in settings.

**Q. Unable to install application during automation?**

A. A quick solution would be to try to install the application using "ideviceinstaller -i ipa_name". If that does not work, check app is built with a valid provisioning profile.

**Q. Can I run tests on iOS app for which I have source code**(e.g:"[Wordpress](https://github.com/wordpress-mobile/WordPress-iOS.git)") **on real devices?**

A. Yes, with a valid provisioning profile, this app can be installed on your device.
(Note: The application must be signed with a valid developer certificate and your device should be added to the provisioning profile)


## Organizations :blue_heart:

ThoughtWorks             |  CeX                      |  Travelstart
:-------------------------:|:-------------------------:|:-------------------------:
[![ThoughtWorks](https://www.thoughtworks.com/etc.clientlibs/thoughtworks/clientlibs/clientlib-site/resources/images/thoughtworks-logo.svg)](https://www.thoughtworks.com)  |  [![Cex](https://in.webuy.com/images/logos/CeX_Logo_Rich_black_RGB-01.png)](https://in.webuy.com/)  |  [![Travelstart](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRahsNjFIF2pQGOxKoinf4Au1gGjyMUQEArL8Mv_gyCl1Aaxuu-)](http://www.travelstart.com/)

Jio India             |  M800 | Reward Gateway
:-:|:-:|:-:
[![Jio](http://images.indianexpress.com/2016/07/reliancejio_newlogo_1.jpg)](https://www.jio.com/)  |  [![M800](https://i.stack.imgur.com/nY9CC.jpg)](http://www.m800.com/) | [![Reward](https://www.rewardgateway.com/hs-fs/hubfs/rg_logo_horizontal.png?width=1156&name=rg_logo_horizontal.png)](https://www.rewardgateway.com/)

dahmakan|
:-------------------------:|
[<img src='https://d2wu471yepgs9e.cloudfront.net/new-website/common/popmeals+purple+logo.svg' width='300' height='150'/>](https://www.dahmakan.com/)
## License

AppiumTestDistribution is released under [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Copyright (c) 2023 AppiumTestDistribution
