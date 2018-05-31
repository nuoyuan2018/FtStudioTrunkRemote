package cn.ftoutiao.account.android.activity.budget;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseActivity;

/**
 * @author 预算金额编辑页面
 * Created on 2018/5/29 0029.
 */
public class BudgetItemMonthActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private ImageView back;
    private EditText edMoney;
    private RelativeLayout reMonth;
    private TextView tvMonth,tvSave;



    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        reMonth.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activiry_budget_item_month_edit);
    }


    @Override
    protected void initView() {
        title.setText("月份预算设置");
        title = getView(R.id.txt_mid);
        back = getView(R.id.txt_left);
        edMoney = getView(R.id.tv_money);
        tvMonth = getView(R.id.tv_month);
        reMonth = getView(R.id.re_month);
        tvSave= getView(R.id.tv_save);
        //点击的第几条
        String position= getIntent().getStringExtra("position");
        //取数据
//        edMoney.setText(money);
//        tvMonth.setText(month);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id. tv_save:
                //存储数据
                edMoney.getText().toString().trim();




                finish();
                break;
            default:
                break;
        }

    }

}
