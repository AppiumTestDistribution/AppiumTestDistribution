package com.appium.utils;

import com.appium.manager.ConfigFileManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class CapabilityManager {

    private static CapabilityManager instance;
    private JSONObject capabilities;

    private CapabilityManager() throws IOException {
        String capabilitiesFilePath = getCapabilityLocation();
        JsonParser jsonParser = new JsonParser(capabilitiesFilePath);
        capabilities = jsonParser.getObjectFromJSON();
    }

    public static CapabilityManager getInstance() throws Exception {
        if (instance == null) {
            instance = new CapabilityManager();
        }
        return instance;
    }

    private String getCapabilityLocation() throws IOException {
        String path = System.getProperty("user.dir") + "/caps/"
                + "capabilities.json";
        String caps = ConfigFileManager.getInstance()
                .getProperty("CAPS");
        if (caps != null) {
            Path userDefinedCapsPath = FileSystems.getDefault().getPath(caps);
            if (!userDefinedCapsPath.getParent().isAbsolute()) {
                path = userDefinedCapsPath.normalize()
                        .toAbsolutePath().toString();
            } else {
                path = userDefinedCapsPath.toString();
            }
        }
        return path;
    }


    public JSONObject getCapabilityObjectFromKey(String key) {
        boolean hasKey = capabilities.has(key);
        if (hasKey) {
            return (JSONObject) capabilities.get(key);
        } else  {
            return null;
        }
    }

    public JSONArray getCapabitiesArrayFromKey(String key) {
        return capabilities.getJSONArray(key);
    }

    public Boolean getCapabilityBoolean(String key) {
        if (capabilities.has(key)) {
            return (Boolean) capabilities.get(key);
        } else {
            return false;
        }
    }

    public JSONArray getHostMachineObject() throws Exception {
        return getCapabitiesArrayFromKey("hostMachines");
    }

    public Boolean shouldExcludeLocalDevices() throws Exception {
        return getCapabilityBoolean("excludeLocalDevices");
    }

    public boolean isSimulatorAppPresentInCapsJson() {
        boolean hasApp = getCapabilityObjectFromKey("iOS").has("app");
        return hasApp && getCapabilityObjectFromKey("iOS").getJSONObject("app").has("simulator");
    }

    public boolean isRealDeviceAppPresentInCapsJson() {
        boolean hasApp = getCapabilityObjectFromKey("iOS").has("app");
        return hasApp && getCapabilityObjectFromKey("iOS").getJSONObject("app").has("device");
    }

    public String getAppiumServerPath(String host) throws Exception {
        JSONArray hostMachineObject = CapabilityManager.getInstance().getHostMachineObject();
        List<Object> objects = hostMachineObject.toList();
        Object o = objects.stream().filter(object -> ((HashMap) object).get("machineIP")
                .toString().equalsIgnoreCase(host)
                && ((HashMap) object).get("appiumServerPath") != null)
                .findFirst().orElse(null);
        if (o != null) {
            return ((HashMap) o).get("appiumServerPath").toString();
        }
        return null;
    }

    public String getAppiumServerPort(String host) throws Exception {
        JSONArray hostMachineObject = CapabilityManager.getInstance().getHostMachineObject();
        List<Object> objects = hostMachineObject.toList();
        Object o = objects.stream().filter(object -> ((HashMap) object).get("machineIP")
                .toString().equalsIgnoreCase(host)
                && ((HashMap) object).get("port") != null)
                .findFirst().orElse(null);
        if (o != null) {
            return ((HashMap) o).get("port").toString();
        }
        return null;
    }

    public JSONObject getCapabilities() {
        return capabilities;
    }

}
