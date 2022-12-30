package com.appium.plugin;

import com.appium.manager.AppiumServerManager;
import com.appium.utils.Api;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class PluginCliRequest {

    Api api;

    public PluginCliRequest() {
        api = new Api();
    }

    public PluginClI getCliArgs() throws IOException {
        AppiumServerManager appiumServerManager = new AppiumServerManager();
        String remoteWDHubIP = appiumServerManager.getRemoteWDHubIP();
        URL url = new URL(remoteWDHubIP);
        String response = api.getResponse(url.getProtocol() + "://" + url.getHost()
                + ":" + url.getPort() + "/device-farm/api/cliArgs");
        final PluginClI[] pluginClIS = new ObjectMapper().readValue(response, PluginClI[].class);
        return pluginClIS[0];
    }
}
