package cn.ftoutiao.account.android.activity.budget;
import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseActivity;

/**
 * @author XuHuiHao
 * Created on 2018/5/29 0029.
 */
public class BudgetActivity extends BaseActivity {

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activiry_budget);
    }

    @Override
    protected void initView() {
        getView(R.id.bb_bottom_bar_appearance_id);
    }
}
