package cn.ftoutiao.account.android.activity.budget;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseActivity;


/**
 * @author 预算主页面
 * Created on 2018/5/29 0029.
 */
public class BudgetActivity extends BaseActivity implements View.OnClickListener {
    private RadioButton rbswitch;
    private TextView title;
    private ImageView back;
    private ListView listView;
    private TextView tvmoney;
    private RelativeLayout rlmoney;
    private RelativeLayout rlmonth;
    private int MONEY_EDIT = 0;
    private int MONTH_EDIT = 1;
    private View headview;
    private String money;
    private BudgetBean budgetBean;

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        rlmoney.setOnClickListener(this);
        rlmonth.setOnClickListener(this);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activiry_budget);
    }

    @Override
    protected void initView() {
        title = getView(R.id.txt_mid);
        back = getView(R.id.txt_left);
        listView = getView(R.id.lv_month);
        headview = View.inflate(this, R.layout.activiry_budget_listview_head, null);
        listView.addHeaderView(headview);
//        DateAdapter dateAdapter=new DateAdapter(list.size(),list,this);
//        listView.setAdapter(dateAdapter);
        rbswitch = headview.findViewById(R.id.rb_switch);
        tvmoney = headview.findViewById(R.id.tv_money);
        rlmoney = headview.findViewById(R.id.rl_money);
        rlmonth = headview.findViewById(R.id.rl_month);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(BudgetActivity.this,BudgetItemMonthActivity.class);
                intent.putExtra("position",i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.rl_money:
                Intent intent = new Intent(this, BudgetEditMoneyActivity.class);
                startActivityForResult(intent, MONEY_EDIT);
                break;
            case R.id.rl_month:
                Intent intent1 = new Intent(this, BudgetEditMonthActivity.class);
                intent1.putExtra("money",money);
                startActivityForResult(intent1, MONTH_EDIT);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MONEY_EDIT && resultCode == RESULT_OK) {
            money = data.getStringExtra("money");
            tvmoney.setText(money);
        }
        if (requestCode == MONTH_EDIT && resultCode == RESULT_OK) {
            budgetBean = (BudgetBean) data.getSerializableExtra("list");
        }
    }
}
