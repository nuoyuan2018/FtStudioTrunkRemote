package cn.ftoutiao.account.android.model;

import com.acmenxd.retrofit.HttpDataEntity;

import java.util.ArrayList;
import java.util.List;

import cn.ftoutiao.account.android.model.db.ATypeListEntity;

/**
 * Created by weichyang on 2018/2/27.
 */

public class NoteBookEntity extends HttpDataEntity {

    public List<ATypeListEntity> list = new ArrayList<>();

}
