package cn.ftoutiao.account.android.activity.login.register;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import org.jetbrains.annotations.NotNull;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.login.register.FindPasswordContract;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.SaltEntity;
import cn.ftoutiao.account.android.utils.MD5Utils;

/**
 * Created by weichyang on 2018/2/9.
 */

public class FindPasswordPresenter extends BasePresenter<FindPasswordContract.View> implements FindPasswordContract.Presenter {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public FindPasswordPresenter(FindPasswordContract.View pView) {
        super(pView);
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    @Override
    public void getCaptcha(@NotNull String phone) {
        request().getmobilevcodewith(UrlConstans.BASE_URL + UrlConstans.REQUEST_VERIFYSMS, phone, ConstanPool.TYPE_RESET)
                .enqueue(new BindCallback<NullEntity>() {
                    @Override
                    public void succeed(@NonNull NullEntity pData) {
                        super.succeed(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMessage());
                    }
                });
    }

    @Override
    public void registerPhone(@NotNull final String phone, @NotNull final String pwd, @NotNull final String mcode) {
        request().initlogin(UrlConstans.BASE_URL + UrlConstans.REQUEST_SALT, phone, ConstanPool.TYPE_RESET)
                .enqueue(new BindCallback<SaltEntity>() {

                    @Override
                    public void succeed(@NonNull SaltEntity pData) {
                        super.succeed(pData);
                        findPasswordRequest(phone, pwd, mcode, pData.salt);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMessage());
                    }
                });

    }

    /**
     * 找回密码
     *
     * @param phone
     * @param pwd
     * @param mcode
     * @param salt
     */
    public void findPasswordRequest(@NotNull String phone, @NotNull String pwd, @NotNull String mcode, String salt) {
        request().updatepwd(UrlConstans.BASE_URL + UrlConstans.REQUEST_RESETPWD, phone, mcode, MD5Utils.getPwdHash(pwd, salt), salt)
                .enqueue(new BindCallback<NullEntity>() {
                    @Override
                    public void succeed(@NonNull NullEntity pData) {
                        super.succeed(pData);
                        mView.findSuccess();
                        Toaster.show(pData.getMsg());
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMessage());
                    }
                });
    }
}
