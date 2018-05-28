package cn.ftoutiao.account.android.activity.login.register;

import android.support.annotation.NonNull;

import com.acmenxd.logger.Logger;
import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import org.jetbrains.annotations.NotNull;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.login.register.RegisterContract;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.SaltEntity;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.utils.MD5Utils;

/**
 * Created by weichyang on 2018/2/9.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public RegisterPresenter(RegisterContract.View pView) {
        super(pView);
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    @Override
    public void getCaptcha(@NotNull String phone) {
        request().getmobilevcodewith(UrlConstans.BASE_URL + UrlConstans.REQUEST_VERIFYSMS, phone, ConstanPool.TYPE_SIGN)
                .enqueue(new BindCallback<NullEntity>() {
                    @Override
                    public void succeed(@NonNull NullEntity pData) {
                        super.succeed(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMsg());
                    }
                });
    }

    @Override
    public void registerPhone(@NotNull final String phone, @NotNull final String pwd, @NotNull final String mcode) {
        request().initlogin(UrlConstans.BASE_URL + UrlConstans.REQUEST_SALT, phone, ConstanPool.TYPE_SIGN)
                .enqueue(new BindCallback<SaltEntity>() {

                    @Override
                    public void succeed(@NonNull SaltEntity pData) {
                        super.succeed(pData);
                        doRegister(phone, pwd, mcode, pData.salt);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Logger.e(pE.getMsg());
                    }
                });

    }

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param mcode
     * @param salt
     */
    public void doRegister(@NotNull String phone, @NotNull String pwd, @NotNull String mcode, String salt) {
        request().register(UrlConstans.BASE_URL + UrlConstans.REQUEST_ONSIGN, phone,  MD5Utils.getPwdHash(pwd, salt), salt, mcode)
                .enqueue(new BindCallback<UserEntity>() {
                    @Override
                    public void succeed(@NonNull UserEntity pData) {
                        super.succeed(pData);
                        mView.registerSuccess(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                      mView.registerFailed(pE.getMsg());
                    }
                });
    }
}
