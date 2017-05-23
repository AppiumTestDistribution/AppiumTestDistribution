package com.appium.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by saikrisv on 23/05/17.
 */
public class JsonParser {

    JSONParser parser = new JSONParser();
    Object object;

    public JsonParser(String filePath){
        try {
            object = parser.parse(new FileReader(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray array = new JSONArray();
        array.add(object);
        Object o = ((JSONArray) object).get(0);
        object = (JSONObject) o;
    }

    public JSONObject getJsonParsedObject() {
        return (JSONObject) object;
    }
}
