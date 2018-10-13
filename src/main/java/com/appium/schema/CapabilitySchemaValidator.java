package com.appium.schema;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;


public class CapabilitySchemaValidator {

    public void validateCapabilitySchema(JSONObject capability) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(getPlatform());
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(capability.toString()));
        } catch (ValidationException e ) {
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
        switch(platform.toLowerCase()) {
            case "both" :
                schema =  "/androidAndiOSSchema.json";
                break;
            case "android" :
                schema = "/androidSchema.json";
                break;
            case "ios" :
                schema = "/iOSSchema.json";
                break;
            default :
                System.out.println("Just for codacy!!");

        }
        return schema;
    }

}
