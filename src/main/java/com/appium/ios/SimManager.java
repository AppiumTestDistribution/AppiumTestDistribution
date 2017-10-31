package com.appium.ios;

import com.appium.manager.ConfigFileManager;
import com.thoughtworks.device.Device;
import com.thoughtworks.device.SimulatorManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by saikrisv on 27/05/16.
 */
public class SimManager {
    private SimulatorManager simulatorManager = new SimulatorManager();

    public List<Device> getAllSimulatorUDIDs() {
        List<Device> UDIDS = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            Object getSimulatorObject = getSimulatorObject(parser);
            Object simulators = ((JSONObject) ((Optional) getSimulatorObject)
                    .get()).get("simulators");

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
                    UDIDS.add(device);
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

    public Object getSimulatorObject(JSONParser parser) throws IOException, ParseException {
        String defaultCapabilityLocation = System.getProperty("user.dir") + "/caps/"
                + "capabilities.json";
        String parserCapabilityFile;
        if (ConfigFileManager.getInstance().getProperty("CAPS") == null) {
            parserCapabilityFile = defaultCapabilityLocation;
        } else {
            String userCapabilityLocation = null;
            try {
                userCapabilityLocation = ConfigFileManager.getInstance()
                        .getProperty("CAPS");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Path path = FileSystems.getDefault().getPath(userCapabilityLocation);
            if (!path.getParent().isAbsolute()) {
                parserCapabilityFile = path.normalize()
                        .toAbsolutePath().toString();
            } else {
                parserCapabilityFile = path.toString();
            }

        }
        Object obj = parser.parse(new FileReader(parserCapabilityFile));
        JSONArray jsonObject = (JSONArray) obj;
        return jsonObject.stream().filter(o -> ((JSONObject) o)
                .get("simulators") != null)
                .findFirst();
    }

    public Device getSimulatorDetails(String UDID) {
        return simulatorManager.getSimulatorDetailsFromUDID(UDID);
    }

    public boolean isSimulatorAvailable() {
        JSONParser parser = new JSONParser();
        Object simulatorObjectPreset = null;
        try {
            simulatorObjectPreset = getSimulatorObject(parser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ((Optional) simulatorObjectPreset).isPresent();
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



