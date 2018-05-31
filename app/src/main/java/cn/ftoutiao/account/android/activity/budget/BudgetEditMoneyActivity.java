package cn.ftoutiao.account.android.activity.budget;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseActivity;

/**
 * @author 预算金额编辑页面
 * Created on 2018/5/29 0029.
 */
public class BudgetEditMoneyActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private ImageView back;
    private EditText edMoney;
    private TextView tvSave;

    @Override
    protected void initListener() {
        back.setOnClickListener(this);

    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activiry_budget_money_edit);
    }

    @Override
    protected void initView() {
        title = getView(R.id.txt_mid);
        back = getView(R.id.txt_left);
        edMoney = getView(R.id.ed_money);
        tvSave = getView(R.id.tv_save);
        title.setText("预算金额");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                finish();
                break;
            case R.id.tv_save:
                String money = edMoney.getText().toString().trim();
                if ("".equals(money)) {
                    Toast toast = Toast.makeText(this, "金额不能为空", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("money", money);
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
            default:
                break;
        }

    }
}
