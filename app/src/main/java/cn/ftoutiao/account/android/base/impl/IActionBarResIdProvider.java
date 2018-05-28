package cn.ftoutiao.account.android.base.impl;

import android.support.annotation.IdRes;

/**
 * Created by yangweichao on 2018/3/7.
 */

public interface IActionBarResIdProvider {

    @IdRes
    int getActionBarId();

    @IdRes
    int getActionBarTitleId();

    @IdRes
    int getActionBarContainerId();

    @IdRes
    int getActionBarIconId();

    @IdRes
    int getActionBarActionId();
}
