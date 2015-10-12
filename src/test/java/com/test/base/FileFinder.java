package com.test.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.junit.FormatterElement;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import com.test.site.HomePageTest1;

public class FileFinder {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		List<Class> testCases = new ArrayList<Class>();
//		testCases.add(HomePageTest1.class);
//		testCases.add(HomePageTest2.class);
//		testCases.add(HomePageTest3.class);
//		testCases.add(HomePageTest4.class);
//		testCases.add(HomePageTest5.class);
		
			  List textFiles = new ArrayList();	
			  File dir = new File(System.getProperty("user.dir") + "/src/test/java/com/test/site");
			  for (File file : dir.listFiles()) {
			    if (file.getName().contains(("Test"))) {
			      textFiles.add(file.getClass().getClassLoader());
			    }
			  }
			  
			 
			}
	

	}
