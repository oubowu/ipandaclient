package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.chinalive.ChinaLiveTab;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/2/5 11:54.
 */
@Singleton
public class ChinaLiveRepository {

    private IpandaService mIpandaService;

    @Inject
    public ChinaLiveRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<ChinaLiveTab>> getChinaLiveTab(String url) {
        return new NetworkBoundResource<ChinaLiveTab, ChinaLiveTab>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected ChinaLiveTab saveCallResponseToDb(@NonNull ChinaLiveTab response) {
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ChinaLiveTab>> createCall() {
                return mIpandaService.getChinaLiveTab(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable ChinaLiveTab data) {
                return true;
            }

        }.asLiveData();
    }


}
