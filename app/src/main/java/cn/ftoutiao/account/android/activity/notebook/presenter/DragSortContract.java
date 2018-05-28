package cn.ftoutiao.account.android.activity.notebook.presenter;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.db.ATypeListEntity;

/**
 * Author: weichyang
 * Date:   2018/3/14
 * Description: 排序P
 */

public interface DragSortContract {

    interface View extends IBView {

        void requestSortCategoryListSuccess(ATypeListEntity pData);

        void requestSortCategoryListFail(String msg);

       // void delSuccess();
    }

    interface Presenter extends IBPresenter {
        void requestSortCategoryList(ATypeListEntity aTypeListEntity);

    }

}