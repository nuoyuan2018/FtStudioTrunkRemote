package cn.ftoutiao.account.android.activity.mine;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import java.io.File;

/**
 * Author: yangweichao
 * Date:   2018/3/3 下午3:14
 * Description: 链接器
 */


public interface AccountContract {

    interface View extends IBView {

        void loginOutSuccess();

        void loginOutFailed(String msg);


    }

    interface Presenter extends IBPresenter {

        void loginOut();

        void updateHeaderImage(File file);

        void updateImagePath2Service(String image);
    }

}
