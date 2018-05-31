package cn.ftoutiao.account.android.activity.budget;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseActivity;
import cn.ftoutiao.account.android.widget.DialogUtils;

/**
 * @author 预算金额编辑页面
 * Created on 2018/5/29 0029.
 */
public class BudgetEditMonthActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private ImageView back;
    private TextView tvMoney;
    private TimePickerView pvCustomLunar;
    private RelativeLayout reMonth;
    private TextView tvMonth;
    private TextView tvSave;


    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        reMonth.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activiry_budget_month_edit);
    }


    @Override
    protected void initView() {
        String money=getIntent().getStringExtra("money");
        oncreateDate();
        title.setText("月份预算设置");
        title = getView(R.id.txt_mid);
        back = getView(R.id.txt_left);
        tvMoney = getView(R.id.tv_money);
        tvMonth = getView(R.id.tv_month);
        reMonth = getView(R.id.re_month);
        tvSave = getView(R.id.tv_save);
        tvMoney.setText(money);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.re_month:
                pvCustomLunar.show();
                break;
            case R.id. tv_save:
                //需要存的值
               tvMoney.getText().toString().trim();
                tvMonth.getText().toString().trim();
                break;
            default:
                break;
        }

    }

    private void oncreateDate() {
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvMonth.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });

                    }

                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.GRAY)
                .build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月 ");
        return format.format(date);
    }
}
