---
title: Remote Execution
hide:
  - navigation
---

<div style="text-align: center">
  <img src="https://raw.githubusercontent.com/AppiumTestDistribution/appium-device-farm/main/docs/assets/images/remote.jpg" class="center"/>
</div>

### HUB

Hub is a server that accepts access requests from the WebDriver client, routing the W3C test commands to the remote drives on nodes. It takes instructions from the client and executes them remotely on the various nodes in parallel


* System with real devices and simulators that need to be considered for test execution.
* Create a hub-config.json or any other config file format that Appium supports. Refer [here](https://github.com/appium/appium/blob/master/packages/appium/docs/en/guides/config.md)

## hub-config.json

```
{
  "server": {
    "port": 31337,
    "plugin": {
      "device-farm": {
        "platform": "both", //If both android and iOS devices/simulator/emulators needs to be considered
    
      }
    }
  }
}
```

* Run the Appium server with _appium-device-farm_ plugin enabled.

```
appium server -ka 800 --use-plugins=device-farm --config ./hub-config.json -pa /wd/hub
```

### Node:

Node is a remote machine that consists of devices and appium server running with device-farm active. It receives requests from the hub in the form of W3C test commands and executes them using WebDriver

* System with real devices and simulators that need to be considered for test execution.
* Create a node-config.json or any other config file format that Appium supports. Refer [here](https://github.com/appium/appium/blob/master/packages/appium/docs/en/guides/config.md)

```
{
    "server": {
      "port": 4723,
      "plugin": {
        "device-farm": {
          "platform": "android",
          "hub": "https://1.1.1.1:4723" //Add the hub host and port the appium server running in node machine. 
        }
      }
    }
  }
```

* Run the Appium server with _appium-device-farm_ plugin enabled in the Node machine.

```
appium server -ka 800 --use-plugins=device-farm --config ./node-config.json -pa /wd/hub
```

### Dashboard
* Navigate to the host and port of Hub server from the above example it will be http://localhost:31137/device-farm
* Dashboard should have device list based on the hub configuration.
  ![](https://github.com/AppiumTestDistribution/appium-device-farm/blob/main/assets/demo.gif)

### Test Execution
* Point your Appium test execution URL to the Hub endpoint. 
