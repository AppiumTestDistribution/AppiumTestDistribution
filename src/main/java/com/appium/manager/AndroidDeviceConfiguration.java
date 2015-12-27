package com.appium.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AndroidDeviceConfiguration {

	CommandPrompt cmd = new CommandPrompt();
	Map<String, String> devices = new HashMap<String, String>();
	ArrayList<String> deviceSerail= new ArrayList<String>();
	ArrayList<String> deviceModel= new ArrayList<String>();

	/**
	 * This method start adb server
	 */
	public void startADB() throws Exception {
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if (lines.length == 1)
			System.out.println("adb service already started");
		else if (lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if (lines[0].contains("internal or external command")) {
			System.out.println("Please set ANDROID_HOME in your system variables");
			System.exit(0);
		}
	}

	/**
	 * This method stop adb server
	 */
	public void stopADB() throws Exception {
		cmd.runCommand("adb kill-server");
	}
	
	/**
	 * This method return connected devices
	 * 
	 * @return hashmap of connected devices information
	 */
	public Map<String, String> getDevices() throws Exception {

		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if (lines.length <= 1) {
			System.out.println("No Device Connected");
			stopADB();
			System.exit(0); // exit if no connected devices found
		}

		for (int i = 1; i < lines.length; i++) {
			lines[i] = lines[i].replaceAll("\\s+", "");

			if (lines[i].contains("device")) {
				lines[i] = lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.model")
						.replaceAll("\\s+", "");
				String brand = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.brand")
						.replaceAll("\\s+", "");
				String osVersion = cmd
						.runCommand("adb -s " + deviceID + " shell getprop ro.build.version.release")
						.replaceAll("\\s+", "");
				String deviceName = brand + " " + model;

				devices.put("deviceID" + i, deviceID);
				devices.put("deviceName" + i, deviceName);
				devices.put("osVersion" + i, osVersion);

				System.out.println("Following device is connected");
				System.out.println(deviceID + " " + deviceName + " " + osVersion + "\n");
			} else if (lines[i].contains("unauthorized")) {
				lines[i] = lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];

				System.out.println("Following device is unauthorized");
				System.out.println(deviceID + "\n");
			} else if (lines[i].contains("offline")) {
				lines[i] = lines[i].replaceAll("offline", "");
				String deviceID = lines[i];

				System.out.println("Following device is offline");
				System.out.println(deviceID + "\n");
			}
		}
		return devices;
	}
	
	
	
	public ArrayList<String> getDeviceSerail() throws Exception {

		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if (lines.length <= 1) {
			System.out.println("No Device Connected");
			stopADB();
			System.exit(0); // exit if no connected devices found
		}

		for (int i = 1; i < lines.length; i++) {
			lines[i] = lines[i].replaceAll("\\s+", "");

			if (lines[i].contains("device")) {
				lines[i] = lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				deviceSerail.add(deviceID);
			} else if (lines[i].contains("unauthorized")) {
				lines[i] = lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];

				System.out.println("Following device is unauthorized");
				System.out.println(deviceID + "\n");
			} else if (lines[i].contains("offline")) {
				lines[i] = lines[i].replaceAll("offline", "");
				String deviceID = lines[i];

				System.out.println("Following device is offline");
				System.out.println(deviceID + "\n");
			}
		}
		return deviceSerail;
	}
	
	/*
	 * This method gets the device model name 
	 */
	public String deviceModel(String deviceID){
		String deviceModelName = null;
		try {
			deviceModelName = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.product.model").replaceAll("\\W", "");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceModelName;
		
	}
	
	
	/*
	 * This method gets the device OS name 
	 */
	public String deviceOS(String deviceID){
		String deviceOSLevel = null;
		try {
			deviceOSLevel = cmd.runCommand("adb -s " + deviceID + " shell getprop ro.build.version.release").replaceAll("\\W", "");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceOSLevel;
		
	}
	
}
