package com.oubowu.ipanda.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;
import android.view.View;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.callback.OnFragmentScrollListener;
import com.oubowu.ipanda.databinding.ActivityHomeBinding;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.util.BottomNavigationViewHelper;
import com.oubowu.ipanda.util.HandleBackUtil;
import com.oubowu.ipanda.util.NavigationController;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.util.ToastUtil;
import com.oubowu.ipanda.viewmodel.HomeViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

// 架构：UI负责从ViewModule取数据进行UI更新；ViewModule负责从Repository中取数据；Repository负责具体的数据业务请求，一般有网络和本地数据业务逻辑
// UI<---ViewModule<----Repository
public class HomeActivity extends AppCompatActivity implements HasSupportFragmentInjector, OnFragmentScrollListener {

    public static final int POSITION_HOST = 0;
    public static final int POSITION_PANDA_LIVE = 1;
    public static final int POSITION_PANDA_VIDEO = 2;
    public static final int POSITION_PANDA_BROADCAST = 3;
    public static final int POSITION_CHINA_LIVE = 4;

    ActivityHomeBinding mHomeBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    NavigationController mNavigationController;

    @Inject
    ViewModelProvider.Factory mFactory;

    private BottomNavigationView mNavigationView;

    private float mFirstBottomNavigationTopY = Integer.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setStatusBarColor(this, R.color.statusBarColorLollipop);
        } else {
            StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark1);
            StatusBarUtil.StatusBarLightMode(this, 3);
        }

        mHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initBottomNavigationView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!HandleBackUtil.handleBackPress(this)) {
            moveTaskToBack(true);
        }
    }

    @SuppressLint("RestrictedApi")
    private void initBottomNavigationView() {

        mNavigationView = mHomeBinding.bnv;

        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

        HomeViewModel homeViewModel = ViewModelProviders.of(this, mFactory).get(HomeViewModel.class);
        homeViewModel.getTabIndex().observe(this, new ObserverImpl<List<TabIndex>>() {
            @Override
            protected void onLoading() {
                // Toast.makeText(HomeActivity.this, "加载中......", Toast.LENGTH_SHORT).show();
                ToastUtil.showSuccessMsg("加载中......");
            }

            @Override
            protected void onError(@NonNull String errorMsg) {
                // Toast.makeText(HomeActivity.this, "请求失败" + errorMsg, Toast.LENGTH_SHORT).show();
                ToastUtil.showErrorMsg(errorMsg);
            }

            @Override
            protected void onSuccess(@NonNull List<TabIndex> data) {
                MenuBuilder menuBuilder = (MenuBuilder) mNavigationView.getMenu();
                if (!data.isEmpty() && data.size() == menuBuilder.size()) {

                    for (int i = 0; i < menuBuilder.size(); i++) {
                        MenuItem item = menuBuilder.getItem(i);
                        if (i == 0) {
                            mNavigationController.navigateToHost("熊猫频道", data.get(i).url);
                        }
                        item.setTitle(data.get(i).title);
                        item.setTitleCondensed(data.get(i).url);
                    }

                    mNavigationView.setOnNavigationItemSelectedListener(item -> {
                        // Log.e("HomeActivity", "85行-onNavigationItemSelected(): " + item.getOrder());
                        String name = item.getTitle().toString();
                        String url = item.getTitleCondensed().toString();
                        switch (item.getOrder()) {
                            case POSITION_HOST:
                                mNavigationController.navigateToHost("熊猫频道", url);
                                break;
                            case POSITION_PANDA_LIVE:
                                mNavigationController.navigateToPandaLive(name, url);
                                break;
                            case POSITION_PANDA_VIDEO:
                                mNavigationController.navigateToPandaVideo(name, url);
                                break;
                            case POSITION_PANDA_BROADCAST:
                                mNavigationController.navigateToPandaBroadcast(name, url);
                                break;
                            case POSITION_CHINA_LIVE:
                                mNavigationController.navigateToChinaLive(name, url);
                                break;
                        }
                        return true;
                    });
                    mNavigationView.setOnNavigationItemReselectedListener(item -> {
                        // 防止重复点击
                    });

                }
            }
        });

    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    private BarBehavior mBarBehavior;

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (mBarBehavior == null) {
            mBarBehavior = new BarBehavior(this, null);
            mBarBehavior.setDirection(BarBehavior.DIRECTION_REVERSE);
        }
        mBarBehavior.onNestedScroll(coordinatorLayout, mNavigationView, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        if (mBarBehavior == null) {
            mBarBehavior = new BarBehavior(this, null);
            mBarBehavior.setDirection(BarBehavior.DIRECTION_REVERSE);
        }
        return mBarBehavior.onNestedFling(coordinatorLayout, mNavigationView, target, velocityX, velocityY, consumed);
    }

    //    @Override
    //    public void onNestedScrollListener(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    ////
    ////        if (mFirstBottomNavigationTopY == Integer.MIN_VALUE) {
    ////            mFirstBottomNavigationTopY = mNavigationView.getY();
    ////        }
    ////        mNavigationView
    ////                .setY(Math.max(Math.min(mNavigationView.getY() + dyConsumed, mFirstBottomNavigationTopY + mNavigationView.getHeight()), mFirstBottomNavigationTopY));
    //
    //
    //
    //    }

}
