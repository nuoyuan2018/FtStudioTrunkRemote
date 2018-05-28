package cn.ftoutiao.account.android.component.router

import android.app.Activity
import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by alan on 2017/10/13.
 */

object ARouterHelper {

    fun <T : IProvider> getServiceByPath(path: String): T =
            ARouter.getInstance().build(path).navigation() as T

    fun <T : IProvider> getServiceByClass(clz: Class<T>): T = ARouter.getInstance().navigation(clz)

    fun navigationToActivityForResult(path: String, activity: Activity, requestCode: Int) {
        ARouter.getInstance().build(path).navigation(activity, requestCode)
    }
}
