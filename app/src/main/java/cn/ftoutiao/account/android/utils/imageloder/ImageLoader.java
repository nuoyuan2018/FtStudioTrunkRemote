package cn.ftoutiao.account.android.utils.imageloder;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.base.BaseApplication;

public final class ImageLoader {
    /**
     * 加载手机本地图片
     */
    public static void loadLocalPhoto(final ImageView imageView, final String photoPath) {
        if (imageView == null || TextUtils.isEmpty(photoPath)) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(new File(photoPath))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 加载app资源图片
     */
    public static void loadResourcePhoto(final ImageView imageView, final int photoId) {
        if (imageView == null) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(photoId)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    /**
     * 加载网络图片
     * @param imgView 显示图片的视图
     * @param imgUrl  图片在服务器的url地址
     */
    public static void loadImage(final ImageView imgView, final String imgUrl) {
        if (imgView == null || imgUrl == null) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(imgUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_header_icon)
                .into(imgView);
    }
    /**
     * 加载网络图片——圆角
     * @param imgView 显示图片的视图
     * @param imgUrl  图片在服务器的url地址
     */
    public static void loadImageCircle(final ImageView imgView, final String imgUrl, int round) {
        if (imgView == null || imgUrl == null) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(imgUrl)
                .transform(new GlideRoundTransform(BaseApplication.instance(),round))
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_header_icon)
                .into(imgView);
    }

    /**
     * 加载网络图片-默认图片为头像icon
     * @param imgView 显示图片的视图
     * @param imgUrl  图片在服务器的url地址
     */
    public static void loadHeaderImage(final ImageView imgView, final String imgUrl) {
        if (imgView == null || imgUrl == null) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(imgUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_header_icon)
                .error(R.drawable.default_header_icon)
                .into(imgView);
    }

    /**
     * 加载网络图片
     * @param imgView      显示图片的视图
     * @param defaultImgId 默认显示的图片ID
     * @param imgUrl       图片在服务器的url地址
     */
    public static void loadImage(final ImageView imgView, int defaultImgId, final String imgUrl) {
        if (imgView == null || imgUrl == null) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(imgUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(defaultImgId)
                .into(imgView);
    }

    /**
     * 加载网络图片
     * @param imgView 显示图片的视图
     * @param imgUrl  图片在服务器的url地址
     */
    public static void loadImageBanner(final ImageView imgView, final String imgUrl) {
        if (imgView == null || imgUrl == null) {
            return;
        }

        Glide.with(BaseApplication.instance())
                .load(imgUrl)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.default_header_icon)
                .into(imgView);
    }



}