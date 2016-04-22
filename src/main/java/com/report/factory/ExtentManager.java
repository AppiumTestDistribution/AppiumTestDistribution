package com.report.factory;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	public static ExtentReports instance;

	public synchronized static ExtentReports getInstance() {
		if (instance == null) {
			System.out.println(System.getProperty("user.dir"));
			instance = new ExtentReports(System.getProperty("user.dir") + "/target/ExtentReport.html");
			try{
				if(System.getenv("ExtentX").equalsIgnoreCase("true")){
					instance.x();
				}
			}catch (Exception e){
				System.out.println("Not taking ExtendReporting");
			}
		}
		return instance;
	}
}
