package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.ui.adapter.VerticalAdapter;

/**
 * Created by Oubowu on 2018/1/25 16:45.
 */
public class VerticalLayout extends LinearLayout {

    private VerticalAdapter mAdapter;
    private float mSpacing;

    public VerticalLayout(Context context) {
        this(context, null);
    }

    public VerticalLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(LinearLayout.VERTICAL);
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalLayout);
            mSpacing = typedArray.getDimension(R.styleable.VerticalLayout_vSpacing, 0);
            typedArray.recycle();
        }
    }

    private class VerticalObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            addChildView(mAdapter);
        }
    }

    private void addChildView(VerticalAdapter adapter) {
        mAdapter = adapter;

        removeAllViews();

        for (int i = 0; i < adapter.getCount(); i++) {
            final View child = adapter.getView(i, null, this);
            LinearLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (i != adapter.getCount() - 1) {
                lp.bottomMargin = (int) mSpacing;
            }
            addView(child);
        }
    }

    public void setAdapter(VerticalAdapter adapter) {

        adapter.registerDataSetObserver(new VerticalObserver());

        addChildView(adapter);

    }

}
