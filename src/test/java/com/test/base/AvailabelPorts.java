package com.test.base;

import java.net.ServerSocket;

public class AvailabelPorts {
	
	public String getPort() throws Exception
	{
		ServerSocket socket = new ServerSocket(0);
		socket.setReuseAddress(true);
		String port = Integer.toString(socket.getLocalPort()); 
		socket.close();
		return port;
	}
}
