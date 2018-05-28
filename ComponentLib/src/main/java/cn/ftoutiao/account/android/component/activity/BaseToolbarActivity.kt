package cn.ftoutiao.account.android.component.activity

import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import cn.ftoutiao.account.android.component.R
import cn.ftoutiao.account.android.component.widget.IActionBarResIdProvider

/**
 * Created by alan on 2017/10/29.
 */
abstract class BaseToolbarActivity : BaseActivity(), IActionBarResIdProvider {

    private var mToolbar: Toolbar? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupActionBar()
    }

    private fun setupActionBar() {
        if (mToolbar == null) {
            mToolbar = findViewById(getActionBarId())
        }
        if (mToolbar == null) {
            throw RuntimeException("can not find toolbar, did you include in xml??")
        }
        setSupportActionBar(mToolbar)
        var iconResId: Int = provideNavigationIcon()
        if (iconResId > 0) {
            mToolbar!!.setNavigationIcon(iconResId)
        } else {
            mToolbar!!.navigationIcon = null
        }

        mToolbar!!.navigationIcon?.clearColorFilter()
        mToolbar!!.setNavigationOnClickListener {
            onBackPressed()
        }
        if (supportActionBar == null) {
            throw RuntimeException(
                    "actionbar not found, have you called setSupportActionBar method")
        } else {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayShowCustomEnabled(true)
        }
    }

    protected fun provideNavigationIcon(): Int {
        return 0
    }

    protected fun setDefaultTitle(resId: Int) {
        setDefaultTitle(getString(resId))
    }

    protected fun setDefaultTitle(title: String) {
        val textView = mToolbar?.findViewById<View>(getActionBarTitleId()) as TextView
        textView.text = title
    }

    protected fun setDefaultTitleColor(color: Int) {
        val textView = mToolbar?.findViewById<View>(getActionBarTitleId()) as TextView
        textView.setTextColor(color)
    }

}