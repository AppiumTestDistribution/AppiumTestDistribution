---
title: Capabilities
---

| Capability Name                  | Description                                                                                                                                                                                    |
| -------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| appium:iPhoneOnly                | Allocate only iPhone simulators for execution when to true. Default value is `false`.                                                                                                          |
| appium:iPadOnly                  | Allocate only iPad simulators for execution when to true. Default value is `false`.                                                                                                            |
| appium:deviceAvailabilityTimeout | When create session requests are more than available connected devices, plugin waits for a certain interval for device availability before it timeout. Default value is `180000` milliseconds. |
| appium:deviceRetryInterval       | When create session requests are more than available connected devices, plugin polls for device availability in certain intervals. Default value is `10000` milliseconds.                      |
| appium:udids                     | Comma separated list of device udid's to execute tests only on specific devices `appium:udids: device1UDID,device2UDID` |
| appium:platformName              | Requests asession for the provided platform name. Valid options are `iOS`, `tvOS`, or `Android`, ex: `'appium:platformName': tvOS`   |
| appium:platformVersion           | This capability is used to filter devices/simulators based on SDK. Only devices/simulators that are an exact match with the platformVerson would be considered for test run. `appium:platformVersion` is optional argument. ex: `'appium:platformVersion': 16.1.1`   |
| appium:minSDK                    | This capability is used to filter devices/simulators based on SDK. Devices/Simulators with SDK greater then or equal to minSDK would only be considered for test run. `appium:minSDK` is optional argument. ex: `'appium:minSDK': 15`   |
| appium:maxSDK                    | This capability is used to filter devices/simulators based on SDK. Devices/Simulators with SDK less then or equal to maxSDK would only be considered for test run. `appium:maxSDK` is optional argument. ex: `'appium:maxSDK': 15`   |