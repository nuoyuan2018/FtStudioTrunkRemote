package cn.ftoutiao.account.android.activity.notebook.presenter;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.UserEntity;

/**
 * Author: weichyang
 * Date:   2018/3/8
 * Description:
 */


public interface SlideDelContract {

    interface View extends IBView {

        void getUserInfoSuccess(UserEntity result);

        void delSuccess(String result);

        void quiteSuccess(String result);

    }

    interface Presenter extends IBPresenter {
        void getUserInfo();
        void requestDel(String aid);
        void requestQuite(String aid);
    }

}