package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

            MutableLiveData<ChinaLiveTab> mLiveData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected void saveCallResponseToDb(@NonNull ChinaLiveTab response) {
                mLiveData.postValue(response);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ChinaLiveTab>> createCall() {
                return mIpandaService.getChinaLiveTab(url);
            }

            @Override
            protected boolean shouldCall(@Nullable ChinaLiveTab data) {
                return true;
            }

            @Override
            protected LiveData<ChinaLiveTab> loadFromDb() {
                if (mLiveData == null) {
                    mLiveData = new MutableLiveData<>();
                    mLiveData.postValue(null);
                }
                return mLiveData;
            }
        }.asLiveData();
    }


}
