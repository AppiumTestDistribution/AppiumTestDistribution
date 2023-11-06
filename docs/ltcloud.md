---
title: LambdaTest
hide:
  - navigation
---

### LambdaTest

No changes to the existing test or ATD implementation. The only change you need to do is set username and password for cloud service as below

```
Platform='android' CLOUD_USERNAME=$user_lt CLOUD_KEY=$pass_lt CONFIG_FILE='./configs/lambdaTest_config.properties' mvn clean -Dtest=Runner test -Dcheckstyle.skip
```

Capabilities for running only on LambdaTest cloud should look as below:

```
{
  "android": {
    "platformName": "Android",
    "automationName": "UIAutomator2",
    "appiumVersion": "2.0",
    "appium:isRealMobile": true,
    "appium:app": "lt://APP10160531401673528314460178",
    "appium:build": "ATDDevs",
    "appium:project": "Appium Device Farm with ATD",
    "lt:options": {
      "w3c": true
    }
  },
  "serverConfig": {
    "server": {
      "port": 31337,
      "use-plugins": [
        "device-farm"
      ],
      "plugin": {
        "device-farm": {
          "platform": "android",
          "remote": [
            {
              "cloudName": "lambdatest",
              "url": "https://mobile-hub.lambdatest.com/wd/hub",
              "devices": [
                {
                  "platformVersion": "11",
                  "deviceName": "Pixel 3a",
                  "platform": "android"
                },
                {
                  "platform": "android",
                  "deviceName": "Pixel 4a",
                  "platformVersion": "12"
                }
              ]
            }
          ]
        }
      }
    }
  }
}
```
```
