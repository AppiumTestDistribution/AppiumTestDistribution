package com.appium.entities;

import io.appium.java_client.service.local.flags.GeneralServerFlag;

public enum ServerArgs{

    SESSION_OVERRIDE(GeneralServerFlag.SESSION_OVERRIDE),
    PRE_LAUNCH(GeneralServerFlag.PRE_LAUNCH),
    LOG_LEVEL(GeneralServerFlag.LOG_LEVEL),
    RELAXED_SECURITY(GeneralServerFlag.RELAXED_SECURITY);

    private GeneralServerFlag serverFlag;

    ServerArgs(GeneralServerFlag args) {
        this.serverFlag = args;
    }

    public GeneralServerFlag getArgument() {
        return  this.serverFlag;
    }
}
