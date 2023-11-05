---
title: Cloud Execution
hide:
  - navigation
---

### BrowserStack

```
CLOUD_USERNAME="username" CLOUD_KEY="apiKey" appium server -ka 800 --use-plugins=device-farm --config ./serverConfig/bs-config.json -pa /wd/hub
```
Refer on BroswerStack config [here](https://github.com/AppiumTestDistribution/appium-device-farm/blob/main/serverConfig/bs-config.json)

### pCloudy

```
CLOUD_USERNAME="useremail" CLOUD_KEY="apiKey" appium server -ka 800 --use-plugins=device-farm --config ./serverConfig/pcloudy-config.json -pa /wd/hub
```
Refer on pCloudy config [here](https://github.com/AppiumTestDistribution/appium-device-farm/blob/main/serverConfig/pcloudy-config.json)

### SauceLabs

```
CLOUD_KEY="apiKey" CLOUD_USERNAME="useremail" appium server -ka 800 --use-plugins=device-farm --config ./serverConfig/sauce-config.json -pa /wd/hub
```
Refer on sauce config [here](https://github.com/AppiumTestDistribution/appium-device-farm/blob/main/serverConfig/sauce-config.json)

### LambdaTest

```
CLOUD_KEY="apiKey" CLOUD_USERNAME="useremail" appium server -ka 800 --use-plugins=device-farm --config ./serverConfig/lt-config.json -pa /wd/hub
```
Make sure all `appiumVersion: 2.0` in your capabilities.
Refer on LambdaTest config [here](https://github.com/AppiumTestDistribution/appium-device-farm/blob/main/serverConfig/lt-config.json)
