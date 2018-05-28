package cn.ftoutiao.account.android.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.acmenxd.logger.Logger;

import cn.ftoutiao.account.android.activity.MainActivity;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.model.evenbus.ChangeForward;

public class ActivityLefecycleImpl implements Application.ActivityLifecycleCallbacks {
    private int refCount = 0;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (refCount == 0) {
            Logger.d("in come  forward_group");
             sendMsgToScheduRefresh(activity, ConstanPool.FORWARD_GROUND);
        }
        refCount++;

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {


        refCount--;
        if (refCount == 0) {
            //确认进入后台
            //  sendBrocast(activity);
            Logger.d("in come  backgroup_group");
        }

        // sendMsgToScheduRefresh(activity, Constants.BACKGROUND);
    }

    /**
     * 定时刷新
     *
     * @param activity
     * @param type
     */
    private void sendMsgToScheduRefresh(Activity activity, int type) {
        if (activity instanceof MainActivity) {
            sendBrocastToMainActivon(activity, type);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

//    private void sendBrocast(Activity activity) {
//        Intent intent = new Intent();
//        intent.setAction("action.activityrunback.brocast");
//        activity.sendBroadcast(intent);
//    }
//
    private void sendBrocastToMainActivon(Activity activity, int type) {

        //判断登录态是否过期
        // TODO: 2018/5/4 判断登录态是否过期
        EventBusHelper.post(new ChangeForward());
//        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(activity);
//        Intent intent = new Intent();
//        intent.setAction(ConstanPool.SCHEDULE_REFRESH_ACTION);
//        lbm.sendBroadcast(intent);
    }

}