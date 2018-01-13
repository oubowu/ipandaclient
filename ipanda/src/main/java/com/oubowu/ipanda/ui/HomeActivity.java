package com.oubowu.ipanda.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.databinding.ActivityHomeBinding;
import com.oubowu.ipanda.util.BottomNavigationViewHelper;
import com.oubowu.ipanda.util.NavigationController;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.util.TabIndex;
import com.oubowu.ipanda.viewmodel.HomeViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

// 架构：UI负责从ViewModule取数据进行UI更新；ViewModule负责从Repository中取数据；Repository负责具体的数据业务请求，一般有网络和本地数据业务逻辑
// UI<---ViewModule<----Repository
public class HomeActivity extends AppCompatActivity implements HasSupportFragmentInjector, HostFragment.OnFragmentInteractionListener {

    ActivityHomeBinding mHomeBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Inject
    NavigationController mNavigationController;

    @Inject
    ViewModelProvider.Factory mFactory;

    private BottomNavigationView mNavigationView;

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

    @SuppressLint("RestrictedApi")
    private void initBottomNavigationView() {

        mNavigationView = mHomeBinding.bnv;

        mNavigationView.setOnNavigationItemSelectedListener(item -> {
            Log.e("HomeActivity", "85行-onNavigationItemSelected(): " + item.getOrder());
            switch (item.getOrder()) {
                case 0:
                    mNavigationController.navigateToHost("熊猫频道", item.getTitleCondensed().toString());
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
            }
            return true;
        });
        mNavigationView.setOnNavigationItemReselectedListener(item -> {
            // 防止重新点击
        });

        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

        HomeViewModel homeViewModel = ViewModelProviders.of(this, mFactory).get(HomeViewModel.class);
        homeViewModel.getTabIndex().observe(this, resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case SUCCESS:
                        List<TabIndex> list = resource.data;

                        MenuBuilder menuBuilder = (MenuBuilder) mNavigationView.getMenu();
                        if (list != null && !list.isEmpty() && list.size() == menuBuilder.size()) {
                            for (int i = 0; i < menuBuilder.size(); i++) {
                                MenuItem item = menuBuilder.getItem(i);
                                if (i == 0) {
                                    mNavigationController.navigateToHost("熊猫频道", list.get(i).url);
                                }
                                item.setTitle(list.get(i).title);
                                item.setTitleCondensed(list.get(i).url);
                            }
                        }

                        Logger.d(resource);
                        break;
                    case ERROR:
                        Toast.makeText(HomeActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        Toast.makeText(HomeActivity.this, "加载中......", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
