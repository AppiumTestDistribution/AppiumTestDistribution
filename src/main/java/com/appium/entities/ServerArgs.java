package com.appium.entities;

import io.appium.java_client.service.local.flags.GeneralServerFlag;

public enum ServerArgs{

    SESSION_OVERRIDE(GeneralServerFlag.SESSION_OVERRIDE),
    PRE_LAUNCH(GeneralServerFlag.PRE_LAUNCH),
    LOG_LEVEL(GeneralServerFlag.LOG_LEVEL),
    RELAXED_SECURITY(GeneralServerFlag.RELAXED_SECURITY),
    CALLBACK_ADDRESS(GeneralServerFlag.CALLBACK_ADDRESS),
    CALLBACK_PORT(GeneralServerFlag.CALLBACK_PORT),
    LOG_TIMESTAMP(GeneralServerFlag.LOG_TIMESTAMP),
    LOCAL_TIMEZONE(GeneralServerFlag.LOCAL_TIMEZONE),
    LOG_NO_COLORS(GeneralServerFlag.LOG_NO_COLORS),
    WEB_HOOK(GeneralServerFlag.WEB_HOOK),
    CONFIGURATION_FILE(GeneralServerFlag.CONFIGURATION_FILE),
    ROBOT_ADDRESS(GeneralServerFlag.ROBOT_ADDRESS),
    ROBOT_PORT(GeneralServerFlag.ROBOT_PORT),
    SHOW_CONFIG(GeneralServerFlag.SHOW_CONFIG),
    NO_PERMS_CHECKS(GeneralServerFlag.NO_PERMS_CHECKS),
    STRICT_CAPS(GeneralServerFlag.STRICT_CAPS),
    TEMP_DIRECTORY(GeneralServerFlag.TEMP_DIRECTORY),
    DEBUG_LOG_SPACING(GeneralServerFlag.DEBUG_LOG_SPACING),
    ASYNC_TRACE(GeneralServerFlag.ASYNC_TRACE),
    ENABLE_HEAP_DUMP(GeneralServerFlag.ENABLE_HEAP_DUMP);

    private GeneralServerFlag serverFlag;

    ServerArgs(GeneralServerFlag args) {
        this.serverFlag = args;
    }

    public GeneralServerFlag getArgument() {
        return  this.serverFlag;
    }
}
