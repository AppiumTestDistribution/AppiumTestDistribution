package com.test.base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlReporter {
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

		sb.append("<table BORDER='2' ALIGN='center' width='100%'>");
		sb.append("<tr><th>TestClassName</th>");
		sb.append("<th>TestMethodName</th>");
		sb.append("<th>TestTime</th></tr>");
	
		for (int i = 0; i < 5; i++) {
			sb.append("<tr><td><a href=#><center><font color='green'>" + a + i + "</font></center></a></td>");
			sb.append("<td><a href=#><center><font color='red'>" + a + i + "</font></center></a></td>");
			sb.append("<td><a href=#><center><font color='yello'>" + a + i + "</font></center></a></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		System.out.println(sb.toString());
		FileWriter fstream = new FileWriter("MyHtml.html");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(sb.toString());
		out.close();

	}


}
