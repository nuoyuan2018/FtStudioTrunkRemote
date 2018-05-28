package cn.ftoutiao.account.android.component.router;

/**
 * Created by alan on 2017/9/28.
 *
 * 路由路径配置， 路径需要注意的是至少需要有两级，/xx/xx
 *
 */

public class PathConfig {
    /**
     * 账单
     */
    private static final String GROUP_BILL = "/bill/";

    public static final String BILL_FRAGMENT_DELEGATE = GROUP_BILL + "fragment_delegate";

    /**
     * 我的
     */
    public static final String GROUP_MINE = "/mine/";

    public static final String MINE_FRAGMENT_DELEGATE = GROUP_MINE + "fragment_delegate";

    /**
     * 登录
     */
    public static final String GROUP_LOGIN = "/login/";

    public static final String LOGIN_ACTIVITY = GROUP_LOGIN + "login_activity";
}
