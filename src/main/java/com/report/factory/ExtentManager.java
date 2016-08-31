package com.report.factory;

import com.relevantcodes.extentreports.ExtentReports;

import java.io.FileInputStream;
import java.util.Properties;

public class ExtentManager {
    public static ExtentReports instance;
    public static Properties prop = new Properties();

    public synchronized static ExtentReports getInstance() {
        if (instance == null) {
            System.out.println(System.getProperty("user.dir"));
            instance =
                new ExtentReports(System.getProperty("user.dir") + "/target/ExtentReport.html");
            try {
                prop.load(new FileInputStream("config.properties"));
//                if (System.getenv("ExtentX").equalsIgnoreCase("true")) {
                if (System.getProperty("ExtentX").equalsIgnoreCase("true")) {
                    instance.x(prop.getProperty("MONGODB_SERVER"),
                        Integer.parseInt(prop.getProperty("MONGODB_PORT")));

                    System.out.println(String.format("ExtentX initialized completed: [%s:%s]",
                            prop.getProperty("MONGODB_SERVER"), prop.getProperty("MONGODB_PORT")));
                }
            } catch (Exception e) {
                System.out.println("Not taking ExtendReporting");
            }
        }
        return instance;
    }
}
