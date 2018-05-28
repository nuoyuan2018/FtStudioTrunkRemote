package cn.ftoutiao.account.android.activity.mine;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;

import java.io.File;

import cn.ftoutiao.account.android.model.NullEntity;

/**
 * Author: yangweichao
 * Date:   2018/4/14 下午4:40
 * Description: 修改密码
 */



public interface ModifyNicknameContract {

    interface View extends IBView {

        void respondModifyNIckFailure(String msg);

        void respondModifyNIckSuccess(NullEntity nullEntity);
    }

    interface Presenter extends IBPresenter {

        void requestModifyNickName(String nickname);
    }

}
