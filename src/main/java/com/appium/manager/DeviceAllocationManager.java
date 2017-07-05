package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 *  DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {
    private ArrayList<String> devices = new ArrayList<String>();
    private ConcurrentHashMap<String, Boolean> deviceMapping =
            new ConcurrentHashMap<String, Boolean>();
    private static DeviceAllocationManager instance;
    private static AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();
    private static IOSDeviceConfiguration iosDevice;

    private DeviceAllocationManager() {
        try {
            iosDevice = new IOSDeviceConfiguration();
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
            throw new RuntimeException("Please execute with Platform environment"
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
                deviceMapping.put(device, true);
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
        ConcurrentHashMap.KeySetView<String, Boolean> devices = deviceMapping.keySet();
        int i = 0;
        for (String device : devices) {
            Thread t = Thread.currentThread();
            t.setName("Thread_" + i);
            System.out.println("Parallel Thread is::" + t.getName());
            i++;
            if (deviceMapping.get(device)) {
                deviceMapping.put(device, false);
                return device;
            }
        }
        throw new RuntimeException("No Available Devices");
    }

    public void freeDevice() {
        deviceMapping.put(DeviceManager.getDeviceUDID(), true);
    }

    public void allocateDevice(String device, String deviceUDID) {
        if (device.isEmpty()) {
            DeviceManager.setDeviceUDID(deviceUDID);
        } else {
            DeviceManager.setDeviceUDID(device);
        }
    }
}
