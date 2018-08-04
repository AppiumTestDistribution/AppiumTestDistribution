package com.test.cucumber.stepdefinitions;


import com.appium.manager.AppiumDriverManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

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
