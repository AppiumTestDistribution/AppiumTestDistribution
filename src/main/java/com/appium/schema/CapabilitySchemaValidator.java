package com.appium.schema;

import com.appium.manager.FigletHelper;

import com.appium.utils.CapabilityManager;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetAddress;


public class CapabilitySchemaValidator {

    public void validateCapabilitySchema(JSONObject capability) {
        try {
            isPlatformInEnv();
            InputStream inputStream = getClass().getResourceAsStream(getPlatform());
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(capability.toString()));
            validateRemoteHosts();
        } catch (ValidationException e) {
            if (e.getCausingExceptions().size() > 1) {
                e.getCausingExceptions().stream()
                        .map(ValidationException::getMessage)
                        .forEach(System.out::println);
            } else {
                System.out.println(e.getErrorMessage());
            }

            throw new ValidationException("Capability json provided is missing the above schema");
        }
    }

    private String getPlatform() {
        String platform = System.getenv("Platform");
        String schema = null;
        switch (platform.toLowerCase()) {
            case "both":
                schema = "/androidAndiOSSchema.json";
                break;
            case "android":
                schema = "/androidSchema.json";
                break;
            case "ios":
                schema = "/iOSSchema.json";
                break;
            default:
                System.out.println("Just for codacy!!");
                break;

        }
        return schema;
    }

    private void isPlatformInEnv() {
        if (System.getenv("Platform") == null) {
            throw new IllegalArgumentException("Please execute with Platform environment"
                    + ":: Platform=android/ios/both mvn clean -Dtest=Runner test");
        }
    }

    private void validateRemoteHosts() {
        CapabilityManager capabilityManager;
        try {
            capabilityManager = CapabilityManager.getInstance();
            if (capabilityManager.getCapabilities().has("hostMachines")) {
                JSONArray hostMachines = capabilityManager.getHostMachineObject();
                for (Object hostMachine : hostMachines) {
                    JSONObject hostMachineJson = ((JSONObject) hostMachine);
                    String machineIP = (String) hostMachineJson.get("machineIP");
                    if (InetAddress.getByName(machineIP).isReachable(5000)) {
                        System.out.println("ATD is Running on " + machineIP);
                    } else {
                        FigletHelper.figlet("Unable to connect to Remote Host " + machineIP);
                        throw new ConnectException();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
