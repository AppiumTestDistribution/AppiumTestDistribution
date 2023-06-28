package com.cucumber.listener;

import com.appium.filelocations.FileLocations;
import com.appium.manager.ATDRunner;
import com.appium.manager.AppiumDeviceManager;
import com.appium.manager.AppiumDriverManager;
import com.appium.manager.AppiumServerManager;
import com.appium.plugin.PluginClI;
import com.appium.utils.CommandPrompt;
import com.appium.utils.OverriddenVariable;
import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.epam.reportportal.service.ReportPortal;
import io.appium.java_client.AppiumDriver;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.logging.LogEntries;

import java.io.File;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import static com.appium.utils.OverriddenVariable.getOverriddenStringValue;

public class CucumberScenarioListener implements ConcurrentEventListener {
    private static final Logger LOGGER = Logger
            .getLogger(CucumberScenarioListener.class.getName());
    private final AppiumDriverManager appiumDriverManager;
    private final AppiumServerManager appiumServerManager;
    private Map<String, Integer> scenarioRunCounts = new HashMap<String, Integer>();

    public CucumberScenarioListener() throws Exception {
        LOGGER.info(String.format("ThreadID: %d: CucumberScenarioListener\n",
                Thread.currentThread().getId()));
        new ATDRunner();
        appiumServerManager = new AppiumServerManager();
        appiumDriverManager = new AppiumDriverManager();
    }

    private AppiumDriver allocateDeviceAndStartDriver(String scenarioName) {
        AppiumDriver driver = AppiumDriverManager.getDriver();
        if (driver == null || driver.getSessionId() == null) {
            return appiumDriverManager.startAppiumDriverInstance(scenarioName);
        } else {
            return driver;
        }
    }

