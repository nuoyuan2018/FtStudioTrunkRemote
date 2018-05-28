package cn.ftoutiao.account.android.activity.mine;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acmenxd.toaster.Toaster;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.activity.login.LoginActivity;
import cn.ftoutiao.account.android.base.ToolbarBaseActivity;
import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.utils.StringUtil;
import cn.ftoutiao.account.android.widget.ClearEditText;

public class ModifyPwdActivity extends ToolbarBaseActivity implements ModifyContract.View, View.OnClickListener {

    private RelativeLayout rlInput;
    private ClearEditText etPhone;
    private LinearLayout llCaptcha;
    private ClearEditText etNewPwd;
    private ClearEditText etConfirmPwd;
    private Button btnRegister;

    private ModifyPresenter modifyPresent;

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_modify_pwd);
    }

    @Override
    protected void initView() {
        rlInput = (RelativeLayout) findViewById(R.id.rl_input);
        etPhone = (ClearEditText) findViewById(R.id.et_phone);
        llCaptcha = (LinearLayout) findViewById(R.id.ll_captcha);
        etNewPwd = (ClearEditText) findViewById(R.id.et_captcha);
        etConfirmPwd = (ClearEditText) findViewById(R.id.et_pwd);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initValue() {
        modifyPresent = new ModifyPresenter(this);
        addPresenters(modifyPresent);
        setToolbarBg(R.color.colorPrimary);
        setDefaultTitle(getString(R.string.uc_modify_pwd));
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnRegister.setOnClickListener(this);
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

    @Override
    public void requestModifyPassworSuccess() {
        finish();
    }

    @Override
    public void requestModifyPassworFailed(String msg) {
        Toaster.show(msg);
    }

//    SimpleTextWater simpleTextWater = new SimpleTextWater() {
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            super.onTextChanged(s, start, before, count);
//            String flag = s.toString();
//            topbar.setRightTextEnable(flag.length() > 0 ? true : false);
//        }
//    };


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_register:
                String oldPassword = etPhone.getText().toString();
                String newPassword = etNewPwd.getText().toString();
                String confirmPwd = etConfirmPwd.getText().toString().trim();
                if (!StringUtil.isPwd(oldPassword)) {
                    Toaster.show("原密码请输入包含数字和字母的6-16位密码");
                } else if (!StringUtil.isPwd(newPassword)) {
                    Toaster.show("新密码请输入包含数字和字母的6-16位密码");
                } else if (!StringUtil.isPwd(confirmPwd)) {
                    Toaster.show("确认密码请输入包含数字和字母的6-16位密码");
                } else if (!newPassword.equals(confirmPwd)) {
                    Toaster.show("新密码与确认密码不一致，请从新输入！");
                } else {
                    if (DataContans.isLogin()) {
                        modifyPresent.requestModifyPasswor(DataContans.userEntity.data.mobile, oldPassword, confirmPwd);
                    } else {
                        startActivity(LoginActivity.class);
                    }
                }
                break;

            default:
        }
    }
}
