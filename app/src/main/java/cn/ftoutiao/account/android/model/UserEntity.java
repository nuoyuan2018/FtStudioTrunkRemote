package cn.ftoutiao.account.android.model;

import com.acmenxd.retrofit.HttpEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by weichyang on 2018/2/9.
 */

public class UserEntity extends HttpEntity {

    /**
     * uid : 11B3iR6wpESKPtsuUiTNgx2PWfVEow+E
     * token : e85f5daf34a02dc5c12a46e5c185d036
     * data : {"uid":"11B3iR6wpESKPtsuUiTNgx2PWfVEow+E","platform":"phone","nickname":"手机用户3416","gender":"","list":[{"aId":"70537155","aType":1,"aname":"日常","own":true,"count":1,"remark":"","icon":"","ctime":1517886752}],"ctime":1517886752,"avatarurl":"","mobile":"18801273416"}
     * type : 0
     */
    public String uid;
    public String token;
    public DataEntity data;
    public int type;

    public static class DataEntity implements Serializable {
        /**
         * uid : 11B3iR6wpESKPtsuUiTNgx2PWfVEow+E
         * platform : phone
         * nickname : 手机用户3416
         * gender :
         * list : [{"aId":"70537155","aType":1,"aname":"日常","own":true,"count":1,"remark":"","icon":"","ctime":1517886752}]
         * ctime : 1517886752
         * avatarurl :
         * mobile : 18801273416
         */

        public String uid;
        public String platform;
        public String nickname;
        public String gender;
        public long ctime;
        public String avatarurl;
        public String mobile;
        public List<ListEntity> list;


    }
}
