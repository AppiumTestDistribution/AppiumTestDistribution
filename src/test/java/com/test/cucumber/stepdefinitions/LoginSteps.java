package com.test.cucumber.stepdefinitions;

import com.cucumber.listener.ExtentCucumberFormatter;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

/**
 * Created by saikrisv on 09/03/16.
 */
public class LoginSteps extends ExtentCucumberFormatter {

    @Given("^i'm on homepage$")
    public void iMOnHomepage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("*****DriverSession***"+Thread.currentThread().getName() + getDriver().toString());
        throw new PendingException();
    }

    @When("^i click on login$")
    public void iClickOnLogin() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^i should see login editbox$")
    public void iShouldSeeLoginEditbox() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^I accept the tip screen$")
    public void iAcceptTheTipScreen() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        getDriver().findElement(By.xpath(".//*[@text='OK']")).click();
    }
}
