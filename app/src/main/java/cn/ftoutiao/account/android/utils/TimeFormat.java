package cn.ftoutiao.account.android.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by yangweichao on 2018/3/6.
 */

public class TimeFormat {


    public static String mill2Weekdate(String milliontime) {

        return "";
    }

    /**
     * 毫秒转换为日期
     *
     * @param timemillis
     * @return
     */
    public static String TimeMillisToDate(long timemillis) {
        DateFormat formatter = new SimpleDateFormat("MM月dd日 EE");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timemillis);
        return formatter.format(calendar.getTime()) + "";
    }
}
