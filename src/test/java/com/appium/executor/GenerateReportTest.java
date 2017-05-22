package com.appium.executor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import java.io.FileReader;

/**
 * Created by saikrisv on 13/05/17.
 */
public class GenerateReportTest {

    @Test
    public void generateReportJson() throws Exception {
       // ImageUtils.creatResultsSet();
        //JSONParser parser = new JSONParser();
       // Object obj = parser.parse(new FileReader("/Users/saikrisv/git/new_appium_parallel/AppiumTestDistribution/caps/android.json"));
       // JSONObject jsonObject = (JSONObject) obj;
        JSONParser parser = new JSONParser();
        Object obj  = parser.parse(new FileReader("/Users/saikrisv/git/new_appium_parallel/AppiumTestDistribution/caps/android.json"));
        JSONArray array = new JSONArray();
        array.add(obj);
        Object o = ((JSONArray) obj).get(0);
        JSONObject object = (JSONObject) o;
        object.forEach((o1, o2) -> {
            System.out.println(o1.toString() + o2.toString());
        });

    }
}
