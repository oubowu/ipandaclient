package com.oubowu.ipanda.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.oubowu.ipanda.R;

/**
 * Created by Oubowu on 2018/1/16 0:42.
 */
public class FollowRecyclerViewBehavior extends CoordinatorLayout.Behavior<View> {

    private float mFirstTopY = Integer.MIN_VALUE;
    private final int mFollowId;

    private int mDependenceId;

    private int mLastHeight;

    private final Drawable mDividerDrawable;

    public FollowRecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FollowRecyclerViewBehavior);
        mDependenceId = ta.getResourceId(R.styleable.FollowRecyclerViewBehavior_dependenceId, -1);
        mFollowId = ta.getResourceId(R.styleable.FollowRecyclerViewBehavior_followId, -1);
        int dividerId = ta.getResourceId(R.styleable.FollowRecyclerViewBehavior_dividerId, 0);

        mDividerDrawable = ContextCompat.getDrawable(context, dividerId != 0 ? dividerId : R.drawable.divider_host_fragment);

        ta.recycle();
    }

    private RecyclerView.ItemDecoration mItemDecoration = null;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        if (mFirstTopY == Integer.MIN_VALUE && dependency.getId() == mDependenceId) {
            child.setY(dependency.getY() + dependency.getHeight());
            mFirstTopY = child.getY();

            View view = parent.findViewById(mFollowId);
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                child.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {

                    // Log.e("xxx", left + "；" + top + "；" + right + "；" + bottom + "；" + oldLeft + "；" + oldTop + "；" + oldRight + "；" + oldBottom);

                    if (mItemDecoration == null) {
                        mItemDecoration = new RecyclerView.ItemDecoration() {
                            @Override
                            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                                // Log.e("HostFragment", "134行-run(): " + child.getHeight());
                                if (parent.getChildAdapterPosition(view) == 0) {
                                    outRect.top = (int) mFirstTopY + child.getHeight() + mDividerDrawable.getIntrinsicHeight();
                                } else {
                                    outRect.top = 0;
                                }
                            }
                        };
                        recyclerView.addItemDecoration(mItemDecoration);
                        recyclerView.setAlpha(1);
                    }

                    final int height = child.getHeight();

                    if (mLastHeight != height) {
                        recyclerView.invalidateItemDecorations();
                    }

                    mLastHeight = height;

                });
            }

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
        if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            View view = recyclerView.getChildAt(0);
            if (view != null) {
                child.setY(view.getY() - child.getHeight() - mDividerDrawable.getIntrinsicHeight());
            }
        }
    }
}
