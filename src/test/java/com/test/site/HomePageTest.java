package com.test.site;

import org.testng.annotations.Test;

import com.test.base.BaseTest;

public class HomePageTest extends BaseTest{

	
	@Test
	public void testApp(){
		driver.get("http://www.google.com");
	}
}
