package cn.ftoutiao.account.android.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.acmenxd.logger.Logger;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import cn.ftoutiao.account.android.constants.DataContans;
import cn.ftoutiao.account.android.db.core.DBManager;
import cn.ftoutiao.account.android.db.dao.DaoSession;
import cn.ftoutiao.account.android.db.dao.ListItemBODao;
import cn.ftoutiao.account.android.model.db.ListItemBO;
import cn.ftoutiao.account.android.utils.StringUtil;

/**
 * Author: weichyang
 * Date:   2018/3/9
 * Description: 账单操作类
 */

public final class ListItemDB {

    private static ListItemDB instance;
    private SQLiteDatabase db;
    private DaoSession ds;
    private ListItemBODao billDao;
    String uid = "0";

    private ListItemDB() {
        db = DBManager.getInstance().getDatabase();
        ds = DBManager.getInstance().getDaoSession();
        billDao = ds.getListItemBODao();
    }

    public static ListItemDB getInstance() {
        if (instance == null) {
            instance = new ListItemDB();
        }
        return instance;
    }

    /**
     * 查询一年的分组
     *
     * @param year
     * @param ctype
     * @param aid
     * @return
     */
    public Cursor getBillCursorByYear(String year, int ctype, String aid) {
        //查询，得到cursor
        String orderBy = ListItemBODao.Properties.Month.columnName + " ASC";//根据Id降序排序
        Cursor cursor = db.query(billDao.getTablename(),
                billDao.getAllColumns(),
                ListItemBODao.Properties.CType.columnName + "= ? AND"
                        + ListItemBODao.Properties.Year.columnName + "=? AND " +
                        ListItemBODao.Properties.AId.columnName + "=?",
                new String[]{String.valueOf(ctype), year, aid},
                ListItemBODao.Properties.Month.columnName, null, orderBy);
        return cursor;
    }


    public Cursor getBillCursorByYear1(String year, int ctype, String aid) {
        //查询，得到cursor
        String orderBy = ListItemBODao.Properties.Month.columnName + " ASC";//根据Id降序排序
        Cursor cursor = db.query(billDao.getTablename(),
                billDao.getAllColumns(),
                ListItemBODao.Properties.CType.columnName + "= ? AND"
                        + ListItemBODao.Properties.Year.columnName + "=? AND " +
                        ListItemBODao.Properties.AId.columnName + "=?",
                new String[]{String.valueOf(ctype), year, aid},
                ListItemBODao.Properties.RId.columnName, null, orderBy);

        return cursor;
    }

    private void initUserId() {
        if (DataContans.isLogin()) {
            if (!uid.equals(DataContans.userEntity.uid)) {
                uid = DataContans.userEntity.uid;
            }
        } else {
            uid = "0";
        }

    }

