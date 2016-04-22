
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;

public class Xml_Json_Parser {
	public static void main(String[] args) throws Exception {
		String fileName = "temp.json";
		BufferedWriter bufferedWriter = null;
		try {
			int i = 1;
			FileWriter fileWriter;
			int dir_1 = new File(System.getProperty("user.dir") + "/test-output/junitreports/").listFiles().length - 1 ;
			
			List textFiles = new ArrayList();
			File dir = new File(System.getProperty("user.dir") + "/test-output/junitreports");
			
			for (File file : dir.listFiles()) {
				if (file.getName().contains(("Test"))) {
					System.out.println(file);
                    System.out.println(i);
					fileWriter = new FileWriter(fileName, true);
					InputStream inputStream = new FileInputStream(file);
					StringBuilder builder = new StringBuilder();
					int ptr = 0;
					while ((ptr = inputStream.read()) != -1) {
						builder.append((char) ptr);
					}

					String xml = builder.toString();
					JSONObject jsonObj = XML.toJSONObject(xml);

					// Always wrap FileWriter in BufferedWriter.
					bufferedWriter = new BufferedWriter(fileWriter);

					// Always close files.
					String jsonPrettyPrintString = jsonObj.toString(4);
					// bufferedWriter.write(jsonPrettyPrintString);
					if (i == 1) {
						bufferedWriter.append("[");
					}
					bufferedWriter.append(jsonPrettyPrintString);
					if (i != dir_1) {
						bufferedWriter.append(",");
					}
			
					if (i == dir_1) {
						bufferedWriter.append("]");
					}

					bufferedWriter.newLine();
					bufferedWriter.close();
					i++;
				}
			}

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
