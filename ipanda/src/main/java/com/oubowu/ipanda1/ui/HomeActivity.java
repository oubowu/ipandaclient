package com.oubowu.ipanda1.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.api.bean.TabIndex;
import com.oubowu.ipanda.bean.Resource;
import com.oubowu.ipanda.databinding.ActivityHomeBinding;
import com.oubowu.ipanda.viewmodel.HomeViewModel;
import com.oubowu.ipanda1.arxjava.ui.UserActivity;
import com.oubowu.ipanda1.db.entity.ProductEntity;
import com.oubowu.ipanda1.model.Product;
import com.oubowu.ipanda1.ui.adapter.ProductAdapter;
import com.oubowu.ipanda1.ui.callback.ProductClickCallback;
import com.oubowu.ipanda1.viewmodel.ProductListViewModel;

import java.util.List;

import javax.inject.Inject;

// 架构：UI负责从ViewModule取数据进行UI更新；ViewModule负责从Repository中取数据；Repository负责具体的数据业务请求，一般有网络和本地数据业务逻辑
// UI<---ViewModule<----Repository
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding mBinding;

    private ProductAdapter mProductAdapter;

    //    @Inject
    //    IpandaApi mIpandaApi;

    private ProductClickCallback mProductClickCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Toast.makeText(HomeActivity.this, product.getId() + ";" + product.getName() + ";" + product.getDescription() + ";" + product.getPrice(),
                        Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(HomeActivity.this, UserActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mProductAdapter = new ProductAdapter(mProductClickCallback);

        mBinding.productsList.setAdapter(mProductAdapter);

        ProductListViewModel viewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);

        subscribeUi(viewModel);

        HomeViewModel homeViewModel = ViewModelProviders.of(this, mFactory).get(HomeViewModel.class);

        //        homeViewModel.getTabIndex().observe(this, new Observer<ApiResponse<List<TabIndex>>>() {
        //            @Override
        //            public void onChanged(@Nullable ApiResponse<List<TabIndex>> listApiResponse) {
        //                Logger.d(listApiResponse);
        //            }
        //        });

        homeViewModel.getTabIndex().observe(this, new Observer<Resource<List<TabIndex>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<TabIndex>> resource) {

                if (resource != null) {
                    switch (resource.status) {
                        case SUCCESS:
                            Toast.makeText(HomeActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            Toast.makeText(HomeActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            break;
                        case LOADING:
                            Toast.makeText(HomeActivity.this, "加载中......", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    Logger.d(resource.status + ";" + resource.message);
                    Logger.d(resource.data);
                }

            }
        });

    }

    @Inject
    ViewModelProvider.Factory mFactory;

    private void subscribeUi(ProductListViewModel viewModel) {
        // 数据更改时更新列表
        viewModel.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (productEntities != null) {
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(productEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso不知道如何等待数据绑定的循环，所以我们执行更改同步
                mBinding.executePendingBindings();
            }
        });
    }


}
