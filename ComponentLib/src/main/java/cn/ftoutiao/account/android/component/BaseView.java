package cn.ftoutiao.account.android.component;

/**
 * Created by alan on 2017/9/19.
 */

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
