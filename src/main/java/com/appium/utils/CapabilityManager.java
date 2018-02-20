package com.appium.utils;

import com.appium.manager.ConfigFileManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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
        return (JSONObject) capabilities.get(key);
    }

    public JSONArray getCapabitiesArrayFromKey(String key) {
        return capabilities.getJSONArray(key);
    }


}
