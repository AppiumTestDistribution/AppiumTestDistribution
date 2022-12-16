package com.appium.plugin;

import com.appium.manager.LocalAppiumManager;
import com.appium.utils.Api;
import okhttp3.Response;

import java.io.IOException;

public class PluginCliRequest {

    Api api;
    public PluginCliRequest() {
        api = new Api();
    }

    public Response getCliArgs() throws IOException {
        LocalAppiumManager localAppiumManager = new LocalAppiumManager();
        String remoteWDHubIP = localAppiumManager.getRemoteWDHubIP("127.0.0.1");
        return api.requestBuilderWithBearerToken(remoteWDHubIP + "device-farm/api/cliArgs");
    }
}
