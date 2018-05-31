package cn.ftoutiao.account.android.activity.budget;

import android.view.View;


/**
 * @author rw
 */

public abstract class BaseViewHolder {
    private View view;

    public BaseViewHolder() {

    }

    public BaseViewHolder(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * 用来返回 view
     *
     * @return
     */
    public abstract View createView();

}