    /**
     * 相同rid 不同cid 作为不同的账单,分开计算
     *
     * @param aid
     * @param ctype
     * @param month
     * @param year
     * @return
     */
    public List<ListItemBO> getMonthCtypeCount(String aid, int ctype, String month, String year) {
        initUserId();
        float amountTotal = queryMonthTotal(aid, ctype, month, year);
        //查询，得到cursor
        String sql = "SELECT *,COUNT(*), SUM(AMOUNT)  FROM tbl_bill WHERE  A_ID = ? AND C_TYPE=? AND MONTH =? AND YEAR=? GROUP BY C_ID";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid, String.valueOf(ctype), month, year});


        List<ListItemBO> list = new ArrayList<>();
        ListItemBO listItemBO = new ListItemBO();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.RName.columnName));
            String cName = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.CName.columnName));
            String icon = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Icon.columnName));
            String yearValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Year.columnName));
            String monthValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Month.columnName));
            String adataValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.ADate.columnName));
            int cid = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CId.columnName));
            int ctypeobject = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CType.columnName));
            int rid = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.RId.columnName));
            String innerAid = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.AId.columnName));

            Long count = cursor.getLong(cursor.getColumnCount() - 2);
            float typeCount = cursor.getFloat(cursor.getColumnCount() - 1);
            ListItemBO bo = listItemBO.clone();
            bo.count = String.valueOf(count);
            bo.typeAmountCount = typeCount;
            bo.amountCount = amountTotal;
            bo.rName = str;
            bo.cName = cName;
            bo.rId = rid;
            bo.aId = innerAid;
            bo.year = yearValue;
            bo.month = monthValue;
            bo.aDate = adataValue;
            bo.color = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Color.columnName));
            bo.cId = cid;
            bo.icon = icon;
            bo.cType = ctypeobject;
            list.add(bo);
        }
        cursor.close();
        return list;
    }

    public List<ListItemBO> getCtypeCount(String aid, int ctype) {
        initUserId();
        float amountTotal = queryAllYearsTotal(aid, ctype);
        //查询，得到cursor
        String sql = "SELECT *,COUNT(*), SUM(AMOUNT)  FROM tbl_bill WHERE  A_ID = ? AND C_TYPE=? GROUP BY C_ID";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid, String.valueOf(ctype)});


        List<ListItemBO> list = new ArrayList<>();
        ListItemBO listItemBO = new ListItemBO();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.RName.columnName));
            String cName = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.CName.columnName));
            String icon = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Icon.columnName));
            int cid = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CId.columnName));
            String yearValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Year.columnName));
            String monthValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Month.columnName));
            int ctypeobject = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CType.columnName));
            String adataValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.ADate.columnName));
            int rid = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.RId.columnName));
            String innerAid = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.AId.columnName));

            Long count = cursor.getLong(cursor.getColumnCount() - 2);
            float typeCount = cursor.getFloat(cursor.getColumnCount() - 1);
            ListItemBO bo = listItemBO.clone();
            bo.count = String.valueOf(count);
            bo.typeAmountCount = typeCount;
            bo.amountCount = amountTotal;
            bo.aDate = adataValue;
            bo.year = yearValue;
            bo.month = monthValue;
            bo.cName = cName;
            bo.rName = str;
            bo.color = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Color.columnName));
            bo.cId = cid;
            bo.rId = rid;
            bo.aId = innerAid;
            bo.icon = icon;
            bo.cType = ctypeobject;
            list.add(bo);
        }
        cursor.close();
        return list;
    }

    /**
     * 每一年的总支出/收入，消费笔数，单笔最大，消费笔数占百分比
     *
     * @param aid
     * @param ctype
     * @param year
     * @return
     */
    public List<ListItemBO> getYearCtypeCount(String aid, int ctype, String year) {
        initUserId();
        float amountTotal = queryYearTotal(aid, ctype, year)[0];
        //查询，得到cursor
        String sql = "SELECT *,COUNT(*), SUM(AMOUNT)  FROM tbl_bill WHERE  A_ID = ? AND C_TYPE=? AND YEAR =? GROUP BY C_ID";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid, String.valueOf(ctype), year});


        List<ListItemBO> list = new ArrayList<>();
        ListItemBO listItemBO = new ListItemBO();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.RName.columnName));
            String cName = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.CName.columnName));
            String icon = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Icon.columnName));
            int cid = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CId.columnName));
            int ctypeobject = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CType.columnName));
            String yearValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Year.columnName));
            String monthValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Month.columnName));
            String adataValue = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.ADate.columnName));
            int rid = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.RId.columnName));
            String innerAid = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.AId.columnName));
            Long count = cursor.getLong(cursor.getColumnCount() - 2);
            float typeCount = cursor.getFloat(cursor.getColumnCount() - 1);
            ListItemBO bo = listItemBO.clone();
            bo.count = String.valueOf(count);
            bo.typeAmountCount = typeCount;
            bo.amountCount = amountTotal;
            bo.cName = cName;
            bo.rName = str;
            bo.color = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Color.columnName));
            bo.cId = cid;
            bo.icon = icon;
            bo.aDate = adataValue;
            bo.year = yearValue;
            bo.rId = rid;
            bo.aId = innerAid;
            bo.month = monthValue;
            bo.cType = ctypeobject;
            list.add(bo);
        }
        cursor.close();
        return list;
    }


    public long queryMonthTotal(String aid, int ctype, String month) {
        String sql2 = "select  SUM(AMOUNT) FROM tbl_bill WHERE MONTH=? AND C_TYPE=? AND A_ID =?";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{month, String.valueOf(ctype), aid});

        long amountTotal = 0;
        while (cursor2.moveToNext()) {
            amountTotal = cursor2.getLong(cursor2.getColumnCount() - 1);

            cursor2.close();
        }
        return amountTotal;
    }

    public float queryMonthTotal(String aid, int ctype, String month, String year) {
        initUserId();
        Cursor cursor2 = null;
        float amountTotal = 0;
        try {
            String sql2 = "select SUM(AMOUNT) FROM tbl_bill WHERE MONTH=? AND C_TYPE=? AND A_ID =? AND YEAR=? ";
            cursor2 = db.rawQuery(
                    sql2, new String[]{month, String.valueOf(ctype), aid, year});
            while (cursor2.moveToNext()) {
                amountTotal = cursor2.getFloat(cursor2.getColumnCount() - 1);
            }
        } finally {
            cursor2.close();
        }
        return amountTotal;
    }

    public float queryMonthTotalByUid(String aid, int ctype, String month, String year, String uid) {
        initUserId();
        Cursor cursor2 = null;
        float amountTotal = 0;
        try {
            String sql2 = "select SUM(AMOUNT) FROM tbl_bill WHERE UID =? AND MONTH=? AND C_TYPE=? AND A_ID =? AND YEAR=? ";
            cursor2 = db.rawQuery(
                    sql2, new String[]{uid, month, String.valueOf(ctype), aid, year});
            while (cursor2.moveToNext()) {
                amountTotal = cursor2.getFloat(cursor2.getColumnCount() - 1);
            }
        } finally {
            cursor2.close();
        }
        return amountTotal;
    }

    public float queryMonthTotal(String aid, int ctype) {
        Cursor cursor2 = null;
        float amountTotal = 0;
        try {
            String sql2 = "select SUM(AMOUNT) FROM tbl_bill WHERE  C_TYPE=? AND A_ID =? ";
            cursor2 = db.rawQuery(
                    sql2, new String[]{String.valueOf(ctype), aid});
            while (cursor2.moveToNext()) {
                amountTotal = cursor2.getFloat(cursor2.getColumnCount() - 1);
            }
        } finally {
            cursor2.close();
        }
        return amountTotal;
    }


    /**
     * 查询一年的数据总和
     *
     * @param aid
     * @param ctype
     * @param year
     * @return
     */
    public float[] queryYearTotal(String aid, int ctype, String year) {
        initUserId();
        String sql2 = "select MAX(AMOUNT) ,SUM(AMOUNT) FROM tbl_bill where  YEAR=? AND C_TYPE=? AND A_ID =?";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{year, String.valueOf(ctype), aid});

        float[] amount = new float[2];

        while (cursor2.moveToNext()) {
            amount[0] = cursor2.getFloat(cursor2.getColumnCount() - 1);
            amount[1] = cursor2.getFloat(cursor2.getColumnCount() - 2);
        }
        cursor2.close();
        return amount;
    }

    public float queryAllYearsTotal(String aid, int ctype) {
        initUserId();
        String sql2 = "select SUM(AMOUNT) FROM tbl_bill where C_TYPE=? AND A_ID =?";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{String.valueOf(ctype), aid});

        float[] amount = new float[1];

        while (cursor2.moveToNext()) {
            amount[0] = cursor2.getFloat(cursor2.getColumnCount() - 1);
        }
        cursor2.close();
        return amount[0];
    }

    public float queryAllYearsTotal(String aid, int ctype, String uid) {

        String sql2 = "select SUM(AMOUNT) FROM tbl_bill where UID=? AND C_TYPE=? AND A_ID =?";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{uid, String.valueOf(ctype), aid});

        float[] amount = new float[1];

        while (cursor2.moveToNext()) {
            amount[0] = cursor2.getFloat(cursor2.getColumnCount() - 1);
        }
        cursor2.close();
        return amount[0];
    }

    @Deprecated
    public String queryAllYearsTotalString(String aid, int ctype) {
        initUserId();
        String sql2 = "select SUM(AMOUNT) FROM tbl_bill where  C_TYPE=? AND A_ID =?";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{String.valueOf(ctype), aid});

        String[] amount = new String[1];

        while (cursor2.moveToNext()) {
            amount[0] = cursor2.getString(cursor2.getColumnCount() - 1);
        }
        cursor2.close();
        return amount[0];
    }


    /**
     * 添加账单
     */
    public void addBillEntiry(ListItemBO listItemBO) {
        listItemBO.year = listItemBO.aDate.substring(0, 4);
        String month = listItemBO.aDate.substring(4, 6);
        if (month.startsWith("0")) {
            listItemBO.month = listItemBO.aDate.substring(5, 6);
        } else {
            listItemBO.month = listItemBO.aDate.substring(4, 6);
        }
        String day = listItemBO.aDate.substring(6, 8);
        if (day.startsWith("0")) {
            listItemBO.day = listItemBO.aDate.substring(7, 8);
        } else {
            listItemBO.day = listItemBO.aDate.substring(6, 8);
        }
        // listItemBO.aType = atype;
        //面向对象添加表数据
        billDao.insertOrReplace(listItemBO);
    }


    /**
     * 通过账本id 进行查询
     *
     * @return
     */
    public List<ListItemBO> queryBillByNoteId(String aid) {
        initUserId();
        if (!StringUtil.isEmpty(aid)) {
            // Query 类代表了一个可以被重复执行的查询
            Query<ListItemBO> query = billDao.queryBuilder()
                    .where(ListItemBODao.Properties.AId.eq(aid))
                    .orderDesc(ListItemBODao.Properties.ADate)
                    .build();
            // 查询结果以 List 返回
            return query.list();
        } else {
            Logger.i("aid is null. please checked the aid ");
            return new ArrayList<>();
        }
    }

    /**
     * 根据年月类型查询信息
     *
     * @param itemBO
     * @return
     */
    public List<ListItemBO> queryBillByMonth(ListItemBO itemBO) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        if (itemBO.month.startsWith("0")) {
            itemBO.month = itemBO.month.substring(1, 2);
        }
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.CType.eq(itemBO.cType),
                        ListItemBODao.Properties.AId.eq(itemBO.aId),
                        ListItemBODao.Properties.Month.eq(itemBO.month),
                        ListItemBODao.Properties.Year.eq(itemBO.year)
                )
                .orderDesc(ListItemBODao.Properties.ADate)
                .build();
        // 查询结果以 List 返回
        List<ListItemBO> listItemBOS = query.list();
        return listItemBOS;
    }

    public List<ListItemBO> queryBillByMonthAndCid(ListItemBO itemBO, int type) {
        initUserId();

        if (type == 0) {
            // Query 类代表了一个可以被重复执行的查询
            if (itemBO.month.startsWith("0")) {
                itemBO.month = itemBO.month.substring(1, 2);
            }

            Query<ListItemBO> query = billDao.queryBuilder()
                    .where(ListItemBODao.Properties.CType.eq(itemBO.cType),
                            ListItemBODao.Properties.AId.eq(itemBO.aId),
                            ListItemBODao.Properties.RId.eq(itemBO.rId),
                            ListItemBODao.Properties.Month.eq(itemBO.month),
                            ListItemBODao.Properties.Year.eq(itemBO.year)
                    )
                    .orderDesc(ListItemBODao.Properties.ADate)
                    .build();
            // 查询结果以 List 返回
            List<ListItemBO> listItemBOS = query.list();
            return listItemBOS;
        }
        if (type == 1) {
            Query<ListItemBO> query = billDao.queryBuilder()
                    .where(ListItemBODao.Properties.CType.eq(itemBO.cType),
                            ListItemBODao.Properties.AId.eq(itemBO.aId),
                            ListItemBODao.Properties.RId.eq(itemBO.rId),
                            ListItemBODao.Properties.Year.eq(itemBO.year)
                    )
                    .orderDesc(ListItemBODao.Properties.ADate)
                    .build();
            // 查询结果以 List 返回
            List<ListItemBO> listItemBOS = query.list();
            return listItemBOS;
        }
        if (type == 2) {

            Query<ListItemBO> query = billDao.queryBuilder()
                    .where(ListItemBODao.Properties.CType.eq(itemBO.cType),
                            ListItemBODao.Properties.AId.eq(itemBO.aId),
                            ListItemBODao.Properties.RId.eq(itemBO.rId)
                    )
                    .orderDesc(ListItemBODao.Properties.ADate)
                    .build();
            // 查询结果以 List 返回
            List<ListItemBO> listItemBOS = query.list();
            return listItemBOS;
        }
        return new ArrayList<>();
    }

    public List<ListItemBO> queryBill(String year, int ctype, String aid, String month) {
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.CType.eq(ctype),
                        ListItemBODao.Properties.AId.eq(aid),
                        ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year)).orderAsc()
                .orderDesc(ListItemBODao.Properties.ADate)
                .build();

        return query.list();
    }

    public String queryBillMaxCount(int ctype, String aid, String year) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.CType.eq(ctype),
                        ListItemBODao.Properties.AId.eq(aid),
                        ListItemBODao.Properties.Year.eq(year)).orderAsc()
                .orderDesc(ListItemBODao.Properties.Amount)
                .build();

        if (query.list().size() > 0) {
            return String.valueOf(query.list().get(0).amount);
        }
        return "0";
    }

    public String queryBillMaxCount(int ctype, String aid, String year, String month) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.CType.eq(ctype),
                        ListItemBODao.Properties.AId.eq(aid),
                        ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year))
                .orderDesc(ListItemBODao.Properties.Amount)
                .build();

        if (query.list().size() > 0) {
            Float max = 0F;
            for (ListItemBO bo : query.list()) {
                if (Float.valueOf(bo.amount) > max) {
                    max = Float.valueOf(bo.amount);
                }
            }
            return String.valueOf(max);
        }
        return "0";
    }

    public String queryBillMaxCount(int ctype, String aid) {
        String sql2 = "select MAX(AMOUNT) FROM tbl_bill where C_TYPE=? AND A_ID =?";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{String.valueOf(ctype), aid});

        float[] amount = new float[2];

        while (cursor2.moveToNext()) {
            amount[0] = cursor2.getFloat(cursor2.getColumnCount() - 1);
        }
        cursor2.close();
        if (amount[0] > 0) {
            return String.valueOf(amount[0]);
        }
        return "0";
    }

    // TODO: 2018/4/1  需要修改
    public float[] queryInput2Output(ListItemBO itembo, String aid) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Month.eq(itembo.month),
                        ListItemBODao.Properties.Year.eq(itembo.year),
                        ListItemBODao.Properties.AId.eq(aid))
                .build();
        // 查询结果以 List 返回
        List<ListItemBO> listItemBOS = query.list();

        float[] moneyArray = new float[2];
        for (ListItemBO itemBO : listItemBOS) {
            if (itemBO.cType == 1) {
                moneyArray[0] += Float.valueOf(itemBO.amount);
            } else if (itemBO.cType == 2) {
                moneyArray[1] += Float.valueOf(itemBO.amount);
            }
        }
        return moneyArray;
    }

    public float[] queryInput2OutputByDay(String day, String month, String year, String aid) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Day.eq(day),
                        ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year),
                        ListItemBODao.Properties.AId.eq(aid))
                .build();
        // 查询结果以 List 返回
        List<ListItemBO> listItemBOS = query.list();

        float[] moneyArray = new float[2];
        for (ListItemBO itemBO : listItemBOS) {
            if (itemBO.cType == 1) {
                moneyArray[0] += Float.valueOf(itemBO.amount);
            } else if (itemBO.cType == 2) {
                moneyArray[1] += Float.valueOf(itemBO.amount);
            }
        }
        return moneyArray;
    }

    public float[] queryInput2OutputByDay(String month, String year, String aid) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year),
                        ListItemBODao.Properties.AId.eq(aid))
                .build();
        // 查询结果以 List 返回
        List<ListItemBO> listItemBOS = query.list();

        float[] moneyArray = new float[2];
        for (ListItemBO itemBO : listItemBOS) {
            if (itemBO.cType == 1) {
                moneyArray[0] += Float.valueOf(itemBO.amount);
            } else if (itemBO.cType == 2) {
                moneyArray[1] += Float.valueOf(itemBO.amount);
            }
        }
        return moneyArray;
    }

    public float[] queryInput2OutputByDayByUid(String month, String year, String aid, String uid) {
        initUserId();
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year),
                        ListItemBODao.Properties.AId.eq(aid),
                        ListItemBODao.Properties.Uid.eq(uid))
                .build();
        // 查询结果以 List 返回
        List<ListItemBO> listItemBOS = query.list();

        float[] moneyArray = new float[2];
        for (ListItemBO itemBO : listItemBOS) {
            if (itemBO.cType == 1) {
                moneyArray[0] += Float.valueOf(itemBO.amount);
            } else if (itemBO.cType == 2) {
                moneyArray[1] += Float.valueOf(itemBO.amount);
            }
        }
        return moneyArray;
    }

    /**
     * 自定义
     *
     * @param month
     * @param type
     * @param cond
     * @param condMore
     * @return
     */
    public float[] queryInput2Output(String month, int type, WhereCondition cond, WhereCondition... condMore) {
        // Query 类代表了一个可以被重复执行的查询
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(cond, condMore)
                .build();
        // 查询结果以 List 返回
        List<ListItemBO> listItemBOS = query.list();

        float[] moneyArray = new float[2];
        for (ListItemBO itemBO : listItemBOS) {
            if (itemBO.cType == 1) {
                moneyArray[0] += Float.valueOf(itemBO.amount);
            } else if (itemBO.cType == 2) {
                moneyArray[1] += Float.valueOf(itemBO.amount);
            }
        }
        return moneyArray;
    }

    public List<ListItemBO> queryDayInOutAmount(String aid, int year, int month, int day) {
        initUserId();
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Month.eq(String.valueOf(month)),
                        ListItemBODao.Properties.Year.eq(String.valueOf(year)),
                        ListItemBODao.Properties.Day.eq(String.valueOf(day)),
                        ListItemBODao.Properties.AId.eq(aid)).orderDesc(ListItemBODao.Properties.Ctime).build();
        // 查询结果以 List 返回
        return query.list();
    }

    public List<ListItemBO> queryDetailDay(String aid, String year, String month, String day) {
        initUserId();
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year),
                        ListItemBODao.Properties.Day.eq(day),
                        ListItemBODao.Properties.AId.eq(aid)).orderDesc(ListItemBODao.Properties.Mtime).build();
        // 查询结果以 List 返回
        return query.list();
    }

    public List<ListItemBO> queryDetailDayByUid(String aid, String year, String month, String day, String uid) {
        initUserId();
        Query<ListItemBO> query = billDao.queryBuilder()
                .where(ListItemBODao.Properties.Month.eq(month),
                        ListItemBODao.Properties.Year.eq(year),
                        ListItemBODao.Properties.Day.eq(day),
                        ListItemBODao.Properties.Uid.eq(uid),
                        ListItemBODao.Properties.AId.eq(aid)).orderDesc(ListItemBODao.Properties.Ctime).build();
        // 查询结果以 List 返回
        return query.list();
    }

    /**
     * 查询当天输入/输出总和
     *
     * @param aid
     * @param year
     * @param month
     * @param day
     * @return
     */
    public List<ListItemBO> queryDayInOutAmount(String aid, String year, String month, String day) {
        initUserId();
        String sql = "SELECT *, SUM(AMOUNT)  FROM tbl_bill WHERE  A_ID = ? AND YEAR=? AND MONTH =? AND DAY =? GROUP BY C_TYPE";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid, year, month, day});


        List<ListItemBO> list = new ArrayList<>();
        ListItemBO listItemBO = new ListItemBO();
        while (cursor.moveToNext()) {
            double typeCount = cursor.getDouble(cursor.getColumnCount() - 1);
            ListItemBO bo = listItemBO.clone();
            bo.typeAmountCount = typeCount;
            bo.cType = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CType.columnName));
            bo.color = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Color.columnName));
            list.add(bo);
        }
        cursor.close();
        return list;
    }


    /**
     * 总共有几个组
     *
     * @param aid
     * @param year
     * @param month
     * @return
     */
    public List<ListItemBO> queryBillDetailGroupDay(String aid, String year, String month) {
        String sql = "SELECT *  FROM tbl_bill WHERE  A_ID = ? AND YEAR=? AND MONTH =?  GROUP BY DAY ORDER BY DAY DESC";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid, year, month});


        List<ListItemBO> list = new ArrayList<>();
        ListItemBO listItemBO = new ListItemBO();
        while (cursor.moveToNext()) {
            ListItemBO bo = listItemBO.clone();
            bo.aId = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.AId.columnName));
            bo.cType = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CType.columnName));
            bo.color = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Color.columnName));
            bo.amount = cursor.getFloat(cursor.getColumnIndex(ListItemBODao.Properties.Amount.columnName));
            bo.cName = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.CName.columnName));
            bo.year = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Year.columnName));
            bo.month = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Month.columnName));
            bo.day = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Day.columnName));
            list.add(bo);
        }
        cursor.close();
        return list;
    }

    public List<ListItemBO> queryBillDetailGroupDayByUid(String aid, String year, String month, String uid) {
        String sql = "SELECT *  FROM tbl_bill WHERE UID=? AND A_ID = ? AND YEAR=? AND MONTH =?  GROUP BY DAY ORDER BY DAY DESC";
        Cursor cursor = db.rawQuery(
                sql, new String[]{uid, aid, year, month});


        List<ListItemBO> list = new ArrayList<>();
        ListItemBO listItemBO = new ListItemBO();
        while (cursor.moveToNext()) {
            ListItemBO bo = listItemBO.clone();
            bo.aId = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.AId.columnName));
            bo.cType = cursor.getInt(cursor.getColumnIndex(ListItemBODao.Properties.CType.columnName));
            bo.color = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Color.columnName));
            bo.amount = cursor.getFloat(cursor.getColumnIndex(ListItemBODao.Properties.Amount.columnName));
            bo.cName = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.CName.columnName));
            bo.year = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Year.columnName));
            bo.month = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Month.columnName));
            bo.day = cursor.getString(cursor.getColumnIndex(ListItemBODao.Properties.Day.columnName));
            list.add(bo);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询最小的时间
     *
     * @param aid
     * @param ctype
     * @return
     */
    public int[] queryMinData(String aid, String ctype) {
        initUserId();
        String sql = "SELECT MIN(YEAR)  FROM tbl_bill WHERE  A_ID = ? AND C_TYPE=? ";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid, ctype});

        int minyear = 0;
        int minMonth = 0;
        int[] data = new int[2];
        while (cursor.moveToNext()) {
            minyear = cursor.getInt(cursor.getColumnCount() - 1);
            data[0] = minyear;
            cursor.close();
        }
        String sql2 = "SELECT MIN(MONTH)  FROM tbl_bill WHERE A_ID = ? AND C_TYPE=? AND YEAR=? ";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{aid, ctype, String.valueOf(minyear)});
        while (cursor2.moveToNext()) {
            minMonth = cursor2.getInt(cursor2.getColumnCount() - 1);
            data[1] = minMonth;
            cursor2.close();
        }
        return data;
    }

    public int[] queryMinData(String aid) {
        initUserId();
        String sql = "SELECT MIN(YEAR)  FROM tbl_bill WHERE  A_ID = ? ";
        Cursor cursor = db.rawQuery(
                sql, new String[]{aid});

        int minyear = 0;
        int minMonth = 0;
        int[] data = new int[2];
        while (cursor.moveToNext()) {
            minyear = cursor.getInt(cursor.getColumnCount() - 1);
            data[0] = minyear;
            cursor.close();
        }
        String sql2 = "SELECT MIN(MONTH)  FROM tbl_bill WHERE A_ID = ? AND YEAR=? ";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{aid, String.valueOf(minyear)});
        while (cursor2.moveToNext()) {
            minMonth = cursor2.getInt(cursor2.getColumnCount() - 1);
            data[1] = minMonth;
            cursor2.close();
        }
        return data;
    }

    public int[] queryMinDataByUid(String aid, String uid) {

        String sql = "SELECT MIN(YEAR)  FROM tbl_bill WHERE UID =? AND A_ID = ? ";
        Cursor cursor = db.rawQuery(
                sql, new String[]{uid, aid});

        int minyear = 0;
        int minMonth = 0;
        int[] data = new int[2];
        while (cursor.moveToNext()) {
            minyear = cursor.getInt(cursor.getColumnCount() - 1);
            data[0] = minyear;
            cursor.close();
        }
        String sql2 = "SELECT MIN(MONTH)  FROM tbl_bill WHERE  UID =? AND A_ID = ? AND YEAR=? ";
        Cursor cursor2 = db.rawQuery(
                sql2, new String[]{uid, aid, String.valueOf(minyear)});
        while (cursor2.moveToNext()) {
            minMonth = cursor2.getInt(cursor2.getColumnCount() - 1);
            data[1] = minMonth;
            cursor2.close();
        }
        return data;
    }

    public void deleteBill(ListItemBO listItemBO) {
        billDao.delete(listItemBO);
    }
}
