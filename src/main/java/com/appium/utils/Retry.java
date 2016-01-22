package com.appium.utils;

import java.lang.reflect.Method;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.annotation.values.RetryCount;

public class Retry implements IRetryAnalyzer {
	private int retryCount = 0;
	private int maxRetryCount;

	public boolean retry(ITestResult result) {		
		Method[] methods = result.getInstance().getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().equals(result.getMethod().getMethodName())) {
				if (m.isAnnotationPresent(RetryCount.class)) {
					RetryCount ta = m.getAnnotation(RetryCount.class);
					maxRetryCount = ta.maxRetryCount();
				}
			}

		}
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Test Failed");
			if (retryCount == maxRetryCount) {
				System.out.println("Log report");
			}
			if (retryCount < maxRetryCount) {
				retryCount++;
				System.out.println("Retrying Failed Test Cases " + retryCount + "out of " + maxRetryCount);
				return true;

			}
		}

		return false;
	}
}