package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.TabIndex;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2017/12/24 23:45.
 */
//@PerActivity
@Singleton
public class HomeRepository {

    private IpandaService mIpandaClientService;

    @Inject
    public HomeRepository(IpandaService ipandaClientService) {
        mIpandaClientService = ipandaClientService;
        Log.e("xxx",mIpandaClientService+" ");
    }

    public LiveData<ApiResponse<List<TabIndex>>> getTabIndex() {
        return mIpandaClientService.getTabIndex();
    }

}
