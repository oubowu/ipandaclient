package com.oubowu.ipanda.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.oubowu.ipanda.R;

/**
 * Created by Oubowu on 2018/1/16 0:42.
 */
public class DependenceBehavior extends CoordinatorLayout.Behavior<View> {

    private int mDependenceId;
    private float mFirstY = Integer.MIN_VALUE;

    public DependenceBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DependenceBehavior);
        mDependenceId = ta.getResourceId(R.styleable.DependenceBehavior_dependenceId, -1);
        ta.recycle();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (child.getId() != dependency.getId() && dependency.getId() == mDependenceId) {
            child.setY(dependency.getY() + dependency.getHeight());
            return true;
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return mDependenceId == dependency.getId();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (!(child instanceof NestedScrollingChild)) {
            if (child.getY() >= -child.getHeight()) {
                if (mFirstY == Integer.MIN_VALUE) {
                    mFirstY = child.getY();
                }
                float y = child.getY() - dyConsumed;
                child.setY(Math.min(Math.max(-child.getHeight(), y), mFirstY));
            }
        }
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        if (!(child instanceof NestedScrollingChild)) {
            Log.e("x","65行-onNestedFling(): "+velocityY);

            return true;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
