package cn.ftoutiao.account.android.login.register

import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import cn.ftoutiao.account.android.R
import cn.ftoutiao.account.android.activity.login.register.RegisterPresenter
import cn.ftoutiao.account.android.activity.webview.WebActivity
import cn.ftoutiao.account.android.base.BaseActivity
import cn.ftoutiao.account.android.base.EventBusHelper
import cn.ftoutiao.account.android.constants.ConstanPool
import cn.ftoutiao.account.android.constants.DataContans
import cn.ftoutiao.account.android.model.UserEntity
import cn.ftoutiao.account.android.model.evenbus.LoginSuccess
import cn.ftoutiao.account.android.utils.AccountManager
import cn.ftoutiao.account.android.utils.StringUtil
import com.acmenxd.toaster.Toaster
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Author: weichyang
 * Date:   2018/2/9
 * Description:
 * 注册
 */
class RegisterActivity : BaseActivity(), TextWatcher, View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, RegisterContract.View {


    private var mPresenter: RegisterPresenter? = null

    private var mTimer: CountDownTimer = object : CountDownTimer(30 * 1000, 1000) {

        override fun onFinish() {
            if (isFinishing) {
                return
            }
            resetGetCaptchaBtn()
        }

        override fun onTick(millisUntilFinished: Long) {
            if (isFinishing) {
                return
            }
            btn_get_captcha.text = getString(R.string.get_captcha_retry, millisUntilFinished / 1000)
        }
    }


    override fun initLayout() {
        setContentView(R.layout.activity_register)
    }

    override fun init() {
        topbar.setTopbarTitle(R.string.register)
        mPresenter = RegisterPresenter(this)
        addPresenters(mPresenter)
        et_pwd.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(16)))
    }

    override fun initListener() {
        topbar.setonTopbarLeftLayoutListener { finish() }
        et_phone.addTextChangedListener(this)
        et_captcha.addTextChangedListener(this)
        et_pwd.addTextChangedListener(this)
        btn_get_captcha.setOnClickListener(this)
        cb_show_pwd.setOnCheckedChangeListener(this)
        btn_register.setOnClickListener(this)
    }


    override fun getCaptchaFailed(msg: String) {
        mTimer.cancel()
        resetGetCaptchaBtn()
    }

    private fun resetGetCaptchaBtn() {
        btn_get_captcha.isClickable = true
        btn_get_captcha.setText(R.string.get_captcha)
    }

    override fun registerSuccess(result: UserEntity) {
        val account: UserEntity = result
        AccountManager.getInstance().setAccount(this, account)
        DataContans.setUserEntity(result)
        EventBusHelper.post(LoginSuccess())
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun registerFailed(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    override fun afterTextChanged(s: Editable?) {
        btn_register.isEnabled = et_phone.length() > 0 && et_captcha.length() > 0 && et_pwd.length() >= 6
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun showLoading() {
        // ProgressDialogFragment.show(this, null, false)
    }

    override fun hideLoading() {
        // ProgressDialogFragment.hide(this)

    }

    override fun onClick(v: View?) {
        var id: Int = v!!.id
        if (id == btn_get_captcha.id) {
            if (et_phone.length() > 0) {
                btn_get_captcha.isClickable = false
                mTimer.start()
                mPresenter!!.getCaptcha(et_phone.text.trim().toString())
            } else {

            }
        } else if (id == btn_register.id) {
            var phoneNum = et_phone.text.trim().toString()
            var vertifyCode = et_captcha.text.trim().toString()
            var password = et_pwd.text.toString()
            if (!StringUtil.isPhoneNumber(phoneNum)) {
                Toaster.show("手机号格式不正确")
            } else if (!StringUtil.isPwd(password)) {
                Toaster.show(getString(R.string.please_input_pwd))
            } else {
                mPresenter?.registerPhone(phoneNum, password, vertifyCode)
            }

        }
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

    override fun onDestroy() {
        mTimer.cancel()
        super.onDestroy()
    }

    fun actionServiceProtocol(view: View) {
        var intent = Intent(this, WebActivity::class.java)
        intent.putExtra(ConstanPool.WEB_TITLE, getString(R.string.uc_user_protocol))
        intent.putExtra(ConstanPool.WEB_URL, "http://account.ftoutiao.cn/app/privacy.html")
        startActivity(intent)
    }
}