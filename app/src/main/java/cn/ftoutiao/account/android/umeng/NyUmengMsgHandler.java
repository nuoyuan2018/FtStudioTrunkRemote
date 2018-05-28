package cn.ftoutiao.account.android.umeng;/**
 * Created by RoryHe on 16/12/5.
 */

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import cn.ftoutiao.account.android.R;

/**
 * @Author roryHe
 * @ClassName NyUmengMsgHandler
 * @CreateTIme 16/12/5 上午11:45
 */
public class NyUmengMsgHandler extends UmengMessageHandler {

    private Context context;

    public NyUmengMsgHandler(Context context){
        this.context = context;
    }
    /**
     * 自定义消息的回调方法
     * */
    @Override
    public void dealWithCustomMessage(final Context context, final UMessage msg) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                // 对自定义消息的处理方式，点击或者忽略
                boolean isClickOrDismissed = true;
                if (isClickOrDismissed) {
                    //自定义消息的点击统计
                    UTrack.getInstance(context).trackMsgClick(msg);
                } else {
                    //自定义消息的忽略统计
                    UTrack.getInstance(context).trackMsgDismissed(msg);
                }
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        });
    }
    /**
     * 自定义通知栏样式的回调方法
     * */
    @Override
    public Notification getNotification(Context context, UMessage msg) {
        switch (msg.builder_id) {
            case 2:
                Notification.Builder builder = new Notification.Builder(context);
                RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                myNotificationView.setImageViewResource(R.id.notification_large_icon, getSmallIconId(context, msg));
            //    myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                builder.setContent(myNotificationView)
                        .setSmallIcon(getSmallIconId(context, msg))
                        .setTicker(msg.ticker)
                        .setAutoCancel(true);
                return builder.getNotification();
             case 0:
             case 1:
                //默认为0，若填写的builder_id并不存在，也使用默认。
                return super.getNotification(context, msg);
             default:
                 return null;
        }
    }
}
