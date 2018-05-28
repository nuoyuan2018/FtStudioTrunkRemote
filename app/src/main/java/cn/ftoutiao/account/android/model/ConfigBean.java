package cn.ftoutiao.account.android.model;

import com.acmenxd.retrofit.HttpDataEntity;

/**
 * Created by weichyang on 2018/4/17.
 */

public class ConfigBean extends HttpDataEntity {
    public String update_url= "";  //下载链接
    public int update_type=1; //更新类型
    public String update_ver= ""; //更新版本
    public String update_msg= ""; //更新信息
}
