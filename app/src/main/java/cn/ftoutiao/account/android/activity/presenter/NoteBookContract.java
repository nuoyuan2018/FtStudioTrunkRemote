package cn.ftoutiao.account.android.activity.presenter;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.NoteBookEntity;
import cn.ftoutiao.account.android.model.db.CategoryEntity;
import cn.ftoutiao.account.android.model.db.ListItemBO;

public interface NoteBookContract {

    interface View extends IBView {

        void getNoteBooksSuccess(NoteBookEntity res);

        void getNoteBooksFailure();

        void modifyItemSuccess(String success);

        void addItemSuccess(ListItemBO listItemBO);


    }

    interface Presenter extends IBPresenter {

        void requestNoteBookCategory(String aid);

        void requestAddItem(CategoryEntity categoryEntity, String adata, String amount, String remark);

        void requestModifyItem(CategoryEntity categoryEntity, String adata, String amount, String remark);

    }

}