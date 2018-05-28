package cn.ftoutiao.account.android.activity.notebook.presenter;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.db.ATypeListEntity;

/**
 * Author: weichyang
 * Date:   2018/3/8
 * Description:
 */


public interface SortCategoryContract {

    interface View extends IBView {
        void sortCategoryListSuccess(ATypeListEntity aTypeListEntity);
    }

    interface Presenter extends IBPresenter {

        void requestSortCategoryList(ATypeListEntity aTypeListEntity);
    }

}