package com.test.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.RemoteWebElement;


public class MyStepdefs {
    RemoteWebElement edit;

    @When("I click on {int} number")
    public void iClickOnNumber(int arg0) {
        System.out.println("I click on {int} number : " + arg0);
        //edit = (RemoteWebElement) getDriver().findElementByClassName("Edit");
    }

    @Then("It should finish")
    public void itShouldFinnish() {
        //Assert.assertNotNull(edit);
        System.out.println("It should finish");

    }

    @Given("I accept the tip screen")
    public void i_accept_the_tip_screen() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("I accept the tip screen step executed ");
    }


}
