package com.appium.manager;

import com.appium.executor.MyTestExecutor;
import com.appium.ios.IOSDeviceConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/*
 * This class picks the devices connected
 * and distributes across multiple thread.
 *
 * Thanks to @Thote_Gowda(thotegowda.gr@gmail.com)
 */

public class ParallelThread {
	protected int deviceCount = 0;
	Map<String, String> devices = new HashMap<String, String>();
	Map<String, String> iOSdevices = new HashMap<String, String>();
	AndroidDeviceConfiguration deviceConf = new AndroidDeviceConfiguration();
	IOSDeviceConfiguration iosDevice= new IOSDeviceConfiguration();
	MyTestExecutor myTestExecutor = new MyTestExecutor();
	public Properties prop = new Properties();
	public InputStream input = null;
	List<Class> testcases;

	@SuppressWarnings({ "rawtypes" })
	public void runner(String pack) throws Exception {
		File f = new File(System.getProperty("user.dir") + "/target/appiumlogs/");
		if (!f.exists()) {
			System.out.println("creating directory: " + "Logs");
			boolean result = false;
			try {
				f.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		File adb_logs = new File(System.getProperty("user.dir") + "/target/adblogs/");
		if (!adb_logs.exists()) {
			System.out.println("creating directory: " + "ADBLogs");
			boolean result = false;
			try {
				adb_logs.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
		input = new FileInputStream("config.properties");
		prop.load(input);

		if(deviceConf.getDevices() != null){
			devices = deviceConf.getDevices();
			deviceCount = devices.size() / 3;
			createSnapshotFolderAndroid(deviceCount,"android");
		}
		if(iosDevice.getIOSUDID() != null){
			iOSdevices = iosDevice.getIOSUDIDHash();
			deviceCount += iosDevice.getIOSUDID().size();
			createSnapshotFolderiOS(deviceCount,"iPhone");
		}

		if(deviceCount == 0){
			System.exit(0);
		}

		System.out.println("Total Number of devices detected::" + deviceCount);
		System.out.println("starting running tests in threads");

		testcases = new ArrayList<Class>();

		// final String pack = "com.paralle.tests"; // Or any other package
		PackageUtil.getClasses(pack).stream().forEach(s -> {
			if (s.toString().contains("Test")) {
				System.out.println("forEach: " + testcases.add((Class) s));
			}
		});

		if (prop.getProperty("RUNNER").equalsIgnoreCase("distribute")) {
			//myTestExecutor.distributeTests(deviceCount, testcases);
			myTestExecutor.runMethodParallelAppium(pack, deviceCount,"distribute");

		}
		else if (prop.getProperty("RUNNER").equalsIgnoreCase("parallel")) {
			myTestExecutor.runMethodParallelAppium(pack, deviceCount,"parallel");
		}

	}

	public void createSnapshotFolderAndroid(int deviceCount,String platform) {
		for (int i = 1; i <= deviceCount; i++) {
			String deviceSerial = devices.get("deviceID" + i);
			System.out.println(deviceSerial);
            createPlatformDirectory(platform);
            File file = new File(System.getProperty("user.dir") + "/target/screenshot/"+platform+"/"+deviceSerial.replaceAll("\\W", "_"));
				if (!file.exists()) {
					if (file.mkdir()) {
						System.out.println("Android Device Serial Directory is created!");
					} else {
						System.out.println("Failed to create directory!");
					}
				}


		}
	}

    public void createSnapshotFolderiOS(int deviceCount,String platform) {
        for (int i = 1; i <= deviceCount; i++) {
            String deviceSerial = iOSdevices.get("deviceID" + i);
            System.out.println(deviceSerial);
            createPlatformDirectory(platform);
            File file = new File(System.getProperty("user.dir") + "/target/screenshot/"+platform+"/"+deviceSerial.replaceAll("\\W", "_"));
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("IOS Device Serial Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }


        }
    }


    public void createPlatformDirectory(String platform){
        File file2 = new File(System.getProperty("user.dir")+"/target/screenshot");
        if (!file2.exists()) {
            if (file2.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        File file3 = new File(System.getProperty("user.dir")+"/target/screenshot/"+platform);
        if (!file3.exists()) {
            if (file3.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

}
