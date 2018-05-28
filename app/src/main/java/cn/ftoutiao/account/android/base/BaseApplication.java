package cn.ftoutiao.account.android.base;


import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.acmenxd.frame.basis.FrameApplication;
import com.acmenxd.frame.configs.HtunConfig;
import com.acmenxd.logger.Logger;
import com.acmenxd.marketer.Marketer;
import com.acmenxd.sptool.SpManager;
import com.acmenxd.sptool.SpTool;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.db.core.DBManager;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.umeng.CustomNotificationHandler;
import cn.ftoutiao.account.android.umeng.NyUmengMsgHandler;
import cn.ftoutiao.account.android.umeng.NyUmengMsgService;
import cn.ftoutiao.account.android.utils.AccountManager;

import static com.umeng.socialize.utils.DeviceConfig.context;


public final class BaseApplication extends FrameApplication {


    // 初始化状态 -> 默认false,初始化完成为true
    public boolean isInitFinish = false;
    // 记录启动时间
    public long startTime = 0;
    private Handler handler;
    private ActivityLefecycleImpl impl;
    private String market;

    public static synchronized BaseApplication instance() {
        return (BaseApplication) FrameApplication.instance();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.w("App已启动!");
        startTime = System.currentTimeMillis();

        // 配置框架设置
        initFrameSetting(HtunConfig.class, AppConfig.DEBUG);
        // 存储项目整体配置信息
        AppConfig.config = getConfig();
        // 初始化App配置
        AppConfig.init();
        market = Marketer.getMarket(context, "default");
        Logger.d("渠道:" + market);
        //内存泄露检测
        //UMConfigure.init(this, "5a9134c28f4a9d236100029a", market, UMConfigure.DEVICE_TYPE_PHONE, "6e65abef92663cadaa526638239fe52b");
//        UMConfigure.init(this, "5a07edccf43e4836b30000da", market, UMConfigure.DEVICE_TYPE_PHONE, "bdc5cc7fc9f8eb74bc339617eb5892e6");
        // initUMeng();
        //LeakCanary.install(this);
        UMShareAPI.get(this);

        // 初始化数据库配置
        DBManager.getInstance().init();
        // 初始化EventBus配置 -> 启用3.0加速功能
        // TODO: 2018/2/8 修改
        EventBus.builder().installDefaultEventBus();
        // 初始化完毕
        isInitFinish = true;
        Logger.w("App初始化完成!");


        //初始化用户登录状态信息
        UserEntity userEntity = AccountManager.getInstance().getAccount(this);
        DataContans.setUserEntity(userEntity);

    }


    private void initUMeng() {

        Config.DEBUG = false;

        //UMConfigure.init(this, "5a07edccf43e4836b30000da", market, UMConfigure.DEVICE_TYPE_PHONE, "bdc5cc7fc9f8eb74bc339617eb5892e6");


        PushAgent mPushAgent = PushAgent.getInstance(this);

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        //设置通知栏显示的通知条数
        mPushAgent.setDisplayNotificationNumber(1);
        //设置应用在前台时是否显示通知,此方法请在mPushAgent.register方法之前调用
        mPushAgent.setNotificaitonOnForeground(true);
        /**
         * 友盟推送收到消息的处理服务
         */
        NyUmengMsgHandler nyUmengMsgHandler = new NyUmengMsgHandler(context);
        mPushAgent.setMessageHandler(nyUmengMsgHandler);
        /**使用自定义的NotificationHandler，来结合友盟统计处理消息通知,有统计功能
         *参考http://bbs.umeng.com/thread-11112-1-1.html
         */
        CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        //完全自定义推送消息跳转处理服务
        mPushAgent.setPushIntentServiceClass(NyUmengMsgService.class);
        registerIUmengCallBack(mPushAgent);
        makeSureHaveDeviceToken(mPushAgent);//确保有deviceToken
    }

    private void makeSureHaveDeviceToken(PushAgent mPushAgent) {
        SpTool spTool = SpManager.getCommonSp(AppConfig.config.SP_Config);
        String device_token = mPushAgent.getRegistrationId();
        String sp_deviceToken = spTool.getString(ConstanPool.UM_DEVICE, device_token);
        if ("".equals(device_token) || "".equals(sp_deviceToken)) {
            registerIUmengCallBack(mPushAgent);
        }
    }


    private void registerIUmengCallBack(PushAgent mPushAgent) {
        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                SpTool spTool = SpManager.getCommonSp(AppConfig.config.SP_Config);
                spTool.putString(ConstanPool.UM_DEVICE, deviceToken); // 初始化umeng推送id
                Logger.e(">>>>>>>>友盟推送服务deviceToken:" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.e(">>>>>>>>友盟推送服务错误日志s:" + s.toString() + "S1:" + s1.toString());
            }
        });
    }


    @Override
    public void crashException(String projectInformation, Thread pThread, Throwable pE) {
        StringBuffer sb = new StringBuffer();
        sb.append("Debug").append(" = ").append(AppConfig.DEBUG).append("\n");
        sb.append("Imei").append(" = ").append(AppConfig.IMEI).append("\n");
        sb.append("Market").append(" = ").append(AppConfig.MARKET).append("\n");
        sb.append("PackageName").append(" = ").append(AppConfig.PKG_NAME).append("\n");
        sb.append("ProjectName").append(" = ").append(AppConfig.PROJECT_NAME).append("\n");
        sb.append("VersionCode").append(" = ").append(AppConfig.VERSION_CODE).append("\n");
        sb.append("VersionName").append(" = ").append(AppConfig.VERSION_NAME).append("\n");
        sb.append("Platform").append(" = ").append(AppConfig.Platform).append("\n");
        sb.append("ThreadName").append(" = ").append(pThread.getName()).append("\n");
        super.crashException(sb.toString(), pThread, pE);
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(new ActivityLefecycleImpl());
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    {
        PlatformConfig.setWeixin("wxc46844851461cd37", "493d11e3763de81513b7caf5d7a57d17");
        PlatformConfig.setQQZone("1106063795", "wA9Dw8wQ9SP3lDBu");
        PlatformConfig.setSinaWeibo("846872856", "eff02ca461f1b39539f480db3ea2497a", "https://ftoutiao.cn");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
