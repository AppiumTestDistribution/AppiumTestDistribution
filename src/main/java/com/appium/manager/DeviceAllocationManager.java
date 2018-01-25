package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.ios.SimManager;
import com.appium.utils.AvailablePorts;
import com.appium.utils.DevicesByHost;
import com.appium.utils.HostMachineDeviceManager;
import com.appium.utils.JsonParser;
import com.github.yunusmete.stf.api.STFService;
import com.github.yunusmete.stf.model.DeviceBody;
import com.github.yunusmete.stf.rest.DeviceResponse;
import com.thoughtworks.android.AndroidManager;
import com.thoughtworks.device.Device;
import com.thoughtworks.utils.CommandPromptUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {

    private ArrayList<String> devices = new ArrayList<>();
    public ConcurrentHashMap<String, Object> deviceMapping;
    private static DeviceAllocationManager instance;
    private static AndroidManager androidManager;
    public List<Device> deviceList;
    private static final String STF_SERVICE_URL = System.getenv("STF_URL");
    private static final String ACCESS_TOKEN = System.getenv("STF_ACCESS_TOKEN");
    static STFService service;
    private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
    private SimManager simManager = new SimManager();
    private AppiumDriverManager appiumDriverManager;
    private static boolean simCapsPresent = false;
    private static boolean deviceCapsPresent = false;
    private DevicesByHost devicesByHost;

    private DeviceAllocationManager() throws Exception {
        try {
            deviceMapping = new ConcurrentHashMap<>();
            deviceList = new ArrayList<>();
            devicesByHost = HostMachineDeviceManager.getInstance();
            deviceList = devicesByHost.getAllDevices();

            androidManager = new AndroidManager();
            appiumDriverManager = new AppiumDriverManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setFlagsForCapsValues();
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
            String platform = System.getenv("Platform");
            if (platform.equalsIgnoreCase("iOS")) {
                LOGGER.info("Adding only iOS Devices");

                if (IOSDeviceConfiguration.validDeviceIds.size() > 0) {
                    LOGGER.info("Adding iOS Devices from DeviceList Provided");
                    devices.addAll(IOSDeviceConfiguration.validDeviceIds);
                } else {
                    if (deviceCapsPresent) {
                        devicesByHost.getAllIOSDevices()
                                .forEach(device -> devices.add(device.getUdid()));
                    }
                    if (simManager.isSimulatorObjectAvailableInCapsJson() && simCapsPresent) {
                        allocateUniqueSimulatorDetails(allSimulatorDetails);
                        allSimulatorDetails.forEach(device -> devices.add(device.getUdid()));
                    }
                }
            }
            if (platform.equalsIgnoreCase("android")) {
                if (AndroidDeviceConfiguration.validDeviceIds.size() > 0) {
                    LOGGER.info("Adding Android Devices from DeviceList Provided");
                    devices.addAll(AndroidDeviceConfiguration.validDeviceIds);
                } else {
                    devicesByHost.getAllAndroidDevices()
                            .forEach(device -> this.devices.add(device.getUdid()));
                }
            }
            if (platform.equalsIgnoreCase("Both")) {
                if (simManager.isSimulatorObjectAvailableInCapsJson()) {
                    allocateUniqueSimulatorDetails(allSimulatorDetails);
                }
                getAllConnectedDevices();
            }
        } else {
            androidManager.getDevices().forEach(device -> devices.add(device.getUdid()));
        }
        for (String device : devices) {
            HashMap<Object, Object> deviceState = new HashMap<>();
            deviceState.put("deviceState", true);
            deviceState.put("port", new AvailablePorts().getPort());
            deviceMapping.put(device, deviceState);
        }
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

    private void allocateUniqueSimulatorDetails(List<Device> allSimulatorDetails) {
        allSimulatorDetails.stream().forEach(device -> {
            Optional<Device> first = deviceList.stream().filter(device1 -> device.getUdid()
                    .equals(device1.getUdid())).findFirst();

            if (!first.isPresent()) {
                deviceList.add(device);
            }

        });
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
            deviceList.forEach(device -> {
                if (device.getUdid().length() == IOSDeviceConfiguration.SIM_UDID_LENGTH
                        && new SimManager().isSimulatorObjectAvailableInCapsJson() && simCapsPresent) {
                    devices.add(device.getUdid());
                } else if (device.getUdid().length() == IOSDeviceConfiguration.IOS_UDID_LENGTH
                        && deviceCapsPresent) {
                    devices.add(device.getUdid());
                } else if (device.getUdid().length() < IOSDeviceConfiguration.SIM_UDID_LENGTH) {
                    devices.add(device.getUdid());
                }
            });
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

    public ArrayList<String> getDevices() {
        LOGGER.info("All devices connected" + devices);
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
        ((HashMap) deviceMapping.get(AppiumDeviceManager.getDeviceUDID())).put("deviceState", true);
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