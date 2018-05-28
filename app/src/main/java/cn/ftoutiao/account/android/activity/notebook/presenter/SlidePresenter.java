package cn.ftoutiao.account.android.activity.notebook.presenter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.UserEntity;

/**
 * Created by weichyang on 2018/3/8.
 */

public class SlidePresenter extends BasePresenter<SlideDelContract.View> implements SlideDelContract.Presenter {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public SlidePresenter(SlideDelContract.View pView) {
        super(pView);
    }

    @Override
    public void getUserInfo() {
        request().requestUserInfo(UrlConstans.BASE_URL + UrlConstans.REQUEST_USERINFO).enqueue(new BindCallback<UserEntity>() {
            @Override
            public void succeed(@NonNull UserEntity pData) {
                mView.getUserInfoSuccess(pData);
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                Toaster.show(pE.getMsg());
            }
        });
    }

    /**
     * 删除账本
     *
     * @param aid
     */
    public void requestDel(String aid) {
        request().delNoteBook(UrlConstans.BASE_URL + UrlConstans.REQUEST_DELETEBOOK, aid).enqueue(new BindCallback<NullEntity>() {
            @Override
            public void succeed(@NonNull NullEntity pData) {
                super.succeed(pData);
                mView.delSuccess(pData.getMsg());
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                Toaster.show(pE.getMessage());
            }
        });
    }

    @Override
    public void requestQuite(String aid) {
        request().quiteNoteBook(UrlConstans.BASE_URL + UrlConstans.REQUEST_QUITBOOK, aid).enqueue(new BindCallback<NullEntity>() {
            @Override
            public void succeed(@NonNull NullEntity pData) {
                super.succeed(pData);
                mView.quiteSuccess(pData.getMsg());
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                Toaster.show(pE.getMessage());
            }
        });
    }
}
