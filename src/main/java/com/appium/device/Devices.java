package com.appium.device;

import com.appium.manager.AppiumServerManager;
import com.appium.utils.Api;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Devices {
    private static List<Device> instance;

    private Devices() {

    }

    @SneakyThrows
    public static List<Device> getConnectedDevices() {
        if (instance == null) {
            System.out.println(Thread.currentThread().getId());
            AppiumServerManager appiumServerManager = new AppiumServerManager();
            String remoteWDHubIP = appiumServerManager.getRemoteWDHubIP();
            URL url = new URL(remoteWDHubIP);
            String response = new Api().getResponse(url.getProtocol()
                    + "://" + url.getHost() + ":" + url.getPort() + "/device-farm/api/devices");
            instance =  Arrays.asList(new ObjectMapper().readValue(response, Device[].class));
        }
        return instance;
    }
}
