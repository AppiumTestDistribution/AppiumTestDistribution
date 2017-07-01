# Appium Parallel Execution for Android and iOS on Real Devices

[![Join the chat at https://gitter.im/saikrishna321/AppiumTestDistribution](https://badges.gitter.im/saikrishna321/AppiumTestDistribution.svg)](https://gitter.im/saikrishna321/AppiumTestDistribution?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/saikrishna321/AppiumTestDistribution.svg?branch=master)](https://travis-ci.org/saikrishna321/AppiumTestDistribution/builds/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/96c555bd567240999e89dba531fe9802)](https://www.codacy.com/app/saikrishna321/AppiumTestDistribution?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=saikrishna321/AppiumTestDistribution&amp;utm_campaign=Badge_Grade)
[![GitHub stars](https://img.shields.io/github/stars/saikrishna321/AppiumTestDistribution.svg?style=flat)](https://github.com/saikrishna321/AppiumTestDistribution/stargazers)

![alt tag](https://raw.githubusercontent.com/saikrishna321/AppiumTestDistribution/master/image/Network-2.gif)

Add the below dependencies in your pom.xml (Release)

```
<dependency>
    <groupId>com.github.saikrishna321</groupId>
    <artifactId>AppiumTestDistribution</artifactId>
    <version>6.0.1</version>
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
Add the below dependencies in your pom.xml (Master)

```
<dependency>
    <groupId>com.github.saikrishna321</groupId>
    <artifactId>AppiumTestDistribution</artifactId>
    <version>a7fe0c9</version>
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

## [Prerequisites](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Prerequisites)


### Sample Tests
 Clone the project (https://github.com/saikrishna321/PageObjectPatternAppium)
 If your application is cross-platform and you end up  building a PageObjectPattern Framework. Then you can also run those tests across Android and iOS devices connected to the same Mac OSX Host.
 
 (e.g. If you have 3 Android and 3 iOS devices connected to the same machine, you will be able to execute these test parallel on both platforms)

## [Configure-tests-for-TestNG](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Configure-tests-for-TestNG)

## [Configure-tests-for-Cucumber](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Configure-test-for-Cucumber)

## [Customize Tests](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Customize-Tests)

## [Tips](https://github.com/saikrishna321/AppiumTestDistribution/wiki/Tips)

## Video log Prerequisites
* Install ffmpeg. [OSX](https://trac.ffmpeg.org/wiki/CompilationGuide/MacOSX)
	* ```$ brew update```
	* ```$ brew install ffmpeg```
* Install mp4box. [OSX](http://hunterford.me/compiling-mp4box-on-mac-os-x/)
	* ```$ brew install mp4box```
* Install ffmpeg. [windows]( https://ffmpeg.org/download.html#build-windows)
* Install mp4box. [windows] (https://gpac.wp.mines-telecom.fr/downloads/)

## Runner
    ##Videos will be logged for failure tests
    VIDEO_LOGS="true" mvn clean -Dtest=Runner test 

## Credits
Thanks to
* [@thote](https://github.com/thote) and [@jaydeepc](https://github.com/jaydeepc) for the motivation.
* [@SrinivasanTarget](https://github.com/SrinivasanTarget) for contributing on iOS parallel.
* [@temyers](https://github.com/temyers) for letting us use the cucumber-jvm-parallel-plugin(https://github.com/temyers/cucumber-jvm-parallel-plugin) 
as part of framework which helps generating a Cucumber JUnit or TestNG runner for each feature file.

### Reports

Your should see report file generated as ExtentReport.html under the target folder.

###TestRunning in Distributed Way

[![ScreenShot](http://s29.postimg.org/uln15acdz/Screen_Shot_2016_01_10_at_12_02_10_pm.png)](https://www.youtube.com/watch?v=KfMoJ6dSC3g)

## WIP
iOS WebTest using Safari

## FAQ
**Q. Can I automate application which I already have installed/ downloaded from App store?**

A. No, Appium can only instruments application which is in debug mode, it will not work with any application which is not in debug mode. Read more about it [here](https://discuss.appium.io/t/appium-ios-app-testing/105/8)

**Q. Is this framework supports to run multiple IOS simulators?**

A. No, Currently Appium does not support multiple simulators, it  will be supported in Appium's future releases.

**Q. Unable to instruments application or instruments crashed on start up?**

A. Below are few possible causes
* (app/ipa) is not on debug mode.
* (app) targeted to simulator will not work with real device and vice versa
* Check device's developer option in settings.
*

**Q. Unable to install application during automation?**

A. A quick solution would try to install the application using "ideviceinstaller -i ipa_name", if that does not work, check app is built with the valid provisioning profile.

**Q. Can I run tests on iOS app for which I have source code**(e.g:"[Wordpress](https://github.com/wordpress-mobile/WordPress-iOS.git)") **on real devices?**

A. Yes, with a Valid provisioning profile this app can be installed on your device.
(Note: The application must be signed with a valid developer certificate and your device should be added to the provisioning profile)


## Organizations :blue_heart:

ThoughtWorks             |  CeX
:-------------------------:|:-------------------------:
![](https://static1.squarespace.com/static/5914a98815cf7d683358255d/t/5914b14d17bffc60d36cf2a7/1494531166569/)  |  ![](https://uk.webuy.com/images/logo/CeX_Logo_Rich_black_RGB-01.png)

Jio India             |  M800 | Reward Gateway
:-:|:-:|:-:
![](http://images.indianexpress.com/2016/07/reliancejio_newlogo_1.jpg)  |  ![](https://i.stack.imgur.com/nY9CC.jpg) | ![](http://www.greathillpartners.com/wp-content/uploads/2016/01/reward-gateway-logo-horizontal-tagline-on-white-1.png)

## License

![GNU Public License version 3.0](http://www.gnu.org/graphics/gplv3-127x51.png)
AppiumTestDistribution is released under [GNU Public License version 3.0](http://www.gnu.org/licenses/gpl-3.0.txt)
