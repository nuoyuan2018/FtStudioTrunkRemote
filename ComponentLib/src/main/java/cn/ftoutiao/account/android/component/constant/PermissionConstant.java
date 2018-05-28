package cn.ftoutiao.account.android.component.constant;

import android.Manifest;

/**
 * Created by alan on 2017/10/13.
 */

public class PermissionConstant {

    public static final int PERMISSION_REQUEST_CODE = 1000;

    public static final String[] MAIN_PERMISSIONS = {Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_LOGS,
            Manifest.permission.SET_DEBUG_APP,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.WRITE_APN_SETTINGS};

}
