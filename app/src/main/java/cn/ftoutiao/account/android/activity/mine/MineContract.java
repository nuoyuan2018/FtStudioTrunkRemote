package cn.ftoutiao.account.android.activity.mine;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import cn.ftoutiao.account.android.model.NoteBookEntity;

/**
 * Created by weichyang on 2018/4/17.
 * 修改密码
 */

public class MineContract {

    interface View extends IBView {
        void  requestFaliur();

        void  requestNoteBookCategorySuccess(NoteBookEntity res);
    }

    interface Presenter extends IBPresenter {
        void requestNoteBookCategory(String aid);
    }
}
