package com.test.cucumber.stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.IOException;

public class Hooks {
    @Before
    public void beforeClass(Scenario scenario) throws Exception {
        //Do Anything
    }

    @After
    public void afterClass(Scenario scenario) throws InterruptedException, IOException {


    }
}
