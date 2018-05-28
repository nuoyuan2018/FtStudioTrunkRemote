package cn.ftoutiao.account.android.activity.mine;

import android.support.annotation.NonNull;

import com.acmenxd.frame.basis.IBPresenter;
import com.acmenxd.frame.basis.IBView;
import com.acmenxd.retrofit.exception.HttpException;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NullEntity;

/**
 * Author: yangweichao
 * Date:   2018/4/14 下午4:40
 * Description: 修改密码
 */


public class ModifyNicknamePersenter extends BasePresenter<ModifyNicknameContract.View> implements ModifyNicknameContract.Presenter {

    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public ModifyNicknamePersenter(ModifyNicknameContract.View pView) {
        super(pView);
    }


    @Override
    public void requestModifyNickName(String nickname) {

        request().requestModifyNick(UrlConstans.BASE_URL + UrlConstans.REQUEST_MNICKNAME,nickname).enqueue(new BindCallback<NullEntity>() {
            @Override
            public void succeed(@NonNull NullEntity pData) {
                super.succeed(pData);
                mView.respondModifyNIckSuccess(pData);
            }

            @Override
            public void failed(@NonNull HttpException pE) {
                super.failed(pE);
                mView.respondModifyNIckFailure(pE.getMsg());
            }
        });




    }
}
