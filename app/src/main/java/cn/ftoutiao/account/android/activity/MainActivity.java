package cn.ftoutiao.account.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.acmenxd.toaster.Toaster;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.Subscribe;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.activity.bill.BillFragment;
import cn.ftoutiao.account.android.activity.calendar.CalendarFragment;
import cn.ftoutiao.account.android.activity.chart.ChartFragment;
import cn.ftoutiao.account.android.activity.login.LoginActivity;
import cn.ftoutiao.account.android.activity.mine.MineFragment;
import cn.ftoutiao.account.android.base.BaseActivity;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.model.db.ListItemBO;
import cn.ftoutiao.account.android.model.evenbus.SelectAidAction;
import cn.ftoutiao.account.android.widget.bottombar.BottomBar;
import cn.ftoutiao.account.android.widget.bottombar.OnTabReselectListener;
import cn.ftoutiao.account.android.widget.bottombar.OnTabSelectListener;

public class MainActivity extends BaseActivity implements OnTabSelectListener, OnTabReselectListener {

    private FrameLayout frameLayout;
    private BottomBar bottomBar;

    public static final int TAG_Bill = 0; //账单
    public static final int TAG_CHART = 1;//图标
    public static final int TAG_CALENDAR = 2;//日历
    public static final int TAG_MINE = 3;//我的
    private FragmentManager fm;
    private Fragment showFg = null;
    private int showPage = TAG_Bill;
    BillFragment billFragment = null;
    ChartFragment chartFragment = null;
    CalendarFragment calendarFragment = null;
    MineFragment mineFragment = null;
    private double keyTime;
    private String currentSelectedBillId = "0"; //全局账单id


    @Override
    protected void initLayout() {
        fm = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initListener() {
        bottomBar.setOnTabSelectListener(this);
        bottomBar.setOnTabReselectListener(this);
    }

    @Override
    protected void initView() {
        frameLayout = getView(R.id.main_container);
        bottomBar = getView(R.id.bottom_bar);

    }


    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {

            case R.id.tab_mine:
                if (mineFragment == null)
                    mineFragment = new MineFragment();
                showFragment(mineFragment, TAG_MINE);
                break;
            case R.id.tab_record: //center recorder
                gotoRecorder();
                break;
            default:
                break;
        }
    }

    private boolean isPerFormClick = false;

    private void showFragment(Fragment f, int tagPage) {
        //  2016/7/21  偶发会出现切换选项卡。fragment内容不变 问题。---->fragment显示 隐藏混乱导致
        FragmentTransaction ft = fm.beginTransaction();
        if (!f.isAdded() && null == getSupportFragmentManager().findFragmentByTag("TAG" + tagPage)
                ) {
            if (showFg != null) {
                ft.hide(showFg).add(R.id.main_container, f, "TAG" + tagPage);
            } else {
                ft.add(R.id.main_container, f, "TAG" + tagPage);
            }
        } else { //已经加载进容器里去了....
            if (showFg != null) {
                ft.hide(showFg).show(f);
            } else {
                ft.show(f);
            }
        }
        showFg = f;
        showPage = tagPage;
        if (!isFinishing()) {
            ft.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        }

        if (tagPage != TAG_MINE) getIntent().putExtra("page", tagPage);

    }

    @Override
    public void onTabReSelected(int tabId) {

        switch (tabId) {
            case R.id.tab_record:
                gotoRecorder();
                break;
        }
    }

    private void gotoRecorder() {
        if (!DataContans.isLogin()) {
            startActivity(LoginActivity.class);
            return;
        }
        Bundle bundle = new Bundle();
        ListItemBO listItemBO = new ListItemBO();
        listItemBO.aId = currentSelectedBillId;
        listItemBO.cType = 2;
        bundle.putSerializable(ConstanPool.BUNDLE, listItemBO);

        overridePendingTransition(R.anim.slide_in_forom_bottom, R.anim.hold);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getIntent().putExtra("page", showPage);
    }

    @Override
    protected void initValue() {
        super.initValue();
        showPage = getIntent().getIntExtra("page", TAG_Bill);
        switchFg(showPage);
    }

    private void switchFg(int page) {
        switch (page) {
            case TAG_Bill:
                findViewById(R.id.tab_bill).performClick();
                break;

            case TAG_CHART:
                findViewById(R.id.tab_chart).performClick();
                break;

            case TAG_CALENDAR:
                findViewById(R.id.tab_calender).performClick();
                break;

            case TAG_MINE:
                findViewById(R.id.tab_mine).performClick();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - keyTime) > 2000) {
                keyTime = System.currentTimeMillis();
                Toaster.show("双击返回键退出");
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    //定制bill id
    public interface BillSelectListener {
        void selectedBillAid(String aid);
    }


    BillSelectListener billSelectListener = new BillSelectListener() {
        @Override
        public void selectedBillAid(String aid) {
            currentSelectedBillId = aid;
//            DateTime nowTime = new DateTime();
//            int month = nowTime.getMonthOfYear() - 1;
//            ListItemDB.getInstance().getMonthCtypeCount(aid, 2, String.valueOf(month));
            //evenBus
            //  EventBusHelper.post(new SelectAidAction(aid));
        }
    };

    @Subscribe
    public void selectedBillAid(SelectAidAction selectAidAction) {
        currentSelectedBillId = selectAidAction.aid;
    }




}
