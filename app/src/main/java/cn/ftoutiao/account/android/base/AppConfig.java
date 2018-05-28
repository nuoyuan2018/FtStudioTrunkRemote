package cn.ftoutiao.account.android.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.acmenxd.frame.configs.HtunConfig;
import com.acmenxd.frame.utils.Encrypt2;
import com.acmenxd.frame.utils.RandomUtils;
import com.acmenxd.frame.utils.Utils;
import com.acmenxd.logger.Logger;
import com.acmenxd.marketer.Marketer;
import com.acmenxd.retrofit.HttpManager;
import com.acmenxd.retrofit.HttpMutualCallback;
import com.acmenxd.sptool.SpManager;
import com.acmenxd.sptool.SpTool;

import java.util.HashMap;
import java.util.Map;

import cn.ftoutiao.account.android.BuildConfig;
import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.http.HttpCode;
import cn.ftoutiao.account.android.utils.Encrypt;
import cn.ftoutiao.account.android.utils.Md5SaltEncode;

/**
 * Author: weichyang
 * Date:   2018/2/8
 * Description: 全局配置
 */
public final class AppConfig {
    /**
     * 调试模式开关
     * * 由build.gradle -> debug | release版本控制
     */
    public static final boolean DEBUG = BuildConfig.MAIN_DEBUG;
    /**
     * 项目整体配置信息
     */
    public static HtunConfig config;
    /**
     * App 版本号
     */
    public static int VERSION_CODE;
    /**
     * App 版本
     */
    public static String VERSION_NAME;
    /**
     * App 名称
     */
    public static String PROJECT_NAME;
    /**
     * App 包名
     */
    public static String PKG_NAME;
    /**
     * App 渠道
     */
    public static String MARKET;
    /**
     * IMEI号
     */
    public static String IMEI = "";
    /**
     * tk
     */
    public static String TK = "";
    /**
     * 登录平台
     */
    public static final int Platform = 1; //
    /**
     * 登陆渠道
     */
    public static final int Client = 2; // pc 1, aos 2, ios 3, other 4
    /**
     * 客户渠道
     */
    public static final int ClientType = Client; // pc 1, aos 2, ios 3, other 4
    /**
     * 客户端类型
     */
    public static final int Terminal = Client; // pc 1, aos 2, ios 3, other 4
    /**
     * 服务器时间
     */
    public static long SYS_TIME;
    /**
     * 服务器与客户端时间差值
     */
    public static long DIF_TIME;

    /**
     * 初始化 -> BaseApplication中调用
     */
    public static synchronized void init() {
        BaseApplication app = BaseApplication.instance();
        PackageManager pkgManager = app.getPackageManager();
        // 设置默认值
        VERSION_CODE = 1;
        VERSION_NAME = "1.0";
        PROJECT_NAME = app.getResources().getString(R.string.app_name);
        PKG_NAME = "";
        MARKET = "";
        /**
         * 初始化值
         */
        PackageInfo info = null;
        try {
            info = pkgManager.getPackageInfo(app.getPackageName(), 0);
            VERSION_CODE = info.versionCode;
            VERSION_NAME = info.versionName;
            PKG_NAME = app.getPackageName();
            PROJECT_NAME = (String) pkgManager.getApplicationLabel(pkgManager.getApplicationInfo(PKG_NAME, 0));
        } catch (PackageManager.NameNotFoundException pE) {
            Logger.e(pE);
        }

        MARKET = Marketer.getMarket(app.getApplicationContext(), MARKET);
        /**
         * 初始化url
         */
        UrlConstans.initURL();
        /**
         * 设置Net公共参数 -> 为动态配置而设置的此函数
         */
        HttpManager.INSTANCE.parseNetCode = new HttpCode();
        HttpManager.INSTANCE.mutualCallback = new HttpMutualCallback() {
            @Override
            public Map<String, String> getBodys(String url, Map<String, String> oldMaps) {
                if (Utils.isEmpty(IMEI)) {
                    permissionsAfterInit();
                }
                //TK = getTK();
                Map<String, String> maps = new HashMap<>();
                long time = System.currentTimeMillis();
                maps.put("client", "android");
                maps.put("appname", "dpaccount");
                maps.put("channel", MARKET);
                maps.put("ver", VERSION_NAME);
                maps.put("imei", IMEI);
                maps.put("ts", String.valueOf(time));
                maps.put("sign", Encrypt.encrypt(time, IMEI, ""));

                // TODO: 2018/2/9
                if (oldMaps.containsKey("verifySms")) {
                    maps.put("tk", Encrypt2.encrypt(Long.valueOf(time), IMEI, oldMaps.get("verifySms")));
                }
                return maps;
            }

            @Override
            public Map<String, String> getParameters(String url) {
                return null;
            }

            @Override
            public Map<String, String> getHeaders(String url) {
                return null;
            }

            @Override
            public Map<String, String> getReHeaders(String url) {
                return null;
            }
        };
    }

    /**
     * 获取到手机权限后回调
     */
    @SuppressLint("MissingPermission")
    public static synchronized void permissionsAfterInit() {
        SpTool spTool = SpManager.getCommonSp(config.SP_Device);
        String tempIMEI = spTool.getString("imei", "");
        if (Utils.isEmpty(tempIMEI)) {
            BaseApplication app = BaseApplication.instance();
            try {
                tempIMEI = ((TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            } catch (Exception pE) {
                Logger.e(pE);
            }
            if (Utils.isEmpty(tempIMEI) || tempIMEI.equals("000000000000000")) {
                tempIMEI = String.valueOf(System.currentTimeMillis());
                int x = (int) Math.pow(10, 15 - tempIMEI.length());
                tempIMEI = Md5SaltEncode.hashEncodeStr(tempIMEI + RandomUtils.randomByMinMax(x / 10, x - 1));
            } else {
                tempIMEI = Md5SaltEncode.hashEncodeStr(tempIMEI);
            }
            spTool.putString("imei", tempIMEI);
        }
        IMEI = tempIMEI;
    }

    /**
     * 设置服务器时间与差值时间
     */
    public static void setSysTime(long time) {
        SYS_TIME = time;
        DIF_TIME = time - System.currentTimeMillis();
    }

    /**
     * 获取TK
     */
    public static String getTK() {
//        return TeaUtil.encrypt2(IMEI, String.valueOf(Client), VERSION_NAME, Build.MODEL, NetUtils.getProvider());
        return "xxxxxxxxxxx";
    }

}
