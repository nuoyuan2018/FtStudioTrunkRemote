package cn.ftoutiao.account.android.activity.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import cn.ftoutiao.account.android.R
import cn.ftoutiao.account.android.base.BaseActivity
import cn.ftoutiao.account.android.base.EventBusHelper
import cn.ftoutiao.account.android.constants.DataContans
import cn.ftoutiao.account.android.db.AtypeListEntityDB
import cn.ftoutiao.account.android.db.CategeryDB
import cn.ftoutiao.account.android.login.register.FindPasswordActivity
import cn.ftoutiao.account.android.login.register.RegisterActivity
import cn.ftoutiao.account.android.model.NoteBookEntity
import cn.ftoutiao.account.android.model.UserEntity
import cn.ftoutiao.account.android.model.evenbus.LoginSuccess
import cn.ftoutiao.account.android.utils.AccountManager
import cn.ftoutiao.account.android.utils.StringUtil
import cn.ftoutiao.account.android.utils.TopBar
import cn.ftoutiao.account.android.widget.DialogUtils
import com.acmenxd.toaster.Toaster
import com.acmenxd.widget.NyDialog
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Author: weichyang
 * Date:   2018/2/8
 * Description: 登录
 */

open class LoginActivity : BaseActivity(), TextWatcher, CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, LoginContract.View {

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            nyDialog?.dismiss()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private var mPresenter: LoginPresenter? = null


    override fun initLayout() {
        setContentView(R.layout.activity_login)
    }


    override fun init() {
        mPresenter = LoginPresenter(this)
        topbar.setTopbarTitle("登录")
        topbar.setTextValue("注册", TopBar.RIGHT)
    }


    override fun initListener() {
        topbar.setonTopbarLeftLayoutListener { finish() }
        topbar.setOnRightLayoutListener({
            startActivityForResult(Intent(this, RegisterActivity::class.java), Companion.REQUEST_CODE_REGISTER)
        })
        et_phone.addTextChangedListener(this)
        et_pwd.addTextChangedListener(this)
        cb_show_pwd.setOnCheckedChangeListener(this)
        tv_forget_pwd.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_wechat.setOnClickListener(this)
        tv_qq.setOnClickListener(this)
        tv_weibo.setOnClickListener(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.REQUEST_CODE_REGISTER && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }


    override fun afterTextChanged(s: Editable?) {
        btn_login.isEnabled = et_phone.text.trim().isNotEmpty() && et_pwd.text.isNotEmpty()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView!!.id == cb_show_pwd.id) {
            if (isChecked) {
                et_pwd.transformationMethod = null
                et_pwd.setSelection(et_pwd.length())
            } else {
                et_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                et_pwd.setSelection(et_pwd.length())
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            tv_forget_pwd.id -> {
                startActivity(FindPasswordActivity::class.java)
            }
            btn_login.id -> {
                var phoneNum = et_phone.text.trim().toString()
                var password = et_pwd.text.toString()
                 if (!StringUtil.isPwd(password)) {
                    Toaster.show(getString(R.string.please_input_pwd))
                } else {
                    mPresenter?.login(phoneNum, password)
                }
            }

            tv_wechat.id -> {
                mPresenter?.oauthLogin(SHARE_MEDIA.WEIXIN, this)
            }

            tv_qq.id -> {
                mPresenter?.oauthLogin(SHARE_MEDIA.QQ, this)
            }

            tv_weibo.id -> {
                mPresenter?.oauthLogin(SHARE_MEDIA.SINA, this)
            }
        }
    }

    override fun showLoading() {
        // showLoading()
    }

    override fun hideLoading() {
        // hideLoading()
    }


    private var nyDialog: NyDialog? = null

    override fun loginSuccess(result: UserEntity) {
        AccountManager.getInstance().setAccount(this, result)
        DataContans.setUserEntity(result)
        EventBusHelper.post(LoginSuccess())
        nyDialog = DialogUtils.loadingDialog(this@LoginActivity, View.inflate(this@LoginActivity, R.layout.asy_layout, null))
        mPresenter?.requestNoteBookCategory(null)
    }


    override fun loginFailed(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        UMShareAPI.get(this).onSaveInstanceState(outState!!)
    }

    companion object {
        private val REQUEST_CODE_REGISTER: Int = 1000
    }

    /**
     *同步数据
     */
    override fun requestNoteBookCategorySuccess(res: NoteBookEntity) {
        insertDb(res)
    }

    override fun requestFaliur() {
        nyDialog?.dismiss()
    }

    private fun insertDb(res: NoteBookEntity) {
        Thread(Runnable {
            val userEntity = if (DataContans.userEntity == null) UserEntity() else DataContans.userEntity
            for (aTypeListEntity in res.list) {
                aTypeListEntity.uid = userEntity.uid
                AtypeListEntityDB.getInstance().addAtypeEntiry(aTypeListEntity)
                for (categoryEntity in aTypeListEntity.category) {
                    categoryEntity.uid = userEntity.uid
                    // TODO: 2018/3/30  aid 临时插入
                    categoryEntity.aId = aTypeListEntity.aId
                    CategeryDB.getInstance().addCategoryEntiry(categoryEntity)
                    val message = Message.obtain()
                    handler.sendMessage(message)
                }
            }
        }).start()
    }

}