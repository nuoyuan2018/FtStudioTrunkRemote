package com.acmenxd.frame.configs;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/5/24 18:39
 * @detail Mvp项目配置文件
 */
public final class MvpConfig extends BaseConfig {

    public String SP_Device = "spDevice";
    public String SP_Config = "spConfig";
    public String SP_User = "spUser";

    @Override
    protected void init(boolean isDebug) {
        DEBUG = isDebug;
        LOG_OPEN = DEBUG;
        TOAST_DEBUG_OPEN = DEBUG;

        DB_NAME = "mvp_db";
        BASE_DIR = SDCARD_DIR + "/Mvp/";
        LOG_DIR = BASE_DIR + "Logger/";
        spAll = new String[]{SP_Cookie, SP_Device, SP_Config, SP_User};

        // 请求地址配置 -1:正式版  0->预发布  1->测试1
        URL_Type = 1;
        initNetURL();
    }

    @Override
    protected void initNetURL() {
        switch (URL_Type) {
            case -1:
                //正式版
                BASE_URL = "http://server.jeasonlzy.com/OkHttpUtils/";
                break;
            case 0:
                //预发布
                BASE_URL = "http://server.jeasonlzy.com/OkHttpUtils/";
                break;
            case 1:
                //测试1
                BASE_URL = "http://server.jeasonlzy.com/OkHttpUtils/";
                break;
            default:
                BASE_URL = "http://server.jeasonlzy.com/OkHttpUtils/";
                break;
        }
    }
}