    private String getCapabilityFor(org.openqa.selenium.Capabilities capabilities, String name) {
        Object capability = capabilities.getCapability(name);
        return null == capability ? "" : capability.toString();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::runStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::caseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::caseFinishedHandler);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::runFinishedHandler);
    }

    private void runStartedHandler(TestRunStarted event) {
        LOGGER.info("runStartedHandler");
        LOGGER.info(String.format("ThreadID: %d: beforeSuite: \n",
                Thread.currentThread().getId()));
    }

    public static File createFile(String dirName, String fileName) {
        File logFile = new File(System.getProperty("user.dir")
                + dirName
                + fileName + ".txt");
        if (logFile.exists()) {
            return logFile;
        }
        try {
            logFile.getParentFile().mkdirs();
            logFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logFile;
    }

    private void caseStartedHandler(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        LOGGER.info("$$$$$   TEST-CASE  -- " + scenarioName + "  STARTED   $$$$$");
        LOGGER.info("caseStartedHandler: " + scenarioName);
        Integer scenarioRunCount = getScenarioRunCount(scenarioName);
        String normalisedScenarioName = normaliseScenarioName(scenarioName);
        LOGGER.info(
                String.format("ThreadID: %d: beforeScenario: for scenario: %s\n",
                        Thread.currentThread().getId(), scenarioName));
        String scenarioReportDirectory = FileLocations.REPORTS_DIRECTORY
                                                 + normalisedScenarioName + File.separator;
        AppiumDriver createdAppiumDriver = allocateDeviceAndStartDriver(scenarioName);
        String deviceLogFileName = startDataCapture(scenarioRunCount,
                scenarioReportDirectory);

        TestExecutionContext testExecutionContext = new TestExecutionContext(scenarioName);
        testExecutionContext.addTestState("appiumDriver", createdAppiumDriver);
        testExecutionContext.addTestState("deviceId",
                AppiumDeviceManager.getAppiumDevice().getUdid());
        testExecutionContext.addTestState("deviceInfo", AppiumDeviceManager.getAppiumDevice());
        testExecutionContext.addTestState("deviceLog", deviceLogFileName);
        testExecutionContext.addTestState("scenarioRunCount", scenarioRunCount);
        testExecutionContext.addTestState("normalisedScenarioName", normalisedScenarioName);
        testExecutionContext.addTestState("scenarioDirectory", scenarioReportDirectory);
        testExecutionContext.addTestState("scenarioScreenshotsDirectory",
                scenarioReportDirectory
                        + "screenshot"
                        + File.separator);
    }

    @SneakyThrows
    private String startDataCapture(Integer scenarioRunCount,
                                    String deviceLogFileDirectory) {
        String fileName = String.format("/run-%s", scenarioRunCount);
        if (AppiumDeviceManager.getAppiumDevice().getPlatformName().equalsIgnoreCase("android")) {
            fileName = String.format("/%s-run-%s",
                            AppiumDeviceManager.getAppiumDevice().getUdid(), scenarioRunCount);
            File logFile = createFile(deviceLogFileDirectory
                                              + FileLocations.DEVICE_LOGS_DIRECTORY,
                    fileName);
            fileName = logFile.getAbsolutePath();
            LOGGER.debug("Capturing device logs here: " + fileName);
            PrintStream logFileStream = new PrintStream(logFile);
            try {
                LogEntries logcatOutput = AppiumDriverManager.getDriver()
                                                  .manage().logs().get("logcat");
                StreamSupport.stream(logcatOutput.spliterator(), false)
                        .forEach(logFileStream::println);
            } catch (Exception e) {
                LOGGER.warn("ERROR in getting logcat. Skipping logcat capture");
            }
        }
        return fileName;
    }

    private boolean isRunningOnpCloudy() {
        boolean isPCloudy = getCloudName().equalsIgnoreCase("pCloudy");
        LOGGER.info(AppiumDeviceManager.getAppiumDevice().getUdid() + ": running on: "
                + getCloudName());
        return isPCloudy;
    }

    private static String getCloudName() {
        return PluginClI.getInstance().getCloudName();
    }

    private boolean isRunningOnBrowserStack() {
        boolean isBrowserStack = getCloudName().equalsIgnoreCase("browserstack");
        LOGGER.info(AppiumDeviceManager.getAppiumDevice().getUdid() + ": running on: "
                + getCloudName());
        return isBrowserStack;
    }

    private boolean isRunningOnHeadspin() {
        boolean isHeadspin = getCloudName().equalsIgnoreCase("headspin");
        LOGGER.info(AppiumDeviceManager.getAppiumDevice().getUdid() + ": running on: "
                + getCloudName());
        return isHeadspin;
    }

    private String normaliseScenarioName(String scenarioName) {
        return scenarioName.replaceAll("[`~ !@#$%^&*()\\-=+\\[\\]{}\\\\|;:'\",<.>/?]", "_");
    }

    private Integer getScenarioRunCount(String scenarioName) {
        if (scenarioRunCounts.containsKey(scenarioName)) {
            scenarioRunCounts.put(scenarioName, scenarioRunCounts.get(scenarioName) + 1);
        } else {
            scenarioRunCounts.put(scenarioName, 1);
        }
        return scenarioRunCounts.get(scenarioName);
    }

    private void caseFinishedHandler(TestCaseFinished event) {
        String scenarioName = event.getTestCase().getName();
        LOGGER.info("caseFinishedHandler Name: " + scenarioName);
        long threadId = Thread.currentThread().getId();
        LOGGER.info(
                String.format("ThreadID: %d: afterScenario: for scenario: %s\n",
                        threadId, event.getTestCase().toString()));

        TestExecutionContext testExecutionContext =
                SessionContext.getTestExecutionContext(threadId);

        AppiumDriver driver = (AppiumDriver) testExecutionContext.getTestState("appiumDriver");
        attachCloudExecutionReportLinkToReportPortal(driver);
        stopAppiumDriver();
        String deviceLogFileName = testExecutionContext.getTestStateAsString("deviceLog");
        if (null != deviceLogFileName) {
            LOGGER.debug(String.format("Attaching device logs %s to ReportPortal: ",
                    deviceLogFileName));
            File file = new File(deviceLogFileName);
            ReportPortal.emitLog("ADB Logs - " + file.getName(),
                    "DEBUG", new Date(), file);
        }
        SessionContext.remove(threadId);
        LOGGER.info("$$$$$   TEST-CASE  -- " + scenarioName + "  ENDED   $$$$$");
    }

    private void stopAppiumDriver() {
        try {
            appiumDriverManager.stopAppiumDriver();
        } catch (Exception e) {
            LOGGER.warn("Error stopping Appium Driver", e);
        }
    }

    private void attachCloudExecutionReportLinkToReportPortal(AppiumDriver driver) {
        if ((PluginClI.getInstance().isCloudExecution()) && isRunningOnpCloudy()) {
            String link = (String) driver.executeScript("pCloudy_getReportLink");
            String message = "pCloudy Report link available here: " + link;
            LOGGER.info(message);
            ReportPortal.emitLog(message, "DEBUG", new Date());
        } else if ((PluginClI.getInstance().isCloudExecution()) && isRunningOnHeadspin()) {
            String sessionId = driver.getSessionId().toString();
            String link = "https://ui-dev.headspin.io/sessions/" + sessionId + "/waterfall";
            String message = "Headspin Report link available here: " + link;
            LOGGER.info(message);
            ReportPortal.emitLog(message, "DEBUG", new Date());
        } else if ((PluginClI.getInstance().isCloudExecution()) && isRunningOnBrowserStack()) {
            String sessionId = driver.getSessionId().toString();
            String link = getReportLinkFromBrowserStack(sessionId);
            String message = "BrowserStack Report link available here: " + link;
            LOGGER.info(message);
            ReportPortal.emitLog(message, "DEBUG", new Date());
        }
    }

    private static String getReportLinkFromBrowserStack(String sessionId) {
        String reportLink = "";
        String cloudUser = getOverriddenStringValue("CLOUD_USERNAME");
        String cloudPassword = getOverriddenStringValue("CLOUD_KEY");
        String curlCommand = "curl --insecure " + getCurlProxyCommand() + " -u \"" + cloudUser + ":" + cloudPassword + "\" -X GET \"https://api-cloud.browserstack.com/app-automate/sessions/" + sessionId + ".json\"";
        LOGGER.debug(String.format("Curl command: '%s'", curlCommand));
        CommandPrompt cmd = new CommandPrompt();
        String resultStdOut = null;
        try {
            resultStdOut = cmd.runCommandThruProcess(curlCommand);
            LOGGER.debug(String.format("Response from BrowserStack - '%s'", resultStdOut));
            JSONObject pr = new JSONObject(resultStdOut);
            JSONObject automation_session = pr.getJSONObject("automation_session");
            reportLink = automation_session.getString("browser_url");
            LOGGER.debug("reportLink: " + reportLink);
        } catch (Exception e) {
            LOGGER.debug("Unable to get report link from BrowserStack: " + e.getMessage());
            e.printStackTrace();
        }
        return reportLink;
    }

    static String getCurlProxyCommand() {
        String curlProxyCommand = "";
        if (null != OverriddenVariable.getOverriddenStringValue("PROXY_URL")) {
            curlProxyCommand = " --proxy " + System.getProperty("PROXY_URL");
        }
        return curlProxyCommand;
    }

    private void runFinishedHandler(TestRunFinished event) {
        LOGGER.info("runFinishedHandler: " + event.getResult().toString());
        LOGGER.info(String.format("ThreadID: %d: afterSuite: %n",
                Thread.currentThread().getId()));
        try {
            appiumServerManager.destroyAppiumNode();
            SessionContext.setReportPortalLaunchURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
