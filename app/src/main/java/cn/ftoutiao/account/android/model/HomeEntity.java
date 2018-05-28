package cn.ftoutiao.account.android.model;

import com.acmenxd.retrofit.HttpEntity;

import java.util.ArrayList;
import java.util.List;

public class HomeEntity extends HttpEntity {
    public List<Products> products=new ArrayList<>();

    public static class Products {
        /**
         * bid_status : 1
         * bid_statusStr : 认购中
         * id : 6285109414330368
         * invest_periodStr : 6天
         * leftAmount : 0
         * left_amountStr : 0元
         * lend_apr : 1250
         * lend_aprStr : 12.50%
         * lend_periods : 6
         * title : 户*，28岁 男 武汉市
         */

        public int bid_status=4;
        public String bid_statusStr="dddd";
        public long id=010110;
        public String invest_periodStr="ppk,,";
        public long leftAmount=10002;
        public String left_amountStr="24545";
        public int lend_apr=105;
        public String lend_aprStr="45454";
        public int lend_periods=100;
        public String title="sadasdasd";
    }
}