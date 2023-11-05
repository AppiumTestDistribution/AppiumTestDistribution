---
title: FAQ
hide:
  - navigation
---

This FAQ provides answers to common questions about automating iOS applications using Appium. If you have any questions or encounter issues while automating iOS apps, you might find the information you need here.

## Can I automate an application that I have already installed/downloaded from the App Store?

Yes, you can automate applications that you have already installed/downloaded from the App Store using the XCUITest framework, available from iOS version 9.3. To do this, you need to use the bundle id of the application. Here's how to find the bundle id:

1. Connect your device with the application installed.
2. Run `ideviceinstaller -l` or `ideviceinstaller -l -u {udid of the device}` if more than one device is connected.
3. In the list, find the name of the application followed by its bundle id.

Instead of using the app capability, use the bundleId with the application's bundle id as the value. Note that Appium still requires WebDriverAgent (WDA) to be built and deployed on the real device, which requires a valid certificate and provisioning profile. For more information, refer to [this link](https://appium.io/docs/en/drivers/ios-xcuitest-real-devices/).

If your tests need to interact with WebViews or the iOS version lacks XCUITest support, Appium may need an instruments application in debug mode. Please read more about it [here](https://appium.io/docs/en/writing-running-appium/web/hybrid/ios-hybrid.html).

## Does this framework support running multiple iOS simulators?

Yes, Appium does support running multiple iOS simulators with version 1.6.6.beta and Xcode 9-beta.

## Unable to instrument the application or instruments crashed on startup?

If you encounter issues with instrumenting the application or instruments crashing on startup, consider the following possible causes:

- The app or .ipa file is not in debug mode.
- An app targeted for the simulator will not work on a real device and vice versa.
- Check the developer options on the device.

## Unable to install the application during automation?

If you are unable to install the application during automation, you can try to install the application using the command `ideviceinstaller -i ipa_name`. If that does not work, make sure that the app is built with a valid provisioning profile.

## Can I run tests on an iOS app for which I have the source code (e.g., "Wordpress") on real devices?

Yes, you can run tests on an iOS app for which you have the source code, such as "Wordpress," on real devices. To do so, you need a valid provisioning profile, and the application must be signed with a valid developer certificate. Additionally, your device should be added to the provisioning profile.
