package com.appium.utils;

import com.appium.capabilities.Capabilities;
import com.appium.device.HostMachineDeviceManager;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;

public class ArtifactsUploader {

    private final JSONObject capabilities;
    private Api api = new Api();
    private List<HostArtifact> hostArtifacts;

    public ArtifactsUploader(JSONObject capabilities) {
        this.capabilities = capabilities;
        hostArtifacts = new ArrayList<>();
    }

    public void initializeArtifacts() {
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

    private boolean isCloud(String hostMachine) {
        return Capabilities.getInstance().isCloud(hostMachine);
    }

    public List<HostArtifact> getHostArtifacts() {
        return hostArtifacts;
    }

    private String uploadFile(String hostMachine, String appPath) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(api.uploadMultiPartFile(
                    new File(appPath).getCanonicalFile(), hostMachine));
        } catch (Exception e) {
            throw new RuntimeException("Error in uploading file: " + appPath, e);
        }
        return jsonObject.getString("filePath");
    }

    private HashMap<String, String> getArtifactForHost(String hostMachine) {
        String platform = getOverriddenStringValue("Platform");
        String app = "app";
        HashMap<String, String> artifactPaths = new HashMap<>();
        JSONObject android = capabilities.has("android")
                ? capabilities.getJSONObject("android")
                : null;
        JSONObject iOSAppPath = capabilities.has("iOS")
                ? capabilities.getJSONObject("iOS")
                : null;
        JSONObject windowsAppPath = capabilities.has("windows")
                ? capabilities.getJSONObject("windows")
                : null;
        if (android != null && android.has(app)
                && (platform.equalsIgnoreCase("android")
                || platform.equalsIgnoreCase("both"))) {
            JSONObject androidApp = android.getJSONObject("app");
            String appPath = androidApp.getString("local");
            if (isCloud(hostMachine) && androidApp.has("cloud")) {
                appPath = androidApp.getString("cloud");
            }
            artifactPaths.put("APK", getArtifactPath(hostMachine, appPath));
        }
        if (iOSAppPath != null && iOSAppPath.has("app")
                && (platform.equalsIgnoreCase("ios")
                || platform.equalsIgnoreCase("both"))) {
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
                if (isCloud(hostMachine) && iOSApp.has("cloud")) {
                    String cloudApp = iOSApp.getString("cloud");
                    artifactPaths.put("APP", getArtifactPath(hostMachine, cloudApp));
                }
            }
        }
        if (windowsAppPath != null && windowsAppPath.has(app)) {
            JSONObject windowsApp = windowsAppPath.getJSONObject("app");
            String appPath = windowsApp.getString("local");
            artifactPaths.put("EXE", getArtifactPath(hostMachine, appPath));
        }
        return artifactPaths;
    }

    private String getArtifactPath(String hostMachine, String artifact) {
        String path;
        if (!isCloud(hostMachine) && !isLocalhost(hostMachine)
                && !new UrlValidator().isValid(artifact)) {
            path = uploadFile(hostMachine, artifact);
        } else {
            path = artifact;
        }
        return path;
    }
}

