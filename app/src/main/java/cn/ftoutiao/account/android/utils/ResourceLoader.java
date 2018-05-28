package cn.ftoutiao.account.android.utils;

import android.content.res.Resources;

/**
 * Created by weichyang on 2018/3/2.
 */

public class ResourceLoader {

    private ResourceLoader() {
    }

    private static ResourceLoader instance = null;

    public static synchronized ResourceLoader getInstance() {
        if (instance == null) {
            instance = new ResourceLoader();
        }
        return instance;
    }

    public int getResourceId(String resId, Resources res, String packageName) {
        if (resId == null && res == null && packageName == null) {
            throw new RuntimeException("Please set Value !!!");
        }
        return res.getIdentifier(resId, "drawable", packageName);
    }

}
