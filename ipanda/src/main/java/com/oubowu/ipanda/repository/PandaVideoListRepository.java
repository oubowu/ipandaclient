package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/2/5 11:54.
 */
@Singleton
public class PandaVideoListRepository {

    private IpandaService mIpandaService;

    @Inject
    public PandaVideoListRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<RecordTab>> getRecordTab(String vsid, int number, int page) {
        return new NetworkBoundResource<RecordTab, RecordTab>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected RecordTab saveCallResponseToDb(@NonNull RecordTab response) {
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RecordTab>> createCall() {
                return mIpandaService.getRecordTab(vsid, number, "panda", "desc", "time", page);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable RecordTab data) {
                return true;
            }

        }.asLiveData();
    }

}
