package com.appium.manager;

import com.appium.android.AndroidDeviceConfiguration;
import com.appium.entities.MobilePlatform;
import com.appium.ios.IOSDeviceConfiguration;
import com.appium.utils.AvailablePorts;
import com.openstf.DeviceApi;
import com.openstf.STFService;
import com.thoughtworks.android.AndroidManager;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.DeviceManager;
import com.thoughtworks.iOS.IOSManager;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private DeviceApi deviceApi;
    private static final String STF_SERVICE_URL = System.getenv("STF_URL");  // Change this URL
    private static final String ACCESS_TOKEN = System.getenv("STF_ACCESS_TOKEN");  // Change this access token


    private DeviceAllocationManager() throws Exception {
        try {
            iosDevice = new IOSManager();
            deviceMapping = new ConcurrentHashMap<>();
            deviceManager = new CopyOnWriteArrayList<>(new DeviceManager().getDeviceProperties());
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
                allocateUniqueSimulatorDetails(allSimulatorDetails);
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
                allocateUniqueSimulatorDetails(allSimulatorDetails);
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
    }

    private void allocateUniqueSimulatorDetails(List<Device> allSimulatorDetails) {
        allSimulatorDetails.stream().forEach(device -> {
            Optional<Device> first = deviceManager.stream().filter(device1 -> device.getUdid()
                    .equals(device1.getUdid())).findFirst();
            if (!first.isPresent()) {
                deviceManager.add(device);
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
        if (STF_SERVICE_URL != null && ACCESS_TOKEN != null) {
            this.deviceApi.releaseDevice(AppiumDeviceManager.getDeviceUDID());
        }
        System.out.println("DeviceMapping after free" + deviceMapping);
    }

    public void allocateDevice(String device, String deviceUDID) {
        if (device.isEmpty()) {
            AppiumDeviceManager.setDeviceUDID(deviceUDID);
        } else {
            AppiumDeviceManager.setDeviceUDID(device);
        }
        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
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
    }

    private void connectToSTFServer() throws MalformedURLException, URISyntaxException {
        STFService stfService = new STFService(STF_SERVICE_URL,
                ACCESS_TOKEN);
        this.deviceApi = new DeviceApi(stfService);
        this.deviceApi.connectDevice(AppiumDeviceManager.getDeviceUDID());
    }
}
