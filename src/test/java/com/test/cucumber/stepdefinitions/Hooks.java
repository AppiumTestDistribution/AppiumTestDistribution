package com.test.cucumber.stepdefinitions;

import com.cucumber.listener.ExtentCucumberFormatter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;

public class Hooks extends ExtentCucumberFormatter {
    @Before public void beforeClass(Scenario scenario) throws Exception {
        System.out.println("Inside Before" + Thread.currentThread().getId());
    }


    @After public void afterClass(Scenario scenario) throws InterruptedException, IOException {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot =
                    ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException wde) {
                System.err.println(wde.getMessage());
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            }
            System.out.println("Inside After" + Thread.currentThread().getId());
        }
        getDriver().quit();

    }

}
