package cn.ftoutiao.account.android.activity.bill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseFragment;

/**
 * Created by weichyang on 2018/2/8.
 * 账单首页
 */

public class BillFragment extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull Bundle savedInstanceState) {
        super.onCreateView(inflater, savedInstanceState);
        return inflater.inflate(R.layout.fragment_bill, null);
    }

}
