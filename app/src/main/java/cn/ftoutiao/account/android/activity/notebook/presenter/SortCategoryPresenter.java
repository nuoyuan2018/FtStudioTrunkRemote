package cn.ftoutiao.account.android.activity.notebook.presenter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.db.ATypeListEntity;

/**
 * Created by weichyang on 2018/3/8.
 */

public class SortCategoryPresenter extends BasePresenter<SortCategoryContract.View> implements
        SortCategoryContract.Presenter {

    public SortCategoryPresenter(SortCategoryContract.View pView) {
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
                mView.sortCategoryListSuccess(pData);
                mView.hideLoadingDialog();
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                mView.hideLoadingDialog();
                Toaster.show(pE.getMsg());
            }
        });

    }
}
