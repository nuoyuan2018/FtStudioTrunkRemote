package cn.ftoutiao.account.android.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.acmenxd.logger.Logger;

import cn.ftoutiao.account.android.R;
import cn.ftoutiao.account.android.utils.StringUtil;

import static cn.ftoutiao.account.android.widget.progressbar.NumberProgressBar.ProgressTextVisibility.Invisible;
import static cn.ftoutiao.account.android.widget.progressbar.NumberProgressBar.ProgressTextVisibility.Visible;

/**
 * Created by daimajia on 14-4-30.
 */
public class NumberProgressBar extends View {
    public String namespace = "cn.ftoutiao.account.android.widget.progressbar.NumberProgressBar";
    private int mMaxProgress = 100;

    /**
     * Current progress, can not exceed the max progress.
     */
    private int mCurrentProgress = 0;

    /**
     * The progress area bar color.
     */
    private int mReachedBarColor;

    /**
     * @Fields mProgresslenth : (用来给progress限制最大长度)
     */
    private int mProgresslenth = 100;
    private boolean mCircleShape;
    private String customText //自定义字体
            ;

    public int getProgresslenth() {
        return mProgresslenth;
    }

    public void setProgresslenth(int mProgresslenth) {
        this.mProgresslenth = mProgresslenth;
    }

    /**
     * The bar unreached area color.
     */
    private int mUnreachedBarColor;

    /**
     * The progress text color.
     */
    private int mTextColor;

    /**
     * The progress text size.
     */
    private float mTextSize;

    /**
     * The height of the reached area.
     */
    private float mReachedBarHeight;

    /**
     * The height of the unreached area.
     */
    private float mUnreachedBarHeight;

    /**
     * The suffix of the number.
     */
    private String mSuffix = "%";

    /**
     * The prefix.
     */
    private String mPrefix = "";

    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66, 145, 241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;

    /**
     * For save and restore instance of progressbar.
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";

    private static final int PROGRESS_TEXT_VISIBLE = 0;

    /**
     * The width of the text that to be drawn.
     */
    private float mDrawTextWidth;

    /**
     * The drawn text start.
     */
    private float mDrawTextStart;

    /**
     * The drawn text end.
     */
    private float mDrawTextEnd;

    /**
     * The text that to be drawn in onDraw().
     */
    private String mCurrentDrawText;

    /**
     * The Paint of the reached area.
     */
    private Paint mReachedBarPaint;
    /**
     * The Paint of the unreached area.
     */
    private Paint mUnreachedBarPaint;
    /**
     * The Paint of the progress text.
     */
    private Paint mTextPaint;

    /**
     * Unreached bar area to draw rect.
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * Reached bar area rect.
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    /**
     * The progress text offset.
     */
    private float mOffset;

    /**
     * Determine if need to draw unreached area.
     */
    private boolean mDrawUnreachedBar = true;

    private boolean mDrawReachedBar = true;

    private boolean mIfDrawText = true;

    private boolean mOnlyDrawRectF = true;

    private boolean textCustom = false; //是否展示自定义字体
    /**
     * Listener
     */
    private OnProgressBarListener mListener;

    public enum ProgressTextVisibility {
        Visible, Invisible
    }

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.numberProgressBarStyle);
    }

    public NumberProgressBar(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_reached_bar_height = dp2px(3f);
        default_unreached_bar_height = dp2px(2f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(3.0f);

        // load styled attributes.
        final TypedArray attributes = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.NumberProgressBar,
                        defStyleAttr, 0);

        mReachedBarColor = attributes.getColor(
                R.styleable.NumberProgressBar_progress_reached_color,
                default_reached_color);
        mUnreachedBarColor = attributes.getColor(
                R.styleable.NumberProgressBar_progress_unreached_color,
                default_unreached_color);
        mCircleShape = attributes.getBoolean(
                R.styleable.NumberProgressBar_progress_is_circle_shape,
                false);
        mTextColor = attributes.getColor(
                R.styleable.NumberProgressBar_progress_text_color,
                default_text_color);
        mTextSize = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_text_size,
                default_text_size);
        mProgresslenth = attrs.getAttributeIntValue(namespace, "progresslenth",
                0);
        mReachedBarHeight = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_reached_bar_height,
                default_reached_bar_height);
        mUnreachedBarHeight = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_unreached_bar_height,
                default_unreached_bar_height);
        mOffset = attributes.getDimension(
                R.styleable.NumberProgressBar_progress_text_offset,
                default_progress_text_offset);

        int textVisible = attributes.getInt(
                R.styleable.NumberProgressBar_progress_text_visibility,
                PROGRESS_TEXT_VISIBLE);

        textCustom = attributes.getBoolean(
                R.styleable.NumberProgressBar_progress_text_custom,
                false);
        if (textVisible != PROGRESS_TEXT_VISIBLE) {
            mIfDrawText = false;
        }

        setProgress(attributes.getInt(
                R.styleable.NumberProgressBar_progress_current, 0));
        setMax(attributes.getInt(R.styleable.NumberProgressBar_progress_max,
                100));

        attributes.recycle();
        initializePainters();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) mTextSize,
                Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true),
                measure(heightMeasureSpec, false) * 2);
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight()
                : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth()
                    : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIfDrawText || mOnlyDrawRectF) {
            calculateDrawRectF();
        } else {
            calculateDrawRectFWithoutProgressText();
        }
        if (mIfDrawText)
            if (mCurrentDrawText != null) {
                canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd,
                        mTextPaint);

            }
