//package cn.ftoutiao.account.android.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.support.v4.view.ViewPager;
//
//import cn.ftoutiao.account.android.base.BaseActivity;
//
//
///**
// * @author  Tom
// */
//public class GuideActivity extends BaseActivity {
//    private MainBroadcast mainBroadcast;
//    @Override
//    public Activity initContext() {
//        return this;
//    }
//
//    @Override
//    public int getPageType() {
//        return 0;
//    }
//
//    @Override
//    public void init() {
//        initBroadCast(context);
//    }
//    @Override
//    public void initLayout() {
//        setContentView(R.layout.guild_layout);
//        ViewPager guide_vp = (ViewPager) findViewById(R.id.guide_vp);
//        GuidePagerAdapter guidePagerAdapter = new GuidePagerAdapter(preferenceUtils,this);
//        guide_vp.setAdapter(guidePagerAdapter);
//    }
//
//    @Override
//    public void initView() {
//        Intent intent = new Intent(AppActions.ACTION_USER_DATA_NEWGUIDEACTIVE);
//        context.sendBroadcast(intent);
//    }
//
//    @Override
//    public void initListener() {
//
//    }
//
//    @Override
//    public void initValue() {
//
//    }
//
//    @Override
//    public void noNetView() {
//
//    }
//
//    @Override
//    public void haveNetView() {
//
//    }
//    @Override
//    public void onCallbackFromThread(String resultJson, int resultCode) {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unRegisterReceiver(context);
//    }
//    private void initBroadCast(Context context) {
//        mainBroadcast = new MainBroadcast();
//        IntentFilter filter = new IntentFilter(AppActions.ACTION_USER_DATA_NEWGUIDEACTIVE);
//        context.registerReceiver(mainBroadcast, filter);
//    }
//
//    private void unRegisterReceiver(BaseActivity context) {
//        if (mainBroadcast != null)
//            context.unregisterReceiver(mainBroadcast);
//    }
//}
