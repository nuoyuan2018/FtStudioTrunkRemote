package cn.ftoutiao.account.android.component.router.delegate

import android.support.annotation.IdRes

import com.alibaba.android.arouter.facade.template.IProvider

import cn.ftoutiao.account.android.component.fragment.BaseFragment

/**
 * Created by alan on 2017/10/12.
 */

interface BaseFragmentDelegate : IProvider {

    val fragment: BaseFragment

    fun setContainerId(@IdRes id: Int)

}
