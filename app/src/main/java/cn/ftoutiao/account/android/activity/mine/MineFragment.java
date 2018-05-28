package cn.ftoutiao.account.android.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.acmenxd.widget.NyDialog;
import com.bumptech.glide.Glide;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.Subscribe;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.activity.login.AboutActivity;
import cn.ftoutiao.account.android.activity.login.LoginActivity;
import cn.ftoutiao.account.android.activity.mine.view.CircleImageView;
import cn.ftoutiao.account.android.base.BaseFragment;
import cn.ftoutiao.account.android.constants.ConstanPool;
import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.model.evenbus.LoginLose;
import cn.ftoutiao.account.android.model.evenbus.LoginOut;
import cn.ftoutiao.account.android.utils.AccountManager;
import cn.ftoutiao.account.android.widget.ShareDialogFragment;

/**
 * Created by weichyang on 2018/2/8.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private CircleImageView circleImageView;
    private TextView tvUserName;
    private TextView tvShare;
    private UserEntity userEntity;
    private TextView tvExitDays;
    private NyDialog nyDialog;
    private MinePresenter minePresenter;


    private View lineHotSpace;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull Bundle savedInstanceState) {
        super.onCreateView(inflater, savedInstanceState);
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getView(R.id.tv_about).setOnClickListener(this);
        circleImageView = getView(R.id.iv_avatar);
        tvUserName = getView(R.id.tv_username);
        tvExitDays = getView(R.id.tv_exit_days);
        lineHotSpace = getView(R.id.line_hot_space);
        tvShare = getView(R.id.tv_share);

        initListener();
        initValue();
    }

    private void initListener() {
        lineHotSpace.setOnClickListener(this);
        tvShare.setOnClickListener(this);
    }


    private void initValue() {
        if (DataContans.isLogin) {
            undateUserInfo();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.tv_share:
                showSharePopWindow();
                break;

            case R.id.line_hot_space:
                if (!DataContans.isLogin) {
                    startActivityForResult(new Intent(mActivity, LoginActivity.class), ConstanPool.REQUEST_CODE);
                    return;
                }
                startActivity(AccountActivity.class);
                break;
            default:
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstanPool.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            undateUserInfo();
        }

    }

    private void undateUserInfo() {
        if (!DataContans.isLogin()) {
            return;
        }
        userEntity = DataContans.userEntity;
        Glide.with(mActivity)
                .load(userEntity.data.avatarurl).into(circleImageView);
//        Glide.with(mActivity)
//                .load(userEntity.data.avatarurl)
//                .placeholder(R.drawable.ic_avatar).
//                diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(circleImageView);
        tvUserName.setText(userEntity.data.nickname);




    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUiInfo();
    }

    private void refreshUiInfo() {
        if (!DataContans.isLogin) {
            circleImageView.setImageResource(R.drawable.ic_avatar);
            tvUserName.setText(mActivity.getString(R.string.immedite_login));
            tvExitDays.setText("");
        } else {
            Glide.with(mActivity)
                    .load(userEntity.data.avatarurl).into(circleImageView);
//            Glide.with(mActivity)
//                    .load(DataContans.userEntity.data.avatarurl)
//                    .placeholder(R.drawable.ic_avatar).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(circleImageView);
            tvUserName.setText(DataContans.userEntity.data.nickname);


        }
    }


    public void showSharePopWindow() {
        UMImage image = new UMImage(mActivity, R.drawable.icon_share);//资源文件
        ShareDialogFragment.shareMsg(getActivity()
                , mActivity.getString(R.string.app_name)
                , "海豚记账本-最简洁的随手记账软件，百万财务用户的共同选择"
                , "https://www.haitunwallet.com/"
                , image);

    }


    @Subscribe
    public void loginOut(LoginOut loginOut) {
        onResume();
    }


    @Subscribe
    public void loginLose(LoginLose loginLose) {
        AccountManager.getInstance().setAccount(mActivity, null);
        DataContans.setUserEntity(null);
        onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && isVisible()) {
            refreshUiInfo();
        }
    }

}

