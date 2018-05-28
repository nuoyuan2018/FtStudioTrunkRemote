package cn.ftoutiao.account.android.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import cn.ftoutiao.account.android.base.BaseApplication;
import cn.ftoutiao.account.android.component.util.BaseTypeUtils;


/**
 * Created by alan on 2017/9/19.
 */

public class AppUtils {

    private AppUtils() {

    }

    private static String sDeviceId;

    private static int sVersionCode;

    private static String sVersionName;

    public static String getDeviceId(Context context) {
        if (!TextUtils.isEmpty(sDeviceId)) {
            return sDeviceId;
        }
        return generateDeviceId(context);
    }

    private static String generateDeviceId(Context context) {
//        String deviceId = SharedPreferenceUtils.getDeviceId(context);
//        if (TextUtils.isEmpty(deviceId)) {
//            deviceId = FileUtils.getRelevantData("device_id");
//            if (TextUtils.isEmpty(deviceId)) {
//                String synthesize = getIMEI();
//                deviceId = MD5Utils.MD5(synthesize);
//                SharedPreferenceUtils.setDeviceId(context, deviceId);
//                FileUtils.saveRelevantData("device_id", deviceId);
//            } else {
//                SharedPreferenceUtils.setDeviceId(context, deviceId);
//            }
//        }
//        sDeviceId = deviceId;
//        return deviceId;
        return "00000";
    }

    /**
     * 获取客户端版本号
     */
    public static int getClientVersionCode() {
        if (sVersionCode != 0) {
            return sVersionCode;
        }
        try {
            PackageInfo packInfo = BaseApplication.instance().getPackageManager()
                    .getPackageInfo(BaseApplication.instance().getPackageName(), 0);
            sVersionCode = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sVersionCode;
    }

    /**
     * 获取客户端版本名
     */
    public static String getClientVersionName() {
        if (!TextUtils.isEmpty(sVersionName)) {
            return sVersionName;
        }

        try {
            PackageInfo packInfo = BaseApplication.instance().getPackageManager()
                    .getPackageInfo(BaseApplication.instance().getPackageName(), 0);
            sVersionName = packInfo.versionName;
            return sVersionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取系统版本号
     */
    public static String getOSVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取系统SDK版本
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断系统是否是6.0及以上
     */
    public static boolean isMashmellow() {
        return getSDKVersion() >= Build.VERSION_CODES.M;
    }

    /**
     * 判断系统是否是5.0及以上
     */
    public static boolean isLolliPop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static String getDeviceName() {
        return BaseTypeUtils.ensureStringValidate(Build.MODEL);
    }

    public static String getBrandName() {
        return BaseTypeUtils.ensureStringValidate(Build.BRAND);
    }

    public static String getModelName() {
        return BaseTypeUtils.ensureStringValidate(Build.MODEL);
    }

    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        String deviceId = ((TelephonyManager) BaseApplication.instance().getApplicationContext().getSystemService(
                Context.TELEPHONY_SERVICE)).getDeviceId();
        return BaseTypeUtils.ensureStringValidate(deviceId);
    }

//    TODO: get user agent
    public static String getUserAgent(String old) {
        return "";
    }


    /**
     * 获取进程名称
     *
     * @param context
     * @param pid
     * @return
     */
    public static String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public static PackageInfo getPackageInfo(Context context) {
        String packageName = context.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", "getPackageInfo:"+e.toString() );
        }
        return packageInfo;
    }

}
