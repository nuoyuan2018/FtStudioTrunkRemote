package com.acmenxd.frame.configs;

import com.acmenxd.toaster.ToastNW;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/5/24 18:39
 * @detail N项目配置文件
 */
public final class HtunConfig extends BaseConfig {

    public String SP_Device = "spDevice";
    public String SP_Config = "spConfig";
    public String SP_User = "spUser";

    @Override
    protected void init(boolean isDebug) {
        DEBUG = isDebug;
        LOG_OPEN = DEBUG;
        TOAST_DEBUG_OPEN = DEBUG;

        DB_NAME = "ftoutiao_db";
        BASE_DIR = SDCARD_DIR + "/Htun/";
        LOG_DIR = BASE_DIR + "Logger/";
        spAll = new String[]{SP_Cookie, SP_Device, SP_Config, SP_User};

        TOAST_NEED_WAIT = ToastNW.No_NEED_WAIT;

        initNetURL();
    }

    @Override
    protected void initNetURL() {
        BASE_URL = "https://account.ftoutiao.cn/";
    }
}
