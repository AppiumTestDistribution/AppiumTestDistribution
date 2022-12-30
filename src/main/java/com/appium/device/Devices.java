package com.appium.device;

import com.appium.manager.AppiumServerManager;
import com.appium.utils.Api;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Devices {
    Api api;

    public Devices() {
        api = new Api();
    }

    public List<Device> getDevices() throws IOException {
        AppiumServerManager appiumServerManager = new AppiumServerManager();
        String remoteWDHubIP = appiumServerManager.getRemoteWDHubIP();
        URL url = new URL(remoteWDHubIP);
        String response = api.getResponse(url.getProtocol()
                + "://" + url.getHost() + ":" + url.getPort() + "/device-farm/api/devices");
        return Arrays.asList(new ObjectMapper().readValue(response, Device[].class));
    }
}
