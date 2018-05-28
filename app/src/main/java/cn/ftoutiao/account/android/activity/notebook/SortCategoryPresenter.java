package cn.ftoutiao.account.android.activity.notebook;

import cn.ftoutiao.account.android.base.BasePresenter;

/**
 * Created by weichyang on 2018/3/8.
 */

public class SortCategoryPresenter extends BasePresenter<SortCategoryContract.View> implements
        SortCategoryContract.Presenter {

    public SortCategoryPresenter(SortCategoryContract.View pView) {
        super(pView);
    }


    @Override
    public void requestSortCategoryList() {
//        request().querySortCategoryList(UrlConstans.BASE_URL + UrlConstans.REQUEST_SORTBOOKCATEGORY).
//                enqueue(new BindCallback<ATypeListEntity>() {
//                    @Override
//                    public void succeed(@NonNull ATypeListEntity pData) {
//                        super.succeed(pData);
//                        mView.getCategoryListSuccess(pData);
//                    }
//
//                    @Override
//                    public void failed(@NonNull HttpException pE) {
//                        super.failed(pE);
//                    }
//                });
    }
    }
