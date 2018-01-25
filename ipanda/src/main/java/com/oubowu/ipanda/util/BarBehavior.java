package com.oubowu.ipanda.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.ipanda.R;

/**
 * Created by Oubowu on 2018/1/17 09:59.
 */
public class BarBehavior extends CoordinatorLayout.Behavior<View> {

    public static final int DIRECTION_SAME = 1;
    public static final int DIRECTION_REVERSE = 2;

    private final int mDirection;

    private float mFirstTopY = Integer.MIN_VALUE;

    private OnNestedScrollListener mOnNestedScrollListener;

    public BarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BarBehavior);
        mDirection = ta.getInt(R.styleable.BarBehavior_direction, DIRECTION_SAME);
        ta.recycle();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        // 相应垂直滑动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (mFirstTopY == Integer.MIN_VALUE) {
            mFirstTopY = child.getY();
        }
        switch (mDirection) {
            case DIRECTION_SAME:
                child.setY(Math.min(Math.max(child.getY() - dyConsumed, -child.getHeight()), mFirstTopY));
                break;
            case DIRECTION_REVERSE:
                child.setY(Math.max(Math.min(child.getY() + dyConsumed, mFirstTopY + child.getHeight()), mFirstTopY));
                break;
        }
        if (mOnNestedScrollListener != null) {
            mOnNestedScrollListener.onNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }
    }

    public interface OnNestedScrollListener {
        void onNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type);
    }

    public void setOnNestedScrollListener(OnNestedScrollListener onNestedScrollListener) {
        mOnNestedScrollListener = onNestedScrollListener;
    }
}
