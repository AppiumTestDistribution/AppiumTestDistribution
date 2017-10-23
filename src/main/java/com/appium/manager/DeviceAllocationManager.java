package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AvailablePorts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {
    private ArrayList<String> devices = new ArrayList<String>();
    public ConcurrentHashMap<String, Object> deviceMapping;
    private static DeviceAllocationManager instance;
    private static AndroidDeviceConfiguration androidDevice;
    private static IOSDeviceConfiguration iosDevice;

    private DeviceAllocationManager() {
        try {
            iosDevice = new IOSDeviceConfiguration();
            androidDevice = new AndroidDeviceConfiguration();
            deviceMapping = new ConcurrentHashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeDevices();
    }

    public static DeviceAllocationManager getInstance() {
        if (instance == null) {
            instance = new DeviceAllocationManager();
        }
        return instance;
    }

    private void initializeDevices() {
        if (System.getenv("Platform") == null) {
            throw new IllegalArgumentException("Please execute with Platform environment"
                    + ":: Platform=android/ios/both mvn clean -Dtest=Runner test");
        }
        try {
            if (System.getProperty("os.name").toLowerCase().contains("mac")
                    && System.getenv("Platform").equalsIgnoreCase("iOS")
                    || System.getenv("Platform").equalsIgnoreCase("Both")) {
                if (iosDevice.getIOSUDID() != null) {
                    System.out.println("Adding iOS devices");
                    if (IOSDeviceConfiguration.validDeviceIds.size() > 0) {
                        devices.addAll(IOSDeviceConfiguration.validDeviceIds);
                    } else {
                        devices.addAll(IOSDeviceConfiguration.deviceUDIDiOS);
                    }
                }
                if (System.getenv("Platform").equalsIgnoreCase("android")
                        || System.getenv("Platform").equalsIgnoreCase("Both")) {
                    getAndroidDeviceSerial();
                }
            } else {
                getAndroidDeviceSerial();
            }
            for (String device : devices) {
                HashMap<String,Object> deviceState = new HashMap<>();
                deviceState.put("deviceState",true);
                deviceState.put("port", new AvailablePorts().getPort());
                deviceMapping.put(device, deviceState);
            }
            System.out.println(deviceMapping);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to initialize framework");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAndroidDeviceSerial() throws Exception {
        if (androidDevice.getDeviceSerial() != null) {
            System.out.println("Adding Android devices");
            if (AndroidDeviceConfiguration.validDeviceIds.size() > 0) {
                System.out.println("Adding Devices from DeviceList Provided");
                devices.addAll(AndroidDeviceConfiguration.validDeviceIds);
            } else {
                devices.addAll(AndroidDeviceConfiguration.deviceSerial);
            }
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
                ((HashMap) deviceMapping.get(device)).put("deviceState",false);
                return device;
            }
        }
        return "";
    }

    public void freeDevice() {
        System.out.println("DeviceMapping before free" + deviceMapping);
        ((HashMap) deviceMapping.get(DeviceManager.getDeviceUDID())).put("deviceState",true);
        System.out.println("DeviceMapping after free" + deviceMapping);
    }

    public void allocateDevice(String device, String deviceUDID) {
        if (device.isEmpty()) {
            DeviceManager.setDeviceUDID(deviceUDID);
        } else {
            DeviceManager.setDeviceUDID(device);
        }
    }
}
