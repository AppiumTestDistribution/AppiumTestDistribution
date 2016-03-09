package com.test.steps;

import cucumber.api.java.en.Given;


public class HomePageSteps extends CucumberBaseTest{
	
//	 protected AppiumDriver driver = new DriverFactory().getDriver();

	@Given("^i'm on application landing page$")
	public void i_m_on_application_landing_page() throws Throwable {
	   new HomePage(driver).NavigateToLHN();
//	   new HomePage(driver).verifyUserLoggedInorNot();
	   
	}
}
