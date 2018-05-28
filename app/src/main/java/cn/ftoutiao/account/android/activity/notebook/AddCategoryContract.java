package cn.ftoutiao.account.android.activity.notebook;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.db.CategoryEntity;

/**
 * Author: weichyang
 * Date:   2018/3/8
 * Description:
 */


public interface AddCategoryContract {

    interface View extends IBView {

        void addCategorySuccess(CategoryEntity result);
    }

    interface Presenter extends IBPresenter {
        void addCategory(CategoryEntity category);
    }

}