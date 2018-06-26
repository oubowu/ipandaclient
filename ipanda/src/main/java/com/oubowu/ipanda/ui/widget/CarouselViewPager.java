package com.oubowu.ipanda.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.databinding.CarousePagerBinding;
import com.oubowu.ipanda.databinding.ItemCarousePagerBinding;
import com.oubowu.ipanda.ui.VideoActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Oubowu on 2018/1/14 14:03.
 */
public class CarouselViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener {

    private CarousePagerBinding mBinding;

    private FixViewPager mViewPager;

    private CarouselPagerAdapter mPagerAdapter;

    private Disposable mDisposable;
    private boolean mAuto = true;

    private int mCurrentPosition = 1;
    private int mLastPosition;
    long mAutoScrollTimeMillis;

    private OnPageChangeListenerAdapter mPageChangeListener;

    public static class OnPageChangeListenerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void setPageChangeListener(OnPageChangeListenerAdapter pageChangeListener) {
        mPageChangeListener = pageChangeListener;
    }

    public void setList(List<HomeIndex.BigImgBean> list) {
        if (mPagerAdapter != null) {
            mPagerAdapter.setList(list);
            mPagerAdapter.notifyDataSetChanged();
            if (mPagerAdapter.getCount() > 1) {
                // 设置viewPager的监听事件
                mViewPager.addOnPageChangeListener(this);
                mBinding.viewPager.setCurrentItem(1, false);
            } else {
                mViewPager.removeOnPageChangeListener(this);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public CarouselViewPager(@NonNull Context context) {
        this(context, null);
    }

    public CarouselViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context, AttributeSet attrs) {

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.carouse_pager, this, true);

        mViewPager = mBinding.viewPager;
        mViewPager.setOffscreenPageLimit(4);
        mPagerAdapter = new CarouselPagerAdapter(context);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    mAuto = false;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                default:
                    mAutoScrollTimeMillis = System.currentTimeMillis();
                    mAuto = true;
                    break;
            }
            return false;
        });

        //        mPageChangeListener = new ViewPager.OnPageChangeListener() {
        //            // 滑动状态改变的方法 state :draaging 拖拽 idle 静止 settling 惯性过程
        //            @Override
        //            public void onPageScrollStateChanged(int state) {
        //                //如果是静止状态,将当前页进行替换
        //                if (state == ViewPager.SCROLL_STATE_IDLE && (mLastPosition == mPagerAdapter.getCount() - 1 || mLastPosition == 0)) {
        //                    // 设置当前页,smoothScroll 平稳滑动
        //                    mViewPager.setCurrentItem(mCurrentPosition, false);
        //                }
        //            }

        //            // 滑动过程中的方法 position 索引值
        //            // positionOffset 0-1
        //            // positionOffsetPixels 偏移像素值
        //            @Override
        //            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //            }

        //            // 选中某一页的监听
        //            @Override
        //            public void onPageSelected(int position) {
        //                mLastPosition = position;
        //                if (position == mPagerAdapter.getCount() - 1) {
        //                    // 设置当前值为1
        //                    mCurrentPosition = 1;
        //                } else if (position == 0) {
        //                    // 如果索引值为0了,就设置索引值为倒数第二个
        //                    mCurrentPosition = mPagerAdapter.getCount() - 2;
        //                } else {
        //                    mCurrentPosition = position;
        //                }
        //            }
        //        };

        mDisposable = Observable //
                .interval(4, 4, TimeUnit.SECONDS) //
                .observeOn(AndroidSchedulers.mainThread()) //
                .subscribe(aLong -> {
                    if (mPagerAdapter != null && mPagerAdapter.getCount() > 1 && mAuto) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - mAutoScrollTimeMillis >= 2000) {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        }
                    }
                });
        // mDisposable.dispose();

        mBinding.indicatorContainer.attachViewPager(mViewPager);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滑动过程中的方法 position 索引值
        // positionOffset 0-1
        // positionOffsetPixels 偏移像素值
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        // 选中某一页的监听
        mLastPosition = position;
        if (position == mPagerAdapter.getCount() - 1) {
            // 设置当前值为1
            mCurrentPosition = 1;
        } else if (position == 0) {
            // 如果索引值为0了,就设置索引值为倒数第二个
            mCurrentPosition = mPagerAdapter.getCount() - 2;
        } else {
            mCurrentPosition = position;
        }
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageSelected(mCurrentPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滑动状态改变的方法 state :draaging 拖拽 idle 静止 settling 惯性过程
        //如果是静止状态,将当前页进行替换
        if (state == ViewPager.SCROLL_STATE_IDLE && (mLastPosition == mPagerAdapter.getCount() - 1 || mLastPosition == 0)) {
            // 设置当前页,smoothScroll 平稳滑动
            mViewPager.setCurrentItem(mCurrentPosition, false);
        }
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private class CarouselPagerAdapter extends PagerAdapter implements OnClickListener {

        private LayoutInflater mInflater;

        private List<HomeIndex.BigImgBean> mList;

        CarouselPagerAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ItemCarousePagerBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_carouse_pager, container, true);

            HomeIndex.BigImgBean bean = mList.get(position);

            binding.setBannerInfo(bean);

            binding.getRoot().setTag(-1, bean);
            binding.getRoot().setOnClickListener(this);

            return binding;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ItemCarousePagerBinding binding = (ItemCarousePagerBinding) object;
            container.removeView(binding.getRoot());
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            ItemCarousePagerBinding binding = (ItemCarousePagerBinding) object;
            return binding.getRoot() == view;
        }

        public void setList(List<HomeIndex.BigImgBean> list) {
            mList = list;
            if (mList != null && mList.size() > 1) {

                // 取首
                HomeIndex.BigImgBean firstBigImgBean = mList.get(0);

                // 取尾
                HomeIndex.BigImgBean lastBigImgBean = mList.get(mList.size() - 1);

                // 首添加到尾部
                mList.add(firstBigImgBean);

                // 尾添加到首部
                mList.add(0, lastBigImgBean);

            }
        }

        @Override
        public void onClick(View v) {
            HomeIndex.BigImgBean bean = (HomeIndex.BigImgBean) v.getTag(-1);
            VideoActivity.start((Activity) v.getContext(), v, bean.pid);
        }
    }


}
