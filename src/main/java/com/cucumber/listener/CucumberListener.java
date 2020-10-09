//package com.cucumber.listener;
//
//import com.appium.android.AndroidDeviceConfiguration;
//import com.appium.ios.IOSDeviceConfiguration;
//import com.appium.manager.AppiumDriverManager;
//import com.appium.manager.AppiumServerManager;
//import com.appium.manager.DeviceAllocationManager;
//import com.appium.manager.DeviceSingleton;
//import com.appium.utils.ImageUtils;
//import com.epam.reportportal.annotations.Step;
//import com.epam.reportportal.listeners.ItemStatus;
//import com.epam.reportportal.service.step.StepReporter;
//import com.video.recorder.XpathXML;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
//import io.reactivex.Maybe;
//import org.jetbrains.annotations.NotNull;
//import org.testng.ISuiteListener;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//
///**
// * Cucumber custom format listener which generates ExtentsReport html file
// * com.epam.reportportal.cucumber.ScenarioReporter
// */
//public class CucumberListener implements StepReporter, ISuiteListener {
//
//  private final DeviceAllocationManager deviceAllocationManager;
//  public AppiumServerManager appiumServerManager;
//  public AppiumDriverManager appiumDriverManager;
//  public DeviceSingleton deviceSingleton;
//  public LinkedList<Step> testSteps;
//  public AppiumDriver<MobileElement> appium_driver;
//  private AndroidDeviceConfiguration androidDevice;
//  private IOSDeviceConfiguration iosDevice;
//  public String deviceModel;
//  public ImageUtils imageUtils = new ImageUtils();
//  public XpathXML xpathXML = new XpathXML();
//  private String CI_BASE_URI = null;
//
//  private static final Map<String, String> MIME_TYPES_EXTENSIONS =
//      new HashMap() {
//        {
//          this.put("image/bmp", "bmp");
//          this.put("image/gif", "gif");
//          this.put("image/jpeg", "jpg");
//          this.put("image/png", "png");
//          this.put("image/svg+xml", "svg");
//          this.put("video/ogg", "ogg");
//        }
//      };
//
//  public CucumberListener() throws Exception {
//    appiumServerManager = new AppiumServerManager();
//    appiumDriverManager = new AppiumDriverManager();
//    deviceAllocationManager = DeviceAllocationManager.getInstance();
//    deviceSingleton = DeviceSingleton.getInstance();
//    iosDevice = new IOSDeviceConfiguration();
//    androidDevice = new AndroidDeviceConfiguration();
//  }
//
//  @Override
//  public void setParent(Maybe<String> maybe) {}
//
//  @Override
//  public Maybe<String> getParent() {
//    return null;
//  }
//
//  @Override
//  public void removeParent(Maybe<String> maybe) {}
//
//  @Override
//  public boolean isFailed(Maybe<String> maybe) {
//    return false;
//  }
//
//  @Override
//  public void sendStep(String s) {}
//
//  @Override
//  public void sendStep(String s, String... strings) {}
//
//  @Override
//  public void sendStep(@NotNull ItemStatus itemStatus, String s) {}
//
//  @Override
//  public void sendStep(@NotNull ItemStatus itemStatus, String s, String... strings) {}
//
//  @Override
//  public void sendStep(@NotNull ItemStatus itemStatus, String s, Throwable throwable) {}
//
//  @Override
//  public void sendStep(String s, File... files) {}
//
//  @Override
//  public void sendStep(@NotNull ItemStatus itemStatus, String s, File... files) {}
//
//  @Override
//  public void sendStep(
//      @NotNull ItemStatus itemStatus, String s, Throwable throwable, File... files) {}
//
//  @Override
//  public void finishPreviousStep() {}
//
//  //    public void before(Match match, Result result) {
//  //    }
//  //
//  //    public void result(Result result) {
//  //        if ("passed".equals(result.getStatus())) {
//  //            // To Be Worked out
//  //        } else if ("failed".equals(result.getStatus())) {
//  //            String failed_StepName = testSteps.poll().getName();
//  //            String context = AppiumDriverManager.getDriver().getContext();
//  //            boolean contextChanged = false;
//  //            if ("Android".equalsIgnoreCase(AppiumDriverManager.getDriver()
//  //                    .getSessionDetails().get("platform")
//  //                    .toString())
//  //                    && !"NATIVE_APP".equals(context)) {
//  //                AppiumDriverManager.getDriver().context("NATIVE_APP");
//  //                contextChanged = true;
//  //            }
//  //            File scrFile = ((TakesScreenshot) AppiumDriverManager.getDriver())
//  //                    .getScreenshotAs(OutputType.FILE);
//  //            if (contextChanged) {
//  //                AppiumDriverManager.getDriver().context(context);
//  //            }
//  //            if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
//  //                deviceModel = androidDevice.getDeviceModel();
//  //                screenShotAndFrame(failed_StepName, scrFile, "android");
//  //            } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
//  //                deviceModel =
//  //                        AppiumDeviceManager.getAppiumDevice().getDevice().getDeviceModel();
//  //                screenShotAndFrame(failed_StepName, scrFile, "iPhone");
//  //            }
//  //            try {
//  //                attachScreenShotToReport(failed_StepName);
//  //            } catch (IOException e) {
//  //                e.printStackTrace();
//  //            }
//  //        }
//  //    }
//  //
//  //    public void after(Match match, Result result) {
//  //
//  //    }
//  //
//  //    public void match(Match match) {
//  //
//  //    }
//  //
//  //    public void embedding(String s, byte[] bytes) {
//  //    }
//  //
//  //    public void write(String s) {
//  //        // ReportManager.endTest(parent);
//  //    }
//  //
//  //    public void syntaxError(String s, String s1, List<String> list,
//  String s2, Integer integer)
//  // {
//  //
//  //    }
//  //
//  //    public void uri(String s) {
//  //
//  //    }
//  //
//  //    public void feature(Feature feature) {
//  //        String[] tagsArray = getTagArray(feature.getTags());
//  //        String tags = String.join(",", tagsArray);
//  //        deviceAllocationManager.allocateDevice(
//  //                deviceAllocationManager.getNextAvailableDevice());
//  //    }
//  //
//  //    private String[] getTagArray(List<Tag> tags) {
//  //        String[] tagArray = new String[tags.size()];
//  //        for (int i = 0; i < tags.size(); i++) {
//  //            tagArray[i] = tags.get(i).getName();
//  //        }
//  //        return tagArray;
//  //    }
//  //
//  //    public void scenarioOutline(ScenarioOutline scenarioOutline) {
//  //
//  //    }
//  //
//  //    public void examples(Examples examples) {
//  //
//  //    }
//  //
//  //    public void startOfScenarioLifeCycle(Scenario scenario) {
//  //        createAppiumInstance(scenario);
//  //        this.testSteps = new LinkedList<Step>();
//  //        System.out.println(testSteps);
//  //    }
//  //
//  //    public void createAppiumInstance(Scenario scenario) {
//  //        String[] tagsArray = getTagArray(scenario.getTags());
//  //        String tags = String.join(",", tagsArray);
//  //        try {
//  //            startAppiumServer(scenario, tags);
//  //        } catch (Exception e) {
//  //            e.printStackTrace();
//  //        }
//  //    }
//  //
//  //    //TO DO fix this
//  //    public void startAppiumServer(Scenario scenario, String tags) throws Exception {
//  //        appiumDriverManager.startAppiumDriverInstance();
//  //        ///This portion should be Broken : TODO
//  //    }
//  //
//  //    public void background(Background background) {
//  //
//  //    }
//  //
//  //    public void scenario(Scenario scenario) {
//  //
//  //    }
//  //
//  //    public void step(Step step) {
//  //        testSteps.add(step);
//  //    }
//  //
//  //    public void endOfScenarioLifeCycle(Scenario scenario) {
//  //        AppiumDriverManager.getDriver().quit();
//  //    }
//  //
//  //    public void done() {
//  //
//  //    }
//  //
//  //    public void close() {
//  //
//  //    }
//  //
//  //    public void eof() {
//  //        try {
//  //            deviceAllocationManager.freeDevice();
//  //        } catch (Exception e) {
//  //            e.printStackTrace();
//  //        }
//  //    }
//  //
//  //
//  //    public void screenShotAndFrame(String failed_StepName, File scrFile, String device) {
//  //        try {
//  //            File framePath =
//  //                    new File(System.getProperty("user.dir") + "/src/test/resources/frames/");
//  //            FileUtils.copyFile(scrFile, new File(
//  //                    System.getProperty("user.dir")
//  //                            + FileLocations.SCREENSHOTS_DIRECTORY + device + "/"
//  //                            + AppiumDeviceManager.getAppiumDevice().getDevice().getUdid()
//  //                            + "/" + deviceModel
//  //                            + "/failed_" + failed_StepName.replaceAll(" ", "_") + ".jpeg"));
//  //            File[] files1 = framePath.listFiles();
//  //            if (framePath.exists()) {
//  //                for (File file : files1) {
//  //                    if (file.isFile()) { //this line weeds out other directories/folders
//  //                        Path p = Paths.get(file.toString());
//  //                        String fileName = p.getFileName().toString().toLowerCase();
//  //                        if (deviceModel.toString().toLowerCase()
//  //                            .contains(fileName.split(".png")[0].toLowerCase())) {
//  //                            try {
//  //                                imageUtils.wrapDeviceFrames(
//  //                                    file.toString(),
//  //                                    System.getProperty("user.dir")
//  //                                        + FileLocations.SCREENSHOTS_DIRECTORY + device
//  //                                        + "/" + AppiumDeviceManager.getAppiumDevice()
//  //                                        .getDevice().getUdid()
//  //                                        .replaceAll("\\W", "_") + "/"
//  //                                        + deviceModel + "/failed_"
//  //                                        + failed_StepName.replaceAll(" ", "_") + ".jpeg",
//  //                                    System.getProperty("user.dir")
//  //                                        + FileLocations.SCREENSHOTS_DIRECTORY
//  //                                        + device
//  //                                        + "/" + AppiumDeviceManager.getAppiumDevice()
//  //                                        .getDevice().getUdid()
//  //                                        .replaceAll("\\W", "_") + "/"
//  //                                        + deviceModel + "/failed_"
//  //                                        + failed_StepName.replaceAll(" ", "_")
//  //                                        + "_framed.jpeg");
//  //                                break;
//  //                            } catch (InterruptedException | IM4JavaException e) {
//  //                                e.printStackTrace();
//  //                            }
//  //                        }
//  //                    }
//  //                }
//  //            }
//  //        } catch (IOException e) {
//  //            e.printStackTrace();
//  //            System.out.println("Resource Directory was not found");
//  //        }
//  //    }
//  //
//  //    public void attachScreenShotToReport(String stepName) throws IOException {
//  //        String platform = null;
//  //        if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.ANDROID)) {
//  //            platform = "android";
//  //        } else if (AppiumDeviceManager.getMobilePlatform().equals(MobilePlatform.IOS)) {
//  //            platform = "iPhone";
//  //        }
//  //        File framedImageAndroid = new File(
//  //                System.getProperty("user.dir")
//  //                        + FileLocations.SCREENSHOTS_DIRECTORY
//  //                        + platform + "/"
//  //                        + AppiumDeviceManager.getAppiumDevice()
//  //                        .getDevice().getUdid() + "/" + deviceModel
//  //                        + "/failed_" + stepName.replaceAll(" ", "_") + "_framed.jpeg");
//  //    }
//  //
//  //    @Override
//  //    public void onStart(ISuite iSuite) {
//  //        try {
//  //            appiumServerManager.startAppiumServer();
//  //        } catch (Exception e) {
//  //            e.printStackTrace();
//  //        }
//  //    }
//  //
//  //    @Override
//  //    public void onFinish(ISuite iSuite) {
//  //        try {
//  //            appiumServerManager.stopAppiumServer();
//  //        } catch (Exception e) {
//  //            e.printStackTrace();
//  //        }
//  //    }
//}
