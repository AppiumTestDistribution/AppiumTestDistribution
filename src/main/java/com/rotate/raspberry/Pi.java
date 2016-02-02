package com.rotate.raspberry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.annotation.values.Rotate;
import com.raspberry.utils.PiAddress;

public class Pi {
	public boolean rotate = false;
	PiAddress piAdd = new PiAddress();
	List<String> receivedPiAddress;
	public static Properties prop = new Properties();
	public InputStream input = null;
	int piAddressPlaceHolder;
	@BeforeClass
	public void loadPiAdderess() throws IOException {
		receivedPiAddress = piAdd.fetchPiAddress(System.getProperty("user.dir") + "/PiAddress");
	}

	@BeforeMethod
	public void setUp(Method name) throws IOException {
		input = new FileInputStream("config.properties");
		prop.load(input);
		//Get the current Thread Id
		if (prop.getProperty("RUNNER").equalsIgnoreCase("distribute")) {
			System.out.println(
					"******Running Test in Distributed ******" + Thread.currentThread().getName().split("-")[3]);
			piAddressPlaceHolder = Integer.valueOf(Thread.currentThread().getName().split("-")[3]) - 1;
		} else if (prop.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
			System.out
					.println("******Running Test in Parallel *******" + Thread.currentThread().getName().split("-")[1]);
			piAddressPlaceHolder = Integer.valueOf(Thread.currentThread().getName().split("-")[1]);
		}
		
		if (name.isAnnotationPresent(Rotate.class)) {
			Rotate ta = name.getAnnotation(Rotate.class);
			rotate = ta.rotate();
		}
		if (rotate == true) {
			SSHJavaRemote remotePi = new SSHJavaRemote();
			//remotePi.rotateDevice("10.4.1.237", "python Documents/rotate_landscape.py");
			remotePi.rotateDevice(receivedPiAddress.get(piAddressPlaceHolder), "python Documents/rotate_landscape.py");
		}

	}

	@Test
	@Rotate(rotate = true)
	public void rotate() {
		System.out.println(receivedPiAddress.get(0));

	}

	@AfterMethod
	public void tearDown(Method name) {
		if (name.isAnnotationPresent(Rotate.class)) {
			Rotate ta = name.getAnnotation(Rotate.class);
			rotate = ta.rotate();
		}
		if (rotate == true) {
			SSHJavaRemote remotePi = new SSHJavaRemote();
			//remotePi.rotateDevice("10.4.1.237", "python Documents/rotate_portrait.py");
			remotePi.rotateDevice(receivedPiAddress.get(piAddressPlaceHolder),"python Documents/rotate_portrait.py");
		}

	}

}
