package cn.ftoutiao.account.android.component.adapter;

import android.content.Context;
import android.widget.BaseAdapter;



import java.util.ArrayList;
import java.util.List;

import cn.ftoutiao.account.android.component.util.BaseTypeUtils;


/**
 * baseAdapter基类
 *
 * @author alan
 *
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context mContext;

    /**
     * 数据源
     */
    protected List<T> mList = new ArrayList<T>();

    /**
     * 一行显示多少个itemView
     */
    protected int mItemCountInOneLine = 1;

    public BaseListAdapter(Context context) {
        mContext = context;
    }

    /**
     * 设置数据源
     *
     * @param list 数据列表
     */
    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    /**
     * 添加数据源
     *
     * @param list
     */
    public void addList(List<T> list) {
        if (BaseTypeUtils.isListEmpty(list)) {
            return;
        }

        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 设置为单个数据
     *
     * @param t
     */
    public void setData(T t) {
        if (t == null) {
            return;
        }

        mList.clear();
        mList.add(t);
        notifyDataSetChanged();
    }

    /**
     * 添加单个数据
     *
     * @param t
     */
    public void addData(T t) {
        if (t == null) {
            return;
        }

        mList.add(t);
        notifyDataSetChanged();
    }

    /**
     * 清空数据源数据
     */
    public void clear(){
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (BaseTypeUtils.isListEmpty(mList) || mItemCountInOneLine == 0) {
            return 0;
        }

        return getCount(mList, mItemCountInOneLine);
    }

    protected int getCount(List<?> list, int itemCountInOneLine) {
        return (list.size() + itemCountInOneLine - 1) / itemCountInOneLine;
    }

    @Override
    public T getItem(int position) {
        if (mItemCountInOneLine == 1) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
