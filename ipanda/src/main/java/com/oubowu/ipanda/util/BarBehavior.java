package com.oubowu.ipanda.util;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.ipanda.R;

/**
 * Created by Oubowu on 2018/1/17 09:59.
 */
public class BarBehavior extends CoordinatorLayout.Behavior implements Animator.AnimatorListener {

    public static final int DIRECTION_SAME = 1;
    public static final int DIRECTION_REVERSE = 2;

    private int mDirection = DIRECTION_SAME;
    private int mDependenceBarId;
    private float mFirstTopY = Integer.MIN_VALUE;
    private float mFirstChangeTopY = 0;

    private OnNestedScrollListener mOnNestedScrollListener;

    public BarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs == null) {
            return;
        }
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BarBehavior);
        mDirection = ta.getInt(R.styleable.BarBehavior_direction, DIRECTION_SAME);
        mDependenceBarId = ta.getResourceId(R.styleable.BarBehavior_dependenceBarId, -1);
        ta.recycle();
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mDependenceBarId == -1) {
            return super.onDependentViewChanged(parent, child, dependency);
        } else {
            if (mFirstChangeTopY == 0 && dependency.getId() == mDependenceBarId) {
                child.setY(dependency.getY() + dependency.getHeight());
                mFirstChangeTopY = child.getY();
            }
            return false;
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return mDependenceBarId == dependency.getId();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
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
            mOnNestedScrollListener.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        if (mOnNestedScrollListener != null) {
            mOnNestedScrollListener.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
        }
        if (target instanceof RecyclerView) {
            // int scrollRange = ((RecyclerView) target).computeVerticalScrollRange();
            // int scrollOffset = ((RecyclerView) target).computeVerticalScrollOffset();
            // int scrollExtent = ((RecyclerView) target).computeVerticalScrollExtent();
            //            Log.e("xxx", child.getClass()
            //                    .getSimpleName() + ";scrollRange: " + scrollRange + ";scrollOffset：" + scrollOffset + ";scrollExtent：" + scrollExtent + ";" + velocityY + ";itemCount: " + itemCount);
            RecyclerView.Adapter adapter = ((RecyclerView) target).getAdapter();
            if (adapter != null) {
                int itemCount = adapter.getItemCount();
                if (velocityY > 0 && (/*scrollOffset == 0 ||*/ itemCount <= 2)) {
                    return false;
                }
            }
        }
        switch (mDirection) {
            case DIRECTION_SAME:
                if (velocityY > 1000) {
                    child.animate().translationY(-child.getHeight()).setListener(this).setDuration(250).start();
                    return false;
                } else if (velocityY < -1000) {
                    child.animate().translationY(mFirstChangeTopY).setListener(this).setDuration(250).start();
                    return false;
                }
                break;
            case DIRECTION_REVERSE:
                if (velocityY > 1000) {
                    child.animate().translationY(child.getHeight()).setListener(this).setDuration(250).start();
                    return false;
                } else if (velocityY < -1000) {
                    child.animate().translationY(mFirstChangeTopY).setListener(this).setDuration(250).start();
                    return false;
                }
                break;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    //    @Override
    //    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
    //        // 相应垂直滑动
    //        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    //    }

    //    @Override
    //    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    //        if (mFirstTopY == Integer.MIN_VALUE) {
    //            mFirstTopY = child.getY();
    //        }
    //        Log.e("BarBehavior","64行-onNestedScroll(): "+dyConsumed);
    //        switch (mDirection) {
    //            case DIRECTION_SAME:
    //                child.setY(Math.min(Math.max(child.getY() - dyConsumed, -child.getHeight()), mFirstTopY));
    //                break;
    //            case DIRECTION_REVERSE:
    //                child.setY(Math.max(Math.min(child.getY() + dyConsumed, mFirstTopY + child.getHeight()), mFirstTopY));
    //                break;
    //        }
    //        if (mOnNestedScrollListener != null) {
    //            mOnNestedScrollListener.onNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    //        }
    //    }

    public interface OnNestedScrollListener {
        void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed);

        boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed);
    }

    public void setOnNestedScrollListener(OnNestedScrollListener onNestedScrollListener) {
        mOnNestedScrollListener = onNestedScrollListener;
    }
}
