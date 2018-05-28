package cn.ftoutiao.account.android.activity.login;

import android.app.Activity;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.ftoutiao.account.android.model.NoteBookEntity;
import cn.ftoutiao.account.android.model.UserEntity;

/**
 * Created by weichyang on 2018/2/9.
 */

public interface LoginContract {

    interface View extends IBView {

        void loginSuccess(UserEntity result);

        void loginFailed(String msg);

        void showLoading();

        void hideLoading();

        void  requestFaliur();

        void  requestNoteBookCategorySuccess(NoteBookEntity res);

    }

    interface Presenter extends IBPresenter {

        void login(String name, String pwd);

        void oauthLogin(SHARE_MEDIA media, Activity activity);

        void requestNoteBookCategory(String aid);

    }

}