//        if (textCustom && !StringUtil.isEmpty(customText)) {
//            String value = customText;
//            Rect rect = new Rect();
//            mTextPaint.getTextBounds(value, 0, value.length(), rect);
//            int width = rect.width();//文字宽
//            float dif = mReachedRectF.right - width;
//            if (dif < 0) {
//                mTextPaint.setColor(Color.parseColor("#ff0000"));
//                float singleTextWidth = (width / value.length());
//                float textcount = (dif / singleTextWidth);
//                try {
//                    canvas.drawText(value, (int) Math.abs(textcount),
//                            value.length() - 1, mReachedRectF.right, mReachedRectF.top + (mReachedRectF.bottom -
//                                    mReachedRectF.top), mTextPaint);
//                } catch (Exception e) {
//                    Logger.e(e.getMessage());
//                }
//
//            }
//        }
        if (mDrawReachedBar) {
            canvas.drawRect(mReachedRectF, mReachedBarPaint);
        }


        if (mDrawUnreachedBar) {
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }
        //兼容处理
        if (mCircleShape) {
            canvas.drawCircle(mReachedRectF.left, (mReachedRectF.bottom - mReachedRectF.top) / 2 + mReachedRectF.top, default_reached_bar_height - default_reached_bar_height / 6, mReachedBarPaint);
            canvas.drawCircle(mReachedRectF.right, (mReachedRectF.bottom - mReachedRectF.top) / 2 + mReachedRectF.top, default_reached_bar_height - default_reached_bar_height / 6, mReachedBarPaint);

        }

    }

    private void initializePainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);
        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    private void calculateDrawRectFWithoutProgressText() {
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        if (getMax() * getProgress() <= getProgresslenth()) {
            mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight())
                    / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        }
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;
        // mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.right = getWidth();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight
                / 2.0f;
    }

    private void calculateDrawRectF() {
        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
        // no offset
        mOffset = 0;
        if (getProgresslenth() >= Integer.valueOf(mCurrentDrawText)) {
            mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
            mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);
            if (getProgress() == 0) {
                mDrawReachedBar = false;
                mDrawTextStart = getPaddingLeft();
            } else {
                mDrawReachedBar = true;
                mReachedRectF.left = getPaddingLeft();
                // mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight /
                // 2.0f+2;
                mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight
                        / 2.0f;
                mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight())
                        / (getMax() * 1.0f)
                        * getProgress()
                        - mOffset
                        + getPaddingLeft();
                mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight
                        / 2.0f;
                mDrawTextStart = (mReachedRectF.right + mOffset);
            }
            mDrawTextEnd = (int) ((getHeight() / 2.0f) - 12);
            if ((mDrawTextStart + mDrawTextWidth) >= getWidth()
                    - getPaddingRight()) {
                mDrawTextStart = getWidth() - getPaddingRight()
                        - mDrawTextWidth;
                mReachedRectF.right = mDrawTextStart - mOffset;
            }

            float unreachedBarStart = mDrawTextStart;
            // float unreachedBarStart=0f;
            if (unreachedBarStart >= getWidth() - getPaddingRight()
                    || getProgress() == 100) {
                mDrawUnreachedBar = false;
                mReachedRectF.right = getWidth() - getPaddingRight();
            } else {
                mDrawUnreachedBar = true;
                mUnreachedRectF.left = unreachedBarStart;
                mUnreachedRectF.right = getWidth() - getPaddingRight();
                mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight
                        / 2.0f;
                mUnreachedRectF.bottom = getHeight() / 2.0f
                        + mUnreachedBarHeight / 2.0f;
            }
        } else {
            mCurrentDrawText = mPrefix + getProgresslenth() + mSuffix;
        }
    }

    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }

    public float getReachedBarHeight() {
        return mReachedBarHeight;
    }

    public float getUnreachedBarHeight() {
        return mUnreachedBarHeight;
    }

    public void setProgressTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this.mUnreachedBarColor = barColor;
        mUnreachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this.mReachedBarColor = progressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setReachedBarHeight(float height) {
        mReachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height) {
        mUnreachedBarHeight = height;
    }

    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            mPrefix = "";
        else {
            mPrefix = prefix;
        }
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(getProgress() + by);
        }

        if (mListener != null) {
            mListener.onProgressChange(getProgress(), getMax());
        }
    }

    public void setProgress(int progress) {
        if (progress <= getMax() && progress >= 0) {
            this.mCurrentProgress = progress;
            invalidate();
        }
    }

    public void setProgress(int progress, String customText) {
        this.customText = customText;
        if (progress <= getMax() && progress >= 0) {
            this.mCurrentProgress = progress;
            invalidate();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString(INSTANCE_PREFIX, getPrefix());
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
            mUnreachedBarHeight = bundle
                    .getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
            mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
            mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
            initializePainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setPrefix(bundle.getString(INSTANCE_PREFIX));
            setSuffix(bundle.getString(INSTANCE_SUFFIX));
            setProgressTextVisibility(bundle
                    .getBoolean(INSTANCE_TEXT_VISIBILITY) ? Visible : Invisible);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public void setProgressTextVisibility(ProgressTextVisibility visibility) {
        mIfDrawText = visibility == Visible;
        invalidate();
    }


    public void setProgresOnlyDrawReached(boolean isDraw) {
        this.mOnlyDrawRectF = isDraw;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return mIfDrawText;
    }

    public void setOnProgressBarListener(OnProgressBarListener listener) {
        mListener = listener;
    }
}
