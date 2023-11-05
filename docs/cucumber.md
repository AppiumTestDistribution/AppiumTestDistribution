---
title: Cucumber
hide:
  - navigation
---

### Cucumber

[teswiz](https://github.com/znsio/teswiz) uses ATD to manage Appium, and the devices (android, iOS, Windows applications). It helps you automate tests for Android & iOS apps, specified using cucumber-jvm and intelligently run them against

- Android apks
- iOS apps
- Windows apps
- Web browsers
- APIs

Additionally, teswiz supports running tests against local devices or any of the device farms.

Refer to examples in [getting-started-with-teswiz](https://github.com/znsio/getting-started-with-teswiz) for examples of how to automate and run tests using cucumber-jvm.

### Example:

To run the below test in [vodqa.feature](https://github.com/znsio/teswiz/blob/main/src/test/resources/com/znsio/teswiz/features/vodqa.feature) file:

Command: `CONFIG=./configs/vodqa_local_config.properties PLATFORM=android TAG=scrollUsing2Points ./gradlew run`

```
  @android @scrollUsing2Points
  Scenario: Validating scroll functionality using 2 points
    Given I login to vodqa application using valid credentials
    When I scroll from one to another element point on vertical swiping screen
    Then Element text "Jasmine" should be visible
```

Steps to get started:

- Refer to [Getting started using teswiz](https://github.com/znsio/getting-started-with-teswiz/#step-to-start-using-tewiz-in-your-project)
- [Running the sample tests](https://github.com/znsio/teswiz/blob/main/docs/SampleTests-README.md)

If running the tests on a Windows OS machine, refer to the [FAQs](https://github.com/znsio/teswiz/blob/main/docs/FAQs-README.md).
