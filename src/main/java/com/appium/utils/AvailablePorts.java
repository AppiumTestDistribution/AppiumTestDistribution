package com.appium.utils;

import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;

public class AvailablePorts {

    public int getAvailablePort() {
        final int[] port = new int[1];
        JSONArray hostMachines = null;
        try {
            hostMachines = getHostMachineObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hostMachines.forEach(hostMachine -> {
            JSONObject hostMachineJson = (JSONObject) hostMachine;
            String machineIP = hostMachineJson.getString("machineIP");
            if ("127.0.0.1".equals(machineIP)) {
                try {
                    port[0] = new AvailablePorts().getPort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String url = String.format("http://%s:4567/machine/availablePort",
                        machineIP);
                try {
                    Response response = new Api().getResponse(url);
                    port[0] = Integer.parseInt(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return port[0];
    }

    private static JSONArray getHostMachineObject() throws Exception {
        CapabilityManager capabilityManager = CapabilityManager.getInstance();
        return capabilityManager.getCapabitiesArrayFromKey("hostMachines");
    }


    /*
 * Generates Random ports
 * Used during starting appium server
 */
    private int getPort() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;

    }

}
