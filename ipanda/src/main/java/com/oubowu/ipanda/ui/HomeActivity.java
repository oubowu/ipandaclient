package com.oubowu.ipanda.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.databinding.ActivityHomeBinding;
import com.oubowu.ipanda.util.BottomNavigationViewHelper;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.StatusBarUtil;
import com.oubowu.ipanda.util.TabIndex;
import com.oubowu.ipanda.viewmodel.HomeViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

// 架构：UI负责从ViewModule取数据进行UI更新；ViewModule负责从Repository中取数据；Repository负责具体的数据业务请求，一般有网络和本地数据业务逻辑
// UI<---ViewModule<----Repository
public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding mHomeBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    @Inject
    IpandaService mIpandaService;

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home);

        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this,3);


        mHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        initBottomNavigationView();

    }

    @SuppressLint("RestrictedApi")
    private void initBottomNavigationView() {

        mNavigationView = mHomeBinding.bnv;
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
                                item.setTitle(list.get(i).title);
                                item.setTitleCondensed(list.get(i).url);
                            }
                            mNavigationView.setOnNavigationItemSelectedListener(item -> {
                                Log.e("xxx", item.getTitleCondensed().toString());
                                if (item.getOrder() == 1) {
                                    LiveData<ApiResponse<Map<String, HomeIndex>>> data = mIpandaService.getHomeIndex(item.getTitleCondensed().toString());
                                    data.observe(HomeActivity.this, mapApiResponse -> {
                                        if (mapApiResponse != null) {
                                            HomeIndex homeIndex = MapUtil.getFirstElement(mapApiResponse.body);
                                        }
                                    });
                                }
                                return true;
                            });
                        }
                        Logger.d(resource);
                        // Toast.makeText(HomeActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
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


}
