package com.appium.ios;

import com.thoughtworks.device.Device;
import com.thoughtworks.device.SimulatorManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by saikrisv on 27/05/16.
 */
public class SimManager {
    private SimulatorManager simulatorManager = new SimulatorManager();

    public ArrayList<String> getAllSimulatorUDIDs() {
        ArrayList<String> UDIDS = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/caps/"
                    + "capabilities.json"));
            JSONArray jsonObject = (JSONArray) obj;
            Object getSimulatorObject = jsonObject.stream().filter(o -> ((JSONObject) o)
                    .get("simulators") != null)
                    .findFirst().orElse(null);
            Object simulators = ((JSONObject) getSimulatorObject).get("simulators");

            ((JSONArray) simulators).forEach(o -> {
                Simulator simulator = new Simulator();
                ((JSONObject) o).forEach((key, value) -> {
                    if ("OS".equals(key)) {
                        simulator.OS = (java.lang.String) value;
                    } else {
                        simulator.DeviceName = (java.lang.String) value;
                    }
                });
                try {
                    Device device = simulatorManager.getDevice(simulator.getDeviceName(),
                            simulator.getOS(), "iOS");
                    UDIDS.add(device.getUdid());
                    //check for simulator state
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return UDIDS;
    }

    public Device getSimulatorDetails(String UDID) {
        Device iOS = null;
        try {
            iOS = simulatorManager.getSimulatorDetailsFromUDID(UDID, "iOS");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return iOS;
    }

    public class Simulator {
        private String OS;

        private String DeviceName;

        public String getOS() {

            return OS;
        }

        public void setOS(String OS) {
            this.OS = OS;
        }

        public String getDeviceName() {
            return DeviceName;
        }

        public void setDeviceName(String deviceName) {
            DeviceName = deviceName;
        }

    }
}



