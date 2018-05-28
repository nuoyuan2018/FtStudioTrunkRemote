package cn.ftoutiao.account.android.activity.mine;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.BasePresenter;
import cn.ftoutiao.account.android.constants.UrlConstans;
import cn.ftoutiao.account.android.model.NoteBookEntity;

/**
 * Created by weichyang on 2018/5/4.
 */

public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {


    /**
     * 构造器,传入BaseView实例
     *
     * @param pView
     */
    public MinePresenter(MineContract.View pView) {
        super(pView);
    }

    @Override
    public void requestNoteBookCategory(String aid) {
        request().requestNotebookCatagory(
                UrlConstans.BASE_URL + UrlConstans.REQUEST_BOOKCATEGORY,
                aid).enqueue(new BindCallback<NoteBookEntity>() {
                    @Override
                    public void succeed(@NonNull NoteBookEntity pData) {
                        super.succeed(pData);
                        mView.requestNoteBookCategorySuccess(pData);
                    }

                    @Override
                    public void failed(@NonNull HttpException pE) {
                        super.failed(pE);
                        Toaster.show(pE.getMsg());
                        mView.requestFaliur();
                    }
                });
    }
}
