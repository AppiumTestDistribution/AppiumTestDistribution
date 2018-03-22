package com.appium.ios;

import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumManagerFactory;
import com.appium.manager.ConfigFileManager;
import com.appium.manager.IAppiumManager;
import com.appium.utils.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class IOSDeviceConfiguration {
    public static List<AppiumDevice> deviceUDIDiOS = new ArrayList<>();
    private final ConfigFileManager prop;
    CommandPrompt commandPrompt = new CommandPrompt();
    public static List<String> validDeviceIds = new ArrayList<>();
    private DevicesByHost devicesByHost;

    public final static int IOS_UDID_LENGTH = 40;
    public final static int SIM_UDID_LENGTH = 36;

    public IOSDeviceConfiguration() throws IOException {
        prop = ConfigFileManager.getInstance();
        devicesByHost = HostMachineDeviceManager.getInstance().getDevicesByHost();
    }

    public List<AppiumDevice> checkIfUserSpecifiedSimulatorAndGetUDID() {
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
