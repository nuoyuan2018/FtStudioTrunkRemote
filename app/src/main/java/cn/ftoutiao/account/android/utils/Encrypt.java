package cn.ftoutiao.account.android.utils;

import android.text.TextUtils;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by alan on 2017/10/28.
 */

public class Encrypt {

    private static final String CS = "UTF-8";
    private static final String K = "FT!ou@T#iao$2016";

    /**
     * @return
     */
    public static String encrypt(long ts, String imei, String mobile) {

        try {
            String s = "";
            if (!TextUtils.isEmpty(mobile)) {
                s += imei + "&";
                s += ts + "&";
                s += mobile;
            }else {
                s += imei;
                s += ts; // 加上时间戳
            }
            System.out.println(s);
            return new String(Base64.encodeBase64(encrypt(s.getBytes(CS), K.getBytes(CS)), false), "utf-8");
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Encrypt data with key.
     *
     * @param data
     * @param key
     * @return
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                encrypt(toIntArray(data, false), toIntArray(key, false)), false);
    }

    /**
     * Encrypt data with key.
     *
     * @param v
     * @param k
     * @return
     */
    private static int[] encrypt(int[] v, int[] k) {
        int n = v.length;

        int y;
        int p;
        int rounds = 6 + 52 / n;
        int sum = 0;
        int z = v[n - 1];
        int delta = 0x9E3779B9;
        do {
            sum += delta;
            int e = (sum >>> 2) & 3;
            for (p = 0; p < n - 1; p++) {
                y = v[p + 1];
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                        + (k[p & 3 ^ e] ^ z);
            }
            y = v[0];
            z = v[n - 1] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                    + (k[p & 3 ^ e] ^ z);
        } while (--rounds > 0);

        return v;
    }

    /**
     * Convert byte array to int array.
     *
     * @param data
     * @param includeLength
     * @return
     */
    private static int[] toIntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0) ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;

        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }

    /**
     * Convert int array to byte array.
     *
     * @param data
     * @param includeLength
     * @return
     */
    private static byte[] toByteArray(int[] data, boolean includeLength) {
        int n = data.length << 2;

        if (includeLength) {
            int m = data[data.length - 1];

            if (m > n) {
                return null;
            } else {
                n = m;
            }
        }
        byte[] result = new byte[n];

        for (int i = 0; i < n; i++) {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return result;
    }

}
