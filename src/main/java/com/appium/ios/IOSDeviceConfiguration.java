package com.appium.ios;

import com.appium.manager.ConfigFileManager;
import com.appium.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void setValidDevices(List<String> deviceID) {
        deviceID.forEach(deviceList -> {
            if (deviceList.length() == IOSDeviceConfiguration.IOS_UDID_LENGTH) {
                validDeviceIds.add(deviceList);
            }
        });
    }
}
