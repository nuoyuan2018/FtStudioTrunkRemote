package cn.ftoutiao.account.android.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.acmenxd.frame.utils.net.NetUtils;
import com.acmenxd.sptool.SpManager;
import com.acmenxd.sptool.SpTool;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.AppConfig;
import cn.ftoutiao.account.android.base.BaseActivity;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.PermissionConstant;
import cn.ftoutiao.account.android.component.util.BaseTypeUtils;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private Bitmap bitmap = null;
    private ImageView mSplash_img;
    private final int MSG_SPLASH = 100;
    private boolean isJumpToUrl = false;
    private String splash_tag = "splash_tag";

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SPLASH:
                    if (isJumpToUrl) {
                        //goSplashAdvertiseH5();
                        isJumpToUrl = false; //需要重新赋值
                    } else {
                        goMain();
                    }
                    break;
            }
        }
    };


    private int showTime;
    private SpTool spTool;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void init() {
        super.init();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(R.layout.notification_view);
        //防止重复打开文件
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
    }


    @Override
    protected void initLayout() {
        super.initLayout();
        setContentView(R.layout.activity_splash);
        if (applyPermission(PermissionConstant.MAIN_PERMISSIONS)) {
            initNextStep();
        }
    }

    /**
     * 申请权限
     *
     * @param permissions
     * @return true 权限已获取不必申请
     */
    public Boolean applyPermission(String[] permissions) {
        if (BaseTypeUtils.isArrayEmpty(permissions)) {
            return true;
        }
        if (EasyPermissions.hasPermissions(this, permissions)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this, "", PermissionConstant.PERMISSION_REQUEST_CODE, permissions);
        }
        return false;
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initNextStep();
    }

    private void initNextStep() {
        startSplash();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
        // finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 启动页需要停滞waitTime
     *
     * @param waitTime
     * @param whatAction
     */
    private void perpareSplash(long waitTime, final int whatAction) {
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = whatAction;
                timeHandler.sendMessage(msg);
            }
        }, waitTime);
    }

    /**
     * 启动页逻辑
     */
    private void startSplash() {
        spTool = SpManager.getCommonSp(AppConfig.config.SP_User);

        boolean firstLogin = spTool.getBoolean(ConstanPool.CONFIG_FIRSTLOGIN, true);

        if (firstLogin) {
            // 第一次安装App，引导页
            timeHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goMain();
                }
            }, 1000);
        } else {
            showTime = 800;
            perpareSplash(showTime, MSG_SPLASH);  // 开启异步线程跳转到首页
//            if (isGoSplashScreen() && !isJumpToUrl) {
//                int splash_showTime = spTool.getInt(ConstanPool.SHOW_TIME, 0);
//                if (splash_showTime > 0) {
//                    showTime = splash_showTime * 1000;
//                } else {
//                    showTime = 1000;
//                }
//                perpareSplash(showTime, MSG_SPLASH);  // 开启异步线程跳转到首页
//            }
        }
    }

    private boolean isGoSplashScreen() {
        try {
            String splash_image_url = spTool.getString(ConstanPool.SPLASH_IMAGE_URL, "");
            if (NetUtils.checkNetwork() && !isClearSdCardImgCacheFiles(splash_image_url)) {
                Glide.with(this).load(splash_image_url).into(mSplash_img);
                mSplash_img.setEnabled(true);//设置可点击
                return true;
            } else {
                mSplash_img.setEnabled(false);//设置不可点击
                mSplash_img.setImageBitmap(bitmap);
                mSplash_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private boolean isClearSdCardImgCacheFiles(String default_splash_url) {
        if (default_splash_url == null || "".equals(default_splash_url)) {
            mSplash_img.setEnabled(false);
            Glide.get(this).clearDiskCache();//清除缓存
            return true;
        }
        return false;
    }

    private void goMain() {
        startActivity(MainActivity.class);
        finish();
    }

}