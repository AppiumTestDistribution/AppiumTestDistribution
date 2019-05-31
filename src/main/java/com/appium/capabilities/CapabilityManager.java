package com.appium.capabilities;

import com.appium.utils.ConfigFileManager;
import com.appium.utils.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class CapabilityManager {

    private static CapabilityManager instance;
    private JSONObject capabilities;

    private CapabilityManager() {
        String capabilitiesFilePath = getCapabilityLocation();
        JsonParser jsonParser = new JsonParser(capabilitiesFilePath);
        capabilities = jsonParser.getObjectFromJSON();
    }

    public static CapabilityManager getInstance() {
        if (instance == null) {
            instance = new CapabilityManager();
        }
        return instance;
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
            e.printStackTrace();
        }
        return false;
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

    public String getRemoteAppiumManangerPort(String host) throws Exception {
        return appiumServerProp(host, "remoteAppiumManagerPort");
    }

    public JSONObject getCapabilities() {
        return capabilities;
    }

}
