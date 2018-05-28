package cn.ftoutiao.account.android.component.widget

import android.support.annotation.IdRes

/**
 * Created by alan on 2017/11/4.
 */
interface IActionBarResIdProvider {

    @IdRes abstract fun getActionBarId(): Int

    @IdRes abstract fun getActionBarTitleId(): Int

    @IdRes abstract fun getActionBarContainerId(): Int

    @IdRes abstract fun getActionBarIconId(): Int

    @IdRes abstract fun getActionBarActionId(): Int
}