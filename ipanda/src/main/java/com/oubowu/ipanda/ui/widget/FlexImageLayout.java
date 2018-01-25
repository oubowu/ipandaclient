package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.ui.adapter.FlexImageAdapter;
import com.oubowu.ipanda.util.MeasureUtil;

/**
 * Created by Oubowu on 2018/1/22 11:37.
 */
public class FlexImageLayout extends ViewGroup {

    private float mSpacing;
    private int mColumnNum;

    private FlexImageAdapter mAdapter;

    public FlexImageLayout(Context context) {
        this(context, null);
    }

    public FlexImageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexImageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private class FlexImageObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            addChildView(mAdapter);
        }

    }

    public void setAdapter(FlexImageAdapter adapter) {

        adapter.registerDataSetObserver(new FlexImageObserver());

        addChildView(adapter);

    }

    private void addChildView(FlexImageAdapter adapter) {
        mAdapter = adapter;

        removeAllViews();

        for (int i = 0; i < adapter.getCount(); i++) {
            final View child = adapter.getView(i, null, this);
//            Log.e("FlexImageLayout","63行-addChildView(): "+i);
            addView(child);
        }

//        requestLayout();

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexImageLayout);
            mSpacing = typedArray.getDimension(R.styleable.FlexImageLayout_spacing, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desireHeight = 0;
        int desireWidth = 0;

        int parentWidth = measureSize(widthMeasureSpec, MeasureUtil.dip2px(getContext(), 200));

        desireWidth = parentWidth;

        int count = getChildCount();

        if (count % 3 == 0) {
            // 3列形式
            mColumnNum = 3;
        } else if (count == 2 || count == 4) {
            // 2列形式
            mColumnNum = 2;
        } else {
            // 3列形式
            mColumnNum = 3;
        }

        int imageWidth = (int) ((parentWidth - getPaddingLeft() - getPaddingRight() - (mColumnNum == 2 ? mSpacing : mSpacing * 2)) / mColumnNum);

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (!(child instanceof ImageView)) {
                throw new IllegalArgumentException("子控件只允许ImageView类型");
            }

            ImageView imageView = (ImageView) child;
            imageView.measure(MeasureSpec.makeMeasureSpec(imageWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(imageWidth, MeasureSpec.EXACTLY));

            int imageViewHeight = imageView.getMeasuredHeight();

            // Log.e("FlexImageLayout","115行-onMeasure(): "+imageWidth+";"+imageViewHeight);

            int column = i % mColumnNum;

            if (column == 0) {
                // 第一列
                desireHeight += imageViewHeight;
                if (i != 0) {
                    // 非第一列第一行，加上间隔
                    desireHeight += mSpacing;
                }
            }
        }

        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());

        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec), resolveSize(desireHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int row = -1;
        int column;

        for (int i = 0; i < getChildCount(); i++) {

            column = i % mColumnNum;

            if (column == 0) {
                row++;
            }

            View child = getChildAt(i);

            int left = (int) (getPaddingLeft() + column * (child.getMeasuredWidth() + mSpacing));

            int right = left + child.getMeasuredWidth();

            int top = (int) (getPaddingTop() + row * (child.getMeasuredHeight() + mSpacing));

            int bottom = top + child.getMeasuredHeight();

            child.layout(left, top, right, bottom);

        }

    }

    // 测量尺寸
    private int measureSize(int measureSpec, int defaultSize) {

        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(size, defaultSize);
        }

        return size;
    }

    // 生成默认的布局参数
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    // 生成布局参数,将布局参数包装成我们的
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    // 生成布局参数,从属性配置中生成我们的布局参数
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    // 查当前布局参数是否是我们定义的类型这在code声明布局参数时常常用到
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }


}
