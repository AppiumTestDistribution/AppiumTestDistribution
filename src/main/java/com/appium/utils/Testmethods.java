package com.appium.utils;

import java.util.List;

public class Testmethods 
{
	
	private String methodName;
	private String gifPath;
	public String getGifPath() {
		return gifPath;
	}
	public void setGifPath(String gifPath) {
		this.gifPath = gifPath;
	}
	private List<String> screnShots;
	
	public String getMethodName() 
	{
		return methodName;
	}
	public void setMethodName(String methodName) 
	{
		this.methodName = methodName;
	}
	public List<String> getScrenShots() 
	{
		return screnShots;
	}
	public void setScrenShots(List<String> screnShots) 
	{
		this.screnShots = screnShots;
	}
	
	
	

}
