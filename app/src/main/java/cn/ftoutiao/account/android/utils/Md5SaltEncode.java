package cn.ftoutiao.account.android.utils;

import com.acmenxd.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 利用MD5生成摘要
 *
 * @author jzy created by May 6, 2010 10:47:22 AM
 */

public class Md5SaltEncode {

    // 用来将字节转换成 16 进制表示的字符
    // static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
    // '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    // static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
    // '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /** */
    /**
     * 对文件全文生成MD5摘要
     *
     * @param file 要加密的文件
     * @return MD5摘要码 未使用salt
     */
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byteToHexStringSingle(b);// byteToHexString(b);
            // 16位加密
            // return buf.toString().substring(8, 24);
        } catch (Exception ex) {
            // logger.error(ex);
            ex.printStackTrace();
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 对一段String生成MD5加密信息
     *
     * @param message 要加密的String
     * @return 生成的MD5信息 未使用salt
     */
    public static String getMD5(String message) {
        MessageDigest md = null;
        byte[] b = null;
        try {
            md = MessageDigest.getInstance("MD5");
            b = md.digest(message.getBytes("utf-8"));
            String digestCode = byteToHexStringSingle(b);
            return digestCode;
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e);
        } catch (UnsupportedEncodingException e) {
            Logger.e(e);
        } finally {
            b = null;
            md = null;
        }
        return null;
    }

    /**
     * 一段String生成MD5加密信息
     *
     * @param message 传入的信息
     * @param salt    密码盐
     * @return 生成的MD5信息
     * @Description: TODO
     */
    public static String getMD5(String message, String salt) {
        MessageDigest md = null;
        byte[] b = null;
        try {
            md = MessageDigest.getInstance("MD5");
            StringBuffer msgSb = new StringBuffer(message);
            msgSb.append(salt);
            b = md.digest(msgSb.toString().getBytes("utf-8"));
            String digestCode = byteToHexStringSingle(b);
            return digestCode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            b = null;
            md = null;
        }
        return null;
    }

    public static String hashEncodeStr(String key) {
        return getMD5(key, String.valueOf(HashUtils.getHexHash(key)));
    }

    /**
     * 独立把byte[]数组转换成十六进制字符串表示形式
     *
     * @param byteArray
     * @return
     * @author jzy
     * @create 2010-2-24 下午03:26:53
     */
    public static String byteToHexStringSingle(byte[] byteArray) {
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    /**
     * @param args
     * @author jzy
     * @create 2010-2-24 下午03:04:53
     */
    public static void main(String[] args) {
    }

}
