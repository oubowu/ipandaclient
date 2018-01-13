package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/1/12 14:32.
 */
@Singleton
public class HostRepository {

    private IpandaService mIpandaService;

    @Inject
    public HostRepository(IpandaService ipandaService){
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<HomeIndex>> getHomeIndex(String url){
        return new NetworkBoundResource<HomeIndex, Map<String, HomeIndex>>() {
            MediatorLiveData<HomeIndex> mHomeLiveData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected void saveCallResponseToDb(@NonNull Map<String, HomeIndex> response) {
                HomeIndex homeIndex = MapUtil.getFirstElement(response);
                mHomeLiveData.postValue(homeIndex);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, HomeIndex>>> createCall() {
                return mIpandaService.getHomeIndex(url);
            }

            @Override
            protected boolean shouldCall(@Nullable HomeIndex data) {
                return true;
            }

            @Override
            protected LiveData<HomeIndex> loadFromDb() {
                if (mHomeLiveData == null) {
                    mHomeLiveData = new MediatorLiveData<>();
                    mHomeLiveData.postValue(null);
                }
                return mHomeLiveData;
            }
        }.asLiveData();
    }

}
