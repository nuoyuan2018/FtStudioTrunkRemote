package cn.ftoutiao.account.android.model;

import com.umeng.socialize.media.UMImage;

import java.io.Serializable;


/**
 * Created by liukui on 2017/05/11.
 */
public class ShareDataResponse implements Serializable {
    public String title ;
    public String content;
    public String content_img;
    public String content_url;
    public UMImage image;

    public ShareDataResponse() {
    }

    public ShareDataResponse(String title, String content, String content_img, String content_url) {
        this.title = title;
        this.content = content;
        this.content_img = content_img;
        this.content_url = content_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public UMImage getImage() {
        return image;
    }

    public void setImage(UMImage image) {
        this.image = image;
    }

    public String getContent_img() {
        return content_img;
    }

    public void setContent_img(String content_img) {
        this.content_img = content_img;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }


}