package cn.ftoutiao.account.android.activity.presenter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NoteBookEntity;
import cn.ftoutiao.account.android.model.db.CategoryEntity;
import cn.ftoutiao.account.android.model.db.ListItemBO;

/**
 * Created by weichyang on 2018/2/27.
 */

public class NoteBookPresenter extends BasePresenter<NoteBookContract.View> implements NoteBookContract.Presenter {

    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public NoteBookPresenter(NoteBookContract.View pView) {
        super(pView);
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
                        mView.getNoteBooksSuccess(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        mView.getNoteBooksFailure();
                    }
                });
    }

    @Override
    public void requestModifyItem(CategoryEntity categoryEntity, String adata, String amount, String remark) {

        mView.showLoadingDialog(true);
        request().modifyItem(
                UrlConstans.BASE_URL + UrlConstans.REQUEST_UPDATA_ITEM,
                categoryEntity.itemId,
                categoryEntity.cType,
                categoryEntity.cId,
                adata,
                amount,
                remark).
                enqueue(new BindCallback<ListItemBO>() {

                    @Override
                    public void succeed(@NonNull ListItemBO pData) {
                        super.succeed(pData);
                        mView.addItemSuccess(pData);
                        mView.hideLoadingDialog();
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMessage());
                        mView.hideLoadingDialog();
                    }
                });
    }

    @Override
    public void requestAddItem(CategoryEntity categoryEntity, String adata, String amount, String remark) {
        mView.showLoadingDialog(true);
        request().addItem(
                UrlConstans.BASE_URL + UrlConstans.REQUEST_ADDITEM,
                categoryEntity.aId,
                categoryEntity.cType,
                categoryEntity.cId,
                adata,
                amount,
                remark).
                enqueue(new BindCallback<ListItemBO>() {

                    @Override
                    public void succeed(@NonNull ListItemBO pData) {
                        super.succeed(pData);
                        mView.addItemSuccess(pData);
                        mView.hideLoadingDialog();
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMessage());
                        mView.hideLoadingDialog();
                    }
                });
    }


}
