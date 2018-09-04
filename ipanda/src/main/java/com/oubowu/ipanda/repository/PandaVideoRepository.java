package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/2/5 11:54.
 */
@Singleton
public class PandaVideoRepository {

    private IpandaService mIpandaService;

    @Inject
    public PandaVideoRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<PandaVideoIndex>> getPandaVideoIndex(String url) {
        return new NetworkBoundResource<PandaVideoIndex, PandaVideoIndex>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected PandaVideoIndex saveCallResponseToDb(@NonNull PandaVideoIndex response) {
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PandaVideoIndex>> createCall() {
                return mIpandaService.getPandaVideoIndex(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable PandaVideoIndex data) {
                return true;
            }
        }.asLiveData();
    }

}
