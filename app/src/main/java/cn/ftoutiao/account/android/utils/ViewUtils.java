package cn.ftoutiao.account.android.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acmenxd.logger.Logger;
import com.acmenxd.recyclerview.SSwipeRefreshLayout;
import com.bumptech.glide.Glide;

import java.util.Calendar;

import cn.ftoutiao.account.android.R;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/8/9 16:31
 * @detail something
 */
public class ViewUtils {

    /**
     * 设置单击间隔事件Listener
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        /**
         * 经过处理后的onClick事件
         */
        public abstract void onClick2(View v);

        private int mDelayTime = 1000;
        private long mLastTime = 0;

        public OnClickListener() {

        }

        public OnClickListener(int delayTime) {
            mDelayTime = delayTime;
        }

        @Override
        public void onClick(View v) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - mLastTime >= mDelayTime) {
                mLastTime = currentTime;
                this.onClick2(v);
            }
        }
    }

    /**
     * SSwipeRefreshLayout下拉刷新监听
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    /**
     * SSwipeRefreshLayout下拉刷新监听
     */
    public interface OnRefreshListenerIncludeStart {
        void onRefresh();

        void onStart(int distance);
    }

    /**
     * SSwipeRefreshLayout下拉刷新+上拉加载监听
     */
    public interface OnRefreshLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * 设置SSwipeRefreshLayout样式 -> White
     */
//    public static void setSSwipeRefreshLayoutStyle$White(Context context, SSwipeRefreshLayout srl, final OnRefreshListener refreshListener) {
//        setSSwipeRefreshLayoutStyle(context, srl, 0xff008de7, 0xffffffff, R.drawable.progress_indeterminate_white, R.drawable.down_arrow_white, refreshListener, null);
//    }

    /**
     * 设置SSwipeRefreshLayout样式 -> White
     */
//    public static void setSSwipeRefreshLayoutStyle$White(Context context, SSwipeRefreshLayout srl, final OnRefreshLoadMoreListener refreshLoadMoreListener) {
//        setSSwipeRefreshLayoutStyle(context, srl, 0xff008de7, 0xffffffff, R.drawable.progress_indeterminate_white, R.drawable.down_arrow_white, null, refreshLoadMoreListener);
//    }

    /**
     * 设置SSwipeRefreshLayout样式
     */
    public static void setSSwipeRefreshLayoutStyle(Context context, SSwipeRefreshLayout srl, final OnRefreshListener refreshListener) {
        setSSwipeRefreshLayoutStyle(context, srl, 0xfff6f6f6, 0xff000000, 0, R.drawable.asy_loading, refreshListener, null, null);
    }

    /**
     * 设置SSwipeRefreshLayout样式
     */
    public static void setSSwipeRefreshLayoutStyle(Context context, SSwipeRefreshLayout srl, final OnRefreshListenerIncludeStart refreshListener) {
        setSSwipeRefreshLayoutStyle(context, srl, 0xfff6f6f6, 0xff000000, 0, R.drawable.asy_loading, null, null, refreshListener);
    }

    /**
     * 设置SSwipeRefreshLayout样式
     */
    public static void setSSwipeRefreshLayoutStyle(Context context, SSwipeRefreshLayout srl, final OnRefreshLoadMoreListener refreshLoadMoreListener) {
        setSSwipeRefreshLayoutStyle(context, srl, 0xfff6f6f6, 0xff000000, R.drawable.progress_indeterminate_black, R.drawable.down_arrow_black, null, refreshLoadMoreListener, null);
    }

    /**
     * 设置SSwipeRefreshLayout样式
     */
    private static void setSSwipeRefreshLayoutStyle(Context context, SSwipeRefreshLayout srl, int bgColor, int textColor, int progressResId, int arrowResId, final OnRefreshListener refreshListener, final OnRefreshLoadMoreListener refreshLoadMoreListener, final OnRefreshListenerIncludeStart includeStart) {
        srl.setHeaderBackgroundColor(bgColor);
        srl.setTargetScrollWithLayout(true);
        if (refreshListener != null || refreshLoadMoreListener != null || includeStart != null) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_asyloading_sswiperefresh, null);
            final TextView tvContent = (TextView) view.findViewById(R.id.layout_header_tvContent);
            //final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.layout_header_ivLoading);
            final ImageView ivArrow = (ImageView) view.findViewById(R.id.layout_header_ivArrow);
            tvContent.setTextColor(textColor);
            //ivArrow.setImageResource(arrowResId);
            Glide.with(context).load(arrowResId).asGif().into(ivArrow);
            // progressBar.setIndeterminateDrawable(context.getResources().getDrawable(progressResId));
            srl.setHeaderView(view);
            srl.setOnRefreshListener(new SSwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    tvContent.setText("正在同步数据..");
                    //progressBar.setVisibility(View.VISIBLE);
                    // ivArrow.setVisibility(View.GONE);
                    if (refreshListener != null) {
                        refreshListener.onRefresh();
                    } else if (refreshLoadMoreListener != null) {
                        refreshLoadMoreListener.onRefresh();
                    } else if (includeStart != null) {
                        includeStart.onRefresh();
                    }
                }

                @Override
                public void onReach(boolean enable) {
                    tvContent.setText(enable ? "松开立即同步" : "下拉同步数据");
                    //progressBar.setVisibility(View.GONE);
                    //ivArrow.setVisibility(View.VISIBLE);
                    //ivArrow.setRotation(enable ? 180 : 0);
                }

                @Override
                public void onPullDistance(int distance) {
                    if (includeStart != null) {
                        includeStart.onStart(distance);
                    }
                }
            });
        }
        if (refreshLoadMoreListener != null) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_sswiperefresh, null);
            final TextView tvContent = (TextView) view.findViewById(R.id.layout_header_tvContent);
            // final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.layout_header_ivLoading);
            final ImageView ivArrow = (ImageView) view.findViewById(R.id.layout_header_ivArrow);
            tvContent.setTextColor(textColor);
            // ivArrow.setImageResource(arrowResId);
            //progressBar.setIndeterminateDrawable(context.getResources().getDrawable(progressResId));
            srl.setFooterView(view);
            srl.setOnLoadMoreListener(new SSwipeRefreshLayout.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    tvContent.setText("正在同步数据...");
                    // progressBar.setVisibility(View.VISIBLE);
                    // ivArrow.setVisibility(View.GONE);
                    refreshLoadMoreListener.onLoadMore();
                }

                @Override
                public void onReach(boolean enable) {
                    tvContent.setText(enable ? "松开立即同步" : "上拉可以加载");
                    // progressBar.setVisibility(View.GONE);
                    //ivArrow.setVisibility(View.VISIBLE);
                    //ivArrow.setRotation(enable ? 0 : 180);
                }

                @Override
                public void onPushDistance(int distance) {
                }
            });
        }
    }

}
