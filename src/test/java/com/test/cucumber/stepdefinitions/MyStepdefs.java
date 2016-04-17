package com.test.cucumber.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

/**
 * Created by vimalraj on 12/02/16.
 */
public class MyStepdefs {

    @Given("I have (\\d+) cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        System.out.format("Cukes:"+cukes);
        Assert.assertTrue(false);
    }

    @Given("I have (\\d+) cukes in my bellies")
    public void I_have_cukes_in_my_bellies(int cukes) {
        System.out.format("Cukes:"+cukes);
    }

    @Then("^I print$")
    public void iPrint() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }
}
