package cn.ftoutiao.account.android.activity.notebook.presenter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NullEntity;
import cn.ftoutiao.account.android.model.db.ATypeListEntity;

/**
 * Author: weichyang
 * Date:   2018/3/14
 * Description: 排序P
 */

public class DragSortPresenter extends BasePresenter<DragSortContract.View>
        implements DragSortContract.Presenter {

    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public DragSortPresenter(DragSortContract.View pView) {
        super(pView);
    }

    @Override
    public void requestSortCategoryList(ATypeListEntity aTypeListEntity) {
        mView.showLoadingDialog(true);
        request().requestSortCategory(UrlConstans.BASE_URL + UrlConstans.REQUEST_SORTBOOKCATEGORY,
                Integer.valueOf(aTypeListEntity.aId),
                aTypeListEntity.incomeSeq1, aTypeListEntity.incomeSeq2,
                aTypeListEntity.outgoSeq1, aTypeListEntity.outgoSeq2).enqueue(new BindCallback<ATypeListEntity>() {
            @Override
            public void succeed(@NonNull ATypeListEntity pData) {
                super.succeed(pData);
                mView.requestSortCategoryListSuccess(pData);
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