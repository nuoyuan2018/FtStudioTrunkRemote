package cn.ftoutiao.account.android.base;

import com.acmenxd.frame.basis.FrameFragment;

import org.greenrobot.eventbus.Subscribe;

import cn.ftoutiao.account.android.activity.login.LoginActivity;
import cn.ftoutiao.account.android.base.impl.IBaseNet;
import cn.ftoutiao.account.android.http.IAllRequest;
import cn.ftoutiao.account.android.utils.ResourceLoader;


public abstract class BaseFragment extends FrameFragment implements IBaseNet {

    @Override
    public void onStart() {
        super.onStart();
        // EventBus事件注册
        EventBusHelper.register(this);
    }

    @Override
    public void onDetach() {
        // EventBus事件反注册
        EventBusHelper.unregister(this);
        super.onDetach();
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

    public void gotoLogin() {
        mActivity.startActivity(LoginActivity.class);
    }


    public int getResourceID(String resId) {
        return ResourceLoader.getInstance().getResourceId(resId, mActivity.getResources(), mActivity.getPackageName());
    }

}
