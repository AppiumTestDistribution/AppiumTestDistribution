package com.test.site;




import com.annotation.values.RetryCount;
import com.annotation.values.SkipIf;
import com.appium.utils.Retry;
import org.openqa.selenium.By;
import org.testng.annotations.Test;



public class HomePageTest1 extends UserBaseTest{
	

	@Test(retryAnalyzer=Retry.class)
	@RetryCount(maxRetryCount=2)
	@SkipIf(platform = "AndroidDriver")
    public void testMethodOne_1() throws Exception  {
	    
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit2")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		captureScreenShot("TestMethod1");
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test(retryAnalyzer=Retry.class)
	@SkipIf(platform = "AndroidDriver")
    public void testMethodOne_6() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		captureScreenShot("TestMethod1");
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		
		//getDriver().close();
    }
	
	@Test(retryAnalyzer=Retry.class)
	@RetryCount(maxRetryCount=2)
	@SkipIf(platform = "AndroidDriver")
    public void testMethodOne_7() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit33")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		captureScreenShot("TestMethod7");
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	
	@Test(retryAnalyzer=Retry.class)
	@SkipIf(platform = "AndroidDriver")
    public void testMethodOne_8() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		captureScreenShot("TestMethod8");
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test(retryAnalyzer=Retry.class)
	@SkipIf(platform = "AndroidDriver")
    public void testMethodOne_9() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		captureScreenShot("TestMethod1");
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
	
	@Test(retryAnalyzer=Retry.class)
	@SkipIf(platform = "AndroidDriver")
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

	@Test(retryAnalyzer=Retry.class)
    public void testMethodOne_11() throws Exception  {
	
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit3")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/plus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		captureScreenShot("TestMethod1");
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
    }
}
