package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.pandalive.LiveTab;
import com.oubowu.ipanda.bean.pandalive.MultipleLive;
import com.oubowu.ipanda.bean.pandalive.WatchTalk;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/2/5 11:54.
 */
@Singleton
public class PandaLiveSubRepository {

    private IpandaService mIpandaService;

    @Inject
    public PandaLiveSubRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<LiveTab>> getLiveTab(String url) {
        return new NetworkBoundResource<LiveTab, LiveTab>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected LiveTab saveCallResponseToDb(@NonNull LiveTab response) {
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LiveTab>> createCall() {
                return mIpandaService.getLiveTab(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable LiveTab data) {
                return true;
            }

        }.asLiveData();
    }

    public LiveData<Resource<List<MultipleLive>>> getMultipleLive(String url) {
        return new NetworkBoundResource<List<MultipleLive>, Map<String, List<MultipleLive>>>() {
            @Override
            protected void onCallFailed() {

            }

            @Override
            protected List<MultipleLive> saveCallResponseToDb(@NonNull Map<String, List<MultipleLive>> response) {
                List<MultipleLive> multipleLives = MapUtil.getFirstElement(response);
                return multipleLives;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<MultipleLive>>>> createCall() {
                return mIpandaService.getMultipleLive(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable List<MultipleLive> data) {
                return true;
            }

        }.asLiveData();
    }

    public LiveData<Resource<WatchTalk>> getLiveWatchTalk(int prepage, int page, String itemid) {
        return new NetworkBoundResource<WatchTalk, WatchTalk>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected WatchTalk saveCallResponseToDb(@NonNull WatchTalk response) {
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<WatchTalk>> createCall() {
                return mIpandaService.getLiveWatchTalk(prepage, 1, "ipandaApp", page, itemid);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable WatchTalk data) {
                return true;
            }

        }.asLiveData();
    }

}
