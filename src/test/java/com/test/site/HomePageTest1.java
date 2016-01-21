package com.test.site;




import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.report.factory.ExtentTestManager;



public class HomePageTest1 extends UserBaseTest{
	

	@Test
    public void testMethodOne_1() throws Exception  {
	    
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		ExtentTestManager.logOutPut("Login Test");
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit2")).click();
		ExtentTestManager.logOutPut("Enter UserName and Password");
		ExtentTestManager.logOutPut("Login Successfull");
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test
    public void testMethodOne_6() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		ExtentTestManager.logOutPut("Second Add method");
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		
		//getDriver().close();
    }
	
	@Test
    public void testMethodOne_7() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		ExtentTestManager.logOutPut(Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit33")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	
	@Test
    public void testMethodOne_8() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		ExtentTestManager.logOutPut(Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test
    public void testMethodOne_9() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test
    public void testMethodOne_10() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test
    public void testMethodOne_11() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
}
