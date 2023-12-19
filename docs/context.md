---
title: SessionContext & TestExecutionContext
---

# Managing Test Data Using TestExecutionContext

There are times when we need to create data and then pass it to subsequent steps in the test execution. For example:

- Create a new user (using random credentials).
- Reset this user's state after test execution completes.

## Potential Solutions

1. One way to accomplish this is to keep passing the relevant information as parameters in various methods. However, this can reduce your test readability.

2. Another way is to use static variables which can be accessed from anywhere. **DO NOT USE THIS APPROACH**, as it will create problems when executing tests in parallel.

3. **Use TestExecutionContext**

## SessionContext & TestExecutionContext

- Each executing test has a TestExecutionContext.
- SessionContext stores information about all currently executing tests in the form of a map of TestExecutionContext.
- Use TestExecutionContext in your test framework to retrieve information about:
    - Driver
    - Device
    - Any information you want to store as part of your test execution.

- TestExecutionContext for a test is available only during the lifetime of the test. You cannot share test execution context between different tests.

## Examples

- To retrieve TestExecutionContext for the current test:
  ```java
  TestExecutionContext testExecutionContext = Session.getTestExecutionContext(Thread.currentThread().getId()); 
  ```
- To retrieve driver for the current test:
  ```java
  testExecutionContext.getDriver();
    ```
- To retrieve the device ID for the current test:
  ```java
  testExecutionContext.getDevice();
  ```
- To retrieve the configuration used for ATD:
    ```java
    testExecutionContext.getLoadedConfig();
    ```
- To add test state to the TestExecutionContext:
    ```java
    testExecutionContext.addTestState("newuser", "newPassword");
    ```
- To retrieve test state from the TestExecutionContext:
    ```java
    testExecutionContext.getTestState("newuser").toString();
    testExecutionContext.getTestStateAsString("newuser");
    ```
Use TestExecutionContext to manage test data and pass it between different steps of your test execution efficiently.
