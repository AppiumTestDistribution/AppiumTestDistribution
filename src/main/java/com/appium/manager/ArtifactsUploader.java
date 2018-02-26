package com.appium.manager;


import com.appium.utils.Api;
import com.appium.utils.CapabilityManager;
import com.appium.utils.HostMachineDeviceManager;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ArtifactsUploader {

    private static ArtifactsUploader instance;
    private CapabilityManager capabilityManager;
    private final HostMachineDeviceManager hostMachineDeviceManager;
    private Api api = new Api();
    private List<HostArtifact> hostArtifacts;


    private ArtifactsUploader() throws IOException {
        try {
            capabilityManager = CapabilityManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hostMachineDeviceManager = HostMachineDeviceManager.getInstance();
        hostArtifacts = new ArrayList<>();
    }
    
    public static ArtifactsUploader getInstance() throws IOException {
        if(instance == null){
            instance = new ArtifactsUploader();
        }
        return instance;
    }

    public void uploadFilesToRemoteMachines() throws IOException {
        hostArtifacts = new ArrayList<>();
        for (String hostMachine : hostMachineDeviceManager.getDevicesByHost().getAllHosts()) {
            if (!hostMachine.equals("127.0.0.1")) {
                HashMap<String, String> artifactPaths = uploadArtifacts(hostMachine);
                HostArtifact hostArtifact = new HostArtifact(hostMachine, artifactPaths);
                hostArtifacts.add(hostArtifact);
            }
        }
    }

    public List<HostArtifact> getHostArtifacts(){
        return hostArtifacts;
    }

    private String uploadFile(String hostMachines, String appPath) throws IOException {
        JSONObject jsonObject = new JSONObject(api.uploadMultiPartFile(new File(appPath), hostMachines));
        return jsonObject.getString("filePath");
    }

    private HashMap<String, String> uploadArtifacts(String hostMachine) throws IOException {
        //check for app as url
        String app = "app";
        HashMap<String, String> artifactPaths = new HashMap<>();
        JSONObject android = capabilityManager
                .getCapabilityObjectFromKey("android");
        JSONObject iOSAppPath = capabilityManager
                .getCapabilityObjectFromKey("iOS");
        if (android != null && android.has(app)) {
            String apkPath = uploadFile(hostMachine, android.getString("app"));
            artifactPaths.put("APK",apkPath);
        }
        if (iOSAppPath != null && iOSAppPath.has("app")) {
            if (iOSAppPath.get("app") instanceof JSONObject) {
                JSONObject iOSApp = iOSAppPath.getJSONObject("app");
                if (iOSApp.has("simulator")) {
                    String simulatorApp = iOSApp.getString("simulator");
                    String appPath = uploadFile(hostMachine, simulatorApp);
                    artifactPaths.put("APP", appPath);
                }
                if (iOSApp.has("device")) {
                    String deviceIPA = iOSApp.getString("device");
                    String ipaPath = uploadFile(hostMachine, deviceIPA);
                    artifactPaths.put("IPA", ipaPath);
                }
            }
        }
        return artifactPaths;
    }
}

