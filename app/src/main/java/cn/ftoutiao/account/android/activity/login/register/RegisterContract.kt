package cn.ftoutiao.account.android.login.register


import cn.ftoutiao.account.android.model.UserEntity
import com.acmenxd.frame.basis.IBPresenter
import com.acmenxd.frame.basis.IBView


interface RegisterContract {

    interface View : IBView {

        fun getCaptchaFailed(msg: String)

        fun registerSuccess(result: UserEntity)

        fun registerFailed(msg: String)

        fun showLoading()

        fun hideLoading()

    }

    interface Presenter : IBPresenter {

        fun getCaptcha(phone: String)

        fun registerPhone(phone: String, pwd: String, mcode: String)

    }

}
