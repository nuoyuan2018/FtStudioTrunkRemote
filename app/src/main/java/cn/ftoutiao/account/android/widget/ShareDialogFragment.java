package cn.ftoutiao.account.android.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.acmenxd.toaster.Toaster;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseApplication;
import cn.ftoutiao.account.android.model.ShareDataResponse;


/**
 * 分享对话框
 */

public class ShareDialogFragment extends DialogFragment implements View.OnClickListener {

    ShareDataResponse mShareDate;
    //    public ArrayList<SnsPlatform> platforms = new ArrayList<SnsPlatform>();
    private ProgressDialog dialog;
    private FragmentActivity mActivity;
    private Activity context;

    public static void shareMsg(FragmentActivity activity, String title, String content, String contentUrl, String contentImg) {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        ShareDataResponse shardate = new ShareDataResponse();
        shardate.setContent_url(contentUrl);
        shardate.setTitle(title);
        shardate.setContent(content);
        shardate.setContent_img(contentImg);
        shareDialogFragment.setShareDate(shardate);//分享数据填充
        shareDialogFragment.show(activity.getSupportFragmentManager(), "sharefragmentdialog");
    }
    public static void shareMsg(FragmentActivity activity, String title, String content, String contentUrl, UMImage umImage) {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        ShareDataResponse shardate = new ShareDataResponse();
        shardate.setContent_url(contentUrl);
        shardate.setTitle(title);
        shardate.setContent(content);
        shardate.setImage(umImage);
        shareDialogFragment.setShareDate(shardate);//分享数据填充
        shareDialogFragment.show(activity.getSupportFragmentManager(), "sharefragmentdialog");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.fragment_share_dialog_layout, ((ViewGroup) window.findViewById(android.R.id.content)), false);//需要用android.R.id.content这个view
        initView(view);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setGravity(Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.SlipDialogAnimation;
        window.setLayout(-1, -2);//这2行,和上面的一样,注意顺序就行;
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.shareDailog_qqf).setOnClickListener(this);
        view.findViewById(R.id.shareDailog_qqk).setOnClickListener(this);
        view.findViewById(R.id.shareDailog_wxf).setOnClickListener(this);
        view.findViewById(R.id.shareDailog_wxq).setOnClickListener(this);
        view.findViewById(R.id.shareDailog_sina).setOnClickListener(this);
        view.findViewById(R.id.shareDailog_cancle).setOnClickListener(this);
//        view.findViewById(R.id.shareDailog_doub).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareDailog_wxq:
                if (!hasMobileNet()) return;
                if (UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN)) {
                    share(SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    Toaster.show(BaseApplication.instance().getString(R.string.appuninstore));
                }
                break;
            case R.id.shareDailog_wxf:
                if (UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.WEIXIN)) {
                    share(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform);
                } else {
                    Toaster.show(BaseApplication.instance().getString(R.string.appuninstore));
                }
                break;
            case R.id.shareDailog_sina:
                share(SHARE_MEDIA.SINA.toSnsPlatform().mPlatform);
                break;
            case R.id.shareDailog_qqf:
                share(SHARE_MEDIA.QQ.toSnsPlatform().mPlatform);

                break;
            case R.id.shareDailog_qqk:
                share(SHARE_MEDIA.QZONE.toSnsPlatform().mPlatform);

                break;
//            case R.id.shareDailog_doub:
//                share(SHARE_MEDIA.DOUBAN.toSnsPlatform().mPlatform);
//                break;
            default:
                break;
        }
        dismiss();
    }

    public void share(SHARE_MEDIA id) {
        if (!hasMobileNet()) return;
        if (null == mShareDate) {
            Toaster.show(BaseApplication.instance().getString(R.string.share_error));
            return;
        }
        dialog = new ProgressDialog(context);
        ShareAction shareAction = new ShareAction(context);
        UMWeb web;
        if (!TextUtils.isEmpty(mShareDate.getContent_url())) {
            web = new UMWeb(mShareDate.getContent_url());
        } else {
            web = new UMWeb(" ");
        }
        if (!TextUtils.isEmpty(mShareDate.title)) {
            web.setTitle(mShareDate.title);
        }
        if (!TextUtils.isEmpty(mShareDate.content)) {
            web.setDescription(mShareDate.content);
        } else {
            web.setDescription("");
        }
        if (mShareDate.image != null) {
            // TODO: 2018/5/16  针对web分享
            web.setThumb(mShareDate.image);
        }
        if (!TextUtils.isEmpty(mShareDate.getContent_img()) && id != SHARE_MEDIA.SMS) {
            UMImage thumb = new UMImage(context, mShareDate.getContent_img());
            web.setThumb(thumb);
        }
        shareAction.withMedia(web).setPlatform(id).setCallback(new UMShareListener() {

            @Override
            public void onStart(SHARE_MEDIA share_media) {
                SocializeUtils.safeShowDialog(dialog);
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                SocializeUtils.safeCloseDialog(dialog);
                //Toaster.show(BaseApplication.instance().getString(R.string.share_result));
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                SocializeUtils.safeCloseDialog(dialog);
                Toaster.show(BaseApplication.instance().getString(R.string.share_error));
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                SocializeUtils.safeCloseDialog(dialog);
                Toaster.show(BaseApplication.instance().getString(R.string.share_cancle));
            }
        }).share();
    }

    public void setShareDate(ShareDataResponse shareDate) {
        mShareDate = shareDate;
    }

    /**
     * 判断是否有网络
     */
    public static boolean hasMobileNet() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.instance().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();

            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        Toaster.show(BaseApplication.instance().getString(R.string.net_error));
        return false;
    }


    public void shareSingleMsgToWx(FragmentActivity activity, String title, String content, String contentUrl, String contentImg) {
        ShareDataResponse shardate = new ShareDataResponse();
        context = activity;
        shardate.setContent_url(contentUrl);
        shardate.setTitle(title);
        shardate.setContent(content);
        shardate.setContent_img(contentImg);
        setShareDate(shardate);//分享数据填充
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            share(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform);
        } else {
            Toaster.show(BaseApplication.instance().getString(R.string.appuninstore));
        }
    }
    public void shareSingleMsgToWx(FragmentActivity activity, String title, String content, String contentUrl,UMImage image) {
        ShareDataResponse shardate = new ShareDataResponse();
        context = activity;
        shardate.setContent_url(contentUrl);
        shardate.setTitle(title);
        shardate.setImage(image);
        shardate.setContent(content);
        shardate.setContent_img("");
        setShareDate(shardate);//分享数据填充
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            share(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform);
        } else {
            Toaster.show(BaseApplication.instance().getString(R.string.appuninstore));
        }
    }
}
