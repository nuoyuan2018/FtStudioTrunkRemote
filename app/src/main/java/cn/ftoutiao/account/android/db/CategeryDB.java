package cn.ftoutiao.account.android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import cn.ftoutiao.account.android.base.BaseApplication;
import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.db.core.DBManager;
import cn.ftoutiao.account.android.db.dao.CategoryEntityDao;
import cn.ftoutiao.account.android.db.dao.DaoSession;
import cn.ftoutiao.account.android.model.UserEntity;
import cn.ftoutiao.account.android.model.db.CategoryEntity;
import cn.ftoutiao.account.android.utils.AccountManager;

/**
 * Author: yangweichao
 * Date:   2018/3/6 下午10:47
 * Description: 类别操作类
 */


public final class CategeryDB {

    private static CategeryDB instance;
    private SQLiteDatabase db;
    private DaoSession ds;
    private CategoryEntityDao categoryDao;
    private String uid="0";

    private CategeryDB() {
        db = DBManager.getInstance().getDatabase();
        ds = DBManager.getInstance().getDaoSession();
        categoryDao = ds.getCategoryEntityDao();
    }

    public static CategeryDB getInstance() {
        if (instance == null) {
            instance = new CategeryDB();
        }
        return instance;
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public Cursor getStudentCursor() {
        //查询，得到cursor
        String orderBy = CategoryEntityDao.Properties.Id.columnName + " DESC";//根据Id降序排序
        Cursor cursor = db.query(categoryDao.getTablename(), categoryDao.getAllColumns(), null, null, null, null, orderBy);
        return cursor;
    }

    /**
     * 类别插入
     */
    public void addCategoryEntiry(CategoryEntity categoryEntity) {
        categoryDao.insertOrReplace(categoryEntity);
    }


    /**
     * 根据cid查询
     *
     * @param cid
     */
    public List<CategoryEntity> queryCategery(int cid) {
        // Query 类代表了一个可以被重复执行的查询
        Query<CategoryEntity> query = categoryDao.queryBuilder()
                .where(CategoryEntityDao.Properties.CId.eq(cid))
                .orderAsc(CategoryEntityDao.Properties.Id)
                .build();
        // 查询结果以 List 返回
        return query.list();
    }

    public List<CategoryEntity> queryCategery(String atype, String uid) {
        // Query 类代表了一个可以被重复执行的查询
        Query<CategoryEntity> query = categoryDao.queryBuilder()
                .where(CategoryEntityDao.Properties.AId.eq(atype))
                .build();
        // 查询结果以 List 返回
        return query.list();
    }

    /**
     * 通过aid ctype uid 查询当前类别
     * @param aid
     * @param atype
     * @return
     */
    public List<CategoryEntity> queryCategery(String aid, int atype) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<CategoryEntity> query = categoryDao.queryBuilder()
                .where(CategoryEntityDao.Properties.AId.eq(aid),
                        CategoryEntityDao.Properties.CType.eq(atype))
                .build();
        // 查询结果以 List 返回
        return query.list()==null ||query.list().size()==0?new ArrayList<CategoryEntity>():query.list();
    }

    private void initUserId() {
        if (uid.equals("0") && DataContans.isLogin()) {
            uid = DataContans.userEntity.uid;
        }
    }



}
