package cn.ftoutiao.account.android.model;


/**
 * Created by weichyang on 2018/4/4.
 * 水平滚动bar 业务bean
 */

public class BarBeanBo implements Cloneable {
    public String data;
    public float monthTotal;
    public long monthsTotal;
    public long yearsTotal;
    public float yearTotal;
    public String year;
    public String month;
    public boolean canClick = true;

    public BarBeanBo(String data, long monthTotal, long monthsTotal, long yearsTotal, long yearTotal,
                     String year, String month, boolean canClick) {
        this.data = data;
        this.monthTotal = monthTotal;
        this.monthsTotal = monthsTotal;
        this.yearsTotal = yearsTotal;
        this.yearTotal = yearTotal;
        this.year = year;
        this.month = month;
        this.canClick = canClick;
    }

    public BarBeanBo() {
    }

    @Override
    public BarBeanBo clone() {
        BarBeanBo barBeanBo = null;
        try {
            barBeanBo = (BarBeanBo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return barBeanBo;
    }

    @Override
    public String toString() {
        return "BarBeanBo{" +
                ", monthTotal=" + monthTotal +
                ", yearTotal=" + yearTotal +
                '}';
    }
}
