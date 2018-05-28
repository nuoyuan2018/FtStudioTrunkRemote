package cn.ftoutiao.account.android.utils;

import android.content.Context;
import android.text.TextUtils;

import com.acmenxd.frame.utils.FileUtils;

import cn.ftoutiao.account.android.base.BaseApplication;
import cn.ftoutiao.account.android.model.UserEntity;

public class AccountManager {

    private static final String FILE_NAME = "account.bin";

    private static AccountManager sInstance = null;

    private UserEntity mAccount;

    public static AccountManager getInstance() {
        if (sInstance == null) {
            sInstance = new AccountManager();
        }
        return sInstance;
    }

    private AccountManager() {
    }

    public UserEntity getAccount(Context context) {
        if (mAccount == null) {
            String data = FileUtils.readInternalFile(context, FILE_NAME);
            if (!TextUtils.isEmpty(data)) {
                mAccount = GsonHelper.getClassFromJsonString(data, UserEntity.class);
            }
        }
        return mAccount;
    }

    public void setAccount(Context context, UserEntity account) {
        mAccount = account;
        FileUtils.writeInternalFile(context, FILE_NAME, GsonHelper.getGsonInstance().toJson(account));
    }

    public boolean isLogin() {
        UserEntity account = getAccount(BaseApplication.instance().getApplicationContext());
        return account != null && !TextUtils.isEmpty(account.uid);
    }

    public String generateCookie() {
        UserEntity account = getAccount(BaseApplication.instance().getApplicationContext());
        String cookie = "loginname=" + account.data.nickname + ";" + "token=" + account.token;
        return cookie;
    }
}
