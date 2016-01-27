package com.appium.ios;

import com.appium.manager.AvailabelPorts;
import com.appium.utils.CommandPrompt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.tools.ant.util.SymbolicLinkUtils;

public class IOSDeviceConfiguration {
	public ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
	CommandPrompt commandPrompt = new CommandPrompt();
	AvailabelPorts ap = new AvailabelPorts();
	public HashMap<String, String> deviceMap = new HashMap<String, String>();
	public Process p;

	public void checkIfMobileDeviceApiIsInstalled() throws InterruptedException, IOException {
		boolean checkMobileDevice = commandPrompt.runCommand("brew list").contains("ideviceinstaller");
		if (checkMobileDevice) {
			System.out.println("iDeviceInstaller already exists");
		} else {
			System.out.println("Brewing iDeviceInstaller API....");
			commandPrompt.runCommand("brew install ideviceinstaller");
		}

	}

	public ArrayList<String> getIOSUDID() {
		try {
			String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
			String[] lines = getIOSDeviceID.split("\n");
			for (int i = 0; i < lines.length; i++) {
				lines[i] = lines[i].replaceAll("\\s+", "");
				deviceUDIDiOS.add(lines[i]);
			}
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceUDIDiOS;
	}

	/**
	 * 
	 * @param UDID
	 *            - Device Serial ID
	 * @param appPath
	 *            - Path of the .ipa file
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void installApp(String UDID, String appPath) throws InterruptedException, IOException {
		System.out.println("Installing App on device********" + UDID);
		commandPrompt.runCommand("ideviceinstaller --udid " + UDID + " --install " + appPath);
	}

	/**
	 * 
	 * @param UDID
	 *            - Device Serial ID
	 * @param bundleID
	 *            - Bundle ID of the .ipa file
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void unInstallApp(String UDID, String bundleID) throws InterruptedException, IOException {
		System.out.println("Uninstalling App on device*******" + UDID);
		System.out.println("ideviceinstaller --udid " + UDID + " -U " + bundleID);
		commandPrompt.runCommand("ideviceinstaller --udid " + UDID + " -U " + bundleID);
	}

	/**
	 * 
	 * @param bundleID
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean checkIfAppIsInstalled(String bundleID) throws InterruptedException, IOException {
		boolean appAlreadyExists = commandPrompt.runCommand("ideviceinstaller --list-apps").contains(bundleID);
		return appAlreadyExists;
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 *             Need to fix bug not fetching the verison and product type for
	 *             report catergory
	 */

	public String getIOSDeviceProductTypeAndVersion(String udid) throws InterruptedException, IOException {
		System.out.println("ideviceinfo --udid " + udid + " | grep ProductType");
		System.out.println("ideviceinfo --udid " + udid + " | grep ProductVersion");
		String productType = commandPrompt.runCommand("ideviceinfo --udid " + udid);
		System.out.println(productType);
		String version = commandPrompt.runCommand("ideviceinfo --udid " + udid);
		System.out.println(version);
		System.out.println(productType.concat(version));
		return productType.concat(version);
	}

	public String getDeviceName(String udid) throws InterruptedException, IOException {
		String deviceName = commandPrompt.runCommand("idevicename --udid " + udid);
		return deviceName;
	}

	public HashMap<String, String> setIOSWebKitProxyPorts() throws Exception {
		try {
			int webKitProxyPort = ap.getPort();
			String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
			String[] lines = getIOSDeviceID.split("\n");
			for (int i = 0; i < lines.length; i++) {
				lines[i] = lines[i].replaceAll("\\s+", "");
				deviceMap.put(lines[i], Integer.toString(webKitProxyPort));
			}
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceMap;
	}

	public void startIOSWebKit(String udid) throws IOException {
		String getWebKitProxyPortToBeStarted = deviceMap.get(udid);
		p = Runtime.getRuntime()
				.exec("ios_webkit_debug_proxy -c " + udid + ":" + getWebKitProxyPortToBeStarted + " -d");
		System.out.println("WwebKit Proxy is started on device " + udid + " and with port number "
				+ getWebKitProxyPortToBeStarted);
	}

	public void destroyIOSWebKitProxy() {
		p.destroy();
	}

	public static void main(String[] arg) throws Exception {
		IOSDeviceConfiguration configuration = new IOSDeviceConfiguration();
		configuration.getIOSUDID();
		/*
		 * configuration.setIOSWebKitProxyPorts();
		 * configuration.startIOSWebKit(configuration.deviceUDIDiOS.get(0));
		 * Thread.sleep(3000); configuration.destroyIOSWebKitProxy();
		 */
		//configuration.getIOSDeviceProductTypeAndVersion(configuration.deviceUDIDiOS.get(0));
		System.out.println(configuration.getDeviceName(configuration.getIOSUDID().get(0)));
		// configuration.unInstallApp(configuration.deviceUDIDiOS.get(0),
		// "com.tesco.sample");
		// configuration.installApp(configuration.deviceUDIDiOS.get(0),
		// "/Users/saikrisv/Downloads/WordPress25-1.ipa");
		// configuration.checkIfMobileDeviceApiIsInstalled();
	}

}
