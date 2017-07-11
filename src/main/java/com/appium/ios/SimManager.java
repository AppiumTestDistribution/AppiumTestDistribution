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
    SimulatorManager simulatorManager = new SimulatorManager();

    public ArrayList<String> getAllSimulatorUDIDs() {
        ArrayList<String> UDIDS = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/caps/"
                    + "simulator.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray msg = (JSONArray) jsonObject.get("simulators");
            ((JSONArray) jsonObject.get("simulators")).forEach(o -> {
                Simulator simulator = new Simulator();
                ((JSONObject) o).forEach((key, value) -> {
                    if (key.equals("OS")) {
                        simulator.OS = (java.lang.String) value;
                    } else {
                        simulator.DeviceName = (java.lang.String) value;
                    }
                });
                try {
                    Device device = simulatorManager.getDevice(simulator.getDeviceName(),
                            simulator.getOS(), "iOS");
                    UDIDS.add(device.getUdid());
                    simulatorManager.bootSimulator(simulator.getDeviceName(),simulator.getOS(), "iOS");
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
        public String getOS() {

            return OS;
        }

        public void setOS(String OS) {
            this.OS = OS;
        }

        private String OS;

        public String getDeviceName() {
            return DeviceName;
        }

        public void setDeviceName(String deviceName) {
            DeviceName = deviceName;
        }

        private String DeviceName;
    }
}



