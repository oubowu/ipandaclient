package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.pandalive.TabList;
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
public class PandaLiveRepository {

    private IpandaService mIpandaService;

    @Inject
    public PandaLiveRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<Resource<List<TabList>>> getTabList(String url) {
        return new NetworkBoundResource<List<TabList>, Map<String, List<TabList>>>() {

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected List<TabList> saveCallResponseToDb(@NonNull Map<String, List<TabList>> response) {
                List<TabList> tabList = MapUtil.getFirstElement(response);
                //                mListLiveData.postValue(tabList);
                return tabList;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<TabList>>>> createCall() {
                return mIpandaService.getTabList(url);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable List<TabList> data) {
                return true;
            }

        }.asLiveData();
    }

}
