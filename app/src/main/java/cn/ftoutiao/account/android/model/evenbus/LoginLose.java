package cn.ftoutiao.account.android.model.evenbus;

public class LoginLose {
    public String url;
    public int code;
    public String msg;

    public LoginLose(String url,int code, String msg) {
        this.url = url;
        this.code = code;
        this.msg = msg;
    }
}