package cn.ftoutiao.account.android.utils;

import cn.ftoutiao.account.android.R;

/**
 * Created by weichyang on 2018/3/5.
 */

public class ResourceManager {

    public static int getResbyIndex(int atype) {
        int res = 0;
        switch (atype) {
            case 0:
                res = R.drawable.icon_rc;
                break;
            case 1:
                res = R.drawable.icon_zx;
                break;
            case 2:
                res = R.drawable.icon_lx;
                break;
            case 3:
                res = R.drawable.icon_xy;
                break;
        }
        return res;
    }

    public static int getResbyIndexSmall(int atype) {
        int res = 0;
        switch (atype) {
            case 0:
                res = R.drawable.icon_rc;
                break;
            case 1:
                res = R.drawable.icon_zx;
                break;
            case 2:
                res = R.drawable.icon_lx;
                break;
            case 3:
                res = R.drawable.icon_xy;
                break;
        }
        return res;
    }

    public static String getResValuByIndex(int atype) {
        String res = "";
        switch (atype) {
            case 1:
                res = "日常账本";
                break;
            case 2:
                res = "装修账本";
                break;
            case 3:
                res = "旅行账本";
                break;
            case 4:
                res = "校园账本";
                break;
        }
        return res;
    }
}
