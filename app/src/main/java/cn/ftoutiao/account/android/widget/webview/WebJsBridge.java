package cn.ftoutiao.account.android.widget.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.webkit.JavascriptInterface;

import com.acmenxd.logger.Logger;
import com.acmenxd.toaster.Toaster;
import com.google.gson.JsonObject;

import java.io.Serializable;

import cn.ftoutiao.account.android.activity.MainActivity;
import cn.ftoutiao.account.android.activity.login.LoginActivity;
import cn.ftoutiao.account.android.utils.StringUtil;
import cn.ftoutiao.account.android.widget.webview.bean.WebBankBean;
import cn.ftoutiao.account.android.widget.webview.bean.WebSendMessageBean;
import cn.ftoutiao.account.android.widget.webview.parser.JSMsgParser;
import cn.ftoutiao.account.android.widget.webview.parser.MsgParser;

/**
 * Created by rory on 2016/10/11.
 */

public class WebJsBridge implements Serializable {
    private static final String TAG = WebJsBridge.class.getSimpleName();

    private Activity mActivity;
    private Handler mhHandler;
    private Intent dataIntent;

    public WebJsBridge(Context context, Handler handler, Intent dataIntent) {
        this.mActivity = (Activity) context;
        this.mhHandler = handler;
        this.dataIntent = dataIntent;
        if (this.dataIntent != null)
            Logger.d(TAG, "this js bridge get intent data success");
        else
            Logger.d(TAG, "this js bridge get intent data null or fail");
    }

    @JavascriptInterface
    public void bridgeSendMessage(String jsonStr) {
        final String json = jsonStr;
        mhHandler.post(new Runnable() {
            @Override
            public void run() {
                Logger.d("js调用Android", json);
                parserMsg(json);
            }
        });
    }

    private void parserMsg(String json) {
        MsgParser parser = new MsgParser(new JSMsgParser() {
            @Override
            public void certificate_result(JsonObject param) {
                certificate(param);
            }

            @Override
            public void tologin(JsonObject param) {
                toApplogin(param);
            }

            @Override
            public void sendurl(JsonObject param, String uniqueId) {
                // sendAppurl(param, uniqueId);
            }

            @Override
            public void share(JsonObject param, String uniqueId) {
                wxshare(param, uniqueId);

            }

            @Override
            public void showShareBtn(JsonObject param, String uniqueId) {
              //  showWxShareBtn(param, uniqueId);
            }

            @Override
            public void toFinancial(JsonObject param) {
               // toHomeFinancial(param);
            }

            @Override
            public void toHome(JsonObject param) {
               // toHomepage(param);
            }

            @Override
            public void toAutherActivity(JsonObject param) {
                goAutherActivity(param);
            }

            @Override
            public void toTel(JsonObject param) {
               // toTelephone(param);
            }

            @Override
            public void toMessage(JsonObject param) {
               // toSendMessage(param);
            }

            @Override
            public void toNativerror(JsonObject param) {
               // toNativefromerror(param);
            }

            @Override
            public void toBrowser(JsonObject param) {
               // toBrowserView(param);
            }


        });
        parser.parserMsg(json);
    }

