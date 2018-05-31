package cn.ftoutiao.account.android.activity.budget;

import java.io.Serializable;
import java.util.List;

/**
 * @author XuHuiHao
 * Created on 2018/5/30 0030.
 */
public class BudgetBean implements Serializable{
    private List<BodyBean> body;
    public List<BodyBean> getBody() {
        return body;
    }
    public void setBody(List<BodyBean> body) {
        this.body = body;
    }
    public static class BodyBean {
        private String year;
        private String month;
        private String money;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
