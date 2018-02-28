package com.appium.manager;

import com.appium.utils.Api;
import org.json.JSONObject;

import java.io.IOException;

public class RemoteAppiumManager implements IAppiumManager {

    @Override
    public void destroyAppiumNode(String host) throws IOException {
        new Api().getResponse("http://" + host + ":4567"
                + "/appium/stop").body().string();

    }

    @Override
    public String getRemoteWDHubIP(String host) throws IOException {
        String hostIP = "http://" + host;
        String appiumRunningPort = new JSONObject(new Api().getResponse(hostIP + ":4567"
                + "/appium/isRunning").body().string()).get("port").toString();
        return hostIP + ":" + appiumRunningPort + "/wd/hub";
    }

    @Override
    public void startAppiumServer(String host) throws Exception {
        System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server on host " + host);
        System.out.println(
                "**************************************************************************\n");
        new Api().getResponse("http://" + host + ":4567"
                + "/appium/start").body().string();

        boolean status = Boolean.getBoolean(new JSONObject(new Api().getResponse("http://" + host + ":4567"
                + "/appium/isRunning").body().string()).get("status").toString());
        if (status) {
            System.out.println(
                    "***************************************************************\n");
            System.out.println("Appium Server started successfully on  " + host);
            System.out.println(
                    "****************************************************************\n");
        }
    }
}

