package cn.ftoutiao.account.android.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acmenxd.frame.basis.FrameActivity;
import com.acmenxd.frame.utils.ApkUtils;
import com.acmenxd.frame.utils.FileUtils;
import com.acmenxd.frame.utils.Utils;
import com.acmenxd.frame.utils.VersionUtils;
import com.acmenxd.retrofit.callback.HttpCallback;
import com.acmenxd.retrofit.exception.HttpException;
import com.acmenxd.sptool.SpManager;
import com.acmenxd.sptool.SpTool;
import com.acmenxd.toaster.Toaster;
import com.acmenxd.widget.NyDialog;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Calendar;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.AppConfig;
import cn.ftoutiao.account.android.base.BaseApplication;
import cn.ftoutiao.account.android.http.IDownloadRequest;
import cn.ftoutiao.account.android.model.ConfigBean;
import cn.ftoutiao.account.android.utils.LoadUtils;
import okhttp3.ResponseBody;


/**
 * Created by weichyang on 2017/9/detail6.
 */

public class DialogUtils {


    public static NyDialog dialog;

    /**
     * 样式1 模板
     *
     * @param activity
     */
    public static void normalDialog(Activity activity, DialogInterface.OnClickListener onClickListener) {
        NyDialog.Builder builder = new NyDialog.Builder(activity, 276,
                135);
        builder.setTitle("删除后无法恢复");
        builder.setTitleColor(activity.getResources().getColor(R.color.text_666666));//标题
        builder.setTextContent("确定要继续删除账本吗?");
        builder.setBottomDivideColor(R.color.text_666666);
        builder.setTitleDividerColor(R.color.text_666666); //分割线颜色， 需要修改
        builder.setTitleVisible(false);
        // 背景色
        builder.setVisibleAreaBackgroundResource(R.drawable.circle_bg); //互斥
        //builder.setVisibleAreaBackgroundColor(getResources().getColor(R.color.main_color_white));
        builder.setBottomViableAreaBackgroundColor(activity.getResources().getColor(R.color.main_color_white));
        builder.setPositiveBtnText("删除");
        builder.setBottomDivideColor(R.color.text_divide_line);
        builder.setNegativeButtonText("取消");
        //  builder.setBtnNegativeBackgroundResource(R.drawable.btn_com_buy_selected); //shap
        //  builder.setBtnPositiveBackgroundResource(R.drawable.btn_com_buy_selected);
        builder.setPositiveBtnTextColor(activity.getResources().getColor(R.color.text_red));
        builder.setNegativeButtonTextColor(activity.getResources().getColor(R.color.text_666666));
        NyDialog dialog = builder.create();
        builder.setPositiveButtonListener(onClickListener);
        dialog.show();
    }

    /**
     * 退出账本
     *
     * @param activity
     * @param onClickListener
     */
    public static void quiteNoteBookDialog(Context activity, DialogInterface.OnClickListener onClickListener) {
        NyDialog.Builder builder = new NyDialog.Builder(activity);
        builder.setTitle("退出后无法恢复");
        builder.setBottomDivideColor(R.color.text_divide_line);
        builder.setTitleColor(activity.getResources().getColor(R.color.text_666666));//标题
        builder.setTextContent("确定要继续退出账本吗?");
        builder.setBottomDivideColor(R.color.text_666666);
        builder.setTitleDividerColor(R.color.text_666666); //分割线颜色， 需要修改
        builder.setTitleVisible(false);
        // 背景色
        builder.setVisibleAreaBackgroundResource(R.drawable.circle_bg); //互斥
        //builder.setVisibleAreaBackgroundColor(getResources().getColor(R.color.main_color_white));
        builder.setBottomViableAreaBackgroundColor(activity.getResources().getColor(R.color.main_color_white));
        builder.setPositiveBtnText("退出");
        builder.setNegativeButtonText("取消");
        //  builder.setBtnNegativeBackgroundResource(R.drawable.btn_com_buy_selected); //shap
        //  builder.setBtnPositiveBackgroundResource(R.drawable.btn_com_buy_selected);
        builder.setPositiveBtnTextColor(activity.getResources().getColor(R.color.text_red));
        builder.setNegativeButtonTextColor(activity.getResources().getColor(R.color.text_666666));
        NyDialog dialog = builder.create();
        builder.setPositiveButtonListener(onClickListener);
        dialog.show();
    }



    public static NyDialog loadingDialog(Activity activity, View view) {
        NyDialog.Builder builder = new NyDialog.Builder(activity, 170, 170);
        builder.setTitleVisible(false);
        builder.setBottomViableAreaVisible(false);
        builder.setmBottomDividerVisiable(View.GONE).setTitleDividerColor(activity.getResources().getColor(R.color.main_color_white));
        builder.setCancelBottomViewVisible(false);
//      builder.setTextContent("中间弹窗").setContainerBackground(R.color.main_color_white);
        builder.setContainerBackgroundResource(R.drawable.circle_bg);
        builder.setContentView(view);
        ImageView imageView = view.findViewById(R.id.img_gif);
        Glide.with(activity).load(R.drawable.asy_loading).asGif().into(imageView);
        NyDialog dialog = builder.create();

        Window window = dialog.getWindow();

        // 设置显示动画
        //window.setWindowAnimations(R.style.SlipDialogAnimation);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        builder.create().show();
        return dialog;
    }


    public static void exit(Activity activity, DialogInterface.OnClickListener onClickListener) {
        NyDialog.Builder builder = new NyDialog.Builder(activity, 276,
                135);
        builder.setTitleVisible(false);
        builder.setTextContent("您确定退出登录?");
        builder.setBottomDivideColor(R.color.text_divide_line);
        builder.setTitleVisible(false);
        // 背景色
        builder.setVisibleAreaBackgroundResource(R.drawable.circle_bg); //互斥
        builder.setBottomViableAreaBackgroundColor(activity.getResources().getColor(R.color.main_color_white));
        builder.setPositiveBtnText("确定");
        builder.setNegativeButtonText("取消");
        //  builder.setBtnNegativeBackgroundResource(R.drawable.btn_com_buy_selected); //shap
        //  builder.setBtnPositiveBackgroundResource(R.drawable.btn_com_buy_selected);
        builder.setPositiveBtnTextColor(activity.getResources().getColor(R.color.text_red));
        dialog = builder.create();
        builder.setPositiveButtonListener(onClickListener);
        dialog.show();
    }






}
