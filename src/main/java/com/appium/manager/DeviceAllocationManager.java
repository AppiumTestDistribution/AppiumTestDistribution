package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AvailablePorts;
import com.thoughtworks.android.AndroidManager;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.DeviceManager;
import com.thoughtworks.iOS.IOSManager;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {

    private ArrayList<String> devices = new ArrayList<>();
    public ConcurrentHashMap<String, Object> deviceMapping;
    private static DeviceAllocationManager instance;
    private static IOSManager iosDevice;
    private static AndroidManager androidManager;
    public List<Device> deviceManager;

    private DeviceAllocationManager() throws Exception {
        try {
            iosDevice = new IOSManager();
            deviceMapping = new ConcurrentHashMap<>();
            deviceManager = new DeviceManager().getDeviceProperties();
            androidManager = new AndroidManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeDevices();
    }

    public static DeviceAllocationManager getInstance() throws Exception {
        if (instance == null) {
            instance = new DeviceAllocationManager();
        }
        return instance;
    }

    private void initializeDevices() throws Exception {
        isPlatformInEnv();
        if (SystemUtils.IS_OS_MAC) {
            List<Device> allSimulatorDetails = new IOSDeviceConfiguration()
                    .checkIfUserSpecifiedSimulatorAndGetUDID();
            if (System.getenv("Platform").equalsIgnoreCase("iOS")) {
                iosDevice.getAllAvailableDevices()
                        .forEach(device -> devices.add(device.getUdid()));
                allSimulatorDetails.forEach(device -> deviceManager.add(device));
                allSimulatorDetails.forEach(device -> devices.add(device.getUdid()));
                if (IOSDeviceConfiguration.validDeviceIds.size() > 0) {
                    System.out.println("Adding iOS Devices from DeviceList Provided");
                    devices.addAll(IOSDeviceConfiguration.validDeviceIds);
                }
            }
            if (System.getenv("Platform").equalsIgnoreCase("android")) {
                androidManager.getDeviceProperties()
                        .forEach(device -> this.devices.add(device.getUdid()));
            }
            if (System.getenv("Platform").equalsIgnoreCase("Both")) {
                allSimulatorDetails.forEach(device -> deviceManager.add(device));
                allSimulatorDetails.forEach(device -> devices.add(device.getUdid()));
                getAllConnectedDevices();
            }
        } else {
            androidManager.getDeviceProperties().forEach(device -> devices.add(device.getUdid()));
        }
        for (String device : devices) {
            HashMap<Object, Object> deviceState = new HashMap<>();
            deviceState.put("deviceState", true);
            deviceState.put("port", new AvailablePorts().getPort());
            deviceMapping.put(device, deviceState);
        }
        System.out.println(deviceMapping);
    }

    private void isPlatformInEnv() {
        if (System.getenv("Platform") == null) {
            throw new IllegalArgumentException("Please execute with Platform environment"
                    + ":: Platform=android/ios/both mvn clean -Dtest=Runner test");
        }
    }

    private void getAllConnectedDevices() {
        if (IOSDeviceConfiguration.validDeviceIds.size() > 0) {
            System.out.println("Adding iOS Devices from DeviceList Provided");
            devices.addAll(IOSDeviceConfiguration.validDeviceIds);
        }
        if (AndroidDeviceConfiguration.validDeviceIds.size() > 0) {
            System.out.println("Adding Android Devices from DeviceList Provided");
            devices.addAll(AndroidDeviceConfiguration.validDeviceIds);
        } else {
            deviceManager.forEach(device -> devices.add(device.getUdid()));
        }

    }

    public ArrayList<String> getDevices() {
        return devices;
    }

    public synchronized String getNextAvailableDeviceId() {
        ConcurrentHashMap.KeySetView<String, Object> devices = deviceMapping.keySet();
        int i = 0;
        for (String device : devices) {
            Thread t = Thread.currentThread();
            t.setName("Thread_" + i);
            i++;
            if (((HashMap) deviceMapping.get(device))
                    .get("deviceState").toString().equals("true")) {
                ((HashMap) deviceMapping.get(device)).put("deviceState", false);
                return device;
            }
        }
        return null;
    }

    public void freeDevice() {
        System.out.println("DeviceMapping before free" + deviceMapping);
        ((HashMap) deviceMapping.get(AppiumDeviceManager.getDeviceUDID())).put("deviceState", true);
        System.out.println("DeviceMapping after free" + deviceMapping);
    }

    public void allocateDevice(String device, String deviceUDID) {
        if (device.isEmpty()) {
            AppiumDeviceManager.setDeviceUDID(deviceUDID);
        } else {
            AppiumDeviceManager.setDeviceUDID(device);
        }
    }
}
