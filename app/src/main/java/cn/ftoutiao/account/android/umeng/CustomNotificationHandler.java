
package cn.ftoutiao.account.android.umeng;

import android.content.Context;
import android.content.Intent;

import com.acmenxd.logger.Logger;
import com.acmenxd.sptool.SpManager;
import com.acmenxd.sptool.SpTool;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

import cn.ftoutiao.account.android.activity.MainActivity;
import cn.ftoutiao.account.android.base.AppConfig;
import cn.ftoutiao.account.android.constants.ConstanPool;

/**
 * 友盟消息推送的自定义处理类
 * 点击事件处理
 */
public class CustomNotificationHandler extends UmengNotificationClickHandler {

    private static final String TAG = CustomNotificationHandler.class.getName();

    @Override
    public void dismissNotification(Context context, UMessage msg) {
        super.dismissNotification(context, msg);
    }

    @Override
    public void launchApp(Context context, UMessage msg) {
        super.launchApp(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "launch_app");
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        super.openActivity(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_activity");
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        super.openUrl(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "open_url");
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        super.dealWithCustomAction(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "custom_action");
        dealCustomMsg(context, msg);
    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        super.autoUpdate(context, msg);
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "auto_update");
    }

    private void dealCustomMsg(final Context context, UMessage msg) {
        Logger.e("自定义消息处理类消息msg:" + msg.custom);
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        SpTool spTool = SpManager.getCommonSp(AppConfig.config.SP_Config);
        spTool.putString(ConstanPool.PUSH_MSG, msg.custom);
        spTool.putBoolean(ConstanPool.IS_PUSH_FROM, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

