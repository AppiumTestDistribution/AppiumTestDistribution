package com.appium.capabilities;

import com.appium.utils.ConfigFileManager;
import com.appium.utils.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CapabilityManager {

    private static CapabilityManager instance;
    private JSONObject capabilities;

    private CapabilityManager() {
        String capabilitiesFilePath = getCapabilityLocation();
        JsonParser jsonParser = new JsonParser(capabilitiesFilePath);
        capabilities = loadAndOverrideFromEnvVars(jsonParser.getObjectFromJSON(), new JSONObject());
    }

    public static CapabilityManager getInstance() {
        if (instance == null) {
            instance = new CapabilityManager();
        }
        return instance;
    }

    private JSONObject loadAndOverrideFromEnvVars(JSONObject originalObject, JSONObject objectToUpdate) {
        Set<String> keys = originalObject.keySet();
        keys.forEach(keyStr ->
        {
            Object keyvalue = originalObject.get(keyStr);
            if (keyvalue instanceof JSONObject) {
                JSONObject jsonObject = new JSONObject();
                objectToUpdate.put(keyStr, jsonObject);
                loadAndOverrideFromEnvVars((JSONObject) keyvalue, jsonObject);
            } else if (keyvalue instanceof JSONArray) {
                JSONArray jsonArray = new JSONArray();
                objectToUpdate.put(keyStr, jsonArray);
                ((JSONArray) keyvalue).forEach(arrayItem -> {
                            JSONObject jsonObject = new JSONObject();
                            jsonArray.put(jsonObject);
                            loadAndOverrideFromEnvVars((JSONObject) arrayItem, jsonObject);
                        }
                );
            } else {
                String updatedValue = ((System.getenv(keyStr) == null) ? keyvalue.toString() : System.getenv(keyStr));
                objectToUpdate.put(keyStr, updatedValue);
            }
        });
        return objectToUpdate;
    }

    private String getCapabilityLocation() {
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
        }
        return null;
    }

    public JSONArray getCapabilitiesArrayFromKey(String key) {
        return capabilities.getJSONArray(key);
    }

    public Boolean getCapabilityBoolean(String key) {
        if (capabilities.has(key)) {
            return (Boolean) capabilities.get(key);
        }
        return false;
    }

    public HashMap<String, String> getMongoDbHostAndPort() {
        HashMap<String, String> params = new HashMap<>();
        if (capabilities.has("ATDServiceHost")
            && capabilities.has("ATDServicePort")) {
            params.put("atdHost", (String) capabilities.get("ATDServiceHost"));
            params.put("atdPort", (String) capabilities.get("ATDServicePort"));
        }
        return params;
    }

    public JSONArray getHostMachineObject() throws Exception {
        return getCapabilitiesArrayFromKey("hostMachines");
    }

    public Boolean shouldExcludeLocalDevices() throws Exception {
        return getCapabilityBoolean("excludeLocalDevices");
    }

    public boolean isSimulatorAppPresentInCapsJson() {
        boolean hasApp = getCapabilityObjectFromKey("iOS").has("app");
        return hasApp && getCapabilityObjectFromKey("iOS").getJSONObject("app").has("simulator");
    }

    public boolean isApp() {
        return getCapabilityObjectFromKey("iOS").has("app");
    }

    public boolean isRealDeviceAppPresentInCapsJson() {
        boolean hasApp = getCapabilityObjectFromKey("iOS").has("app");
        return hasApp && getCapabilityObjectFromKey("iOS").getJSONObject("app").has("device");
    }

    public String getAppiumServerPath(String host) throws Exception {
        return appiumServerProp(host, "appiumServerPath");
    }

    public boolean isCloud(String host) {
        try {
            return appiumServerProp(host, "isCloud");
        } catch (Exception e) {
            return false;
        }
    }

    private <T> T appiumServerProp(String host, String arg) throws Exception {
        JSONArray hostMachineObject = CapabilityManager.getInstance().getHostMachineObject();
        List<Object> hostIP = hostMachineObject.toList();
        Object machineIP = hostIP.stream().filter(object -> ((HashMap) object).get("machineIP")
            .toString().equalsIgnoreCase(host)
            && ((HashMap) object).get(arg) != null)
            .findFirst().orElse(null);
        return (T) ((HashMap) machineIP).get(arg);
    }

    public String getAppiumServerPort(String host) throws Exception {
        return appiumServerProp(host, "appiumPort");
    }

    public String getRemoteAppiumManangerPort(String host) {
        try {
            return appiumServerProp(host, "remoteAppiumManagerPort");
        } catch (Exception e) {
            return "4567";
        }
    }

    public JSONObject getCapabilities() {
        return capabilities;
    }

}
