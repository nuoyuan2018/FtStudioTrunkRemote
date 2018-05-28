package cn.ftoutiao.account.android.model.evenbus;

/**
 * Created by weichyang on 2018/4/4.
 * 选择账本类别
 */

public class SelectAidAction {

   public String aid; //账本id

    public SelectAidAction(String aid) {
        this.aid = aid;
    }
}
