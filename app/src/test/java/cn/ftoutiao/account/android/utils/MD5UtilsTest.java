package cn.ftoutiao.account.android.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by yangweichao on 2018/4/27.
 */
public class MD5UtilsTest {
    @Test
    public void getPwdHash() throws Exception {
        //Assert.assertEquals("DD",MD5Utils.MD5("123456"));
        Assert.assertEquals("b3f178836bdc3bef23fd1b7db2e38fd4",MD5Utils.getPwdHash("q11111","DOcQF8dlXS"));


    }

    @Test
    public void MD5() throws Exception {

    }

}