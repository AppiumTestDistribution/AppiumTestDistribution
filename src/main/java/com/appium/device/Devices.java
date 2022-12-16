package com.appium.device;

import com.appium.manager.LocalAppiumManager;
import com.appium.utils.Api;
import okhttp3.Response;

import java.io.IOException;

public class Devices {
    Api api;
    public Devices() {
        api = new Api();
    }

    public Response getDevices() throws IOException {
        LocalAppiumManager localAppiumManager = new LocalAppiumManager();
        String remoteWDHubIP = localAppiumManager.getRemoteWDHubIP("127.0.0.1");
        return api.requestBuilderWithBearerToken(remoteWDHubIP + "device-farm/api/devices");
    }
}
