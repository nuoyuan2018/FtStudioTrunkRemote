package cn.ftoutiao.account.android.constants;

import com.acmenxd.sptool.SpManager;

import cn.ftoutiao.account.android.base.AppConfig;


/**
 * Created by weichyang on 2017/6/21.
 */

public class SpConstans {

    /**
     * 是否登录
     */
    public static final String USER_INFO_ISLOGIN = "user_info_isLogin";
    /**
     * 真实姓名
     */
    public static final String USER_REALITY_NAME = "user_reality_name";
    /**
     * 用户身份证号
     */
    public static final String USER_ID_NUMBER = "user_id_number";
    /**
     * 升级apk提示类型
     */
    public static final String INSTALL_APK_UPSTATE = "apk_install_upState";
    /**
     * app升级提示内容
     */
    public static final String INSTALL_APK_UPDES = "apk_install_updes";
    /**
     * apk升级下载地址
     */
    public static final String INSTALL_APK_URL = "apk_install_url";
    /**
     * apk升级下载版本
     */
    public static final String INSTALL_APK_VERSION = "apk_install_version";
    /**
     * apk升级下载包大小
     */
    public static final String INSTALL_APK_SIZE = "apk_install_size";

    /**
     * 用户登录之后保存输入金额
     */
    public static final String USER_MONEY = "user_money";

    public static final String ISFIRSTLOGIN = "isfirstlogin";

    /**
     * 用户信息-账户
     * <p/>
     * <pre>
     * String
     * </pre>
     */
    public static final String USER_INFO_ACOUNT = "user_info_acount";

    //    spconfig
    public static String HASCARD = "bankcard_bindcard";

    public static String getRealName() {
        return SpManager.getCommonSp(AppConfig.config.SP_User).getString("RealName", "");
    }

    public static void setRealName(String realName) {
        SpManager.getCommonSp(AppConfig.config.SP_User).putString("RealName", realName);
    }
}
