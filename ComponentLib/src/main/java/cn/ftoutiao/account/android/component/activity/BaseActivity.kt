package cn.ftoutiao.account.android.component.activity

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager

import com.alibaba.android.arouter.launcher.ARouter

import cn.ftoutiao.account.android.component.constant.PermissionConstant
import cn.ftoutiao.account.android.component.fragment.BookFragmentListener
import com.acmenxd.utils.BaseTypeUtils
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import java.lang.reflect.Field

/**
 * Created by alan on 2017/9/20.
 */

abstract class BaseActivity : AppCompatActivity() {

    /**
     * 返回activity中所有fragment对应的tag
     *
     * @return
     */
    abstract val allFragmentTags: Array<String>?

    /**
     * 返回activity名称.使用return XXActivity.class.getName()
     *
     * @return
     */
    abstract val activityName: String

    /**
     * 首页ARouter注解参数，需要在Activity初始化时调用此方法
     */
    fun injectARouter() {
        ARouter.getInstance().inject(this)
    }

    /**
     * 申请权限
     *
     * @param permissions
     * @return true 权限已获取不必申请
     */
    fun applyPermission(permissions: Array<String>): Boolean {
        if (BaseTypeUtils.isArrayEmpty(permissions)) {
            return true
        }
        if (EasyPermissions.hasPermissions(this, *permissions)) {
            return true
        } else {
            EasyPermissions.requestPermissions(this, "", PermissionConstant.PERMISSION_REQUEST_CODE, *permissions)
        }
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 批量添加fragment
     *
     * @param fragments
     */
    fun addFragments(vararg fragments: Fragment) {
        if (fragments.isEmpty()) {
            return
        }

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        for (fragment in fragments) {
            if (fragment !is BookFragmentListener) {
                continue
            }

            val listener = fragment as BookFragmentListener

            val tag = listener.tagName
            if (TextUtils.isEmpty(tag)) {
                continue
            }

            val containerId = listener.containerId
            if (containerId <= 0) {
                continue
            }

            if (fragmentManager.findFragmentByTag(tag) != null) {
                continue
            }

            transaction.add(containerId, fragment, tag)
        }

        try {
            transaction.commitAllowingStateLoss()
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }

    /**
     * 显示fragment，默认隐藏或移除其他fragment
     *
     * @param fragment
     */
    protected fun showFragmentIfNeeded(fragment: Fragment) {
        showFragmentIfNeeded(fragment, true)
    }

    /**
     * 显示fragment
     *
     * @param fragment
     * @param hideOrRemoveOthers 是否关闭其他的fragment
     */
    fun showFragmentIfNeeded(fragment: Fragment, hideOrRemoveOthers: Boolean) {
        if (fragment !is BookFragmentListener) {
            return
        }

        val listener = fragment as BookFragmentListener

        val tag = listener.tagName
        if (TextUtils.isEmpty(tag)) {
            return
        }

        val tags = allFragmentTags ?: return

        val containerId = listener.containerId
        if (containerId <= 0) {
            return
        }

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        if (fragmentManager.findFragmentByTag(tag) == null) {
            // 当没添加过该fragment时，添加
            transaction.add(containerId, fragment, tag)
        }
        transaction.show(fragment)
        listener.onShow()

        if (hideOrRemoveOthers) {
            // 隐藏或移除其他fragment
            for (hideTag in tags) {
                if (TextUtils.equals(tag, hideTag)) {
                    continue
                }

                val fragmentNeedHide = fragmentManager.findFragmentByTag(hideTag)
                if (fragmentNeedHide is BookFragmentListener) {
                    val flag = (fragmentNeedHide as BookFragmentListener).disappearFlag
                    if (flag == BookFragmentListener.FLAG_REMOVE) {
                        transaction.remove(fragmentNeedHide)
                    } else if (flag == BookFragmentListener.FLAG_HIDE) {
                        transaction.hide(fragmentNeedHide)
                        (fragmentNeedHide as BookFragmentListener).onHide()
                    }
                }
            }
        }

        try {
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 隐藏fragment
     *
     * @param tag
     */
    fun hideFragment(tag: String) {
        if (TextUtils.isEmpty(tag))
            return

        val fragmentManager = supportFragmentManager
        hideFragment(fragmentManager.findFragmentByTag(tag))
    }

    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    fun hideFragment(fragment: Fragment?) {
        if (fragment == null)
            return

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.hide(fragment)

        if (fragment is BookFragmentListener) {
            (fragment as BookFragmentListener).onHide()
        }

        try {
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 删除fragment
     *
     * @param fragment
     */
    fun removeFragment(fragment: Fragment?) {
        if (fragment == null)
            return

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.remove(fragment)

        try {
            transaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 修复输入法造成的activity内存泄漏
     *
     * @param destContext
     */
    fun fixInputMethodManagerLeak(destContext: Context?) {

        if (destContext == null) {
            return
        }

        val imm = destContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return

        val arr = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var f: Field
        var obj_get: Any?
        for (i in arr.indices) {
            val param = arr[i]
            try {
                f = imm.javaClass.getDeclaredField(param)
                if (f.isAccessible == false) {
                    f.isAccessible = true
                }
                obj_get = f.get(imm)
                if (obj_get != null && obj_get is View) {
                    val v_get = obj_get as View?
                    if (v_get!!.context === destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null) // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }

}
