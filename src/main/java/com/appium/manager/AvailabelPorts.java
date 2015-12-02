package com.appium.manager;

import java.net.ServerSocket;

public class AvailabelPorts {
	
	/*
	 * Generates Random ports
	 * Used during starting appium server
	 */
	public int getPort() throws Exception
	{
		ServerSocket socket = new ServerSocket(0);
		socket.setReuseAddress(true);
		int port = socket.getLocalPort(); 
		socket.close();
		return port;
	}
}
