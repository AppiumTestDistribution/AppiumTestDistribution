package com.appium.manager;

import com.appium.utils.*;
import com.github.yunusmete.stf.api.STFService;
import com.github.yunusmete.stf.model.DeviceBody;
import com.github.yunusmete.stf.rest.DeviceResponse;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {

    private static DeviceAllocationManager instance;
    private static final String STF_SERVICE_URL = System.getenv("STF_URL");
    private static final String ACCESS_TOKEN = System.getenv("STF_ACCESS_TOKEN");
    static STFService service;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private HostMachineDeviceManager hostMachineDeviceManager;
    private List<AppiumDevice> allDevices;
    private List<Thread> suspendedThreads;
    private AppiumDriverManager appiumDriverManager;

    private DeviceAllocationManager() throws Exception {
        try {
            isPlatformInEnv();
            suspendedThreads = new ArrayList<>();
            hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
            if(CapabilityManager.getInstance().isApp()) {
                ArtifactsUploader.getInstance().initializeArtifacts();
            }
            DevicesByHost appiumDeviceByHost = hostMachineDeviceManager.getDevicesByHost();
            allDevices = appiumDeviceByHost.getAllDevices();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static DeviceAllocationManager getInstance() throws Exception {
        if (instance == null) {
            instance = new DeviceAllocationManager();
        }
        return instance;
    }

    private void isPlatformInEnv() {
        if (System.getenv("Platform") == null) {
            throw new IllegalArgumentException("Please execute with Platform environment"
                    + ":: Platform=android/ios/both mvn clean -Dtest=Runner test");
        }
    }

    private void connectToSTF() {
        try {
            if (STF_SERVICE_URL != null && ACCESS_TOKEN != null) {
                connectToSTFServer();
            }
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<AppiumDevice> getDevices() {
        LOGGER.info("All devices connected");
        return hostMachineDeviceManager.getDevicesByHost().getAllDevices();
    }

    public synchronized AppiumDevice getNextAvailableDevice() {
        int i = 0;
        for (AppiumDevice device : allDevices) {
            Thread t = Thread.currentThread();
            t.setName("Thread_" + i);
            i++;
            if (device.isAvailable()) {
                device.blockDevice();
                return device;
            }
        }
        if(Thread.activeCount() > allDevices.size()) {
            suspendedThreads.add(Thread.currentThread());
            Thread.currentThread().suspend();
        }
        return null;
    }

    public synchronized void freeDevice() {
        AppiumDeviceManager.getAppiumDevice().freeDevice();
        LOGGER.info("DeAllocated Device " + AppiumDeviceManager.getAppiumDevice().getDevice()
                .getUdid()
                + " from execution list");
        if (suspendedThreads.size() > 0) {
            suspendedThreads.get(0).resume();
            suspendedThreads.remove(0);
        }
    }

    public void allocateDevice(AppiumDevice appiumDevice) {
        LOGGER.info("Allocated Device " + appiumDevice + " for Execution");
        AppiumDeviceManager.setDevice(appiumDevice);
    }

    private void connectToSTFServer() throws MalformedURLException, URISyntaxException {
        DeviceResponse devices = service.getDevices();
        List<com.github.yunusmete.stf.model.Device> deviceList = devices.getDevices();
        for (com.github.yunusmete.stf.model.Device device : deviceList) {
            if (device.isPresent()) {
                if (device.getOwner() == null) {
                    service.addDeviceToUser(new DeviceBody(device.getSerial(), 90000));
                }
            }
        }
    }
}
