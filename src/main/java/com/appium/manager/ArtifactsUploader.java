package com.appium.manager;


import com.appium.utils.Api;
import com.appium.utils.CapabilityManager;
import com.appium.utils.HostMachineDeviceManager;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

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
        if (instance == null) {
            instance = new ArtifactsUploader();
        }
        return instance;
    }

    public void initializeArtifacts() throws IOException {
        for (String hostMachine : hostMachineDeviceManager.getDevicesByHost().getAllHosts()) {
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

    private String uploadFile(String hostMachine, String appPath) throws IOException {
        JSONObject jsonObject = new JSONObject(api.uploadMultiPartFile(new File(appPath), hostMachine));
        return jsonObject.getString("filePath");
    }

    private HashMap<String, String> getArtifactForHost(String hostMachine) throws IOException {
        String app = "app";
        HashMap<String, String> artifactPaths = new HashMap<>();
        JSONObject android = capabilityManager
                .getCapabilityObjectFromKey("android");
        JSONObject iOSAppPath = capabilityManager
                .getCapabilityObjectFromKey("iOS");
        if (android != null && android.has(app)) {
            artifactPaths.put("APK", getArtifactPath(hostMachine, android.getString("app")));
        }
        if (iOSAppPath != null && iOSAppPath.has("app")) {
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

    private String getArtifactPath(String hostMachine, String artifact) throws IOException {
        String path;
        if (!isLocalhost(hostMachine) && !ResourceUtils.isUrl(artifact)) {
            path = uploadFile(hostMachine, artifact);
        } else {
            path = artifact;
        }
        return path;
    }
}

