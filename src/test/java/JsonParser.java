import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;


public class JsonParser {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("temp.json"));
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    StringBuilder everything = sb;
	    JSONArray jsonObj = new JSONArray(everything.toString());
	    
	    System.out.println(jsonObj.length());
	    for (int i=0 ; i < jsonObj.length();i++ )
	    {
	    	JSONObject entierJson = (JSONObject) jsonObj.getJSONObject(i).get("testsuite");
		   // System.out.println(entierJson.get("testcase"));	
		    Set keys = ((JSONObject) entierJson.get("testcase")).keySet();
	        Iterator itr = keys.iterator();
	 
	        String key;
	        String value;
	        while(itr.hasNext())
	        {
	            key = (String)itr.next();
	            if(key.equals("classname") ){
	                value = (String)((JSONObject) entierJson.get("testcase")).get(key).toString();
	  	            System.out.println(key + " - "+ value.toString());
	            }
	            
	          
	        }
		   
		  }
	    }

	}


