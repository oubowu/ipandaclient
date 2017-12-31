package com.oubowu.ipanda1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oubowu.ipanda1.BasicApp;
import com.oubowu.ipanda1.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/13 17:08.
 */
public class ProductListViewModel extends AndroidViewModel {

    // 在你的应用中可能需要除了上面两种以外更多的LiveData的转换，为了实现这些转换，你可以使用MediatorLiveData类，它可以用来正确的处理其他多个LiveData的事件变化，并处理这些事件。
    private final MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public ProductListViewModel(@NonNull Application application) {
        super(application);

        mObservableProducts = new MediatorLiveData<>();
        mObservableProducts.setValue(null);

        LiveData<List<ProductEntity>> products = ((BasicApp) application).getRepository().getProducts();

        // 从数据库中观察产品的变化并转发它们
        mObservableProducts.addSource(products, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                mObservableProducts.setValue(productEntities);
            }
        });

        LiveData<ProductEntity> liveData = ((BasicApp) application).getRepository().loadProduct(12333);
        // Log.e("xxx",liveData+";");

    }

    // 暴露LiveData产品查询以便UI可以观察到它。
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

}
