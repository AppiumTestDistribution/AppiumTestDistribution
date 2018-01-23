package com.appium.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.DeviceManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class HostMachineDeviceManager {

    private static DevicesByHost instance;

    public static DevicesByHost getInstance() {
        if (instance == null) {
            try {
                Map<String, List<Device>> devices = getDevices();
                instance = new DevicesByHost(devices);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private static Map<String, List<Device>> getDevices() throws Exception {
        Map<String, List<Device>> devicesByHost = new HashMap<>();
        Map<String, List<Device>> remoteDevices = getRemoteDevices();
        DeviceManager deviceManager = new DeviceManager();
        List<Device> devices = deviceManager.getDevices();
        devicesByHost.put("127.0.0.1", devices);
        devicesByHost.putAll(remoteDevices);
        return devicesByHost;
    }

    private static Map<String, List<Device>> getRemoteDevices() throws Exception {
        Map<String, List<Device>> devices = new HashMap<>();
        CapabilityManager capabilityManager = CapabilityManager.getInstance();
        JSONArray hostMachines = capabilityManager.getCapabitiesArrayFromKey("hostMachines");
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (hostMachines != null) {
            hostMachines.forEach(hostMachine -> {
                JSONObject hostMachineJson = (JSONObject) hostMachine;
                String machineIP = hostMachineJson.getString("machineIP");

                try {
                    ArrayList<Device> deviceList = new ArrayList<>();
                    List<Device> physicalDevices = Arrays.asList(mapper.readValue(new URL("http://" + machineIP + ":4567/devices"),
                            Device[].class));
                    deviceList.addAll(physicalDevices);
                    if (hostMachineJson.has("simulators")) {
                        JSONArray simulators = hostMachineJson.getJSONArray("simulators");
                        List<Device> simulatorsToBoot = getSimulators(machineIP, simulators, mapper);
                        deviceList.addAll(simulatorsToBoot);
                    }
                    devices.put(machineIP, deviceList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return devices;
    }

    private static List<Device> getSimulators(String ipAddress, JSONArray simulators, ObjectMapper mapper) throws Exception {
        List<Device> devices = new ArrayList<>();
        simulators.forEach(simulator -> {
            JSONObject simulatorJson = (JSONObject) simulator;
            String deviceName = simulatorJson.getString("deviceName");
            String os = simulatorJson.getString("OS");
            try {
                String url = String.format("http://%s:4567/device/ios/simulators?simulatorName=%s&simulatorOSVersion=%s",
                        ipAddress, URLEncoder.encode(deviceName, "UTF-8"), URLEncoder.encode(os, "UTF-8"));
                Device device = mapper.readValue(new URL(url),
                        Device.class);
                devices.add(device);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return devices;
    }
}

