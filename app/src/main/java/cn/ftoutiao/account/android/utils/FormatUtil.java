package cn.ftoutiao.account.android.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormatUtil {
    public static String afterdecimaldouble(String str) {
        BigDecimal bd = new BigDecimal(str);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }


    /**
     * 格式化
     *
     * @param formatNum
     * @return
     */
    public static String formatBigNumber(String formatNum) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(10);
        return nf.format(formatNum);
    }

    public static String getDoubleRealMoney(String money) {
        String[] s = money.split(",");
        money = "";
        for (int i = 0; i < s.length; i++) {
            money += s[i];
        }
        return money.replaceAll("\\s*", "");
    }

    public static String getRealMoney(String money) {
        if (StringUtil.isEmpty(money)) {
            return "0";
        }
        if (money.endsWith(".00")) {
            money = money.substring(0, money.length() - 3);
        }
        if (money.endsWith(".0")) {
            money = money.substring(0, money.length() - 2);
        }
        if (money.endsWith(".")) {
            money = money.substring(0, money.length() - 1);
        }
        String[] s = money.split(",");
        money = "";
        for (int i = 0; i < s.length; i++) {
            money += s[i];
        }
        return money.replaceAll("\\s*", "");
    }

    /**
     * @param money 金额
     * @param len   小数位数
     * @return 格式化的金额 ，针对整数
     */
    public static String getFromatMoney(String money, int len) {
        if (StringUtil.isEmpty(money)) return "";
        if (!money.endsWith(".00")) money = money + ".00";
        NumberFormat nf = null;
        double num = Double.parseDouble(money);
        if (len == 0)
            nf = new DecimalFormat("###,###");
        else {
            StringBuffer sb = new StringBuffer();
            sb.append("###,###.00");
            for (int i = 0; i < len; i++) {
                sb.append("#");
            }
            nf = new DecimalFormat(sb.toString());
        }
        return nf.format(num);
    }

    /**
     * 小数点后是有值的formatMoeny
     * http://blog.163.com/wangzhengquan85@126/blog/static/36082995201182111847325/
     *
     * @param meney 必须大于1 ，小于1 会有0.83--->.83 异常
     * @param len
     * @return
     */
    @Deprecated
    public static String getDecimalMoney(String meney, int len) {
        if (StringUtil.isEmpty(meney)) return "0.00";
        NumberFormat nf = null;
        double num = Double.parseDouble(meney);
        if (len == 0)
            nf = new DecimalFormat("###,###");
        else {
            StringBuffer sb = new StringBuffer();
            sb.append("###,###.00");
            for (int i = 0; i < len; i++) {
                sb.append("#");
            }
            nf = new DecimalFormat(sb.toString());
        }
        return nf.format(num);
    }

    /**
     * 获的全分计算格式
     * 100 ->10000 分
     * 100.00 ->10000 分
     * 1，000.00 ->100000 分
     *
     * @param money
     * @return
     */
    public static String getMinuteOfArc(String money) {
        if (money.endsWith(".00")) {
            money = money.substring(0, money.length() - 3);
        }
        String[] s = money.split(",");
        money = "";
        for (int i = 0; i < s.length; i++) {
            money += s[i];
        }
        return money + "00";
    }

    /**
     * 其他功能同getDecimalMoney（var,var）
     * .00 -->0.00
     *
     * @param meney
     * @param len
     * @return
     */
    public static String getDecimalMoney2(String meney, int len) {
        if (StringUtil.isEmpty(meney)) return "0.00";
        NumberFormat nf = null;
        double num = Double.parseDouble(meney.replaceAll(",", ""));
        if (len == 0)
            nf = new DecimalFormat("###,###");
        else {
            StringBuffer sb = new StringBuffer();
            sb.append("###,###.00");
            nf = new DecimalFormat(sb.toString());
        }
        String changeValue = nf.format(num);
        //-0.005 控制不进行四舍五入操作
        return (changeValue.startsWith(".")) ? "0" + changeValue : changeValue;
    }
    public static String getDecimalMoney22(String meney, int len) {
        if (StringUtil.isEmpty(meney)) return "0.00";
        NumberFormat nf = null;
        double num = Double.parseDouble(meney.replaceAll(",", ""));
        if (len == 0)
            nf = new DecimalFormat("######");
        else {
            StringBuffer sb = new StringBuffer();
            sb.append("######.00");
            nf = new DecimalFormat(sb.toString());
        }
        String changeValue = nf.format(num);
        //-0.005 控制不进行四舍五入操作
        return (changeValue.startsWith(".")) ? "0" + changeValue : changeValue;
    }

    /**
     * earn //如果是
     * xxx.00 去掉.00
     *
     * @param transportMoney
     * @param len
     * @return
     */
    public static String getDecimalMoney3(long transportMoney, int len) {
        if (transportMoney == 0)
            return "0";
        String meney = String.valueOf(transportMoney);
        if (StringUtil.isEmpty(meney)) return "0";
        NumberFormat nf = null;
        double num = Double.parseDouble(meney) / 100;
        if (len == 0)
            nf = new DecimalFormat("######");
        else {
            StringBuffer sb = new StringBuffer();
            sb.append("######.00");
            for (int i = 0; i < len; i++) {
                sb.append("#");
            }
            nf = new DecimalFormat(sb.toString());
        }
        String changeValue = nf.format(num);
        if (changeValue.startsWith(".")) {
            changeValue = "0" + nf.format(num);
            return changeValue;
        }
        if (changeValue.length() >= 4 && changeValue.substring(changeValue.length() - 3, changeValue.length()).equals(".00")) {
            changeValue = changeValue.substring(0, changeValue.length() - 3);
        }
        return changeValue;
    }

    public static String getDecimalMoney4(long transportMoney, int len) {
        if (transportMoney == 0)
            return "0.00";
        String meney = String.valueOf(transportMoney);
        if (StringUtil.isEmpty(meney)) return "0.00";
        NumberFormat nf = null;
        double num = Double.parseDouble(meney) / 100;
        if (len == 0)
            nf = new DecimalFormat("######.00");
        else {
            StringBuffer sb = new StringBuffer();
            sb.append("######.00");
            for (int i = 0; i < len; i++) {
                sb.append("#");
            }
            nf = new DecimalFormat(sb.toString());
        }
        String changeValue = nf.format(num);
        if (changeValue.startsWith(".")) {
            changeValue = "0" + nf.format(num);
            return changeValue;
        }
//        if (changeValue.length() >= 4 && changeValue.substring(changeValue.length() - 3, changeValue.length()).equals(".00")) {
//            changeValue = changeValue.substring(0, changeValue.length() - 3);
//        }
        return changeValue;
    }

    /**
     * 格式化 money
     * 1.22-->1.2万
     * 0.5- 0.5万
     * 1.0->万
     *
     * @param money
     * @param len
     */
    public static String formatMontyDecimal(long money, int len) {
        String tempMoney = String.valueOf(money / 1000000f);
        if (tempMoney.contains(".")) {
            String[] splitV = tempMoney.split("\\.");
            String temp = splitV[1].substring(0, len).equals("0") ? "" : "." + splitV[1].substring(0, len);
            tempMoney = splitV[0] + temp;
        }
        return tempMoney;
    }


    /**
     * @param sellOutMillisecond 售罄时间
     * @param curTimeMillisecond 当前时间
     * @return
     */
    public static String showTimeState(long sellOutMillisecond, long curTimeMillisecond) {

        long calculateTime = curTimeMillisecond - sellOutMillisecond;
        if (calculateTime <= 0 || sellOutMillisecond <= 0) {
            return "刚刚";
        }
        long days = calculateTime / (1000 * 60 * 60 * 24);
        long hours = (calculateTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        String returnValue = "";
        if (days > 0 || hours > 23) {
            //显示日期
            DateFormat formatter = new SimpleDateFormat("M月d日");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(sellOutMillisecond);
            returnValue = formatter.format(calendar.getTime());
        } else if (days <= 0 && hours >= 1 && hours <= 23) {
            //显示小时数
            returnValue = hours + "小时前";
        } else if (days == 0 && hours == 0) {
            returnValue = "刚刚";
        }
        return returnValue;
    }

    /**
     * 返回时间格式化的字符串
     *
     * @param unit   格式化标记
     * @param period 时间
     * @return
     */
    public static String getProTimeStr(int unit, long period) {
        switch (unit) {
            case 1:
                return period + "月期";
            case 2:
                return period + "天";
            case 0:
                return period + "年";
            default:
                return period + "月期";
        }
    }

    /**
     * 返回时间规则 ： 天/年/月
     * //借款单位 0y1m2d
     */
    public static String getProTime(int unit) {
        switch (unit) {
            case 1:
                return "月";
            case 2:
                return "天";
            case 0:
                return "年";
            default:
                return "月";
        }
    }

    /**
     * 毫秒转换为日期
     *
     * @param timemillis
     * @return
     */
    public static String TimeMillisToDate(long timemillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timemillis);
        return formatter.format(calendar.getTime()) + "";
    }

    public static String timeMillisToDate(long timemillis) {
        DateFormat formatter = new SimpleDateFormat("MM.dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timemillis);
        return formatter.format(calendar.getTime()) + "";
    }

    public static String periodToString(Long millisecond) {
        String str = "";
        long day = millisecond / 86400000;
        long hour = (millisecond % 86400000) / 3600000;
        long minute = (millisecond % 86400000 % 3600000) / 60000;
        if (day > 0) {
            str = String.valueOf(day);
        }
        if (hour > 0) {
            str += String.valueOf(hour) + "小时";
        }
        if (minute > 0) {
            str += String.valueOf(minute) + "分钟";
        }
        return str;
    }

    /*
     * 毫秒转化时分秒毫秒
     */
    public static String formatmillisToDateTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "小时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        return sb.toString();
    }
}