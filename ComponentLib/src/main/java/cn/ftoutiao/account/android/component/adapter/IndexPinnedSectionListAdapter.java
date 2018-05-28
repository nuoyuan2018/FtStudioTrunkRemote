package cn.ftoutiao.account.android.component.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;

import cn.ftoutiao.account.android.component.widget.list.PinnedSectionListView;

/**
 * Created by alan on 2017/10/19.
 */

public class IndexPinnedSectionListAdapter extends BaseListAdapter implements PinnedSectionListView.PinnedSectionListAdapter, SectionIndexer {

    public IndexPinnedSectionListAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
