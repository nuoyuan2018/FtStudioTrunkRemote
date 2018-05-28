package cn.ftoutiao.account.android.activity.notebook;

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

        void getCategoryListSuccess(ATypeListEntity result);
    }

    interface Presenter extends IBPresenter {
        void requestSortCategoryList();
    }

}