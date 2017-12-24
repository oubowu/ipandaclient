package com.oubowu.ipanda1.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.databinding.ActivityHomeBinding;
import com.oubowu.ipanda.di.component.DaggerIpandaApiComponent;
import com.oubowu.ipanda.di.module.IpandaApiModule;
import com.oubowu.ipanda.http.Hosts;
import com.oubowu.ipanda.http.IpandaApi;
import com.oubowu.ipanda.http.service.IpandaService;
import com.oubowu.ipanda1.arxjava.ui.UserActivity;
import com.oubowu.ipanda1.db.entity.ProductEntity;
import com.oubowu.ipanda1.model.Product;
import com.oubowu.ipanda1.ui.adapter.ProductAdapter;
import com.oubowu.ipanda1.ui.callback.ProductClickCallback;
import com.oubowu.ipanda1.viewmodel.ProductListViewModel;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding mBinding;

    private ProductAdapter mProductAdapter;

    @Inject
    IpandaApi mIpandaApi;

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

        DaggerIpandaApiComponent.builder().ipandaApiModule(new IpandaApiModule()).build().inject(this);

        IpandaService ipandaService = mIpandaApi.retrofitStudio(Hosts.IPANDA_KEHUDUAN).create(IpandaService.class);
        ipandaService.getTabIndex().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(stringListMap -> {
            Logger.d(stringListMap);
            if (stringListMap != null && !stringListMap.isEmpty()) {
                Collection<List<TabIndex>> values = stringListMap.values();
                if (!values.isEmpty()) {
                    Log.e("HomeActivity","80行-onCreate(): "+" ");
                    List<TabIndex> tabIndexList = values.iterator().next();
                    return Observable.just(tabIndexList);
                }
            }
            return null;
        }).subscribe(tabIndices -> {
            Logger.d(tabIndices);
//            ipandaService.getHomeIndex(tabIndices.get(0).url).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(homeIndex -> {
//
//            });
        }, throwable -> {
            Log.e("HomeActivity", "90行-onCreate(): " + " ", throwable);
        });



    }

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
