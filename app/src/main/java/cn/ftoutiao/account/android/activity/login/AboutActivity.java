package cn.ftoutiao.account.android.activity.login;

import android.app.Notification;
import android.widget.RemoteViews;
import android.widget.TextView;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.AppConfig;
import cn.ftoutiao.account.android.base.ToolbarBaseActivity;

/**
 * Author: weichyang
 * Date:   2018/4/17
 * Description: 关于我们
 */
public class AboutActivity extends ToolbarBaseActivity  {

    private TextView tv_version;
    private RemoteViews myNotificationView;
    private Notification notification;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initView() {
        tv_version=getView(R.id.tv_version);
    }

    @Override
    protected void initValue() {
        super.initValue();
        setToolbarBg(R.color.colorPrimary);
        setDefaultTitle(getString(R.string.text_about));
        tv_version.setText(AppConfig.VERSION_NAME);
//        tv_version.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                testNotify();
//            }
//        });
    }

    @Override
    public int getActionBarId() {
        return R.id.toolbar;
    }

    @Override
    public int getActionBarTitleId() {
        return R.id.actionbar_title;
    }

    @Override
    public int getActionBarContainerId() {
        return 0;
    }

    @Override
    public int getActionBarIconId() {
        return 0;
    }

    @Override
    public int getActionBarActionId() {
        return 0;
    }


//    public void testNotify(){
//        notification = new Notification(
//                R.drawable.icon,//应用的图标
//                "",
//                System.currentTimeMillis());
//
//        myNotificationView = new RemoteViews(AppConfig.PKG_NAME, R.layout.notification_view);
//        myNotificationView.setTextViewText(R.id.notification_title,"test");
//        myNotificationView.setTextViewText(R.id.notification_text,"test");
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");//将毫秒级long值转换成日期格式
//        String format = dateformat.format(date);
//        myNotificationView.setTextViewText(R.id.notification_time, format);
//        myNotificationView.setImageViewResource(R.id.notification_large_icon, R.drawable.icon);
////TODO 分情况处理,根据uMessage。custom 判断是否需要Pending跳转启动页来启动activity
//        // if (!StringUtil.isEmpty(uMessage.custom)) {
//        // TODO: 2018/4/17
//        PendingIntent pendingIntent = null;
//        try {
//            pendingIntent = dealCustomMsg( this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        myNotificationView.setOnClickPendingIntent(R.id.notification_large_icon, pendingIntent);
//        // }
//        notification.contentView = myNotificationView;
//        notification.defaults=Notification.DEFAULT_ALL;
//        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(R.layout.notification_view, notification);
//    }
//
//    private PendingIntent dealCustomMsg( Context context) {
//        Intent intent = new Intent(context, SplashActivity.class);
//        intent.putExtra(ConstanPool.IS_PUSH_FROM, true);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        return pendingIntent;
//    }
}
