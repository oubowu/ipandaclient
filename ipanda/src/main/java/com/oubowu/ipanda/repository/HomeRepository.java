package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oubowu.ipanda.api.bean.TabIndex;
import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2017/12/24 23:45.
 */
@Singleton
public class HomeRepository {

    private IpandaService mIpandaClientService;

    @Inject
    public HomeRepository(IpandaService ipandaClientService) {
        mIpandaClientService = ipandaClientService;
        Log.e("xxx", mIpandaClientService + " ");
    }

    //    public LiveData<ApiResponse<List<TabIndex>>> getTabIndex() {
    //        return mIpandaClientService.getTabIndex();
    //    }

    public LiveData<Resource<List<TabIndex>>> getTabIndex() {

        return new NetworkBoundResource<List<TabIndex>, List<TabIndex>>() {

            private List<TabIndex> mResponse;

            @Override
            protected void onCallFailed() {
                Log.e("HomeRepository", "网络请求失败");
            }

            @Override
            protected void saveCallResponseToDb(@NonNull List<TabIndex> response) {
                mResponse = response;
                Log.e("HomeRepository", "保存请求成功数据到数据库");

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<TabIndex>>> createCall() {
                return mIpandaClientService.getTabIndex();
            }

            @Override
            protected boolean shouldCall(@Nullable List<TabIndex> data) {
                return data == null;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected LiveData<List<TabIndex>> loadFromDb() {
                Log.e("HomeRepository","从数据库加载");
                MutableLiveData liveData = new MutableLiveData<List<TabIndex>>();
                liveData.postValue(mResponse);
                return liveData;
            }
        }.asLiveData();

    }


}
