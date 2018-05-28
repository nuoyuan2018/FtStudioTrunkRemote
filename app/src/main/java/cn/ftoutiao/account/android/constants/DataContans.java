package cn.ftoutiao.account.android.constants;

import com.acmenxd.frame.basis.impl.IFrameStart;

import java.util.HashMap;
import java.util.Map;

import cn.ftoutiao.account.android.activity.login.LoginActivity;
import cn.ftoutiao.account.android.model.ListEntity;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.utils.StringUtil;

/**
 *
 */
public class DataContans {
    /**
     * 是否登录
     */
    public static boolean isLogin = false;

    public static UserEntity userEntity;
    private static Map<String, Integer> billTypeMap;

    public static boolean isLogin() {
        if (isLogin && userEntity != null) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否登录,如未登录->打开登录页面
     */
    public static boolean checkLogin(IFrameStart pContext) {
        if (!isLogin) {
            pContext.startActivity(LoginActivity.class);
        }
        return isLogin;
    }

    /**
     * 设置用户信息, 同时设置登录态
     */
    public static void setUserEntity(UserEntity pUserEntity) {
        if (pUserEntity == null) {
            DataContans.isLogin = false;
            userEntity = null;
        } else {
            DataContans.isLogin = true;
            userEntity = pUserEntity;
        }
    }

    /**
     * 获取账本类型通过账本id 可变
     *
     * @param aid
     * @return
     */
    public static int getAtypeByAid(String aid) {
        if (StringUtil.isEmpty(aid)) {
            throw new RuntimeException("bill id is empty ,please check aid is null");
        }
        if (billTypeMap == null) {
            billTypeMap = new HashMap<>();
            if (isLogin) {
                for (ListEntity entity : userEntity.data.list) {
                    billTypeMap.put(entity.aId, entity.aType);
                }

            }
        }
        return billTypeMap.get(aid);
    }

    public static String getBookNameByAid(String aid) {
        if (StringUtil.isEmpty(aid)) {
            throw new RuntimeException("bill id is empty ,please check aid is null");
        }
        if (billTypeMap == null) {
            if (isLogin) {
                for (ListEntity entity : userEntity.data.list) {
                    if (entity.aId.equals(aid)) {
                        return entity.aname;
                    }
                }

            }
        }
        return "";
    }


    /**
     * 是否是管理员
     *
     * @param uid
     * @return
     */
    public static boolean isOwenr(String uid) {

        if (userEntity != null && userEntity.uid.equals(uid)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAccountLogin() {
        if (isLogin() && !StringUtil.isEmpty(DataContans.userEntity.data.mobile)) {
            return true;
        } else {
            return false;
        }

    }

}
