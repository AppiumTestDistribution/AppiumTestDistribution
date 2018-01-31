package com.appium.ios;

import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.ConfigFileManager;
import com.appium.utils.AvailablePorts;
import com.appium.utils.CommandPrompt;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import com.thoughtworks.device.Device;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class IOSDeviceConfiguration {
    public static List<Device> deviceUDIDiOS = new ArrayList<>();
    private final ConfigFileManager prop;
    CommandPrompt commandPrompt = new CommandPrompt();
    AvailablePorts ap = new AvailablePorts();
    public HashMap<String, String> deviceMap = new HashMap<String, String>();
    public Process p;
    public Process p1;
    public static List<String> validDeviceIds = new ArrayList<>();
    private DevicesByHost devicesByHost;

    public final static int IOS_UDID_LENGTH = 40;
    public final static int SIM_UDID_LENGTH = 36;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

    public static ConcurrentHashMap<Long, Integer> iosDebugProxyProcess = new ConcurrentHashMap<>();


    public IOSDeviceConfiguration() throws IOException {
        prop = ConfigFileManager.getInstance();
        devicesByHost = HostMachineDeviceManager.getInstance();
    }

    public List<Device> checkIfUserSpecifiedSimulatorAndGetUDID() {
        String xcode_version = "";
        try {
            xcode_version = commandPrompt.runCommand("xcodebuild -version");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        if (new SimManager().isSimulatorObjectAvailableInCapsJson()) {
            if (xcode_version.contains("9")) {
                deviceUDIDiOS = devicesByHost.getAllSimulators();
            } else {
                new RuntimeException("Xcode version should be 9.0 to run parallel simulators");
            }
        }
        return deviceUDIDiOS;
    }


    /**
     * @throws InterruptedException
     * @throws IOException          Need to fix bug not fetching the version and product type for
     *                              report category
     *                              iPhone 7,1
     */

    public String getIOSDeviceProductTypeAndVersion()
            throws InterruptedException, IOException {
        return devicesByHost.getDeviceProperty(AppiumDeviceManager.getDeviceUDID())
                .getDeviceModel();
    }

    public String getDeviceName() throws InterruptedException, IOException {
        return devicesByHost.getDeviceProperty(AppiumDeviceManager.getDeviceUDID()).getName();
    }

    public String getIOSDeviceProductVersion() throws InterruptedException, IOException {
        return devicesByHost.getDeviceProperty(AppiumDeviceManager.getDeviceUDID())
                .getOsVersion();
    }

    public HashMap<String, String> setIOSWebKitProxyPorts() {
        try {
            int webkitproxyport = ap.getPort();
            deviceMap.put(AppiumDeviceManager.getDeviceUDID(), Integer.toString(webkitproxyport));
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceMap;
    }

    public String startIOSWebKit() throws IOException, InterruptedException {
        setIOSWebKitProxyPorts();

        String webkitRunner = "ios_webkit_debug_proxy -c " + AppiumDeviceManager.getDeviceUDID()
                + ":" + deviceMap.get(AppiumDeviceManager.getDeviceUDID());
        p1 = Runtime.getRuntime().exec(webkitRunner);
        LOGGER.info(
                "WebKit Proxy is started on device " + AppiumDeviceManager.getDeviceUDID()
                        + " and with port number "
                        + deviceMap.get(AppiumDeviceManager.getDeviceUDID())
                        + " and in thread "
                        + Thread.currentThread().getId());
        //Add the Process ID to hashMap, which would be needed to kill IOSwebProxywhen required
        iosDebugProxyProcess.put(Thread.currentThread().getId(), getPid(p1));
        System.out.println("Process ID's:" + iosDebugProxyProcess);
        return String.valueOf(deviceMap.get(AppiumDeviceManager.getDeviceUDID()));
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
        if (iosDebugProxyProcess.get(Thread.currentThread().getId()) != -1) {
            String command = "kill -9 " + iosDebugProxyProcess.get(Thread.currentThread().getId());
            LOGGER.info("Kills webkit proxy" + "******************" + command);
            Runtime.getRuntime().exec(command);
        }
    }

    public void checkExecutePermissionForIOSDebugProxyLauncher() throws IOException {
        String serverPath = prop.getProperty("APPIUM_JS_PATH");
        File file = new File(serverPath);
        File currentPath = new File(file.getParent());
        System.out.println(currentPath);
        file = new File(currentPath + "/.." + "/..");
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

    public void setValidDevices(List<String> deviceID) {
        deviceID.forEach(deviceList -> {
            if (deviceList.length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                validDeviceIds.add(deviceList);
            }
        });
    }
}
