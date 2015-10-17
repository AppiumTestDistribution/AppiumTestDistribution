package com.test.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileFinder {
	public static void main(String[] args) throws IOException {
		String logo = "http://sauceio.com/wp-content/uploads/2014/04/appium_logo_final.png";
		StringBuilder sb = new StringBuilder();

		String a = "testcasepassed";
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>Automation Results");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body BGCOLOR='white'> <center><img src=" + logo + " ALIGN='center'></center>");
		sb.append("</body>");
		sb.append("<div style=float:left>");
		sb.append("<table BORDER='1' ALIGN='center' width='320'>");
		sb.append("<tr><th>TestClassName</th></tr>");
        
        	sb.append("<tr><TH><a href=#><center><font color='green'>" + a + "</font></center></a></TH>");
    		sb.append("</tr>");
    		sb.append("</table>");
    		sb.append("</div>");

    		sb.append("<div style=float:left>");
    		sb.append("<table BORDER='1' ALIGN='center' width='320'>");
    		sb.append("<tr><th>TestClassMethod</th></tr>");

    		sb.append("<tr><TH><a href=#><center><font color='red'>" + a + "</font></center></a></TH>");
        
		

		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");
		sb.append("</html>");
		System.out.println(sb.toString());
		FileWriter fstream = new FileWriter("MyHtml.html");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(sb.toString());
		out.close();
	}
	
	
	public void jSonParser() throws IOException{
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
		    System.out.println(entierJson.get("testcase"));	
		    Set keys = ((JSONObject) entierJson.get("testcase")).keySet();
	        Iterator itr = keys.iterator();
	 
	        String key;
	        String value;
	        while(itr.hasNext())
	        {
	            key = (String)itr.next();
	            if(key.contains("name") ){
	                value = (String)((JSONObject) entierJson.get("testcase")).get(key).toString();
	  	            System.out.println(key + " - "+ value.toString());
	            }
	            
	          
	        }
		   
		  }
	}
}
