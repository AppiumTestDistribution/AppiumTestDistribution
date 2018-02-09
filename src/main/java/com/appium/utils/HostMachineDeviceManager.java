package com.appium.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.DeviceManager;
import com.thoughtworks.device.SimulatorManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostMachineDeviceManager {

    private static DevicesByHost devicesByHost;

    public static void initialize(){

    }

    public static DevicesByHost getDevicesByHost() {
        if (devicesByHost == null) {
            try {
                Map<String, List<AppiumDevice>> devices = getDevices();
                devicesByHost = new DevicesByHost(devices);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return devicesByHost;
    }

    private static Map<String, List<AppiumDevice>> getDevices() throws Exception {
        Map<String, List<AppiumDevice>> devicesByHost = new HashMap<>();
        devicesByHost.putAll(getLocalDevices());
        devicesByHost.putAll(getRemoteDevices());
        return devicesByHost;
    }

    private static Map<String, List<AppiumDevice>> getRemoteDevices() throws Exception {
        Map<String, List<AppiumDevice>> devices = new HashMap<>();
        JSONArray hostMachines = getHostMachineObject();
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (hostMachines != null) {
            hostMachines.forEach(hostMachine -> {
                JSONObject hostMachineJson = (JSONObject) hostMachine;
                String machineIP = hostMachineJson.getString("machineIP");
                if (!"127.0.0.1".equals(machineIP)) {
                    try {
                        ArrayList<AppiumDevice> deviceList = new ArrayList<>();
                        List<Device> physicalDevices = Arrays.asList(mapper.readValue(new URL(
                                        "http://" + machineIP + ":4567/devices"),
                                Device[].class));
                        physicalDevices.forEach(physicalDevice -> {
                            AppiumDevice appiumDevice = new AppiumDevice(physicalDevice, machineIP);
                            appiumDevice.setPort(new AvailablePorts().getAvailablePort());
                            deviceList.add(appiumDevice);
                        });
                        if (hostMachineJson.has("simulators")) {
                            JSONArray simulators = hostMachineJson.getJSONArray("simulators");
                            List<Device> simulatorsToBoot = getSimulators(
                                    machineIP, simulators, mapper);
                            simulatorsToBoot.forEach(simulator -> {
                                AppiumDevice appiumDevice = new AppiumDevice(simulator, machineIP);
                                appiumDevice.setPort(new AvailablePorts().getAvailablePort());
                                deviceList.add(appiumDevice);
                            });
                        }
                        devices.put(machineIP, deviceList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return devices;
    }

    private static List<Device> getSimulators(String ipAddress, JSONArray simulators,
                                              ObjectMapper mapper) throws Exception {
        List<Device> devices = new ArrayList<>();
        simulators.forEach(simulator -> {
            JSONObject simulatorJson = (JSONObject) simulator;
            String deviceName = simulatorJson.getString("deviceName");
            String os = simulatorJson.getString("OS");
            try {
                String url = String.format("http://%s:4567/device/ios/simulators"
                                + "?simulatorName=%s&simulatorOSVersion=%s",
                        ipAddress, URLEncoder.encode(deviceName, "UTF-8"),
                        URLEncoder.encode(os, "UTF-8"));
                Device device = mapper.readValue(new URL(url),
                        Device.class);
                //Booted the same simulators(as in json) on remoteMachine
                // and check unique devices are picked
                if (!device.getState().equals("Booted")) {
                    devices.add(device);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return devices;
    }

    private static Map<String, List<AppiumDevice>> getLocalDevices() throws Exception {
        List<AppiumDevice> devices = new ArrayList<>();
        Map<String, List<AppiumDevice>> simulatorsToBoot = new HashMap<>();
        JSONArray hostMachines = getHostMachineObject();
        String localIpAddress = "127.0.0.1";
        hostMachines.forEach(hostMachine -> {
            JSONObject hostMachineJson = (JSONObject) hostMachine;
            String machineIP = hostMachineJson.getString("machineIP");
            if (localIpAddress.equals(machineIP)) {
                if (hostMachineJson.has("simulators")) {
                    JSONArray simulators = hostMachineJson.getJSONArray("simulators");
                    simulators.forEach(sim -> {
                        JSONObject simulatorJson = (JSONObject) sim;
                        String deviceName = simulatorJson.getString("deviceName");
                        String os = simulatorJson.getString("OS");
                        try {
                            Device simulatorDetails = new SimulatorManager()
                                    .getDevice(deviceName, os, "iOS");
                            if (!simulatorDetails.getState().equals("Booted")) {
                                AppiumDevice appiumDevice = new AppiumDevice(simulatorDetails, localIpAddress);
                                appiumDevice.setPort(new AvailablePorts().getAvailablePort());
                                devices.add(appiumDevice);
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        new DeviceManager().getDevices().forEach(allBootedDevice -> {
            AppiumDevice appiumDevice = new AppiumDevice(allBootedDevice, localIpAddress);
            appiumDevice.setPort(new AvailablePorts().getAvailablePort());
            devices.add(appiumDevice);
        } );
        simulatorsToBoot.put(localIpAddress, devices);
        return simulatorsToBoot;
    }

    private static JSONArray getHostMachineObject() throws Exception {
        CapabilityManager capabilityManager = CapabilityManager.getInstance();
        return capabilityManager.getCapabitiesArrayFromKey("hostMachines");
    }
}

