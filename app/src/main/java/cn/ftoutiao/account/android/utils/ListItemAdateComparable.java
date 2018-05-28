package cn.ftoutiao.account.android.utils;

import java.util.Comparator;

import cn.ftoutiao.account.android.model.db.ListItemBO;

/**
 * Author: weichyang
 * Date:   2018/4/13
 * Description: 针对账单排序 个数--->总额
 */
public class ListItemAdateComparable implements Comparator<ListItemBO> {

    // 对象的排序方式[升、降]   
//    public static boolean sortASC = true;

    @Override
    public int compare(ListItemBO pro1, ListItemBO pro2) {
        return -(int) (Long.valueOf(pro1.aDate) - Long.valueOf(pro2.aDate));
    }

}  