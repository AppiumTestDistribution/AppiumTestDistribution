package com.appium.plugin;

import com.appium.manager.AppiumServerManager;
import com.appium.utils.Api;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class PluginCliRequest {

    Api api;

    public PluginCliRequest() {
        api = new Api();
    }

    public PluginClI getCliArgs() throws IOException {
        AppiumServerManager appiumServerManager = new AppiumServerManager();
        String remoteWDHubIP = appiumServerManager.getRemoteWDHubIP();
        URL url = new URL(remoteWDHubIP);
        Response response = api.requestBuilderWithBearerToken(url.getProtocol() + "://" + url.getHost()
                + ":" + url.getPort() + "/device-farm/api/cliArgs");
        final PluginClI[] pluginClIS = new ObjectMapper().readValue(Objects.requireNonNull(response.body()).string(), PluginClI[].class);
        return pluginClIS[0];
    }
}
