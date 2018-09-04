package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import java.util.List;
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
    public HostRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<HomeIndex>> getHomeIndex(String url) {
        return new NetworkBoundResource<HomeIndex, Map<String, HomeIndex>>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected HomeIndex saveCallResponseToDb(@NonNull Map<String, HomeIndex> response) {
                HomeIndex homeIndex = MapUtil.getFirstElement(response);
                return homeIndex;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, HomeIndex>>> createCall() {
                return mIpandaService.getHomeIndex(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable HomeIndex data) {
                return true;
            }

        }.asLiveData();
    }

    private LiveData<Resource<List<VideoList>>> getVideoList(String url) {
        return new NetworkBoundResource<List<VideoList>, Map<String, List<VideoList>>>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected List<VideoList> saveCallResponseToDb(@NonNull Map<String, List<VideoList>> response) {
                List<VideoList> videoLists = MapUtil.getFirstElement(response);
                return videoLists;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<VideoList>>>> createCall() {
                return mIpandaService.getVideoListIndex(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable List<VideoList> data) {
                return true;
            }

        }.asLiveData();
    }

    public LiveData<Resource<List<VideoList>>> getWonderfulMomentIndex(String url) {
        return getVideoList(url);
    }

    public LiveData<Resource<List<VideoList>>> getGungunVideoIndex(String url) {
        return getVideoList(url);
    }

}
