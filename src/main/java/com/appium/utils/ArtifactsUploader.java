package com.appium.utils;


import com.appium.capabilities.CapabilityManager;
import com.appium.device.HostMachineDeviceManager;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ArtifactsUploader {

    private static ArtifactsUploader instance;
    private CapabilityManager capabilityManager;
    private Api api = new Api();
    private List<HostArtifact> hostArtifacts;


    protected ArtifactsUploader() {
        try {
            capabilityManager = CapabilityManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hostArtifacts = new ArrayList<>();
    }

    protected static ArtifactsUploader getInstance() {
        if (instance == null) {
            instance = new ArtifactsUploader();
        }
        return instance;
    }

    public void initializeArtifacts() throws Exception {
        for (String hostMachine : HostMachineDeviceManager.getInstance()
                .getDevicesByHost().getAllHosts()) {
            HashMap<String, String> artifactPaths = getArtifactForHost(hostMachine);
            HostArtifact hostArtifact = new HostArtifact(hostMachine, artifactPaths);
            hostArtifacts.add(hostArtifact);
        }
    }

    private boolean isLocalhost(String hostMachine) {
        return hostMachine.equals("127.0.0.1");
    }

    public List<HostArtifact> getHostArtifacts() {
        return hostArtifacts;
    }

    private String uploadFile(String hostMachine, String appPath) throws Exception {
        JSONObject jsonObject = new JSONObject(api.uploadMultiPartFile(
                new File(appPath).getCanonicalFile(), hostMachine));
        return jsonObject.getString("filePath");
    }

    private HashMap<String, String> getArtifactForHost(String hostMachine) throws Exception {
        String platform = System.getenv("Platform");
        String app = "app";
        HashMap<String, String> artifactPaths = new HashMap<>();
        JSONObject android = capabilityManager
                .getCapabilityObjectFromKey("android");
        JSONObject iOSAppPath = capabilityManager
                .getCapabilityObjectFromKey("iOS");
        if (android != null && android.has(app)
                && platform.equalsIgnoreCase("android")
                || platform.equalsIgnoreCase("both")) {
            artifactPaths.put("APK", getArtifactPath(hostMachine, android.getString("app")));
        }
        if (iOSAppPath != null && iOSAppPath.has("app")
                && platform.equalsIgnoreCase("ios")
                || platform.equalsIgnoreCase("both")) {
            if (iOSAppPath.get("app") instanceof JSONObject) {
                JSONObject iOSApp = iOSAppPath.getJSONObject("app");
                if (iOSApp.has("simulator")) {
                    String simulatorApp = iOSApp.getString("simulator");
                    artifactPaths.put("APP", getArtifactPath(hostMachine, simulatorApp));
                }
                if (iOSApp.has("device")) {
                    String deviceIPA = iOSApp.getString("device");
                    artifactPaths.put("IPA", getArtifactPath(hostMachine, deviceIPA));
                }
            }
        }
        return artifactPaths;
    }

    private String getArtifactPath(String hostMachine, String artifact) throws Exception {
        String path;
        if (!isLocalhost(hostMachine) && !ResourceUtils.isUrl(artifact)) {
            path = uploadFile(hostMachine, artifact);
        } else {
            path = artifact;
        }
        return path;
    }
}

