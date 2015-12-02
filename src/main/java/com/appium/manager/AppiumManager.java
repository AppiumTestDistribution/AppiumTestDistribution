package com.appium.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * Appium Manager - this class contains method to start and stops appium server
 * To execute the tests from eclipse, you need to set PATH as
 * /usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin in run configuration
 */
public class AppiumManager {

	CommandPrompt cp = new CommandPrompt();
	AvailabelPorts ap = new AvailabelPorts();
	AppiumDriverLocalService appiumDriverLocalService;
	public static Properties prop = new Properties();
	public static InputStream input = null;
	/**
	 * start appium with default arguments
	 */
	public void startDefaultAppium() throws Exception {
		cp.runCommand("appium --session-override");
		Thread.sleep(5000);
	}

	/**
	 * start appium with auto generated ports : appium port, chrome port,
	 * bootstrap port and device UDID
	 */

	public AppiumServiceBuilder appiumServer(String deviceID) throws Exception {
		input = new FileInputStream("config.properties");
		prop.load(input);
		int port = ap.getPort();
		int chromePort = ap.getPort();
		int bootstrapPort = ap.getPort();
		AppiumServiceBuilder builder = new AppiumServiceBuilder()
				.withAppiumJS(new File("/usr/local/lib/node_modules/appium/bin/appium.js"))
				.withArgument(GeneralServerFlag.APP, System.getProperty("user.dir") + "/build/" + prop.getProperty("appname"))
				.withArgument(GeneralServerFlag.LOG_LEVEL, "info").withArgument(GeneralServerFlag.UIID, deviceID)
				.withArgument(GeneralServerFlag.CHROME_DRIVER_PORT,Integer.toString(chromePort))
				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,Integer.toString(bootstrapPort))
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.usingPort(port);
		/* and so on */;
		appiumDriverLocalService = builder.build();
		appiumDriverLocalService.start();
		return builder;

	}

	public URL getAppiumUrl() {
		return appiumDriverLocalService.getUrl();
	}

	public void destroyAppiumNode() {
		appiumDriverLocalService.stop();
	}

}
