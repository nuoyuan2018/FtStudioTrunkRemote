package cn.ftoutiao.account.android.component.fragment

import android.support.v4.app.Fragment

/**
 * Created by alan on 2017/9/26.
 */

abstract class BaseFragment : Fragment(), BookFragmentListener {

    override val disappearFlag: Int
        get() = BookFragmentListener.FLAG_HIDE
}
