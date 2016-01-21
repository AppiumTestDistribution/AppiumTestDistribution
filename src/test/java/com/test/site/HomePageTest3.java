package com.test.site;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.report.factory.ExtentTestManager;


public class HomePageTest3 extends UserBaseTest{


	@Test
	public void testMethodOne_3() throws InterruptedException {
		
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		Thread.sleep(3000);
		ExtentTestManager.logOutPut(Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		getDriver().findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit42")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/minus")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/digit9")).click();
		getDriver().findElement(By.id("com.android2.calculator3:id/equal")).click();
		//getDriver().close();
	}
}
