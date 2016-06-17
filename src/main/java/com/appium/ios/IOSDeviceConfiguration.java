package com.appium.ios;

import com.appium.manager.AvailablePorts;
import com.appium.utils.CommandPrompt;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class IOSDeviceConfiguration {
    public ArrayList<String> deviceUDIDiOS = new ArrayList<>();
    CommandPrompt commandPrompt = new CommandPrompt();
    AvailablePorts ap = new AvailablePorts();
    public HashMap<String, String> deviceMap = new HashMap<>();
    Map<String, String> devices = new HashMap<>();
    public Process p, p1;
    public Properties prop = new Properties();
    public InputStream input = null;
    public static ConcurrentHashMap<Long, Integer> appiumServerProcess = new ConcurrentHashMap<>();

    public void checkIfiDeviceApiIsInstalled() throws InterruptedException, IOException {
        boolean checkMobileDevice =
            commandPrompt.runCommand("brew list").contains("ideviceinstaller");
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
            if (getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID
                .isEmpty()) {
                return null;
            } else {
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

    public Map<String, String> getIOSUDIDHash() {
        try {
            String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
            if (getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID
                .isEmpty()) {
                return null;
            } else {
                String[] lines = getIOSDeviceID.split("\n");
                for (int i = 0; i < lines.length; i++) {
                    lines[i] = lines[i].replaceAll("\\s+", "");
                    devices.put("deviceID" + i, lines[i]);
                }
                return devices;
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param UDID    - Device Serial ID
     * @param appPath - Path of the .ipa file
     * @throws InterruptedException
     * @throws IOException
     */
    public void installApp(String UDID, String appPath) throws InterruptedException, IOException {
        System.out.println("Installing App on device********" + UDID);
        commandPrompt.runCommand("ideviceinstaller --udid " + UDID + " --install " + appPath);
    }

    /**
     * @param UDID     - Device Serial ID
     * @param bundleID - Bundle ID of the .ipa file
     * @throws InterruptedException
     * @throws IOException
     */
    public void unInstallApp(String UDID, String bundleID)
        throws InterruptedException, IOException {
        System.out.println("Uninstalling App on device*******" + UDID);
        System.out.println("ideviceinstaller --udid " + UDID + " -U " + bundleID);
        commandPrompt.runCommand("ideviceinstaller --udid " + UDID + " -U " + bundleID);
    }

    /**
     * @param bundleID - Bundle ID of the .ipa file
     * @return - Returns booleans based on if the ios app provided is installed
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean checkIfAppIsInstalled(String bundleID) throws InterruptedException, IOException {
        return commandPrompt.runCommand("ideviceinstaller --list-apps").contains(bundleID);
    }

    /**
     * @throws InterruptedException
     * @throws IOException          Need to fix bug not fetching the version and product type for
     *                              report category
     */

    public String getIOSDeviceProductTypeAndVersion(String udid)
        throws InterruptedException, IOException {
        return commandPrompt
            .runCommandThruProcessBuilder("ideviceinfo --udid " + udid + " | grep ProductType");
    }

    public String getDeviceName(String udid) throws InterruptedException, IOException {
        return commandPrompt.runCommand("idevicename --udid " + udid).replace("\\W", "_");
    }

    public boolean checkiOSDevice(String UDID) throws Exception {
        try {
            String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
            return getIOSDeviceID.contains(UDID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public HashMap<String, String> setIOSWebKitProxyPorts(String device_udid) throws Exception {
        try {
            int webkitProxyPort = ap.getPort();
            deviceMap.put(device_udid, Integer.toString(webkitProxyPort));
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return deviceMap;
    }

    public String startIOSWebKit(String udid) throws IOException, InterruptedException {
        input = new FileInputStream("config.properties");
        prop.load(input);
        String serverPath = prop.getProperty("APPIUM_JS_PATH");
        File file = new File(serverPath);
        File currentPath = new File(file.getParent());
        System.out.println(currentPath);
        file = new File(currentPath + "/.." + "/..");
        String ios_web_lit_proxy_runner =
            file.getCanonicalPath() + "/bin/ios-webkit-debug-proxy-launcher.js";
        String webkitRunner =
            ios_web_lit_proxy_runner + " -c " + udid + ":" + deviceMap.get(udid) + " -d";
        System.out.println(webkitRunner);
        p1 = Runtime.getRuntime().exec(webkitRunner);
        System.out.println(
            "WebKit Proxy is started on device " + udid + " and with port number " + deviceMap
                .get(udid) + " and in thread " + Thread.currentThread().getId());
        //Add the Process ID to hashMap, which would be needed to kill IOSwebProxywhen required
        appiumServerProcess.put(Thread.currentThread().getId(), getPid(p1));
        System.out.println("Process ID's:" + appiumServerProcess);
        Thread.sleep(1000);
        return deviceMap.get(udid);
    }

    public long getPidOfProcess(Process p) {
        long pid = -1;

        try {
            if (p1.getClass().getName().equals("java.lang.UNIXProcess")) {
                Field f = p1.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                pid = f.getLong(p1);
                f.setAccessible(false);
            }
        } catch (Exception e) {
            pid = -1;
        }
        return pid;
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
        Thread.sleep(3000);
        if (appiumServerProcess.get(Thread.currentThread().getId()) != -1) {
            String process = "pgrep -P " + appiumServerProcess.get(Thread.currentThread().getId());
            Process p2 = Runtime.getRuntime().exec(process);
            BufferedReader r = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String command = "kill -9 " + r.readLine();
            System.out.println("Kills webkit proxy");
            System.out.println("******************" + command);
            Runtime.getRuntime().exec(command);
        }
    }

    public void checkExecutePermissionForIOSDebugProxyLauncher() throws IOException {
        input = new FileInputStream("config.properties");
        prop.load(input);
        String serverPath = prop.getProperty("APPIUM_JS_PATH");
        File file = new File(serverPath);
        File curentPath = new File(file.getParent());
        System.out.println(curentPath);
        file = new File(curentPath + "/.." + "/..");
        File executePermission =
            new File(file.getCanonicalPath() + "/bin/ios-webkit-debug-proxy-launcher.js");
        if (executePermission.exists()) {
            if (!executePermission.canExecute()) {
                executePermission.setExecutable(true);
                System.out.println("Access Granted for iOSWebKitProxyLauncher");
            } else {
                System.out.println("iOSWebKitProxyLauncher File already has access to execute");
            }
        }
    }
}
