package cn.ftoutiao.account.android.model.evenbus;

import cn.ftoutiao.account.android.model.db.CategoryEntity;

/**
 * Created by weichyang on 2018/4/3.
 * 1.动态添加类别
 *
 */

public class AddCategoryEvent {

    public CategoryEntity categoryEntity;

    public AddCategoryEvent(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }


}
