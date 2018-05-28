package cn.ftoutiao.account.android.activity.bill.test;

import java.util.ArrayList;
import java.util.List;

import cn.ftoutiao.account.android.model.db.ListItemBO;

/**
 * Author: weichyang
 * Date:   2018/4/20
 * Description:
 * 支出收入排行allInputTotal
 */

public class GroupBean implements Cloneable {
    private String groupName;

    public float inputTotal; //收入
    public float outputTotal;//支出
    public float difInputOrOutPut; //结余
    public float allInputTotal; //所有输入综合
    public float allOutTotal;
    public String year;
    public String month;

    public List<ListItemBO> childs = new ArrayList<>();


    @Override
    public GroupBean clone() {
        GroupBean groupBean = null;
        try {
            groupBean = (GroupBean) super.clone();
            groupBean.childs = childs;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return groupBean;
    }
}
