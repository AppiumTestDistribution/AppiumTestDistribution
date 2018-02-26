package com.appium.manager;

import com.appium.utils.AppiumDevice;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import com.appium.utils.JsonParser;
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
    private AppiumDriverManager appiumDriverManager;
    private static boolean simCapsPresent = false;
    private static boolean deviceCapsPresent = false;

    private DeviceAllocationManager() throws Exception {
        try {
            isPlatformInEnv();
            hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
            ArtifactsUploader.getInstance().uploadFilesToRemoteMachines();
            DevicesByHost appiumDeviceByHost = hostMachineDeviceManager.getDevicesByHost();
            allDevices = appiumDeviceByHost.getAllDevices();
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
        return null;
    }

    public void freeDevice() {
        AppiumDeviceManager.getDevice().freeDevice();
        LOGGER.info("DeAllocated Device " + AppiumDeviceManager.getDevice().getDevice()
                .getUdid()
                + " from execution list");
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