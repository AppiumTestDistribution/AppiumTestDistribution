package com.test.cucumber;

import com.test.site.UserBaseTest;
import io.appium.java_client.MobileBy;
import io.cucumber.core.runtime.CucumberExecutionContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.appium.manager.AppiumDriverManager.getDriver;

public class SampleSteps extends UserBaseTest {

    public SampleSteps() {
        super();
    }

    @Given("^I have the this useless scenario$")
    public void uselessScenario() {
        System.out.println("Passed");
        System.out.println("*****DriverSession***"
                + Thread.currentThread().getName()
                + getDriver().toString());
    }

    @And("I click on <t{int}> number")
    public void iClickOnTNumber(int arg0) throws InterruptedException {
        System.out.println("sleep for " + arg0 + " sec");
        Thread.sleep(arg0 * 1000);
        System.out.println("Passed");
    }

    @Then("^It should finish$")
    public void It_should_finish() {
        System.out.println("Passed");
    }

    @Given("I login")
    public void iLogin() {
        login("login").click();
    }

    @When("I drag & drop")
    public void iDragDrop() {
        waitForElement("dragAndDrop").click();
    }

    @Then("drag and drop should have worked")
    public void dragAndDropShouldHaveWorked() {
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions
                        .elementToBeClickable(MobileBy.AccessibilityId("dragMe")));
    }
}
