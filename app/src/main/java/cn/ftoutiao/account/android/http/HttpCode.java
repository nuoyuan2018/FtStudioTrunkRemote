package cn.ftoutiao.account.android.http;

import com.acmenxd.frame.utils.Utils;
import com.acmenxd.retrofit.HttpCodeParse;
import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.retrofit.exception.HttpExceptionFail;
import com.acmenxd.retrofit.exception.HttpExceptionSuccess;
import com.acmenxd.retrofit.exception.HttpExceptionUnknownCode;
import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.base.EventBusHelper;
import cn.ftoutiao.account.android.model.evenbus.LoginLose;


/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/3/10 9:50
 * @detail 统一处理服务器响应的code和msg
 */
public final class HttpCode extends HttpCodeParse.parseNetCode {
    /**
     * 默认ToastMsg提示消息
     */
    public static final String TOAST_MSG = "网络连接超时，请稍后再试";
    /**
     * 服务器返回的状态码 - 成功
     */
    public static final int SUCCESS_DATA = 0; // 数据正常
    public static final int SUCCESS_RECHARGE = 1; // 充值申请成功，请及时查看账户余额
    public static final int ACCOUNT_EXIST = -9;// 账户已存在
    public static final int PWD_ERROR = -10;// 登录密码错误
    public static final int ERR_BANK_CONFIRM = -70;//需要进行卡密验证（银行卡）
    public static final int REALNAME_ONCARD = -126;// 正在实名、绑卡操作
    /**
     * 服务器返回的状态码 - 失败
     */
    public static final int TIE_ON_CARD = 1;//实名未绑卡
    public static final int HAS_TIED_CARD = 2;//已绑卡
    public static final int NOT_REGIST = -1;//账户未注册
    public static final int ACCOUNT_ISLOCK = -2;// 账户被锁
    public static final int ACCOUNT_NOTEXIST = -3;// 账户不存在
    public static final int REACH_MAX_ERROR = -4;//
    public static final int ERR_MOBILE_NO = -5;// 手机号格式不正确
    public static final int ERR_VCODE = -6;// 图片验证码错误 & 盐验证失败
    public static final int ERR_MVCODE = -7;// 短信验证码错误
    public static final int ERR_TOOFAST = -8;// 手机验证码获取太快
    public static final int NOT_LOGIN = 9000;// 未登录

    public static final int ERR_MUST_UPDATE = -700301;//强制升级
    public static final int CONNECT_FAIL = 400;//请求失效（网络没有、或者超时导致）
    public static final int CONNECTTIMEOUT = 500;// 请求超时

    /**
     * 统一处理服务器响应的code和msg
     *
     * @param code 服务器返回的code码
     * @param msg  服务器返回的msg消息
     * @return 返回生成的Exception
     */
    public final synchronized HttpException parse(final String url, final int code, final String msg) {
        // 数据成功处理
        HttpExceptionSuccess success = parseSuccess(url, code, msg);
        if (success != null) {
            return success;
        }
        // 数据异常处理 - 弹吐司
        HttpExceptionFail failToast = parseFailToast(url, code, msg);
        if (failToast != null) {
            return failToast;
        }
        // 数据异常处理 - 不弹吐司
        HttpExceptionFail fail = parseFail(url, code, msg);
        if (fail != null) {
            return fail;
        }
        /**
         * code无匹配处理
         */
        return new HttpExceptionUnknownCode(code, msg, TOAST_MSG);
    }

    /**
     * 数据成功处理
     */
    private static HttpExceptionSuccess parseSuccess(final String url, final int code, final String msg) {
        switch (code) {
            case SUCCESS_DATA:
                return new HttpExceptionSuccess(code, msg, msg);
        }
        return null;
    }

    /**
     * 数据异常处理 - 弹吐司
     */
    private static HttpExceptionFail parseFailToast(final String url, final int code, final String msg) {
        String toastMsg = null;
        switch (code) {
            case ERR_MOBILE_NO:// 手机号格式不正确
                toastMsg = "手机号格式不正确";
                break;

            case ACCOUNT_NOTEXIST:// 账号不存在
                toastMsg = "您的手机号尚未注册，请先注册";
                break;
        }
        if (!Utils.isEmpty(toastMsg)) {
            Toaster.show(toastMsg);
            return new HttpExceptionFail(code, msg, "");
        }
        return null;
    }

    /**
     * 数据异常处理 - 不弹吐司
     */
    private static HttpExceptionFail parseFail(final String url, final int code, final String msg) {
        String toastMsg = null;
        switch (code) {
            case NOT_LOGIN:// 登录失效或您在其它设备登录过,请重新登录
                toastMsg = "-";
                EventBusHelper.post(new LoginLose(url, code, ""));
                break;
        }
        if (!Utils.isEmpty(toastMsg)) {
            return new HttpExceptionFail(code, "", "");
        }
        return null;
    }
}
