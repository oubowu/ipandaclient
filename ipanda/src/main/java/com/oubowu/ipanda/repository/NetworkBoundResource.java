package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.bean.Resource;
import com.oubowu.ipanda.util.CommonUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oubowu on 2017/12/30 22:07.
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> mResult = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        // 先设置加载中状态
        mResult.setValue(Resource.loading(null));
        Observable//
                .create((ObservableOnSubscribe<LiveData<ResultType>>) e -> {
                    // 从数据库加载数据
                    LiveData<ResultType> dbSource = loadFromDb();
                    e.onNext(dbSource);
                }).subscribeOn(Schedulers.io())//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(dbSource -> {
                    // 监听数据库加载的数据
                    mResult.addSource(dbSource, data -> {
                        // 数据库数据变化了的话，首先移除监听
                        mResult.removeSource(dbSource);
                        if (shouldCall(data)) {
                            // 若需要从网络请求数据，加载网络数据
                            fetchFromNetwork(dbSource);
                        } else {
                            // 不需要从网络加载，监听数据库数据变化并赋值
                            mResult.addSource(dbSource, newData -> setValue(Resource.success(newData)));
                        }
                    });
                });
    }

    private void setValue(Resource<ResultType> data) {
        if (!CommonUtil.equals(mResult.getValue(), data)) {
            // 没变化，不需要重新设置值
            mResult.setValue(data);
        }
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        // 网络请求获取数据
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // 重新监听数据库，迅速分发最新的值；显示loading状态，因为上面开始做网络请求
        mResult.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        // 添加网络请求数据的监听
        mResult.addSource(apiResponse, response -> {
            // 移除网络请求监听
            mResult.removeSource(apiResponse);
            // 移除数据库监听；上面的加载中状态
            mResult.removeSource(dbSource);
            if (response != null && response.isSuccessful()) {
                Observable//
                        .create((ObservableOnSubscribe<LiveData<ResultType>>) e -> {
                            // 网络请求成功的话，保存数据到数据库中
                            saveCallResponseToDb(processResponse(response));
                            // 再从数据库读取出数据
                            LiveData<ResultType> newDbSource = loadFromDb();
                            e.onNext(newDbSource);
                        })//
                        .subscribeOn(Schedulers.io())//
                        .observeOn(AndroidSchedulers.mainThread())//
                        .subscribe(newDbSource -> {
                            mResult.addSource(newDbSource, newData -> {
                                setValue(Resource.success(newData));
                            });
                        });
            } else {
                // 网络请求失败，使用数据库的数据
                onCallFailed();
                mResult.addSource(dbSource, newData -> setValue(Resource.error(response != null ? response.errorMessage : null, newData)));
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return mResult;
    }

    @MainThread
    protected abstract void onCallFailed();

    @WorkerThread
    protected abstract void saveCallResponseToDb(@NonNull RequestType response);

    @WorkerThread
    protected RequestType processResponse(@NonNull ApiResponse<RequestType> response) {
        return response.body;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    @MainThread
    protected abstract boolean shouldCall(@Nullable ResultType data);

    @WorkerThread
    protected abstract LiveData<ResultType> loadFromDb();
}
