package com.test.steps;

import static org.junit.Assert.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;

public class HomePage{
	static AppiumDriver driver ;
	
	public HomePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
	@FindBy(xpath = "//*[@name='action_bar_up_navigation'][1]")
	private WebElement LHN;
	
	@FindBy(xpath = "//*[@name='My orders']")
	private WebElement Myorders;
	
	@FindBy(name = "Sign in")
	private WebElement Signin;
	
	@FindBy(name = "Sign out")
	private WebElement Signout;
	
	@FindBy(name = "Wi-Fi")
	private WebElement WiFi;
	
	public void NavigateToLHN(){
		try{
//			WebDriverWait wait= new WebDriverWait(driver,10);
//	        wait.until(ExpectedConditions.visibilityOf(LHN));
//			LHN.click();
//	        wait.until(ExpectedConditions.visibilityOf(Myorders));
//	        Myorders.click();
//	        Thread.sleep(6000L);
////	        driver.removeApp("com.tesco.grocery");
//	        driver.resetApp();
	        Thread.sleep(6000L);
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	
	public void verifyUserLoggedInorNot(){
		try{
			assertTrue("User is not signed in",Signin.isDisplayed());
		}catch(Exception e){
			
		}
	}
}
