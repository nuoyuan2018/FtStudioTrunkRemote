package cn.ftoutiao.account.android.constants;

import android.Manifest;

public class PermissionConstant {

    public static final int PERMISSION_REQUEST_CODE = 1000;

    public static final String[] MAIN_PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


}