package cn.ftoutiao.account.android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import cn.ftoutiao.account.android.db.core.DBManager;
import cn.ftoutiao.account.android.db.dao.ATypeListEntityDao;
import cn.ftoutiao.account.android.db.dao.CategoryEntityDao;
import cn.ftoutiao.account.android.db.dao.DaoSession;
import cn.ftoutiao.account.android.model.db.ATypeListEntity;

/**
 * Author: yangweichao
 * Date:   2018/3/6 下午10:47
 * Description: 类别操作类
 */


public final class AtypeListEntityDB {

    private static AtypeListEntityDB instance;
    private SQLiteDatabase db;
    private DaoSession ds;
    private ATypeListEntityDao categoryDao;

    private AtypeListEntityDB() {
        db = DBManager.getInstance().getDatabase();
        ds = DBManager.getInstance().getDaoSession();
        categoryDao = ds.getATypeListEntityDao();
    }

    public static AtypeListEntityDB getInstance() {
        if (instance == null) {
            instance = new AtypeListEntityDB();
        }
        return instance;
    }


    /**
     * 类别
     */
    public void addAtypeEntiry(ATypeListEntity atypeListEntity) {
        categoryDao.insertOrReplace(atypeListEntity);
    }

    /**
     * 根据id删除H
     *
     * @param cid
     */
    public void deleteStudent(@IntRange(from = 0) long cid) {
        categoryDao.deleteByKey(cid);
    }


    public ATypeListEntity getAtypeDataByUidAtype( String atype) {
        List<ATypeListEntity> aTypeListEntities = queryATypeList(atype);
        return aTypeListEntities.size() == 0 ? null : aTypeListEntities.get(0);
    }

//    /**
//     * 更新
//     */
//    public void updateStudent(CategoryEntity categoryEntity) {
//        categoryDao.update(categoryEntity);
//    }

    /**
     * @param aType 类别
     * @return
     */
    public List<ATypeListEntity> queryATypeList(String aType) {
        // Query 类代表了一个可以被重复执行的查询
        Query<ATypeListEntity> query = categoryDao.queryBuilder()
                .where(ATypeListEntityDao.Properties.AId.eq(aType))
                .build();
        return query.list();
    }
}
