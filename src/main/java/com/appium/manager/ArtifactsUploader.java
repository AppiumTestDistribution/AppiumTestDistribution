package com.appium.manager;


import com.appium.utils.Api;
import com.appium.utils.CapabilityManager;
import com.appium.utils.HostMachineDeviceManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.collections.ListMultiMap;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ArtifactsUploader {

    private CapabilityManager capabilityManager;
    private final HostMachineDeviceManager hostMachineDeviceManager;
    private Api api = new Api();


    public ArtifactsUploader() {
        try {
            capabilityManager = CapabilityManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
    }

    public Multimap<String,String> upLoadFilesToRemoteMachines() throws IOException {
        Multimap<String,String> appPerHostMachine = ArrayListMultimap.create();
        hostMachineDeviceManager.getDevicesByHost().getAllHosts().forEach(hostMachines -> {
            if (hostMachines.equals("127.0.0.1")) {
                   getAppPathsToUpload().forEach(appPath -> {
                       try {
                           JSONObject jsonObject = new JSONObject(api.uploadMultiPartFile(new File(appPath), hostMachines));
                           appPerHostMachine.put(hostMachines, jsonObject.get("filePath").toString());
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   });
            }
        });
        return appPerHostMachine;
    }

    public List<String> getAppPathsToUpload() {
        //check for app as url
        String app = "app";
        List<String> appPaths = new ArrayList<>();
        JSONObject android = capabilityManager
                .getCapabilityObjectFromKey("android");
        JSONObject iOSAppPath = capabilityManager
                .getCapabilityObjectFromKey("iOS");
        if (android != null && android.has(app)) {
            appPaths.add((String) android.get(app));
        }
        if (iOSAppPath!=null && iOSAppPath.has("app")) {
            if (iOSAppPath.get("app") instanceof  JSONObject) {
                if (((JSONObject)iOSAppPath.get("app")).has("simulator")) {
                    Object simulator = ((JSONObject)iOSAppPath.get("app")).get("simulator");
                    appPaths.add((String) simulator);
                }
                if (((JSONObject)iOSAppPath.get("app")).has("device")) {
                    Object device = ((JSONObject)iOSAppPath.get("app")).get("device");
                    appPaths.add((String) device);
                }
            }
        }
        return appPaths;
    }
}
