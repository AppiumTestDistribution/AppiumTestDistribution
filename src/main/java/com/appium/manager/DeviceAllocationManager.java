package com.appium.manager;

import com.appium.device.DevicesByHost;
import com.appium.device.HostMachineDeviceManager;
import com.appium.utils.ArtifactsUploader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager extends ArtifactsUploader {

    private static DeviceAllocationManager instance;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private HostMachineDeviceManager hostMachineDeviceManager;
    private List<AppiumDevice> allDevices;
    private List<Thread> suspendedThreads;

    private DeviceAllocationManager() throws Exception {
        suspendedThreads = new ArrayList<>();
        hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
        ArtifactsUploader.getInstance().initializeArtifacts();
        DevicesByHost appiumDeviceByHost = hostMachineDeviceManager.getDevicesByHost();
        allDevices = appiumDeviceByHost.getAllDevices();

    }


    public static DeviceAllocationManager getInstance() {
        if (instance == null) {
            try {
                instance = new DeviceAllocationManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }


    public List<AppiumDevice> getDevices() {
        LOGGER.info("All devices connected");
        return hostMachineDeviceManager.getDevicesByHost().getAllDevices();
    }

    public synchronized List<AppiumDevice> getNextAvailableDevice(int devices) {
        List<AppiumDevice> appiumDevice = new ArrayList<>();
        for (int i = 0; i < devices; i++) {
            AppiumDevice appiumDevice1 = allDevices.get(i);
            if (appiumDevice1.isAvailable()) {
                appiumDevice1.blockDevice();
                appiumDevice.add(appiumDevice1);
            }
        }
        if (appiumDevice.size() >= 1) {
            return appiumDevice;
        }
        if (Thread.activeCount() > allDevices.size()) {
            suspendedThreads.add(Thread.currentThread());
            Thread.currentThread().suspend();
        }
        return null;
    }

    public synchronized void freeDevice() {
        List<AppiumDevice> appiumDevices = AppiumDeviceManager.getAppiumDevices();
        appiumDevices.forEach(appiumDevice -> {
            appiumDevice.freeDevice();
            LOGGER.info("DeAllocated Device " + appiumDevice.getDevice()
                .getUdid()
                + " from execution list");
        });

        if (suspendedThreads.size() > 0) {
            suspendedThreads.get(0).resume();
            suspendedThreads.remove(0);
        }
    }

    public void allocateDevice(List<AppiumDevice> appiumDevice) {
        LOGGER.info("Allocated DevicesDevice " + appiumDevice + " for Execution");
        AppiumDeviceManager.setDevice(appiumDevice);
    }

}
