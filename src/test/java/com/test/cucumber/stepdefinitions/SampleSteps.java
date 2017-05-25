package com.test.cucumber.stepdefinitions;

import com.cucumber.listener.ExtentCucumberFormatter;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.appium.manager.AppiumDriverManager.getDriver;

public class SampleSteps extends ExtentCucumberFormatter {

    public SampleSteps() throws Exception {
        super();
    }

    @Given
        ("^I have the this useless scenario$") public void uselessScenario() {
        System.out.println("Passed");
        System.out.println(
            "*****DriverSession***" + Thread.currentThread().getName() + getDriver().toString());
    }

    @When
        ("^I click on (\\d+) number") public void I_sleep_for_seconds(int arg1)
        throws InterruptedException {
        Thread.sleep(arg1 * 1000);
        getDriver().findElementByXPath(".//*[@text=" + arg1 + "]").click();
        System.out.println("Passed");
    }

    @Then
        ("^It should finnish$") public void It_should_finnish() {
        System.out.println("Passed");
    }
}
