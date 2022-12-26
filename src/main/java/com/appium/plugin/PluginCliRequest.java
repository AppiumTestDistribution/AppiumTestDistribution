package com.appium.plugin;

import com.appium.manager.AppiumServerManager;
import com.appium.utils.Api;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

public class PluginCliRequest {

    Api api;

    public PluginCliRequest() {
        api = new Api();
    }

    public Response getCliArgs() throws IOException {
        AppiumServerManager appiumServerManager = new AppiumServerManager();
        String remoteWDHubIP = appiumServerManager.getRemoteWDHubIP();
        URL url = new URL(remoteWDHubIP);
        return api.requestBuilderWithBearerToken(url.getProtocol() + "://" + url.getHost()
                + ":" + url.getPort() + "/device-farm/api/cliArgs");
    }
}
