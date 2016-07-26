package com.appium.ios;

import com.appium.manager.AvailabelPorts;
import com.appium.utils.CommandPrompt;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class IOSDeviceConfiguration {
    public ArrayList<String> deviceUDIDiOS = new ArrayList<String>();
    CommandPrompt commandPrompt = new CommandPrompt();
    AvailabelPorts ap = new AvailabelPorts();
    public HashMap<String, String> deviceMap = new HashMap<String, String>();
    Map<String, String> devices = new HashMap<>();
    public Process p;
    public Process p1;
    public Properties prop = new Properties();
    public InputStream input = null;

    public final static int IOS_UDID_LENGTH = 40;

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
            int startPos = 0;
            int endPos = IOS_UDID_LENGTH - 1;
            String profile = "system_profiler SPUSBDataType | sed -n -E -e '/(iPhone|iPad)/,"
                    + "/Serial/s/ Serial Number: (.+)/\\1/p'";
            String getIOSDeviceID = commandPrompt.runProcessCommandToGetDeviceID(profile);
            if (getIOSDeviceID == null || getIOSDeviceID.equalsIgnoreCase("") || getIOSDeviceID
                    .isEmpty()) {
                return null;
            } else {
                while (endPos < getIOSDeviceID.length()) {
                    deviceUDIDiOS.add(getIOSDeviceID.substring(startPos, endPos + 1));
                    startPos += IOS_UDID_LENGTH;
                    endPos += IOS_UDID_LENGTH;
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
     * @param bundleID
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean checkIfAppIsInstalled(String bundleID) throws InterruptedException, IOException {
        boolean appAlreadyExists =
                commandPrompt.runCommand("ideviceinstaller --list-apps").contains(bundleID);
        return appAlreadyExists;
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
        String deviceName =
                commandPrompt.runCommand("idevicename --udid " + udid).replace("\\W", "_");
        return deviceName;
    }

    public boolean checkiOSDevice(String UDID) throws Exception {
        try {
            String getIOSDeviceID = commandPrompt.runCommand("idevice_id --list");
            boolean checkDeviceExists = getIOSDeviceID.contains(UDID);
            if (checkDeviceExists) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public HashMap<String, String> setIOSWebKitProxyPorts(String device_udid) throws Exception {
        try {
            int webkitproxyport = ap.getPort();
            deviceMap.put(device_udid, Integer.toString(webkitproxyport));
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
        File curentPath = new File(file.getParent());
        System.out.println(curentPath);
        file = new File(curentPath + "/.." + "/..");
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
            if (executePermission.canExecute() == false) {
                executePermission.setExecutable(true);
                System.out.println("Access Granted for iOSWebKitProxyLauncher");
            } else {
                System.out.println("iOSWebKitProxyLauncher File already has access to execute");
            }
        }
    }

    @Test
    public void testApp() {
        IOSDeviceConfiguration iosDeviceConfiguration = new IOSDeviceConfiguration();
        try {
            commandPrompt.runCommandThruProcessBuilder("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iosDeviceConfiguration.getIOSUDID();
    }
}
