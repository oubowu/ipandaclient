package com.oubowu.ipanda1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.oubowu.ipanda1.db.AppDatabase;
import com.oubowu.ipanda1.db.entity.CommentEntity;
import com.oubowu.ipanda1.db.entity.ProductEntity;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/14 11:58.<p></p>
 * 处理产品和评论的仓库
 */
public class DataRepository {

    private static volatile DataRepository sInstance;

    private final AppDatabase mDatabase;

    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (mDatabase.getIsDatabaseCreated().getValue() != null) {
                    mObservableProducts.postValue(productEntities);
                }
            }
        });

    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    // 从数据库获取产品列表，并在数据改变时得到通知。
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(final int productId){
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId){
        return mDatabase.commentDao().loadComments(productId);
    }

}