    /**
     * 跳转浏览器
     *
     * @param param
     */
    private void toBrowserView(JsonObject param) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String url = "";
        if (param.has("url")) url = param.get("url").getAsString();
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        mActivity.startActivity(intent);
    }

    private void toNativefromerror(JsonObject param) {
        mActivity.finish();
    }

    /**
     * 调用发送短信功能
     *
     * @param param
     */
    private void toSendMessage(JsonObject param) {
        Logger.d("js调用Android", "toSendMessage");
        WebSendMessageBean bean = new WebSendMessageBean();
        if (param.has("msg")) bean.msg = param.get("msg").getAsString();
        if (param.has("tel")) bean.tel = param.get("tel").getAsString();
        Logger.d("msg", bean.msg);
        Logger.d("tel", bean.tel);
        // if (!StringUtil.isEmpty(bean.msg) && !StringUtil.isEmpty(bean.tel)) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + bean.tel));
        intent.putExtra("sms_body", bean.msg);
        mActivity.startActivity(intent);
        //   }
    }

    /**
     * 调用拨打电话功能
     *
     * @param param
     */
    private void toTelephone(JsonObject param) {
//        WebPhoneBean bean = new WebPhoneBean();
//        if (param.has("tel")) bean.tel = param.get("tel").getAsString();
//        if (!StringUtil.isEmpty(bean.tel)) {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:" + bean.tel));
//
//            mActivity.startActivity(intent);
//        }
    }


    private void toHomepage(JsonObject param) {
        Logger.d("js调用Android", "toHome");
        Intent intent = new Intent();
        intent.setClass(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    private void goAutherActivity(JsonObject param) {
//        Logger.d("js调用Android", "toAutherActivity");
//        Intent intent = new Intent();
//        intent.setClass(mActivity, AutherActivity.class);
//        mActivity.setResult(111);
//        mActivity.finish();
    }

    private void toHomeFinancial(JsonObject param) {
//        Logger.d("js调用Android", "toHomeFinancial");
//        Intent intent = new Intent();
//        intent.setClass(mActivity, ProductListActivity.class);
//        mActivity.startActivity(intent);
//        mActivity.finish();
    }

    private void showWxShareBtn(final JsonObject param, final String uniqueId) {
        Logger.d("js调用Android", "ShowShareBtn -- parse param");
//        ImageView imgBtn = ((WebActivity) mActivity).getImg_right();
//        imgBtn.setImageResource(R.drawable.icon_more);
//        imgBtn.setScaleType(ImageView.ScaleType.CENTER);
//        imgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                wxshare(param, uniqueId);
//            }
//        });
//        imgBtn.setVisibility(View.VISIBLE);
    }

    private void wxshare(JsonObject param, String uniqueId) {
        Logger.d("js调用Android", "share -- parse param");
//        WebShareBean bean = new WebShareBean();
//        if (param.containsKey("title")) bean.title = (String) param.get("title");
//        if (param.containsKey("description")) bean.description = (String) param.get("description");
//        if (param.containsKey("jump_url")) bean.jump_url = (String) param.get("jump_url");
//        if (param.containsKey("img_url")) bean.img_url = (String) param.get("img_url");
//        DiaLogger.wxShareDialog(mActivity, bean.title, bean.description, bean.jump_url, bean.img_url);
    }

    private void certificate(JsonObject param) {
        if (param == null) return;
        WebBankBean bean = new WebBankBean();
        if (param.has("msg")) bean.setMsg(param.get("msg").getAsString());
        if (param.has("code")) bean.setCode(param.get("code").getAsLong());
        Logger.d("js调用Android", "certificate_result -- parse param");
        if (!StringUtil.isEmpty(bean.getMsg())) {
            Toaster.show(bean.getMsg());
        } else {
            Toaster.show("招行充值鉴权失败");
        }
    }

    private void toApplogin(JsonObject param) {
        Logger.d("js调用Android", "tologin -- parse param==" + param.toString());
        Intent intent = new Intent(mActivity, LoginActivity.class);
//        intent.putExtra(ComKey.WEB_LOGIN_TYPE, ComKey.WEB_LOGIN_TYPE);
//        intent.putExtra(ComKey.WEB_URL, loadUrl);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

//    private void sendAppurl(final JsonObject param, final String uniqueId) {
//        Logger.d("js调用Android", "sendurl -- parse param==" + param.toString());
//        //使用客户端请求并回调
//        final WebAjaxBean bean = new WebAjaxBean();
//        if (param.has("url")) bean.url = param.get("url").getAsString();
//        Logger.d("js调用Android", "sendurl ---开始请求");
//
//        //兼容优化
//        ParamsGet get = new ParamsGet();
//        if (bean.url.contains("?")) {
//            bean.url = bean.url + "&" + get.toString();
//        } else {
//            bean.url = bean.url + "?" + get.toString();
//        }
//
//        OkgoUtil.get(bean.url, null, this, new AbsCallback() {
//            @Override
//            public void onSuccess(Object o, Call call, Response response) {
//                try {
//                    useJsFunc("window.bridgeCallBack", uniqueId, response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public Object convertSuccess(Response response) throws Exception {
//                useJsFunc("window.bridgeCallBack", uniqueId, response.body().string());
//                return null;
//            }
//        });
//
//    }

    private void useJsFunc(final String funcName, String uid, String returnJson) {
        final String[] params = new String[2];
        params[0] = "\"" + uid + "\"";
        params[1] = returnJson;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                useJsFunc(funcName, params);
            }
        });
    }

    private void useJsFunc(String funcName, String... params) {
        String param = "";
        for (int i = 0; i < params.length; i++) {
            if (i == params.length)
                param += params[i];
            else
                param += params[i] + ",";

        }
        if (param.endsWith(",")) param = param.substring(0, param.length() - 1);
        String fuc = "javascript:" + funcName + "(" + param + ")";
        Logger.d("js调用Android", "----请求调用js方法:----" + fuc);
    }


}
