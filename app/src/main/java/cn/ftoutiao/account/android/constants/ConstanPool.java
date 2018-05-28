package cn.ftoutiao.account.android.constants;

/**
 * Created by weichyang on 2017/6/26.
 */

public class ConstanPool {

    /**
     * 常量池
     */
    public static final String TYPE_SIGN = "sign";
    public static final String TYPE_LOGIN = "login";
    public static final String TYPE_RESET = "resetPwd";
    public static final String TYPE_UPDATE = "updatePwd";

    //记事本
    public static final String P_CATEGORYLIST = "categorylist";
    public static final String P_CATEGORY = "category";
    public static final String P_CURRENTSELECTEDTAB = "currentSelectedTab";

    public static final String P_NOTEBOOK = "notebook";
    public static final String P_INDEX = "index"; //选择项目

    public static final String P_TYPE_INPUT = "2"; //收入 1支出
    public static final String P_TYPE_OUTPUT = "1"; //收入 1支出


    public static final String BUNDLE = "bundle";

    public static final String P_AID = "aid";
    public static final String P_DES = "describe";
    public static final int REQUEST_CODE = 100;
    public static final String P_PARAMTER = "p_paramter";
    public static final String CONFIG_FIRSTLOGIN = "is_first_login";
    public static final String SPLASH_IMAGE_URL = "splash_image_url";
    public static final String SHOW_TIME = "show_time";
    public static final int FORWARD_GROUND = 1; //前台
    public static final String SCHEDULE_REFRESH_ACTION = "schedule_refresh_action"; //定时刷新
    public static final int TEXT_LENTH = 16;
    public static final String ISEDITER = "isediter";
    public static final int CODE_UPDATE = 1001;


    //记账类型
    public static int RECORDER_TYPE_INPUT = 1;
    public static int RECORDER_TYPE_OUTPUT = 2;

    //账本分类

//    推送类型

    public static final String PUSH_MSG = "pushMsg";//推送自定义消息体
    public static final String IS_PUSH_FROM = "isFromPush";//是否来自推送的initent
    public static final String UM_DEVICE = "umeng_device_token";
    ;


    //webview
    public static final String WEB_IS_COOKIE = "isneedCookie";
    public static final String WEB_IS_PARAMS = "isneedPARAMS";
    public static final String WEB_IS_COLSE = "isCanClose";
    public static final String WEB_URL = "weburl";
    public static final String WEB_TITLE = "webTitle";
    public static final String WEB_FINISH_STARTMAIN = "web_finish_startmain";
    public static final String WEB_LOGIN_TYPE = "webLogin";
    public static final String WEB_KEY = "web_key";// webtitle key-value
    public static final int WEBKEY_PARAMS = -1;//webView需要url参数
    /**
     * boolean类型 t=开启，f=关闭
     */
    public static final String WEB_BTN_RIGHT = "web_btn_right";//是否开启webview标题栏 右菜单按钮
    /**
     * 点击事件类型
     */
    public static final String WEB_BTN_RIGHT_CLICK_TYPE = "web_btn_right_click_type";
    /**
     * //关闭当前页
     */
    public static final int WEB_BTN_CLICK_TYPE_FINISH = 1;
    /**
     * //打开新activity，暂不支持webActivity
     */
    public static final int WEB_BTN_CLICK_TYPE_STARTACT = 2;
    public static final String WEB_BTN_RIGHT_TITLE = "web_btn_right_title";
    /**
     * 打开新页面的activityclass参数
     */
    public static final String WEB_BTN_RIGHT_CLASS_ACT = "web_btn_right_intent";
    /**
     * 给right_btn传递的bundle
     */
    public static final String WEB_BTN_RIGHT_BUNDLE = "web_btn_right_bundle";

}
