package cn.ftoutiao.account.android.activity.notebook.presenter;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.ListEntity;

/**
 * Author: weichyang
 * Date:   2018/3/8
 * Description:
 */

public interface CreatNoteBookContract {

    interface View extends IBView {

        void addBookSuccess(ListEntity listEntity);

        void updateBookSuccess(ListEntity listEntity);

        void updateBookFailure(String msg);
    }

    interface Presenter extends IBPresenter {

        void updateNoteBook(String aid, String aname);

        void addNoteBook(int aType, String aname);
    }

}
