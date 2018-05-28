package com.acmenxd.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.acmenxd.logger.Logger;

import java.lang.reflect.Method;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/7/26 14:11
 * @detail 设备相关Utils
 */
public class DeviceUtils {
    private static int screenX = 0; // 屏幕宽（px）
    private static int screenY = 0; // 屏幕高（px）
    private static int screenDipX = 0; // 屏幕宽（dip）
    private static int screenDipY = 0; // 屏幕高（dip）
    private static int densityDPI = 0; // 屏幕密度（每寸像素：120/160/240/320）
    private static float density = 0; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
    private static float scaledDensity = 0;
    private static float xdpi = 0;
    private static float ydpi = 0;

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context pContext) {
        int statusBarHeight = 0;
        int resourceId = pContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = pContext.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 屏幕宽（px）
     */
    public static int getScreenX(Context pContext) {
        if (screenX <= 0) {
            initDisplay(pContext);
        }
        return screenX;
    }

    /**
     * 屏幕高（px）
     */
    public static int getScreenY(Context pContext) {
        if (screenY <= 0) {
            initDisplay(pContext);
        }
        return screenY;
    }

    /**
     * 屏幕宽（dip）
     */
    public static int getScreenDipX(Context pContext) {
        if (screenDipX <= 0) {
            initDisplay(pContext);
        }
        return screenDipX;
    }

    /**
     * 屏幕高（dip）
     */
    public static int getScreenDipY(Context pContext) {
        if (screenDipY <= 0) {
            initDisplay(pContext);
        }
        return screenDipY;
    }

    /**
     * 屏幕密度（每寸像素：120/160/240/320）
     */
    public static int getDensityDPI(Context pContext) {
        if (densityDPI <= 0) {
            initDisplay(pContext);
        }
        return densityDPI;
    }

    /**
     * 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public static float getDensity(Context pContext) {
        if (density <= 0) {
            initDisplay(pContext);
        }
        return density;
    }

    public static float getScaledDensity(Context pContext) {
        if (scaledDensity <= 0) {
            initDisplay(pContext);
        }
        return scaledDensity;
    }

    public static float getXdpi(Context pContext) {
        if (xdpi <= 0) {
            initDisplay(pContext);
        }
        return xdpi;
    }

    public static float getYdpi(Context pContext) {
        if (ydpi <= 0) {
            initDisplay(pContext);
        }
        return ydpi;
    }

    private static void initDisplay(Context pContext) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        screenX = dm.widthPixels;
        screenY = dm.heightPixels;
        density = dm.density;
        densityDPI = dm.densityDpi;
        scaledDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        screenDipX = (int) (screenX / density);
        screenDipY = (int) (screenY / density);
    }



    public static void hideIMM(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && view != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     *
     * @param context
     * @param view
     */
    public static void showIMM(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * @param cx
     * @return
     */
    public static Bundle getAppMetaDate(Context cx) {
        try {
            ApplicationInfo appInfo = cx.getPackageManager().getApplicationInfo(cx.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e);
        }
        return null;
    }

    private static int getHasVirtualKey(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }
}
