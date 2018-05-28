package cn.ftoutiao.account.android.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MesureGrid extends GridView {

    public boolean hasScrollBar = true;

    public MesureGrid(Context context) {
        this(context, null);
    }

    public MesureGrid(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MesureGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = heightMeasureSpec;
        if (hasScrollBar) {
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);// 注意这里,这里的意思是直接测量出GridView的高度
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}