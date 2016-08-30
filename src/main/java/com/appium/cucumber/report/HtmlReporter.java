package com.appium.cucumber.report;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.velocity.exception.VelocityException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlReporter {
    public File reportOutputDirectory = new File(System.getProperty("user.dir") + "/target/");
    public List<String> list = new ArrayList<String>();

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else if (fileEntry.getName().endsWith(".json")) {
                System.out.println("*******" + fileEntry.getName());
                list.add(reportOutputDirectory + "/" + fileEntry.getName());

            }
        }
    }

    public void generateReports() throws VelocityException, IOException {
        listFilesForFolder(reportOutputDirectory);
        String jenkinsBasePath = "";
        String buildNumber = "1";
        String projectName = "cucumber-jvm";
        boolean skippedFails = true;
        boolean pendingFails = false;
        boolean undefinedFails = true;
        boolean missingFails = true;
        boolean runWithJenkins = false;
        boolean parallelTesting = false;

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setStatusFlags(skippedFails, pendingFails, undefinedFails, missingFails);
        configuration.setParallelTesting(parallelTesting);
        configuration.setJenkinsBasePath(jenkinsBasePath);
        configuration.setRunWithJenkins(runWithJenkins);
        configuration.setBuildNumber(buildNumber);

        ReportBuilder reportBuilder = new ReportBuilder(list, configuration);
        reportBuilder.generateReports();
    }

}
