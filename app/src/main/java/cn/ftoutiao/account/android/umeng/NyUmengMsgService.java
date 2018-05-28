package cn.ftoutiao.account.android.umeng;/**
 * Created by RoryHe on 16/12/5.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.acmenxd.logger.Logger;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.activity.SplashActivity;
import cn.ftoutiao.account.android.constants.ConstanPool;

/**
 * @Author roryHe
 * @ClassName NyUmengMsgService
 * @CreateTIme 16/12/5 上午11:28
 */
public class NyUmengMsgService extends UmengMessageService {
    private static final String TAG = NyUmengMsgService.class.getName();

    private Notification notification;
    private RemoteViews myNotificationView;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        String msg = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        try {
            UMessage uMessage = new UMessage(new JSONObject(msg));
            Logger.d(TAG, "message=" + uMessage);
            Logger.d(TAG, "custom=" + uMessage.custom);
            Logger.d(TAG, "title=" + uMessage.title);
            Logger.d(TAG, "text=" + uMessage.text);
            switch (uMessage.builder_id) {
                case 0://自定义
                    notification = new Notification(
                            R.drawable.icon,//应用的图标
                            "",
                            System.currentTimeMillis());

                    myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                    myNotificationView.setTextViewText(R.id.notification_title, uMessage.title);
                    myNotificationView.setTextViewText(R.id.notification_text, uMessage.text);
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");//将毫秒级long值转换成日期格式
                    String format = dateformat.format(date);
                    myNotificationView.setTextViewText(R.id.notification_time, format);
                    myNotificationView.setImageViewResource(R.id.notification_large_icon, R.drawable.icon);
//TODO 分情况处理,根据uMessage。custom 判断是否需要Pending跳转启动页来启动activity
                    // if (!StringUtil.isEmpty(uMessage.custom)) {
                    // TODO: 2018/4/17
                    PendingIntent pendingIntent = dealCustomMsg(uMessage, context);
                    myNotificationView.setOnClickPendingIntent(R.id.notification_layout, pendingIntent);
                    // }
                    notification.defaults = Notification.DEFAULT_ALL;
                    notification.contentView = myNotificationView;
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(R.layout.notification_view, notification);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private PendingIntent dealCustomMsg(UMessage uMessage, Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.putExtra(ConstanPool.PUSH_MSG, uMessage.custom);
        intent.putExtra(ConstanPool.IS_PUSH_FROM, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
