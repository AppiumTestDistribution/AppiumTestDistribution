package com.appium.ios;

import com.appium.manager.AvailabelPorts;
import com.appium.utils.CommandPrompt;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IOSDeviceConfiguration {
	public ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
	CommandPrompt commandPrompt = new CommandPrompt();
	AvailabelPorts ap = new AvailabelPorts();
	public HashMap<String, String> deviceMap = new HashMap<String, String>();
	Map<String, String> devices = new HashMap<>();
	public Process p;
	String getWebKitProxyPortToBeStarted;
	HashMap appiumServerProcess = new HashMap();

	public void checkIfiDeviceApiIsInstalled() throws InterruptedException, IOException {
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
			if(getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID.isEmpty()){
				return null;
			}else{
				String[] lines = getIOSDeviceID.split("\n");
				for (int i = 0; i < lines.length; i++) {
					lines[i] = lines[i].replaceAll("\\s+", "");
					deviceUDIDiOS.add(lines[i]);
				}
				return deviceUDIDiOS;
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String,String> getIOSUDIDHash() {
		try {
			String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
			if(getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID.isEmpty()){
				return null;
			}else{
				String[] lines = getIOSDeviceID.split("\n");
				for (int i = 0; i < lines.length; i++) {
					lines[i] = lines[i].replaceAll("\\s+", "");
					devices.put("deviceID" + i,lines[i]);
				}
                return devices;
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return null;
		}
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
	 *             Need to fix bug not fetching the version and product type for
	 *             report category
	 */

	public String getIOSDeviceProductTypeAndVersion(String udid) throws InterruptedException, IOException {
		return commandPrompt.runCommandThruProcessBuilder("ideviceinfo --udid "+udid+" | grep ProductType");
	}

	public String getDeviceName(String udid) throws InterruptedException, IOException {
		String deviceName = commandPrompt.runCommand("idevicename --udid " + udid).replace("\\W","_");
		return deviceName;
	}
	
	public boolean checkiOSDevice(String UDID) throws Exception{
		try{
            boolean checkDeviceExists = commandPrompt.runCommand("idevice_id --list").contains(UDID);
			if(checkDeviceExists){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
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
		getWebKitProxyPortToBeStarted = deviceMap.get(udid);
		p = Runtime.getRuntime()
				.exec("ios_webkit_debug_proxy -c " + udid + ":" + getWebKitProxyPortToBeStarted + " -d");
		System.out.println("WebKit Proxy is started on device " + udid + " and with port number "
				+ getWebKitProxyPortToBeStarted);
		//Add the Process ID to hashMap, which would be needed to kill IOSwebProxywhen required
		appiumServerProcess.put(Thread.currentThread().getId(),getPid(p));
	}

	public int getPid(Process process) {

		try {
			Class<?> cProcessImpl = process.getClass();
			Field fPid = cProcessImpl.getDeclaredField("pid");
			if (!fPid.isAccessible()) {
				fPid.setAccessible(true);
			}
			return fPid.getInt(process);
		} catch (Exception e) {
			return -1;
		}
	}

	public void destroyIOSWebKitProxy() throws IOException, InterruptedException {
		if (appiumServerProcess.get(Thread.currentThread().getId()) != "-1") {
			String command = "kill -9 " + appiumServerProcess.get(Thread.currentThread().getId());
			System.out.println("******************" + command);
			Runtime.getRuntime().exec(command);
		}


	}
}
