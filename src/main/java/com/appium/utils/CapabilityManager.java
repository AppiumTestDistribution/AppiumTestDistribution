package com.appium.utils;

import com.appium.manager.ConfigFileManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Optional;

public class CapabilityManager {

    private static CapabilityManager instance;
    private static JsonParser jsonParser;
    private static Object obj;

    private CapabilityManager() {
    }
    public static CapabilityManager getInstance() throws Exception {
        if (instance == null) {
            String capabilititesFilePath =getCapabilityLocation();
            instance = new CapabilityManager();
            jsonParser=new JsonParser(capabilititesFilePath);
            obj = jsonParser.getObjectFromJSON();

        }

        return instance;
    }

    private static String getCapabilityLocation() throws IOException {
        String path  = System.getProperty("user.dir") + "/caps/"
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


    public Object getCapabilityFromKey(String key){
        JSONArray jsonArray = (JSONArray) obj;

        Optional first = jsonArray.stream().filter(o -> ((JSONObject) o)
                .get(key) != null)
                .findFirst();
        return ((JSONObject) first.get()).get(key);
    }






}
