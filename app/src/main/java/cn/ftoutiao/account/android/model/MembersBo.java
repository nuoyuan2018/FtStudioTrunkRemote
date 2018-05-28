package cn.ftoutiao.account.android.model;

import com.acmenxd.retrofit.HttpDataEntity;
import com.acmenxd.retrofit.HttpEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weichyang on 2018/3/6.
 */

public class MembersBo extends HttpDataEntity implements Serializable {

        public List<?> blacklist;
        public MembersBean owner;
        public List<MembersBean> members=new ArrayList<>();

    /**
     * owner : {"uid":"11B3iR6wpEQjluX+rDUeIsNYpHdCg98r","nickname":"tian","gender":"","permission":"","avatarurl":"http://account.test.cn/img/2/47/125/121493951861_100101.jpg","platform":"","sourceId":"","unionId":""}
     * members : [{"uid":"0VbQKAtn0FnLcUbq30clMw==","nickname":"王亮","gender":"","permission":1,"avatarurl":"http://account.test.cn/img/3/47/114/131493017928_383711.jpeg","platform":"","sourceId":"","unionId":""}]
     */


    public static class MembersBean implements Serializable {//账本成员列表
        /**
         * uid : 0VbQKAtn0FnLcUbq30clMw==
         * nickname : 王亮
         * gender :
         * permission : 1
         * avatarurl : http://account.test.cn/img/3/47/114/131493017928_383711.jpeg
         * platform :
         * sourceId :
         * unionId :
         */

        public String uid;//用户id
        public String nickname;
        public String gender;
        public String permission;//权限，0–禁止,1–允许,该字段对账本主人无意义
        public String avatarurl;
        public String platform;
        public String sourceId;
        public String unionId;
    }
}
