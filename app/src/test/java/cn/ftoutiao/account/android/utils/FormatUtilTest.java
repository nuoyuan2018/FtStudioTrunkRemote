package cn.ftoutiao.account.android.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Created by weichyang on 2018/4/28.
 */
public class FormatUtilTest {
    @Test
    public void formatBigNumber() throws Exception {
       // Assert.assertEquals("ddd",FormatUtil.formatBigNumber("0.0001078137"));
        Assert.assertEquals("2.56",FormatUtil.getDecimalMoney2("2.56001078137",2));
    }

    @Test
    public void format(){
//        Assert.assertEquals("6,655.80",formatTosepara(String.valueOf(6655.8063-0.005),2));
        Assert.assertEquals("5.80",formatTosepara(Float.valueOf(5.8055f)));
//        Assert.assertEquals("0.01",formatTosepara("0.01",2));
//        Assert.assertEquals("0.10",formatTosepara("0.10",2));
//        Assert.assertEquals("123.21",formatTosepara("123.21",2));
//        Assert.assertEquals("123,213,123.00",formatTosepara("123213123.0",2));
//        Assert.assertEquals("0.00",formatTosepara("0.00",2));

    }

    public static String formatTosepara(float data) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }
}