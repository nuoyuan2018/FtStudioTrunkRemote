package cn.ftoutiao.account.android.activity.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseFragment;

/**
 * Created by weichyang on 2018/2/8.
 * 1.查询整月数据
 * 2.查询当天的数据
 * 3.账本切换重新查询？移除之前账本(进行重绘)
 * <p>
 * 4.首次进入刷新
 * 5.当页面刷新
 * 6.点击切换，手动滑动切换刷新
 * <p>
 * <p></>
 * 主要刷新逻辑
 * 1.首次进入
 * 2.更换账本后-进入
 * 3.添加过新账本后
 * 4.滑动刷新
 * 5.tab 切换不刷新
 */

public class CalendarFragment extends BaseFragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull Bundle savedInstanceState) {
        super.onCreateView(inflater, savedInstanceState);
        return inflater.inflate(R.layout.activity_schedule, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        initValue();
    }

    protected void initView() {

    }

    protected void initListener() {

    }


    protected void initValue() {

    }


}
