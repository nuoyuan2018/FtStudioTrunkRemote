package cn.ftoutiao.account.android.activity.mine;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

/**
 * Created by weichyang on 2018/4/17.
 * 修改密码
 */

public class ModifyContract {

    interface View extends IBView {

        void requestModifyPassworSuccess();

        void requestModifyPassworFailed(String msg);


    }

    interface Presenter extends IBPresenter {

        void requestModifyPasswor(String phone, String oldPwd, String pwd);
    }
}
