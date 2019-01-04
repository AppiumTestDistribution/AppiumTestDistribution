package com.appium.utils;

import java.util.Map;

public class HostArtifact {
    private final Map<String, String> artifactsByType;
    private String host;

    HostArtifact(String host, Map<String, String> artifactsByType) {
        this.host = host;
        this.artifactsByType = artifactsByType;
    }

    public String getArtifactPath(String type) {
        return artifactsByType.get(type);
    }

    public String getHost() {
        return host;
    }

}
