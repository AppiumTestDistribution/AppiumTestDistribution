package com.rotate.raspberry;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.annotation.values.Rotate;

public class Pi {
	public boolean rotate = false;

	@BeforeMethod
	public void setUp(Method name) {
		if (name.isAnnotationPresent(Rotate.class)) {
			Rotate ta = name.getAnnotation(Rotate.class);
			rotate = ta.rotate();
		}
		if (rotate == true) {
			SSHJavaRemote remotePi = new SSHJavaRemote();
			remotePi.rotateLandscape("10.4.1.237", "python Documents/rotate_landscape.py");
		}

	}

	@Test
	@Rotate(rotate = true)
	public void rotate() {
		System.out.println("1");
		System.out.println("1");
		System.out.println("1");
		System.out.println("1");
		System.out.println("1");
		
	}
	
	@AfterMethod
	public void tearDown(Method name) {
		if (name.isAnnotationPresent(Rotate.class)) {
			Rotate ta = name.getAnnotation(Rotate.class);
			rotate = ta.rotate();
		}
		if (rotate == true) {
			SSHJavaRemote remotePi = new SSHJavaRemote();
			remotePi.rotateLandscape("10.4.1.237", "python Documents/rotate_portrait.py");
		}

	}	
	
}
