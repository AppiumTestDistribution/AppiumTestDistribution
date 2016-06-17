import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JsonParser {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("temp.json"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        List<String> classname = new ArrayList<>();
        List<String> name = new ArrayList<>();

        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        JSONArray jsonObj = new JSONArray(sb.toString());

        System.out.println(jsonObj.length());
        for (int i = 0; i < jsonObj.length(); i++) {
            JSONObject entireJson = (JSONObject) jsonObj.getJSONObject(i).get("testsuite");
            // System.out.println(entierJson.get("testcase"));
            Set keys = ((JSONObject) entireJson.get("testcase")).keySet();
            Iterator itr = keys.iterator();

            String key;
            String value;
            while (itr.hasNext()) {
                key = (String) itr.next();
                if (key.equals("classname")) {
                    value = (String) ((JSONObject) entireJson.get("testcase")).get(key).toString();
                    classname.add(value);
                    System.out.println(key + " - " + value);

                }
                if (key.equals("name")) {
                    value = (String) ((JSONObject) entireJson.get("testcase")).get(key).toString();
                    name.add(value);
                    System.out.println(key + " - " + value);

                }

            }

        }
    }

}
