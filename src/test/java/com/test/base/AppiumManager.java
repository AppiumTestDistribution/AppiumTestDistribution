package com.test.base;

/**
 * Appium Manager - this class contains method to start and stops appium server
 */
public class AppiumManager {

	CommandPrompt cp = new CommandPrompt();
	AvailabelPorts ap = new AvailabelPorts();

	/**
	 * start appium with default arguments
	 */
	public void startDefaultAppium() throws Exception {
		cp.runCommand("appium --session-override");
		Thread.sleep(5000);
	}

	/**
	 * start appium with auto generated ports : appium port, chrome port, and
	 * bootstap port
	 */
	public String startAppium(String devices) throws Exception {
		// start appium server
		String port = ap.getPort();
		String chromePort = ap.getPort();
		String bootstrapPort = ap.getPort();
		String command = "appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
				+ bootstrapPort + " -U " + devices;
		System.out.println(command);

		String output = cp.runCommand(command);
		

		if (output.contains("not")) {
			System.out.println("\nAppium is not installed");
			System.exit(0);
		}
		return port;
	}

	/**
	 * start appium with modified arguments : appium port, chrome port, and
	 * bootstap port as user pass port number
	 * 
	 * @param appium
	 *            port
	 * @param chrome
	 *            port
	 * @param bootstrap
	 *            port
	 */
	public void startAppium(String port, String chromePort, String bootstrapPort) throws Exception {
		String command = "appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
				+ bootstrapPort;
		System.out.println(command);
		String output = cp.runCommand(command);
		System.out.println(output);
	}

}
