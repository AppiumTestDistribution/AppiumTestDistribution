package com.appium.utils;

import com.appium.ios.IOSDeviceConfiguration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HostMachineDeviceManager {

    @Test
    public void testApp() throws IOException {
        List<DeviceProperties> device = getDevice();
        System.out.println(device);
    }


    public List<DeviceProperties> getDevice() throws IOException {
        DeviceProperties[] deviceProperties = null;
        ObjectMapper mapper = new ObjectMapper().
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
        try {
            deviceProperties  = mapper.readValue(new URL("http://localhost:4567/devices"),
                    DeviceProperties[].class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Arrays.asList(deviceProperties);
    }
    
}
