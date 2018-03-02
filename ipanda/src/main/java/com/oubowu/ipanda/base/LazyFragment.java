package com.oubowu.ipanda.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于懒加载的Fragment
 */
public abstract class LazyFragment extends Fragment {

    protected View mRootView;
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    protected boolean mIsFragmentVisible;

    //是否是第一次开启网络加载
    protected boolean mIsFirstInit;

    public LazyFragment() {
    }

    /**
     * Fragment可见时，setUserVisibleHint()回调，其中isVisibleToUser=true。
     * 当前Fragment由可见到不可见或实例化时，setUserVisibleHint()回调，其中isVisibleToUser=false
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsFragmentVisible = isVisibleToUser;
        if (mRootView == null) {
            // 实例化的时候，mRootView为null，setUserVisibleHint是最优先跑的，直接返回，避免下面的逻辑
            return;
        }
        // 可见，并且没有加载过
        if (mIsFragmentVisible && !mIsFirstInit) {
            onFragmentVisibleChange(true, false);
        } else if (mIsFragmentVisible && mIsFirstInit) {
            // 可见，并且初始化过了
            onFragmentVisibleChange(true, true);
        } else if (!mIsFragmentVisible) {
            // 由可见——>不可见
            onFragmentVisibleChange(false, mIsFirstInit);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflateView(inflater, container);
        }

        //可见，但是并没有加载过
        if (mIsFragmentVisible && !mIsFirstInit) {
            onFragmentVisibleChange(true, false);
        }

        return mRootView;
    }

    protected abstract @NonNull
    View inflateView(LayoutInflater inflater, ViewGroup container);

    protected abstract void onFragmentVisibleChange(boolean isVisible, boolean isFirstInit);

}
