package com.appium.ios;

import com.appium.utils.CapabilityManager;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.SimulatorManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by saikrisv on 27/05/16.
 */
public class SimManager {
    private SimulatorManager simulatorManager = new SimulatorManager();
    private CapabilityManager capabilityManager;

    public SimManager() {
        try {
            capabilityManager = CapabilityManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Device> getAllSimulatorUDIDs() {
        List<Device> UDIDS = new ArrayList<>();
        /*JSONParser parser = new JSONParser();

        try {
            //localhost
            Object simulators = capabilityManager.getCapabilityObjectFromKey("simulators");
            Object hostMachines = capabilityManager.getCapabilityObjectFromKey("hostMachines");

            if (hostMachines != null) {
                ((JSONObject) hostMachines).forEach((key, value) -> {
                    if (((JSONObject) key).get("simulators") != null) {

                    }
                });
                Object isSim = ((JSONObject) hostMachines).get("simulators");
                if (isSim != null) {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return UDIDS;
    }

    public Device getSimulatorDetails(String UDID) {
        return simulatorManager.getSimulatorDetailsFromUDID(UDID);
    }

    public boolean isSimulatorObjectAvailableInCapsJson() {
        Object simulators = capabilityManager.getCapabilityObjectFromKey("simulators");
        Object hostMachines = capabilityManager.getCapabilityObjectFromKey("hostMachines");
        JSONObject firstSimulatorObject = null;
        if (hostMachines != null) {
            firstSimulatorObject = ((JSONObject)hostMachines).getJSONObject("simulators");
        }
        return simulators != null || firstSimulatorObject !=null;
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



