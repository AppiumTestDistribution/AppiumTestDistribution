package com.appium.manager;

/**
 * Created by saikrisv on 18/05/17.
 */
public class DeviceSingleton {

    public static DeviceSingleton instance;
    static DeviceAllocationManager deviceID = null;

    public static DeviceSingleton getInstance(){
        if(instance == null){
            instance = new DeviceSingleton();
            deviceID = DeviceAllocationManager.getInstance();
        }
        return instance;
    }

    protected String getDeviceUDID(){
        return deviceID.getNextAvailableDeviceId();
    }
}
