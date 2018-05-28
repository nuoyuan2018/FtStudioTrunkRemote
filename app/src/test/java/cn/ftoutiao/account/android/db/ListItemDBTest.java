package cn.ftoutiao.account.android.db;

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by weichyang on 2018/4/3.
 */
public class ListItemDBTest {


    @Test
    public void ee() {
        Date date = new Date(1517886752);
        DateTime d2 = new DateTime(date);
       // Assert.assertEquals("dd", d2);
        Assert.assertEquals(TimeStamp2Date("1517886752", ""), "");
    }


    public static String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(timestamp));
        return date;
    }

}