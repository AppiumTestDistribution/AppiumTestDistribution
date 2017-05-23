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

    static JsonParser instance;
    static JSONParser parser = new JSONParser();;
    static Object obj;

    public JsonParser(String filePath) throws IOException, ParseException {
        if(obj == null){
            obj = parser.parse(new FileReader(filePath));
        }
    }


    public static JSONObject getInstance(String filePath){
        if(instance == null){
            try {
                instance = new JsonParser(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONArray array = new JSONArray();
            array.add(obj);
            Object o = ((JSONArray) obj).get(0);
            return (JSONObject) o;
        }
        return null;
    }

}
