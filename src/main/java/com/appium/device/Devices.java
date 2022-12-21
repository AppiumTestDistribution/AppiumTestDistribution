package com.appium.device;

import com.appium.manager.LocalAppiumManager;
import com.appium.utils.Api;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

public class Devices {
    Api api;

    public Devices() {
        api = new Api();
    }

    public Response getDevices() throws IOException {
        LocalAppiumManager localAppiumManager = new LocalAppiumManager();
        String remoteWDHubIP = localAppiumManager.getRemoteWDHubIP("127.0.0.1");
        URL url = new URL(remoteWDHubIP);
        return api.requestBuilderWithBearerToken(url.getProtocol()
                + "://" + url.getHost() + ":" + url.getPort() + "/device-farm/api/devices");
    }
}
