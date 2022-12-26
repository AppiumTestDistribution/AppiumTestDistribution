package com.appium.device;

import com.appium.manager.AppiumServerManager;
import com.appium.utils.Api;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Devices {
    Api api;

    public Devices() {
        api = new Api();
    }

    public List<Device> getDevices() throws IOException {
        AppiumServerManager appiumServerManager = new AppiumServerManager();
        String remoteWDHubIP = appiumServerManager.getRemoteWDHubIP();
        URL url = new URL(remoteWDHubIP);
        Response response = api.requestBuilderWithBearerToken(url.getProtocol()
                + "://" + url.getHost() + ":" + url.getPort() + "/device-farm/api/devices");
        return Arrays.asList(new ObjectMapper().readValue(Objects.requireNonNull(
                response.body()).string(),
                Device[].class));
    }
}
