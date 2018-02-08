package com.appium.manager;

import com.appium.ios.SimManager;
import com.appium.utils.*;
import com.github.yunusmete.stf.api.STFService;
import com.github.yunusmete.stf.model.DeviceBody;
import com.github.yunusmete.stf.rest.DeviceResponse;
import com.thoughtworks.android.AndroidManager;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {

    public ConcurrentHashMap<String, List<AppiumDevice>> deviceMapping;
    private static DeviceAllocationManager instance;
    private static AndroidManager androidManager;
    private static final String STF_SERVICE_URL = System.getenv("STF_URL");
    private static final String ACCESS_TOKEN = System.getenv("STF_ACCESS_TOKEN");
    static STFService service;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private SimManager simManager = new SimManager();
    private AppiumDriverManager appiumDriverManager;
    private static boolean simCapsPresent = false;
    private static boolean deviceCapsPresent = false;
    private DevicesByHost appiumDeviceByHost;

    private DeviceAllocationManager() throws Exception {
        try {
            deviceMapping = new ConcurrentHashMap<>();
            appiumDeviceByHost = HostMachineDeviceManager.getInstance();
            deviceMapping.putAll(appiumDeviceByHost.getAllHostDevices());
            androidManager = new AndroidManager();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setFlagsForCapsValues();
    }


    public static DeviceAllocationManager getInstance() throws Exception {
        if (instance == null) {
            instance = new DeviceAllocationManager();
        }
        return instance;
    }

    private void setFlagsForCapsValues() throws FileNotFoundException {
        String filePath = getCapsFilePath();
        JSONObject jsonParsedObject = new JsonParser(filePath).getObjectFromJSON();
        JSONObject iOSCaps = jsonParsedObject.getJSONObject("iOS");
        if (iOSCaps != null) {
            iOSCaps.keySet().forEach(key -> {
                boolean app = key.equals("app");
                if (app
                        && iOSCaps.getJSONObject(key).has("simulator")) {
                    simCapsPresent = true;
                }
                if (app
                        && iOSCaps.getJSONObject(key).has("device")) {
                    deviceCapsPresent = true;
                }
            });
        }
    }

    private String getCapsFilePath() throws FileNotFoundException {
        String filePath = appiumDriverManager.getCapsPath();
        if (new File(filePath).exists()) {
            Path path = FileSystems.getDefault().getPath(filePath);
            if (!path.getParent().isAbsolute()) {
                filePath = path.normalize()
                        .toAbsolutePath().toString();
            }
            return filePath;
        } else {
            throw new FileNotFoundException("Capability file not found");
        }
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public List<AppiumDevice> getDevices() {
        LOGGER.info("All devices connected");
        return HostMachineDeviceManager.getInstance().getAllDevices();
    }

    public synchronized String getNextAvailableDeviceId() {
        ConcurrentHashMap.KeySetView<String, List<AppiumDevice>> devices =
                deviceMapping.keySet();
        final String[] deviceReadyForExecution = new String[1];
        int i = 0;
        for (String device : devices) {
            Thread t = Thread.currentThread();
            t.setName("Thread_" + i);
            i++;

            deviceMapping.get(device).forEach(appiumDevice -> {
                if (appiumDevice.getDeviceState().equalsIgnoreCase("Available")) {
                    appiumDevice.setDeviceState("Busy");
                     deviceReadyForExecution[0] = appiumDevice.getDevice().getUdid();
                }
            });
            return deviceReadyForExecution[0];
        }
        return null;
    }

    public void freeDevice() {
        HostMachineDeviceManager.getInstance()
                .getDeviceProperty(AppiumDeviceManager.getDeviceUDID()).setDeviceState("Available");
        LOGGER.info("DeAllocated Device " + AppiumDeviceManager.getDeviceUDID()
                + " from execution list");
    }

    public void allocateDevice(String device, String deviceUDID) {
        if (device.isEmpty()) {
            LOGGER.info("Allocated Device " + deviceUDID + " for Execution");
            AppiumDeviceManager.setDeviceUDID(deviceUDID);
        } else {
            LOGGER.info("Allocated Device " + deviceUDID + " for Execution");
            AppiumDeviceManager.setDeviceUDID(device);
        }
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