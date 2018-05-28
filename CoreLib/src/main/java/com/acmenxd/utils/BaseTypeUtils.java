package com.acmenxd.utils;

import android.text.TextUtils;
import android.util.SparseArray;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by alan on 2017/9/19.
 */

public class BaseTypeUtils {

    private BaseTypeUtils() {

    }

    /**
     * 字符串转整型值
     *
     * @param str
     */
    public static int stoi(String str) {
        int value = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 字符串转整型值
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static int stoi(String str, final int defaultValue) {
        int value = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * string to float
     *
     * @param str
     * @return
     */
    public static float stof(String str) {
        float value = 0.0f;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Float.parseFloat(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * string to float
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static float stof(String str, float defaultValue) {
        float value = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Float.parseFloat(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * string to double
     *
     * @param str
     * @return
     */
    public static double stod(String str) {
        double value = 0.0;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * string to double
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static double stod(String str, final double defaultValue) {
        double value = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * string to long
     *
     * @param str
     * @return
     */
    public static long stol(String str) {
        long value = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Long.parseLong(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * string to long
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    public static long stol(String str, final long defaultValue) {
        long value = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                value = Long.parseLong(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * list是否为空
     *
     * @param list
     * @return true if list is null or zero size
     */
    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * set是否为空
     *
     * @param set
     * @return true if set is null or zero size
     */
    public static <T> boolean isSetEmpty(Set<T> set) {
        return set == null || set.isEmpty();
    }

    /**
     * 队列是否为空
     *
     * @param queue
     * @return
     */
    public static <T> boolean isQueueEmpty(Queue<T> queue) {
        return queue == null || queue.isEmpty();
    }

    /**
     * 获取list size
     *
     * @param list
     * @return
     */
    public static int getListSize(List list) {
        if (isListEmpty(list)) {
            return 0;
        }

        return list.size();
    }

    /**
     * 从list中获取数据
     *
     * @param list
     * @param index 索引
     * @return
     */
    public static <T> T getElementFromList(List<T> list, int index) {
        if (isListEmpty(list)) {
            return null;
        }

        if (index < 0 || index >= list.size()) {
            return null;
        }

        return list.get(index);
    }

    /**
     * 从list中移除数据
     *
     * @param list
     * @param index 索引
     * @param <T>
     */
    public static <T> void removeElementFromList(List<T> list, int index) {
        if (isListEmpty(list))
            return;
        if (index < 0 || index >= list.size())
            return;
        list.remove(index);
    }

    /**
     * array是否为空
     *
     * @param array
     * @return true if array is null or zero size
     */
    public static <T> boolean isArrayEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 从array中获取数据
     *
     * @param array
     * @param index 索引
     * @return
     */
    public static <T> T getElementFromArray(T[] array, int index) {
        return getElementFromArray(array, index, null);
    }

    /**
     * 从array中获取数据
     *
     * @param array
     * @param index
     * @param defaultT
     * @param <T>
     * @return
     */
    public static <T> T getElementFromArray(T[] array, int index, T defaultT) {
        if (isArrayEmpty(array)) {
            return defaultT;
        }

        if (index < 0 || index >= array.length) {
            return defaultT;
        }

        return array[index];
    }

    /**
     * map是否为空
     *
     * @param map
     * @return true if map is null or zero size
     */
    public static boolean isMapEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    /**
     * map是否包含key
     *
     * @param map
     * @param key
     * @return
     */
    public static boolean isMapContainsKey(Map<? extends Object, ? extends Object> map, Object key) {
        return key != null && !isMapEmpty(map) && map.containsKey(key);
    }

    /**
     * map是否包含key
     *
     * @param map
     * @param key
     * @return
     */
    public static <T> T getElementFromMap(Map<? extends Object, T> map, Object key) {
        if (isMapContainsKey(map, key)) {
            return map.get(key);
        }

        return null;
    }

    /**
     * 防止string为空
     *
     * @param str
     * @return
     */
    public static String ensureStringValidate(String str) {
        return str == null ? "" : str;
    }

    /**
     * 防止string为空 0或者其他数字
     *
     * @param str
     * @return
     */
    public static String ensureStringValidateNumber(String str) {
        return TextUtils.isEmpty(str) ? "0" : str;
    }

    /**
     * SparseArray 是否为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isSparseArrayEmpty(SparseArray<T> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 通过value获取SparseArray对应的第一个key
     *
     * @param sparseArray
     * @param value
     * @return
     */
    public static int getSparseArrayFirstKeyByValue(SparseArray<String> sparseArray, String value) {
        if (sparseArray == null || value == null)
            return -1;

        for (int i = 0; i < sparseArray.size(); i++) {
            if (TextUtils.equals(sparseArray.valueAt(i), value)) {
                return sparseArray.keyAt(i);
            }
        }

        return -1;
    }

}
