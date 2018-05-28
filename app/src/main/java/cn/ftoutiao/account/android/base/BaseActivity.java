package cn.ftoutiao.account.android.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.acmenxd.frame.basis.FrameActivity;
import com.acmenxd.frame.utils.DeviceUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import cn.ftoutiao.account.android.base.impl.IBaseNet;
import cn.ftoutiao.account.android.http.IAllRequest;


/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXDaccouont
 * @date 2017/5/24 14:35
 * @detail 顶级Activity
 */
public abstract class BaseActivity extends FrameActivity implements IBaseNet {

    @CallSuper
    protected void onCreateBefore(@NonNull Bundle savedInstanceState) {
        super.onCreateBefore(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // EventBus事件注册
        EventBusHelper.register(this);
       // PushAgent.getInstance(this).onAppStart();
    }


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        init();
        initView();
        initListener();
        initValue();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //进行重写添加模板 出现问题

    protected void initLayout() {

    }


    protected void init() {
    }

    protected void initView() {
    }

    protected void initListener() {
    }


    protected void initValue() {
    }


    @Override
    protected void onDestroy() {
        // EventBus事件反注册
        EventBusHelper.unregister(this);
        super.onDestroy();
    }

    /**
     * EventBus默认添加的函数(子类无法重写,无需关心此函数)
     * * EventBus注册时,类中必须有@Subscribe注解的函数
     */
    @Subscribe
    public final void eventBusDefault(Object object) {
    }
    //------------------------------------子类可使用的工具函数 -> IBaseNet

    /**
     * 获取IAllRequest实例
     * * 开放重写,满足不同需求
     */
    @Override
    public IAllRequest request() {
        return request(IAllRequest.class);
    }

    /**
     * 创建新的Retrofit实例
     * * 开放重写,满足不同需求
     */
    @Override
    public IAllRequest newRequest() {
        return newRequest(IAllRequest.class);
    }

    /**
     * 创建新的Retrofit实例,并设置超时时间
     * * 开放重写,满足不同需求
     */
    @Override
    public IAllRequest newRequest(int connectTimeout, int readTimeout, int writeTimeout) {
        return newRequest(IAllRequest.class, connectTimeout, readTimeout, writeTimeout);
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyBoard() {
            DeviceUtils.hideIMM(this, getCurrentFocus());
    }
}
