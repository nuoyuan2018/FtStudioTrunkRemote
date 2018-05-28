//package cn.ftoutiao.account.android.login
//
//import android.app.Activity
//import cn.ftoutiao.account.android.core.entity.Account
//import cn.ftoutiao.account.android.core.entity.Salt
//import cn.ftoutiao.account.android.core.retrofit.BookResult
//import cn.ftoutiao.account.android.login.datasource.AccountDataSource
//import com.umeng.socialize.UMAuthListener
//import com.umeng.socialize.UMShareAPI
//import com.umeng.socialize.bean.SHARE_MEDIA
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
///**
// * Created by alan on 2017/11/7.
// */
//class LoginPresenter(private var mView: LoginContract.View, private var mDataSource: AccountDataSource)
//    : LoginContract.Presenter, UMAuthListener {
//
//
//
//    override fun login(name: String, pwd: String) {
//        mView.showLoading()
//        mDataSource.getSalt(name, Salt.TYPE_LOGIN)
//                .subscribeOn(Schedulers.io())
//                .flatMap { saltResult -> mDataSource.login(name, pwd, saltResult.salt) }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ accountResult ->
//                    run {
//                        mView.hideLoading()
//                        mView.loginSuccess(accountResult)
//                    }
//                }) { t ->
//                    run {
//                        mView.hideLoading()
//                        mView.loginFailed(t.message!!)
//                        t.printStackTrace()
//                    }
//                }
//    }
//
//    override fun oauthLogin(media: SHARE_MEDIA, activity: Activity) {
//        UMShareAPI.get(activity).getPlatformInfo(activity, media, this)
//    }
//
//    override fun onStart(p0: SHARE_MEDIA?) {
//
//    }
//
//    override fun onComplete(p0: SHARE_MEDIA?, p1: Int, p2: MutableMap<String, String>?) {
//
//        if (p0 != null) {
//            var id: String? = null
//            var accessToken: String? = null
//            var platform = ""
//            var unionId: String? = null
//            var refreshToken: String? = null
//            when (p0) {
//                SHARE_MEDIA.SINA -> {
//                    id = p2?.get("uid")
//                    accessToken = p2?.get("access_token")
//                    platform = "sina"
//                    refreshToken = p2?.get("refresh_token")
//                }
//
//                SHARE_MEDIA.QQ -> {
//                    id = p2?.get("openid")
//                    accessToken = p2?.get("access_token")
//                    platform = "qq"
//                }
//
//                SHARE_MEDIA.WEIXIN -> {
//                    id = p2?.get("openid")
//                    unionId = p2?.get("unionid")
//                    accessToken = p2?.get("access_token")
//                    platform = "weixin"
//                    refreshToken = p2?.get("refreshToken")
//                }
//                else -> {
//                }
//            }
//            mDataSource.oauthLogin(id!!, accessToken!!, platform, unionId, refreshToken)
//                    .enqueue(object : Callback<BookResult<Account>> {
//                        override fun onResponse(call: Call<BookResult<Account>>?, response: Response<BookResult<Account>>?) {
//                            mView.loginSuccess(response?.body()!!)
//                        }
//
//                        override fun onFailure(call: Call<BookResult<Account>>?, t: Throwable?) {
//                            mView.loginFailed(t?.message!!)
//                        }
//                    })
//        }
//    }
//
//    override fun onCancel(p0: SHARE_MEDIA?, p1: Int) {
//
//    }
//
//    override fun onError(p0: SHARE_MEDIA?, p1: Int, p2: Throwable?) {
//        mView.loginFailed(p2?.message!!)
//    }
//}