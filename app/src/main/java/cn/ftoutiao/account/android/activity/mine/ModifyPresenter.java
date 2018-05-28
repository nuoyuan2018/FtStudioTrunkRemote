package cn.ftoutiao.account.android.activity.mine;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.SaltEntity;
import cn.ftoutiao.account.android.utils.MD5Utils;

/**
 * Created by weichyang on 2018/4/17.
 */

public class ModifyPresenter extends BasePresenter<ModifyContract.View> implements ModifyContract.Presenter {

    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public ModifyPresenter(ModifyContract.View pView) {
        super(pView);
    }


    @Override
    public void requestModifyPasswor(final String phone, final String oldPwd, final String pwd) {
        request().initlogin(UrlConstans.BASE_URL + UrlConstans.REQUEST_SALT, phone, ConstanPool.TYPE_UPDATE)
                .enqueue(new BindCallback<SaltEntity>() {

                    @Override
                    public void succeed(@NonNull SaltEntity pData) {
                        super.succeed(pData);
                       String oldPwdSign=MD5Utils.getPwdHash(oldPwd, pData.salt);
                        modifyPassword(phone, oldPwdSign, pwd, pData.newsalt);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMessage());
                    }
                });


    }

    private void modifyPassword(String phone, String oldPwd, String pwd, String salt) {
        request().modifyPwd(UrlConstans.BASE_URL + UrlConstans.REQUEST_UPDATEPWD, phone, oldPwd, MD5Utils.getPwdHash(pwd, salt), salt)
                .enqueue(new BindCallback<NullEntity>() {
                    @Override
                    public void succeed(@NonNull NullEntity pData) {
                        mView.requestModifyPassworSuccess();
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        mView.requestModifyPassworFailed(pE.getMsg());
                        Toaster.show(pE.getMessage());
                    }
                });
    }
}


