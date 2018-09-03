package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.chinalive.ChinaLiveDetail;
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
public class ChinaLiveSubRepository {

    private IpandaService mIpandaService;

    @Inject
    public ChinaLiveSubRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<List<ChinaLiveDetail>>> getChinaLiveDetail(String url) {
        return new NetworkBoundResource<List<ChinaLiveDetail>, Map<String, List<ChinaLiveDetail>>>() {

            MutableLiveData<List<ChinaLiveDetail>> mLiveData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected List<ChinaLiveDetail> saveCallResponseToDb(@NonNull Map<String, List<ChinaLiveDetail>> response) {
                List<ChinaLiveDetail> details = MapUtil.getFirstElement(response);
                mLiveData.postValue(details);
                return details;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<ChinaLiveDetail>>>> createCall() {
                return mIpandaService.getChinaLiveDetail(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable List<ChinaLiveDetail> data) {
                return true;
            }

            @Override
            protected LiveData<List<ChinaLiveDetail>> loadFromDb() {
                if (mLiveData == null) {
                    mLiveData = new MutableLiveData<>();
                    mLiveData.postValue(null);
                }
                return mLiveData;
            }
        }.asLiveData();
    }

}
