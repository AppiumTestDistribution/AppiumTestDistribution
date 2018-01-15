package com.appium.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.device.Device;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostMachineDeviceManager {

    @Test
    public void testApp() throws Exception {
        List<Device> device = getDevices();
        System.out.println(device);
    }


    public List<Device> getDevices() throws Exception {
        List<String> ipAddress = new ArrayList<>();
        JSONArray hostMachines = (JSONArray)CapabilityManager.getInstance().getCapabilityFromKey("hostMachines");
        hostMachines.forEach(hostMachine -> {
            String machineIP = ((JSONObject) hostMachine).get("machineIP").toString();
            ipAddress.add(machineIP);
        });
        final List<Device> deviceProperties = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper().
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ipAddress.forEach(s -> {
            try {
                List listOfDevices = mapper.readValue(new URL("http://" + s + ":4567/devices"),
                        deviceProperties.getClass());
                deviceProperties.addAll(listOfDevices);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        return deviceProperties;
    }
    
}
