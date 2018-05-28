package cn.ftoutiao.account.android.activity.notebook.presenter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.ListEntity;

/**
 * Created by weichyang on 2018/3/8.
 * 创建账本
 */

public class CreatNoteBookPresenter extends BasePresenter<CreatNoteBookContract.View> implements
        CreatNoteBookContract.Presenter {
    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public CreatNoteBookPresenter(CreatNoteBookContract.View pView) {
        super(pView);
    }

    @Override
    public void updateNoteBook(String aid, String aname) {
        mView.showLoadingDialog(true);
        request().updateNoteBook(UrlConstans.BASE_URL + UrlConstans.REQUEST_UPDATEBOOK, aid, aname).
                enqueue(new BindCallback<ListEntity>() {
                    @Override
                    public void succeed(@NonNull ListEntity pData) {
                        super.succeed(pData);
                        mView.updateBookSuccess(pData);
                        mView.hideLoadingDialog();
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        mView.hideLoadingDialog();
                    }
                });
    }

    @Override
    public void addNoteBook(int aType, String aname) {
        mView.showLoadingDialog(true);
        request().addNoteBook(UrlConstans.BASE_URL + UrlConstans.REQUEST_ADDBOOK, aType, aname).
                enqueue(new BindCallback<ListEntity>() {
                    @Override
                    public void succeed(@NonNull ListEntity pData) {
                        super.succeed(pData);
                        mView.addBookSuccess(pData);
                        mView.hideLoadingDialog();
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        mView.hideLoadingDialog();
                    }
                });
    }
}
