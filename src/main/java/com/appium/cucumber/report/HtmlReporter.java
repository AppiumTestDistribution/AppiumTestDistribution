package com.appium.cucumber.report;

import net.masterthought.cucumber.ReportBuilder;
import org.apache.velocity.exception.VelocityException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlReporter {
	static File reportOutputDirectory = new File(System.getProperty("user.dir")+"/target"+"/");

	static List<String> list = new ArrayList<String>();

	public  void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else if (fileEntry.getName().endsWith(".json")) {
				list.add(reportOutputDirectory +"/"+fileEntry.getName());
                
			}
		}
	}

	public void generateReports() throws VelocityException, IOException {
		listFilesForFolder(reportOutputDirectory);
		String pluginUrlPath = "";
		String buildNumber = "1";
		String buildProject = "cucumber-appium-parallel";
		boolean skippedFails = true;
		boolean pendingFails = true;
		boolean undefinedFails = true;
		boolean missingFails = true;
		boolean flashCharts = true;
		boolean runWithJenkins = true;
		boolean highCharts = true;
		boolean parallelTesting = true;
		ReportBuilder reportBuilder = new ReportBuilder(list, reportOutputDirectory,
				pluginUrlPath, // String pluginUrlPath
				buildNumber, // String buildNumber
				buildProject, // String buildProject,
				skippedFails, // boolean skippedFails,
				pendingFails, // boolean pendingFails,
				undefinedFails, // boolean undefinedFails,
				missingFails, // boolean missingFails
				flashCharts, // boolean flashCharts,
				runWithJenkins, // boolean runWithJenkins,
				false, // boolean artifactsEnabled,
				"", // String artifactConfig
				highCharts, // boolean highCharts
				parallelTesting // boolean parallelTesting
		);

		reportBuilder.generateReports();
	}

}
