package com.oubowu.ipanda1.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.databinding.ActivityHomeBinding;
import com.oubowu.ipanda.di.qualifier.Service;
import com.oubowu.ipanda1.arxjava.ui.UserActivity;
import com.oubowu.ipanda1.db.entity.ProductEntity;
import com.oubowu.ipanda1.model.Product;
import com.oubowu.ipanda1.ui.adapter.ProductAdapter;
import com.oubowu.ipanda1.ui.callback.ProductClickCallback;
import com.oubowu.ipanda1.viewmodel.ProductListViewModel;

import java.util.List;

import javax.inject.Inject;

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

        //        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        //        homeViewModel.getTabIndex().observe(this, tabIndices -> {
        //            Logger.e("数据回调");
        //            Logger.d(tabIndices);
        //        });

        //        SparseArray<String> hosts = new SparseArray<>(Hosts.COUNT);
        //        hosts.put(Hosts.IPANDA_KEHUDUAN, "www.ipanda.com");
        //        hosts.put(Hosts.CNTV_APPS, "vdn.apps.cntv.cn");
        //        hosts.put(Hosts.CNTV_LIVE, "vdn.live.cntv.cn");
        //        IpandaApi api = new IpandaApi(hosts);

        //        LiveData<ApiResponse<List<TabIndex>>> liveData = api.getRetrofit(Hosts.IPANDA_KEHUDUAN).create(IpandaService.class).getTabIndex();

        LiveData<ApiResponse<List<TabIndex>>> liveData = mIpandaClientService.getTabIndex();
        liveData.observe(this, new Observer<ApiResponse<List<TabIndex>>>() {
            @Override
            public void onChanged(@Nullable ApiResponse<List<TabIndex>> listApiResponse) {
                Logger.d(listApiResponse);
            }
        });

        //        LiveData<ApiResponse<RecordVideo>> detail = mIpandaApi.getRetrofit(Hosts.CNTV_APPS).create(IpandaService.class)
        //                .getPandaBroadcastDetail("29abf1c6e9624cb19568808256662b3b");
//        LiveData<ApiResponse<RecordVideo>> detail = mCntvAppsService.getPandaBroadcastDetail("29abf1c6e9624cb19568808256662b3b");
//        detail.observe(this, new Observer<ApiResponse<RecordVideo>>() {
//            @Override
//            public void onChanged(@Nullable ApiResponse<RecordVideo> recordVideoApiResponse) {
//                Logger.d(recordVideoApiResponse.toString());
//            }
//        });

    }

    //    @Inject
    //    HomeRepository mHomeRepository;

    //    @Inject
    //    IpandaApi mIpandaApi;

    @Inject
    @Service("IPANDA_KEHUDUAN")
    IpandaService mIpandaClientService;

    @Inject
    @Service("CNTV_APPS")
    IpandaService mCntvAppsService;

    @Inject
    @Service("CNTV_LIVE")
    IpandaService mCntvLiveService;

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
