package com.appium.utils;

import com.appium.manager.AppiumManagerFactory;
import com.appium.manager.IAppiumManager;
import com.thoughtworks.device.Device;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HostMachineDeviceManager {

    private static final String PLATFORM = "Platform";
    private static final String UDIDS = "udids";
    private CapabilityManager capabilityManager;
    private DevicesByHost devicesByHost;
    private static HostMachineDeviceManager instance;

    private HostMachineDeviceManager() {
        try {
            capabilityManager = CapabilityManager.getInstance();
            initializeDevicesByHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HostMachineDeviceManager getInstance() throws IOException {
        if (instance == null) {
            instance = new HostMachineDeviceManager();
        }
        return instance;
    }

    private void initializeDevicesByHost() {
        if (devicesByHost == null) {
            try {
                Map<String, List<AppiumDevice>> allDevices = getDevices();
                Map<String, List<AppiumDevice>> devicesFilteredByPlatform
                        = filterByDevicePlatform(allDevices);
                Map<String, List<AppiumDevice>> devicesFilteredByUserSpecified
                        = filterByUserSpecifiedDevices(devicesFilteredByPlatform);
                devicesByHost = new DevicesByHost(devicesFilteredByUserSpecified);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, List<AppiumDevice>> filterByUserSpecifiedDevices(
            Map<String, List<AppiumDevice>> devicesByHost) {
        String udidsString = System.getenv(UDIDS);
        List<String> udids = udidsString == null ? Collections.emptyList()
                : Arrays.asList(udidsString.split(","));

        if (udids.size() == 0) {
            return devicesByHost;
        } else {
            HashMap<String, List<AppiumDevice>> filteredDevicesHostName = new HashMap<>();
            devicesByHost.forEach((hostName, appiumDevices) -> {
                List<AppiumDevice> filteredDevices = appiumDevices.stream()
                        .filter(appiumDevice -> udids.contains(appiumDevice
                                .getDevice().getUdid())).collect(Collectors.toList());
                if (!filteredDevices.isEmpty()) {
                    filteredDevicesHostName.put(hostName, filteredDevices);
                }
            });
            return filteredDevicesHostName;
        }
    }

    private Map<String, List<AppiumDevice>> filterByDevicePlatform(
            Map<String, List<AppiumDevice>> devicesByHost) {
        String platform = System.getenv(PLATFORM);
        if (platform.equalsIgnoreCase(OSType.BOTH.name())) {
            return devicesByHost;
        } else {
            HashMap<String, List<AppiumDevice>> filteredDevicesHostName = new HashMap<>();
            devicesByHost.forEach((hostName, appiumDevices) -> {
                List<AppiumDevice> filteredDevices = appiumDevices.stream().filter(appiumDevice ->
                        appiumDevice.getDevice().getOs()
                                .equalsIgnoreCase(platform)).collect(Collectors.toList());
                if (!filteredDevices.isEmpty()) {
                    filteredDevicesHostName.put(hostName, filteredDevices);
                }
            });
            return filteredDevicesHostName;
        }
    }

    public DevicesByHost getDevicesByHost() {
        return devicesByHost;
    }

    private Map<String, List<AppiumDevice>> getDevices() throws Exception {
        String platform = System.getenv(PLATFORM);
        Map<String, List<AppiumDevice>> devicesByHost = new HashMap<>();
        if (capabilityManager.getCapabilities().has("hostMachines")) {
            JSONArray hostMachines = capabilityManager.getHostMachineObject();
            for (Object hostMachine : hostMachines) {
                JSONObject hostMachineJson = (JSONObject) hostMachine;
                String machineIP = hostMachineJson.getString("machineIP");
                IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(machineIP);
                List<Device> devices = appiumManager.getDevices(machineIP, platform);

                if ((!platform.equalsIgnoreCase("android")
                        && capabilityManager.isApp()
                        && capabilityManager.isSimulatorAppPresentInCapsJson()
                        && hostMachineJson.has("simulators"))
                        && !capabilityManager.getCapabilityObjectFromKey("iOS")
                        .has("browserName")) {
                    JSONArray simulators = hostMachineJson.getJSONArray("simulators");
                    List<Device> simulatorsToBoot = getSimulatorsToBoot(
                            machineIP, simulators);
                    devices.addAll(simulatorsToBoot);
                }

                List<AppiumDevice> appiumDevices = getAppiumDevices(machineIP, devices);

                devicesByHost.put(machineIP, appiumDevices);
            }
        } else {
            throw new RuntimeException("Provide hostMachine in Caps.json for execution");
        }
        return devicesByHost;
    }


    private List<AppiumDevice> getAppiumDevices(String machineIP, List<Device> devices) {
        return devices.stream().map(getAppiumDevice(machineIP)).collect(Collectors.toList());
    }

    private Function<Device, AppiumDevice> getAppiumDevice(String machineIP) {
        return device -> {
            AppiumDevice appiumDevice = new AppiumDevice(device, machineIP);
            try {
                appiumDevice.setPort(new AvailablePorts().getAvailablePort(machineIP));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return appiumDevice;
        };
    }

    private List<Device> getSimulatorsToBoot(String machineIP, JSONArray simulators)
            throws IOException, InterruptedException {
        List<Device> devices = new ArrayList<>();
        for (Object simulator : simulators) {
            JSONObject simulatorJson = (JSONObject) simulator;
            String deviceName = simulatorJson.getString("deviceName");
            String os = simulatorJson.getString("OS");
            IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(machineIP);
            Device device = appiumManager.getSimulator(machineIP, deviceName, os);
            if (!device.getState().equals("Booted")) {
                devices.add(device);
            }
        }
        return devices;
    }
}

