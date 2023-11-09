---
title: BrowserStack
hide:
  - navigation
---

No changes to the existing test or ATD implementation. The only change you need to do is set username and password for cloud service as below

```
Platform='android' CLOUD_USERNAME=$user_bs CLOUD_KEY=$pass_bs CONFIG_FILE='./configs/bs_config.properties' mvn clean -Dtest=Runner test
```

Capabilities for running only on BrowserStack cloud should look as below:

```
{
  "android": {
    "automationName": "UIAutomator2",
    "project": "ATD",
    "app": "bs://c45ee418d63142cea1f288e561fc970cf05a5a7d",
    "noSign": true
  },
  "iOS": {
    "platformName": "iOS",
    "app": "bs://c45ee418d63142cea1f288e561fc970cf05a5a7d"
    "deviceName": "iPhone",
    "wdaLaunchTimeout": 80000,
    "wdaConnectionTimeout": 80000,
    "preventWDAAttachments": true,
    "clearSystemFiles": true,
    "shouldUseSingletonTestManager": false,
    "simpleIsVisibleCheck": true,
    "maxTypingFrequency": 10
  },
   "serverConfig" : {
    "server": {
      "use-plugins": ["device-farm"],
      "plugin": {
        "device-farm": {
          "platform": "android",
          "skipChromeDownload": true,
          "remote": [
            {
              "cloudName": "browserstack",
              "url": "http://hub-cloud.browserstack.com",
              "devices": [
                {
                  "deviceName": "Samsung Galaxy S22",
                  "os_version": "12.0",
                  "platform": "android"
                },
                {
                  "deviceName": "iPhone 11 Pro",
                  "os_version": "15",
                  "platform": "ios"
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
