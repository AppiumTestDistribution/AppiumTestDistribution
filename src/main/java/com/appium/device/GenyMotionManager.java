package com.appium.device;

import com.appium.utils.Api;
import com.appium.utils.CommandPrompt;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class GenyMotionManager {

    private static final Logger LOGGER = Logger
        .getLogger(GenyMotionManager.class.getSimpleName());
    private static String cloud_user = System.getenv("CLOUD_USER");
    private static String cloud_key = System.getenv("CLOUD_KEY");

    protected  static void connectToGenyCloud(String udid, Object devices) throws IOException {

        String gmLogin = "gmsaas auth login "
            + cloud_user + " " + cloud_key;
        try {
            new CommandPrompt()
                .runCommandThruProcess(gmLogin);
            LOGGER.info("Connected to Genymotion Cloud..");
        } catch (IOException e) {
            throw new IOException("Failed to Connect to geny cloud..");
        }

        ((ArrayList)devices).parallelStream().forEach(o -> {

            String instanceUdid = (String) ((HashMap) o).get("udid");
            String instanceName = (String) ((HashMap) o).get("deviceName");
            String gnInstance = "gmsaas instances start "
                + instanceUdid + " " + "\"" + instanceName + "\"";
            LOGGER.info("Starting Device on genymotion cloud instance with uuid"
                + instanceUdid + "and device name " + instanceName);
            String createdInstance;
            try {
                createdInstance = new CommandPrompt()
                    .runCommandThruProcess(gnInstance);
                String adbTunnel = "gmsaas instances adbconnect "
                    + " " + createdInstance;
                new CommandPrompt()
                    .runCommandThruProcess(adbTunnel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            LOGGER.info("Running Instances");
            String gmsaas_instances_list = new CommandPrompt()
                .runCommandThruProcess("gmsaas instances list");
            LOGGER.info(gmsaas_instances_list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stopAllGenymotionInstances() throws ParseException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", cloud_user);
        jsonObject.put("password", cloud_key);
        Api api = new Api();
        String post = api.post("https://api.geny.io/cloud/v1/users/login", jsonObject.toString());
        JSONParser parser = new JSONParser();
        Object parse = parser.parse(post);
        Object token = ((JSONObject) parse).get("token");
        Response response = api.requestBuilderWithBearerToken("https://api.geny.io/cloud/v1/instances", token.toString());
        String string = response.body().string();
        JSONParser responseUdid = new JSONParser();
        ((JSONArray) responseUdid.parse(string)).parallelStream().forEach(o -> {
            Object uuid = ((JSONObject) o).get("uuid");
            LOGGER.info("Stopping Genymotion instance.." + uuid.toString());
            String recipe_uuid = null;
            recipe_uuid = api.postWithNoBody("https://api.geny.io/cloud/v1/instances/" + uuid
                + "/stop-disposable", token.toString());
            System.out.println(recipe_uuid);
        });
    }
}
