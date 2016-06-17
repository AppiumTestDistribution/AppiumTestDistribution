import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadXML {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            File inputFile =
                new File("/Users/saikrisv/Documents/workspace/TestNGParallel/merged_xml.xml");

            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);

            System.out.println("Root element :" + document.getRootElement().getName());

            Element classElement = document.getRootElement();

            List<Element> tests = classElement.getChildren("testsuite");
            System.out.println("----------------------------");

            for (Element supercarElement : tests) {
                System.out.println("\nCurrent Element :" + supercarElement.getName());
                Attribute attribute = supercarElement.getAttribute("name");
                System.out.println("TestCaseName : " + attribute.getValue());
                List<Element> testCases = supercarElement.getChildren("testcase");
                for (int count = 0; count < testCases.size(); count++) {
                    Element carElement = testCases.get(count);
                    Attribute typeAttribute = carElement.getAttribute("name");
                    if (typeAttribute != null)
                        System.out.println(
                                "TestMethod in *********" + attribute.getValue() + typeAttribute
                                        .getValue());
                    else {
                        System.out.println("");
                    }


                }
            }
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

}
