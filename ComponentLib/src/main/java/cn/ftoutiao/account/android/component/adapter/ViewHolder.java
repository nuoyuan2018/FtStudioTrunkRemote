package cn.ftoutiao.account.android.component.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;


/**
 * adapter对应的viewHolder
 *
 * @author alan
 */
public class ViewHolder {
    /**
     * 绑定的自定义viewHolder
     **/
    public Object bindObj;
    private View convertView;
    private SparseArray<View> viewArray;
    private SparseArray<SparseArray<View>> childViewArray;
    /**
     * 绑定的layoutid
     **/
    private int mLayoutId;

    private ViewHolder(Context context, int layoutId) {
        this.mLayoutId = layoutId;
        viewArray = new SparseArray<>();
        childViewArray = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(layoutId, null);
        convertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, int layoutId) {
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder)) {
            return new ViewHolder(context, layoutId);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
       // Timber.d("ViewHolder", "holder.getLayoutId()=" + holder.getLayoutId() + ",layoutId=" + layoutId);
        if (holder.getLayoutId() != layoutId) {
            return new ViewHolder(context, layoutId);
        }

        return holder;
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public <T extends View> T getView(int viewId) {
        View view = viewArray.get(viewId);

        if (view == null) {
            view = convertView.findViewById(viewId);
            viewArray.put(viewId, view);
        }

        return (T) view;
    }

    /**
     * 获取parent下面的view
     *
     * @param parentId
     * @param viewId
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public <T extends View> T getView(int parentId, int viewId) {
        if (childViewArray.get(parentId) == null) {
            childViewArray.put(parentId, new SparseArray<View>());

            View view = getView(parentId).findViewById(viewId);
            childViewArray.get(parentId).put(viewId, view);

            return (T) view;
        } else {
            View view = childViewArray.get(parentId).get(viewId);

            if (view == null) {
                view = getView(parentId).findViewById(viewId);
                childViewArray.get(parentId).put(viewId, view);
            }

            return (T) view;
        }
    }

    /**
     * 获取convertView
     *
     * @return
     */
    public View getConvertView() {
        return convertView;
    }

    /**
     * 获取layoutId
     *
     * @return
     */
    public int getLayoutId() {
        return mLayoutId;
    }
}
