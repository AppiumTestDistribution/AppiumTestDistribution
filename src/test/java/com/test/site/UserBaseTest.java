package com.test.site;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.appium.manager.AppiumParallelTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class UserBaseTest extends AppiumParallelTest{

	AppiumDriver<MobileElement> driver;
	
	@BeforeMethod()
	public void startApp(Method method) throws Exception {
     this.driver= startAppiumServerInParallel(method.getName());
	}
	
	@AfterMethod()
	public void killServer(ITestResult result){
      killAppiumServer(result);		
	}
	

	public AppiumDriver<MobileElement> getDriver(){
		return this.driver;
	}
	
<<<<<<< Updated upstream
	@BeforeClass
	public void beforeClass(){
		System.out.println("Before Class" + Thread.currentThread().getId());
=======
	@BeforeClass()
	public void beforeClass()
	{
	System.out.println("Before Class called" + Thread.currentThread().getId());	
	}
	
	@AfterClass()
	public void afterClass(){
		System.out.println("After Class called" + Thread.currentThread().getId());
>>>>>>> Stashed changes
	}

	
	@AfterClass
	public void afterClass(){
		System.out.println("After Class" + Thread.currentThread().getId());
	}
}
