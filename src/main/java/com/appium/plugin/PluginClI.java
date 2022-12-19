package com.appium.plugin;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class PluginClI {
    public String subcommand;
    public String address;
    public String basePath;
    public int port;
    public ArrayList<String> usePlugins;
    public Plugin plugin;
    public ArrayList<Object> extraArgs;
    public boolean allowCors;
    public ArrayList<Object> allowInsecure;
    public int callbackPort;
    public boolean debugLogSpacing;
    public ArrayList<Object> denyInsecure;
    public int keepAliveTimeout;
    public boolean localTimezone;
    public String loglevel;
    public boolean logNoColors;
    public boolean logTimestamp;
    public boolean longStacktrace;
    public boolean noPermsCheck;
    public boolean relaxedSecurityEnabled;
    public boolean sessionOverride;
    public boolean strictCaps;
    public ArrayList<Object> useDrivers;
    public String tmpDir;
    public Meta meta;
    public int $loki;

    @Getter
    public static class DeviceFarm{
        public String platform;
        public String deviceTypes;
        public JsonNode remote;
        public boolean skipChromeDownload;
    }

    public static class Meta{
        public int revision;
        public long created;
        public int version;
    }
    public static class Plugin{
        @JsonProperty("device-farm")
        @JsonAlias("deviceFarm")
        @Getter
        public DeviceFarm deviceFarm;
    }

    public String getPlatFormName() {
        return getPlugin().getDeviceFarm().getPlatform();
    }

    public boolean isCloud() {
        return getPlugin().getDeviceFarm().getRemote().get(0).has("cloudName");
    }

    private static PluginClI instance;
    @SneakyThrows
    public static PluginClI getInstance() {
        if (instance == null) {
            PluginCliRequest plugin = new PluginCliRequest();
            Response cliArgs = plugin.getCliArgs();
            ObjectMapper mapper = new ObjectMapper();
            PluginClI[] pluginClIS = mapper.readValue(cliArgs.body().string(), PluginClI[].class);
            instance = pluginClIS[0];
        }
        return instance;
    }
}