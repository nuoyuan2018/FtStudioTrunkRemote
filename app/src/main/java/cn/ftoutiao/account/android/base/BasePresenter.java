package cn.ftoutiao.account.android.base;

import com.acmenxd.frame.basis.FramePresenter;
import com.acmenxd.frame.basis.IBView;


import org.greenrobot.eventbus.Subscribe;

import cn.ftoutiao.account.android.base.impl.IBaseNet;
import cn.ftoutiao.account.android.http.IAllRequest;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/5/24 14:46
 * @detail 顶级Presenter
 */
public abstract class BasePresenter<T extends IBView> extends FramePresenter<T> implements IBaseNet {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public BasePresenter(T pView) {
        super(pView);
        // EventBus事件注册
        EventBusHelper.register(this);
    }

    /**
     * mView销毁时回调
     */
    public void unSubscribe() {
        // EventBus事件反注册
        EventBusHelper.unregister(this);
        super.unSubscribe();
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

}
