package cn.ftoutiao.account.android.db.migrator;


import org.greenrobot.greendao.database.Database;

import cn.ftoutiao.account.android.db.core.MigrationHelperUtil;
import cn.ftoutiao.account.android.db.dao.TestBeanDao;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/2/28 10:00
 * @detail 更新数据库版本 -> DB版本号 1
 */
public class MigratorHelper1 extends BaseMigratorHelper {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onUpgrade(Database db) {
        //更新数据库表结构
        MigrationHelperUtil.getInstance().migrate(db, new DefaultCallback() {
            @Override
            public String onText(String tablename, String columnName) {
                return null;
            }

            public Long onInteger(String tablename, String columnName) {
                return null;
            }

            @Override
            public Double onReal(String tablename, String columnName) {
                return null;
            }

            @Override
            public Boolean onBoolean(String tablename, String columnName) {
                return null;
            }
        }, TestBeanDao.class);
    }
}