package cn.ftoutiao.account.android.component.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by alan on 2017/10/10.
 */

public abstract class CachedFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private SparseArray<T> mRegisteredFragments = new SparseArray<>();

    public CachedFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T fragment = (T) super.instantiateItem(container, position);
        mRegisteredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public T getFragment(int position) {
        return mRegisteredFragments.get(position);
    }
}
