package cn.ftoutiao.account.android.activity.mine;

import android.support.annotation.NonNull;

import com.acmenxd.logger.Logger;
import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import java.io.File;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.base.EventBusHelper;
import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.ImageHeaderEntiry;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.model.evenbus.UpdateHeadImg;
import cn.ftoutiao.account.android.utils.AccountManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by yangweichao on 2018/3/3.
 */

public class AccountPresenter extends BasePresenter<AccountContract.View> implements AccountContract.Presenter {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public AccountPresenter(AccountContract.View pView) {
        super(pView);
    }

    @Override
    public void loginOut() {

        request().exitUserAccountRequest(UrlConstans.BASE_URL + UrlConstans.REQUEST_LOGINOUT).enqueue(new BindCallback<NullEntity>() {
            @Override
            public void succeed(@NonNull NullEntity pData) {
                super.succeed(pData);
                mView.loginOutSuccess();
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                mView.loginOutFailed(pE.getMsg());
            }
        });


    }

    @Override
    public void updateHeaderImage(File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        request().undateImage(UrlConstans.BASE_URL + UrlConstans.REQUEST_ONUPLOAD, body).enqueue(new BindCallback<ImageHeaderEntiry>() {
            @Override
            public void succeed(@NonNull ImageHeaderEntiry pData) {
                super.succeed(pData);
                //上传到服务起
                updateImagePath2Service(pData.imageUrl);
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                Toaster.show(pE.getMsg());
                Logger.d("pdate" + pE.getMsg());
            }
        });
    }

    @Override
    public void updateImagePath2Service(final String imageUrl) {
        request().modifyImageHeader(UrlConstans.BASE_URL + UrlConstans.REQUEST_MPHOTO, imageUrl).enqueue(new BindCallback<NullEntity>() {
            @Override
            public void succeed(@NonNull NullEntity pData) {
                super.succeed(pData);
                EventBusHelper.post(new UpdateHeadImg(imageUrl));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserEntity userEntity = AccountManager.getInstance().getAccount(mView.getContext());
                        userEntity.data.avatarurl = imageUrl;
                        AccountManager.getInstance().setAccount(mView.getContext(), userEntity);
                        DataContans.setUserEntity(userEntity);
                    }
                }).start();


            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
            }
        });
    }
}
