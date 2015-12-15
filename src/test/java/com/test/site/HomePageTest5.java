package com.test.site;


import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.appium.manager.BaseTest;



public class HomePageTest5 extends BaseTest {


	@Test

	public void testMethodFive_5() throws Exception {
		System.out.println("ThreadName: " + Thread.currentThread().getName() + Thread.currentThread().getStackTrace()[1].getClassName());
		waitForElement(By.id("com.android2.calculator3:id/cling_dismiss"), 30);
		driver.findElement(By.id("com.android2.calculator3:id/cling_dismiss")).click();
		driver.findElement(By.id("com.android2.calculator3:id/digit9")).click();
		driver.findElement(By.id("com.android2.calculator3:id/minus")).click();
		driver.findElement(By.id("com.android2.calculator3:id/digit9")).click();
		driver.findElement(By.id("com.android2.calculator3:id/equal")).click();

	}

}
