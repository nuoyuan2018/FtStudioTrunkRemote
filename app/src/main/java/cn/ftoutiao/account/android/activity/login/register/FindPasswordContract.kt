package cn.ftoutiao.account.android.login.register


import cn.ftoutiao.account.android.model.UserEntity
import com.acmenxd.frame.basis.IBPresenter
import com.acmenxd.frame.basis.IBView

/**
 * Created by alan on 2017/11/4.
 */

interface FindPasswordContract {

    interface View : IBView {

        fun getCaptchaFailed(msg: String)

        fun findSuccess()

        fun  findFailed(msg: String)

        fun showLoading()

        fun hideLoading()

    }

    interface Presenter : IBPresenter {

        fun getCaptcha(phone: String)

        fun registerPhone(phone: String, pwd: String, mcode: String)

    }

}
