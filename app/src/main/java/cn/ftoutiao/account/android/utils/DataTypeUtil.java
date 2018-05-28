package cn.ftoutiao.account.android.utils;

import java.nio.ByteBuffer;


/**
 * This class provides convenient functions to convert hex string to byte array and vice versa.
 * @author 99bill
 *
 */
public class DataTypeUtil {

    private DataTypeUtil() {}

    /**
     * Converts a byte array to hex string.
     *
     * @param b -
     *            the input byte array
     * @return hex string representation of b.
     */

    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(DataTypeUtil.HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
            sb.append(DataTypeUtil.HEX_CHARS.charAt(b[i] & 0x0F));
        }
        return sb.toString();
    }

    /**
     * Converts a hex string into a byte array.
     *
     * @param s -
     *            string to be converted
     * @return byte array converted from s
     */
    public static byte[] toByteArray(String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(s.charAt(j++), 16) << 4) | Character.digit(s.charAt(j++), 16));
        }
        return buf;
    }
    
    /**
     * 转换byte数组为long型(<strong>仅取前8个字节</strong>).
     * @param data byte数组
     * @return long
     */
    public static final long bytesToLong(byte[] data) {
    	ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);
        buffer.put(data);
        buffer.flip();	//need flip 
        return buffer.getLong();
    }

    private static final String HEX_CHARS = "0123456789abcdef";

    public static String appendParam(String returnStr, String paramId, String paramValue) {
        if (!returnStr.equals("")) {
            if (!paramValue.equals("")) {
                returnStr = returnStr + "&" + paramId + "=" + paramValue;
            }
        } else {
            if (!paramValue.equals("")) {
                returnStr = paramId + "=" + paramValue;
            }
        }
        return returnStr;
    }
}
