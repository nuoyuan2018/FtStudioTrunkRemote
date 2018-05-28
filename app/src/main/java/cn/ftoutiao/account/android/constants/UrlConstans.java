package cn.ftoutiao.account.android.constants;

import com.acmenxd.logger.Logger;
import com.acmenxd.sptool.SpManager;
import com.acmenxd.sptool.SpTool;

import cn.ftoutiao.account.android.base.AppConfig;


/**
 * Created by weichyang on 2017/6/21.
 */

public class UrlConstans {
    public static final String RB_ERRSTATE_3084 = "3084";
    public static final String RB_ERRSTATE_500702 = "500702";
    public static final String SHAKEY = "2q8giy8bau035fyiq0dfxsl6ok4pizn8xv0vcnou0si0lhr190xj0iu9u3j3mwm1";
    public static final String EARNINGS = "earnings";
    public static final String ID_PID = "id_pid";
    public static final int BUYPAGE_TYPE = 600;//跳转到webview页面需要处理的内容

    // 请求地址配置 -2:表示动态配置(点击版本号3次,弹出窗口选择配置,默认为正式版) -1:正式版  0->预发布  1->测试1  2->测试2
    public static final byte URL_Type = 2;
    public static final byte URL_Type_Default = 2; // 当URL_Type = -2, 设置默认的取值
    public static final String SALT = "salt";
    public static String BASE_URL = "";

    public static void initURL() {
        int type = URL_Type;
        String typeStr = "";
        if (!AppConfig.DEBUG) {
            type = -1;
        } else if (AppConfig.DEBUG && type == -2) {
            SpTool spTool = SpManager.getCommonSp(AppConfig.config.SP_Config);
            type = spTool.getInt("URL_Type", URL_Type_Default);
        }
        switch (type) {
            case -1:
                typeStr = "正式环境";
                BASE_URL = "https://account.ftoutiao.cn/";
                break;
            case 0:
                typeStr = "预发布环境";
                BASE_URL = "https://account.ftoutiao.cn/";
                break;
            case 1:
                typeStr = "测试1";
                BASE_URL = "https://account.ftoutiao.cn/";
                break;
            case 2:
                typeStr = "测试2";
                BASE_URL = "https://account.ftoutiao.cn/";
                break;
        }
        Logger.e("当前url环境:" + typeStr);
    }


    public static final String SCHEDULE_REFRESH_ACTION = "action.scheduleback.brocast";
    public static final int BACKGROUND = 1; //后台
    public static final int FORWARD_GROUND = 2;//前台
    public static final String REFRESH_TYPE = "type";


    public static final String REQUEST_SALT = "app/login/initSalt"; //密码盐
    public static final String REQUEST_LOGIN = "app/login/onLogin";
    public static final String REQUEST_AUTHLOGIN = "app/login/authLogin";
    public static final String REQUEST_VERIFYSMS = "app/login/verifySms";
    public static final String REQUEST_ONSIGN = "app/login/onSign";
    public static final String REQUEST_LOGINOUT = "app/login/logout";//exit

    //    public static final String REQUEST_ONSIGN = "app/login/onSign";
    //账本相关
    public static final String REQUEST_ADDBOOKCATEGORY = "app/account/addBookCategory";

    public static final String REQUEST_BOOKCATEGORY = "app/account/bookCategory"; //记账类别
    public static final String REQUEST_UPDATEBOOKCATEGORY = "app/account/updateBookCategory";
    public static final String REQUEST_SORTBOOKCATEGORY = "app/account/sortBookCategory"; //账本定制
    public static final String REQUEST_DELETECATEGORY = "app/account/deleteItem"; //删除

    public static final String REQUEST_ADDBOOK = "app/account/addBook";
    public static final String REQUEST_UPDATEBOOK = "app/account/updateBook";
    public static final String REQUEST_DELETEBOOK = "app/account/deleteBook";
    public static final String REQUEST_ADDITEM = "app/account/addItem";
    public static final String REQUEST_UPDATA_ITEM = "app/account/updateItem";

    //首页
    public static final String REQUEST_ITEMLIST = "app/account/itemList"; //账本列表


    //用户相关

    public static final String REQUEST_USERINFO = "app/user/userInfo"; //用户信息接口
    public static final String REQUEST_MNICKNAME = "app/user/mNickname";//修改昵称
    public static final String REQUEST_MPHOTO = "app/user/mPhoto";//修改头像
    public static final String REQUEST_UPDATEPWD = "app/user/updatePwd"; //修改密码


    /**
     * 重置密码
     */
    public static final String REQUEST_RESETPWD = "app/login/resetPwd";

    public static final String REQUEST_ONUPLOAD = "pic/onUpload"; //上传图片


    public static final String REQUEST_UPGRADE = "/app/upgrade"; //检查更新
    public static final String REQUEST_CONFIG = "app/config"; //检查配置


    public static final String REQUEST_MEMBERS = "app/account/members"; //账本成员
    public static final String REQUEST_SHAREBOOK = "app/account/shareBook"; //分享账本

    public static final String REQUEST_PERMISSION = "app/account/permission"; //修改权限

    public static final String REQUEST_QUITBOOK = "app/account/quitBook"; //退出　 非账本管理员


}
