package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.util.MeasureUtil;

/**
 * Created by Oubowu on 2018/1/15 0:56.
 */
public class PagerIndicator extends View {

    private final int mDefaultSize;
    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private float mOffset;
    private int mCurrentPosition;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDefaultSize = MeasureUtil.dip2px(context, 100);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(MeasureUtil.dip2px(context, 2));
        mPaint.setStyle(Paint.Style.STROKE);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 考虑padding值
        mWidth = measureSize(widthMeasureSpec, mDefaultSize) + getPaddingLeft() + getPaddingRight();
        mHeight = measureSize(heightMeasureSpec, mDefaultSize / 6) + getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float radius = getHeight() / 4.0f;

        float diameter = radius * 2;

        float margin = mPaint.getStrokeWidth() * 4;

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 4; i++) {
            canvas.drawCircle(getPaddingLeft() + mPaint.getStrokeWidth() / 2 + radius + i * (margin + diameter + mPaint.getStrokeWidth()), getHeight() / 2, radius,
                    mPaint);
        }

        radius = (diameter - mPaint.getStrokeWidth()) / 2.0f;
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent1));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getPaddingLeft() + mPaint.getStrokeWidth() + radius + mOffset * (margin + diameter + mPaint
                        .getStrokeWidth()) + ((mCurrentPosition == 0 ? 1 : mCurrentPosition) - 1) * (margin + diameter + mPaint.getStrokeWidth()), getHeight() / 2, radius,
                mPaint);

    }

    public void attachViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                PagerAdapter adapter = viewPager.getAdapter();
                if (adapter != null) {
                    int count = adapter.getCount();
                    if (count > 1) {
                        mCurrentPosition = position;
                        mOffset = positionOffset;
                        if (mCurrentPosition == count - 2 || mCurrentPosition == 0) {
                            mOffset = 0;
                        }
                        invalidate();
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
