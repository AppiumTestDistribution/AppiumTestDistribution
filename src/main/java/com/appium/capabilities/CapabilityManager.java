package com.appium.capabilities;

import static com.appium.utils.ConfigFileManager.CAPS;

import com.appium.utils.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CapabilityManager {
    private static CapabilityManager instance;
    private JSONObject capabilities;

    private CapabilityManager() {
        String capabilitiesFilePath = getCapabilityLocation();
        JsonParser jsonParser = new JsonParser(capabilitiesFilePath);
        StringBuilder varParsing = new StringBuilder(200);
        varParsing.append("atd").append("_");
        capabilities = loadAndOverrideFromEnvVars(jsonParser.getObjectFromJSON(),
            new JSONObject(),
            getAllATDOverrideEnvVars(),
            varParsing);
    }

    public static CapabilityManager getInstance() {
        if (instance == null) {
            instance = new CapabilityManager();
        }
        return instance;
    }

    private Map<String, Object> getAllATDOverrideEnvVars() {
        Map<String, Object> atdOverrideEnv = new HashMap<>();
        System.getenv().forEach((key, value) -> {
            if (key.startsWith("atd")) {
                atdOverrideEnv.put(key, value);
            }
        });
        return atdOverrideEnv;
    }

    private JSONObject loadAndOverrideFromEnvVars(JSONObject originalObject,
                                                  JSONObject objectToUpdate,
                                                  Map<String, Object> allATDOverrideEnvVars,
                                                  StringBuilder currentPath) {
        Set<String> keys = originalObject.keySet();
        keys.forEach(keyStr -> {
            Object keyvalue = originalObject.get(keyStr);
            if (keyvalue instanceof JSONObject) {
                processJSONObject(objectToUpdate,
                    allATDOverrideEnvVars,
                    currentPath,
                    keyStr,
                    (JSONObject) keyvalue);
            } else if (keyvalue instanceof JSONArray) {
                processJSONArray(objectToUpdate,
                    allATDOverrideEnvVars,
                    currentPath,
                    keyStr,
                    (JSONArray) keyvalue);
            } else {
                processJSONObject(objectToUpdate,
                    currentPath,
                    keyStr,
                    keyvalue);
            }
        });
        return objectToUpdate;
    }

    private void processJSONObject(JSONObject objectToUpdate,
                                   StringBuilder currentPath,
                                   String keyStr,
                                   Object keyvalue) {
        currentPath.append(keyStr);
        String getFromEnv = System.getenv(currentPath.toString());
        Object updatedValue = (null == getFromEnv) ? keyvalue : getFromEnv;
        objectToUpdate.put(keyStr, updatedValue);
        currentPath.delete(currentPath.lastIndexOf("_") + 1, currentPath.length());
    }

    private void processJSONArray(JSONObject objectToUpdate,
                                  Map<String, Object> allATDOverrideEnvVars,
                                  StringBuilder currentPath,
                                  String keyStr,
                                  JSONArray keyvalue) {
        JSONArray jsonArray = new JSONArray();
        objectToUpdate.put(keyStr, jsonArray);
        currentPath.append(keyStr).append("_");
        for (int arrIndex = 0; arrIndex < keyvalue.length(); arrIndex++) {
            processJSONArrayItem(allATDOverrideEnvVars,
                currentPath,
                jsonArray,
                keyvalue,
                arrIndex);
        }
        currentPath.delete(currentPath.lastIndexOf(keyStr), currentPath.length());
    }

    private void processJSONArrayItem(Map<String, Object> allATDOverrideEnvVars,
                                      StringBuilder currentPath,
                                      JSONArray jsonArray,
                                      JSONArray arrayValues,
                                      int arrIndex) {
        JSONObject arrayItem = (JSONObject) arrayValues.get(arrIndex);
        JSONObject jsonObject = new JSONObject();
        jsonArray.put(jsonObject);
        currentPath.append(arrIndex).append("_");
        loadAndOverrideFromEnvVars((JSONObject) arrayItem,
            jsonObject,
            allATDOverrideEnvVars,
            currentPath);
        currentPath.delete(currentPath.lastIndexOf(String.valueOf(arrIndex)), currentPath.length());
    }

    private void processJSONObject(JSONObject objectToUpdate,
                                   Map<String, Object> allATDOverrideEnvVars,
                                   StringBuilder currentPath,
                                   String keyStr,
                                   JSONObject keyvalue) {
        JSONObject jsonObject = new JSONObject();
        objectToUpdate.put(keyStr, jsonObject);
        currentPath.append(keyStr).append("_");
        loadAndOverrideFromEnvVars(keyvalue, jsonObject, allATDOverrideEnvVars, currentPath);
        currentPath.delete(currentPath.lastIndexOf(keyStr), currentPath.length());
    }

    private String getCapabilityLocation() {
        String path = System.getProperty("user.dir") + "/caps/"
            + "capabilities.json";
        String caps = CAPS.get();
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
        try {
            return appiumServerProp(host, "appiumServerPath");
        } catch (Exception e) {
            return null;
        }
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
        Object machineIP = hostIP.stream().filter(object -> ((Map) object).get("machineIP")
            .toString().equalsIgnoreCase(host)
            && ((Map) object).get(arg) != null)
            .findFirst().orElse(null);
        return (T) ((Map) machineIP).get(arg);
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