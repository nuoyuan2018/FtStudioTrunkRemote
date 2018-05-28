package cn.ftoutiao.account.android.activity.login;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.acmenxd.logger.Logger;
import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NoteBookEntity;
import cn.ftoutiao.account.android.model.SaltEntity;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.utils.MD5Utils;

/**
 * Created by weichyang on 2018/2/9.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter, UMAuthListener {


    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public LoginPresenter(LoginContract.View pView) {
        super(pView);
    }

    @Override
    public void login(final String name, final String pwd) {
        request().initlogin(UrlConstans.BASE_URL + UrlConstans.REQUEST_SALT, name, ConstanPool.TYPE_LOGIN)
                .enqueue(new BindCallback<SaltEntity>() {

                    @Override
                    public void succeed(@NonNull SaltEntity pData) {
                        super.succeed(pData);
                        doLogin(name, pwd, pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMsg());
                    }
                });


    }

    /**
     * 登录
     *
     * @param name
     * @param pwd
     * @param saltEntity
     */
    public void doLogin(String name, String pwd, SaltEntity saltEntity) {
        mView.showLoadingDialog(true);
        request().dologin(UrlConstans.BASE_URL + UrlConstans.REQUEST_LOGIN, name, MD5Utils.getPwdHash(pwd, saltEntity.salt))
                .enqueue(new BindCallback<UserEntity>() {
                    @Override
                    public void succeed(@NonNull UserEntity pData) {
                        super.succeed(pData);
                        mView.hideLoadingDialog();
                        mView.loginSuccess(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        mView.hideLoadingDialog();
                        mView.loginFailed(pE.getMsg());
                    }
                });


    }

    @Override
    public void oauthLogin(SHARE_MEDIA media, Activity activity) {
        UMShareAPI.get(activity).getPlatformInfo(activity, media, this);
    }

    @Override
    public void requestNoteBookCategory(String aid) {
        request().requestNotebookCatagory(
                UrlConstans.BASE_URL + UrlConstans.REQUEST_BOOKCATEGORY,
                aid).enqueue(
                new BindCallback<NoteBookEntity>() {
                    @Override
                    public void succeed(@NonNull NoteBookEntity pData) {
                        super.succeed(pData);
                        mView.requestNoteBookCategorySuccess(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMsg());
                        mView.requestFaliur();
                    }
                });
    }


// 三方登录

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Logger.d("--start");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        if (share_media != null) {
            String id = "";
            String accessToken = "";
            String platform = "";
            String unionId = "";
            String refreshToken = "";

            switch (share_media) {
                case SINA:
                    id = map.get("uid");
                    accessToken = map.get("access_token");
                    platform = "sina";
                    refreshToken = map.get("refresh_token");
                    break;
                case QQ:
                    id = map.get("openid");
                    accessToken = map.get("access_token");
                    platform = "qq";
                    break;
                case WEIXIN:
                    id = map.get("openid");
                    unionId = map.get("unionid");
                    accessToken = map.get("access_token");
                    platform = "weixin";
                    refreshToken = map.get("refreshToken");
                    break;
                default:
            }

            request().authorLogin(UrlConstans.BASE_URL + UrlConstans.REQUEST_AUTHLOGIN,
                    id, accessToken, platform, refreshToken, unionId)
                    .enqueue(new BindCallback<UserEntity>() {
                        @Override
                        public void succeed(@NonNull UserEntity pData) {
                            super.succeed(pData);
                            mView.loginSuccess(pData);
                        }

                        @Override
                        public void failed(@NonNull HttpException pE) {
                            Toaster.show(pE.getMessage());
                        }
                    });

        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        Logger.d("--onerroe");
        mView.loginFailed(throwable.getMessage());
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        Logger.d("--onCancel");
    }
}
