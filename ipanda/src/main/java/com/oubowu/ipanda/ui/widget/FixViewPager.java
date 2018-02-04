package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by Oubowu on 2018/1/19 09:28.
 */
public class FixViewPager extends ViewPager {

    public FixViewPager(@NonNull Context context) {
        this(context, null);
    }

    public FixViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new ViewPagerScroller(context).initViewPagerScroll(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {

            // 声明临时变量存储父容器的期望值
            int parentDesireHeight = 0;
            int tmpHeight = 0;

            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
                parentDesireHeight = view.getMeasuredHeight();
                tmpHeight = Math.max(parentDesireHeight, tmpHeight);
            }
            parentDesireHeight = tmpHeight + getPaddingTop() + getPaddingBottom();
            parentDesireHeight = Math.max(parentDesireHeight, getSuggestedMinimumHeight());
            setMeasuredDimension(resolveSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec), resolveSize(parentDesireHeight, heightMeasureSpec));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public class ViewPagerScroller extends Scroller {
        // 滑动速度
        private int mScrollDuration = 500;

        /**
         * 设置速度速度
         *
         * @param duration
         */
        public void setScrollDuration(int duration) {
            this.mScrollDuration = duration;
        }

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        public void initViewPagerScroll(ViewPager viewPager) {
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
