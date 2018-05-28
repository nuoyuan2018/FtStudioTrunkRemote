package cn.ftoutiao.account.android.component.router.delegate

import android.content.Intent

/**
 * Created by alan on 2017/10/12.
 */

interface MineFragmentDelegate : BaseFragmentDelegate {
    fun onActivityResultWrap(requestCode: Int, resultCode: Int, data: Intent?)
}
