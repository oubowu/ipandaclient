package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
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

    private float mDefaultWidth;
    private float mDefaultHeight;
    private float mMargin;

    private int mHollowCircleColor = Color.WHITE;
    private int mSolidCircleColor = ContextCompat.getColor(getContext(), R.color.colorAccent1);
    private float mHollowCircleStrokeWidth = MeasureUtil.dip2px(getContext(), 2);
    private float mHollowCircleRadius = MeasureUtil.dip2px(getContext(), 4);

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private float mOffset;
    private int mCurrentPosition;

    private int mItemCount = 3;
    private int mLastItemCount = 3;

    private boolean mAlowDraw;

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicator);

            mHollowCircleColor = typedArray.getColor(R.styleable.PagerIndicator_hollowCircleColor, Color.WHITE);
            mSolidCircleColor = typedArray.getColor(R.styleable.PagerIndicator_solidCircleColor, ContextCompat.getColor(context, R.color.colorAccent1));

            mHollowCircleStrokeWidth = typedArray.getDimension(R.styleable.PagerIndicator_hollowCircleStrokeWidth, MeasureUtil.dip2px(context, 2));
            mHollowCircleRadius = typedArray.getDimension(R.styleable.PagerIndicator_hollowCircleRadius, MeasureUtil.dip2px(context, 4));

            typedArray.recycle();
        }

        mMargin = mHollowCircleStrokeWidth * 4;

        mDefaultWidth = getPaddingLeft() + getPaddingRight() + (mHollowCircleRadius * 2 + mHollowCircleStrokeWidth) * mItemCount + (mItemCount - 1) * mMargin;
        mDefaultHeight = getPaddingTop() + getPaddingBottom() + (mHollowCircleRadius * 2 + mHollowCircleStrokeWidth);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(mHollowCircleColor);
        mPaint.setStrokeWidth(mHollowCircleStrokeWidth);
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

        if (getPaddingLeft() + getPaddingRight() > 0) {
            throw new IllegalArgumentException("不允许设置左右padiing值!!!");
        }

        mWidth = measureSize(widthMeasureSpec, (int) mDefaultWidth);
        mHeight = measureSize(heightMeasureSpec, (int) mDefaultHeight);

        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mAlowDraw) {
            return;
        }

        float hRadius = mHollowCircleRadius;

        float hDiameter = hRadius * 2;

        float margin = mMargin;

        float strokeWidth = mPaint.getStrokeWidth();

        mPaint.setColor(mHollowCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < mItemCount; i++) {
            canvas.drawCircle(getPaddingLeft() + strokeWidth / 2 + hRadius + i * (margin + hDiameter + strokeWidth), getHeight() / 2, hRadius, mPaint);
        }

        float sDiameter = hDiameter - strokeWidth;
        float sRadius = sDiameter / 2;
        mPaint.setColor(mSolidCircleColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getPaddingLeft() + strokeWidth + sRadius + (mOffset + (mCurrentPosition - 1)) * (margin + hDiameter + strokeWidth), getHeight() / 2, sRadius,
                mPaint);

        if (mCurrentPosition == 0) {
            canvas.drawCircle(getWidth() + strokeWidth + sRadius + (mOffset - 1) * (strokeWidth + sRadius) * 2, getHeight() / 2, sRadius, mPaint);
        } else if (mCurrentPosition == mItemCount) {
            canvas.drawCircle(-(sRadius + strokeWidth) + mOffset * (strokeWidth + sRadius) * 2, getHeight() / 2, sRadius, mPaint);
        }

    }

    public void attachViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                PagerAdapter adapter = viewPager.getAdapter();
                if (adapter != null) {
                    int count = adapter.getCount();
                    if (count > 1) {

                        mAlowDraw = true;

                        mItemCount = count - 2;

                        if (mLastItemCount != mItemCount) {
                            mDefaultWidth = getPaddingLeft() + getPaddingRight() + (mHollowCircleRadius * 2 + mHollowCircleStrokeWidth) * mItemCount + (mItemCount - 1) * mMargin;
                            requestLayout();
                        }

                        mLastItemCount = mItemCount;

                        mCurrentPosition = position;
                        mOffset = positionOffset;

                        // Log.e("xxx", mCurrentPosition + ";" + mOffset);

                        if (mCurrentPosition == count - 1) {
                            mOffset = 0;
                            mCurrentPosition = 1;
                        }
                    } else {
                        mAlowDraw = false;
                    }
                    invalidate();
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
