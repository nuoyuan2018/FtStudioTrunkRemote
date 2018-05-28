package cn.ftoutiao.account.android.utils;

import android.content.res.Resources;

import cn.ftoutiao.account.android.base.AppConfig;
import cn.ftoutiao.account.android.base.BaseApplication;

/**
 * Created by weichyang on 2018/4/28.
 */

public class SpacalResourceHelper {

    private static String[] resString = {"12001", "12002"};

    public static int getResourceId(String resUri) {
        Resources resource = BaseApplication.instance().getResources();
        if (resUri.contains(resString[0]) || resUri.contains(resString[1])) {
            return resource.getIdentifier(resUri.split(".png")[0] + "_pree", "drawable", AppConfig.PKG_NAME);
        }else {
            return resource.getIdentifier(resUri.split(".png")[0] + "_pre", "drawable", AppConfig.PKG_NAME);
        }
    }
}
