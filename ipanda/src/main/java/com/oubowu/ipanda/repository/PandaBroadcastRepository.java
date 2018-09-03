package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastDetail;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastIndex;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastList;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/2/5 11:54.
 */
@Singleton
public class PandaBroadcastRepository {

    private IpandaService mIpandaService;

    @Inject
    public PandaBroadcastRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<PandaBroadcastIndex>> getPandaBroadcastIndex(String url) {
        return new NetworkBoundResource<PandaBroadcastIndex, Map<String, PandaBroadcastIndex>>() {

            MutableLiveData<PandaBroadcastIndex> mLiveData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected PandaBroadcastIndex saveCallResponseToDb(@NonNull Map<String, PandaBroadcastIndex> response) {
                PandaBroadcastIndex broadcastIndex = MapUtil.getFirstElement(response);
                mLiveData.postValue(broadcastIndex);
                return broadcastIndex;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, PandaBroadcastIndex>>> createCall() {
                return mIpandaService.getPandaBroadcastIndex(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable PandaBroadcastIndex data) {
                return true;
            }

            @Override
            protected LiveData<PandaBroadcastIndex> loadFromDb() {
                if (mLiveData == null) {
                    mLiveData = new MutableLiveData<>();
                    mLiveData.postValue(null);
                }
                return mLiveData;
            }
        }.asLiveData();
    }

    public LiveData<Resource<PandaBroadcastList>> getPandaBroadcastList(String url) {
        return new NetworkBoundResource<PandaBroadcastList, PandaBroadcastList>() {

            MutableLiveData<PandaBroadcastList> mLiveData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected PandaBroadcastList saveCallResponseToDb(@NonNull PandaBroadcastList response) {
                mLiveData.postValue(response);
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PandaBroadcastList>> createCall() {
                return mIpandaService.getPandaBroadcastList(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable PandaBroadcastList data) {
                return true;
            }

            @Override
            protected LiveData<PandaBroadcastList> loadFromDb() {
                if (mLiveData == null) {
                    mLiveData = new MutableLiveData<>();
                    mLiveData.postValue(null);
                }
                return mLiveData;
            }
        }.asLiveData();
    }

    public LiveData<Resource<PandaBroadcastDetail>> getPandaBroadcastDetail(String id) {
        return new NetworkBoundResource<PandaBroadcastDetail, PandaBroadcastDetail>() {

            MutableLiveData<PandaBroadcastDetail> mLiveData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected PandaBroadcastDetail saveCallResponseToDb(@NonNull PandaBroadcastDetail response) {
                mLiveData.postValue(response);
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PandaBroadcastDetail>> createCall() {
                return mIpandaService.getPandaBroadcastDetail(id, "panda");
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable PandaBroadcastDetail data) {
                return true;
            }

            @Override
            protected LiveData<PandaBroadcastDetail> loadFromDb() {
                if (mLiveData == null) {
                    mLiveData = new MutableLiveData<>();
                    mLiveData.postValue(null);
                }
                return mLiveData;
            }
        }.asLiveData();
    }

}
