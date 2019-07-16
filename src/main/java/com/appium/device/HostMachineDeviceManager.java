package com.appium.device;

import com.appium.manager.AppiumDevice;
import com.appium.manager.AppiumManagerFactory;
import com.appium.manager.IAppiumManager;
import com.appium.utils.Api;
import com.appium.utils.AvailablePorts;
import com.appium.capabilities.CapabilityManager;
import com.appium.utils.OSType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.device.Device;
import com.report.factory.TestStatusManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class HostMachineDeviceManager {

    private static final String PLATFORM = "Platform";
    private static final String UDIDS = "udids";
    private CapabilityManager capabilityManager;
    private DevicesByHost devicesByHost;
    private static HostMachineDeviceManager instance;
    private String atdHost = null;
    private String atdPort = null;

    private HostMachineDeviceManager() {
        try {
            capabilityManager = CapabilityManager.getInstance();
            atdHost = CapabilityManager.getInstance()
                .getMongoDbHostAndPort().get("atdHost");
            atdPort = CapabilityManager.getInstance()
                .getMongoDbHostAndPort().get("atdPort");
            initializeDevicesByHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HostMachineDeviceManager getInstance() {
        if (instance == null) {
            instance = new HostMachineDeviceManager();
        }
        return instance;
    }

    private void initializeDevicesByHost() throws IOException {
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
            if (atdHost != null && atdPort != null) {
                Api api = new Api();
                api.getResponse("http://" + atdHost + ":" + atdPort + "/drop");
                api.post("http://" + atdHost + ":" + atdPort + "/devices",
                    new ObjectMapper().writerWithDefaultPrettyPrinter()
                        .writeValueAsString(devicesByHost));
                api.post("http://" + atdHost + ":" + atdPort + "/envInfo",
                    new TestStatusManager()
                        .envInfo(devicesByHost.getAllDevices().size()));
                api.post("http://" + atdHost + ":" + atdPort + "/envInfo/appium/logs",
                    new TestStatusManager()
                        .appiumLogs(devicesByHost));
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

    private List<AppiumDevice> getDevicesByIP(String ip, String platform,
                                              JSONObject hostMachineJson)
        throws Exception {
        IAppiumManager appiumManager = AppiumManagerFactory.getAppiumManager(ip);
        List<Device> devices = appiumManager.getDevices(ip, platform);
        if ((!platform.equalsIgnoreCase("android")
            && capabilityManager.isSimulatorAppPresentInCapsJson()
            && hostMachineJson.has("simulators"))
            && !capabilityManager.getCapabilityObjectFromKey("iOS")
            .has("browserName")) {
            JSONArray simulators = hostMachineJson.getJSONArray("simulators");
            List<Device> simulatorsToBoot = getSimulatorsToBoot(
                ip, simulators);
            devices.addAll(simulatorsToBoot);
        }
        return getAppiumDevices(ip, devices);
    }


    private Map<String, List<AppiumDevice>> getDevices() throws Exception {
        String platform = System.getenv(PLATFORM);
        Map<String, List<AppiumDevice>> devicesByHost = new HashMap<>();

        if (capabilityManager.getCapabilities().has("hostMachines")) {
            JSONArray hostMachines = capabilityManager.getHostMachineObject();
            for (Object hostMachine : hostMachines) {
                JSONObject hostMachineJson = (JSONObject) hostMachine;
                Object machineIPs = hostMachineJson.get("machineIP");
                if (machineIPs instanceof JSONArray) {
                    for (Object machineIP : (JSONArray) machineIPs) {
                        String ip = machineIP.toString();
                        devicesByHost.put(ip, getDevicesByIP(ip, platform, hostMachineJson));
                    }
                } else if (machineIPs instanceof String) {
                    String ip = hostMachineJson.getString("machineIP");
                    if (CapabilityManager.getInstance().isCloud(ip)) {
                        List<Device> device = new ArrayList<>();
                        JSONObject cloud = capabilityManager.getCapabilityObjectFromKey("cloud");
                        cloud.toMap().forEach((devicePlatform, devices) -> {
                            ((List) devices).forEach(o -> {
                                Device d = new Device();
                                d.setOs(devicePlatform);
                                d.setOsVersion(((Map) o).get("osVersion").toString());
                                d.setName(((Map) o).get("deviceName").toString());
                                d.setCloud(true);
                                device.add(d);
                            });
                        });
                        devicesByHost.put(ip, getAppiumDevices(ip, device));
                    } else {
                        if (capabilityManager.getCapabilityObjectFromKey("genycloud") != null) {
                            JSONObject cloud = capabilityManager
                                .getCapabilityObjectFromKey("genycloud");
                            for (Map.Entry<String, Object> entry : cloud.toMap().entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();
                                GenyMotionManager.connectToGenyCloud(key, value);
                            }
                        }
                        devicesByHost.put(ip, getDevicesByIP(ip, platform, hostMachineJson));
                    }

                }
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
                if (appiumDevice.getDevice().isCloud()) {
                    appiumDevice.setPort(8543); //need to make sure for specific cloud
                } else {
                    appiumDevice.setPort(new AvailablePorts().getAvailablePort(machineIP));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return appiumDevice;
        };
    }

    private List<Device> getSimulatorsToBoot(String machineIP, JSONArray simulators)
        throws Exception {
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



