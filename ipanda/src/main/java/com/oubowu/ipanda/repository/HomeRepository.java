package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.db.IpandaDb;
import com.oubowu.ipanda.db.dao.TabIndexDao;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;
import com.oubowu.ipanda.util.TabIndex;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2017/12/24 23:45.
 */
@Singleton
public class HomeRepository {

    private IpandaService mIpandaClientService;

    private IpandaDb mIpandaDb;

    private TabIndexDao mTabIndexDao;

    @Inject
    public HomeRepository(IpandaService ipandaClientService, IpandaDb ipandaDb, TabIndexDao tabIndexDao) {
        mIpandaClientService = ipandaClientService;
        mIpandaDb = ipandaDb;
        mTabIndexDao = tabIndexDao;
        // Log.e("xxx", mIpandaClientService + " ");
    }

    public LiveData<Resource<List<TabIndex>>> getTabIndex() {

        /*return new NetworkBoundResource<List<TabIndex>, Map<String, List<TabIndex>>>() {

            @Override
            protected void onCallFailed() {
                Log.e("HomeRepository", "网络请求失败");
            }

            @Override
            protected void saveCallResponseToDb(@NonNull Map<String, List<TabIndex>> response) {
                // mResponse = response;
                Log.e("HomeRepository", "保存请求成功数据到数据库");
                mIpandaDb.runInTransaction(() -> mTabIndexDao.insertTabIndexes(MapUtil.getFirstElement(response)));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<TabIndex>>>> createCall() {
                return mIpandaClientService.getTabIndex();
            }

            @Override
            protected boolean shouldCall(@Nullable List<TabIndex> data) {
                return CommonUtil.isEmpty(data);
            }

            @SuppressWarnings("unchecked")
            @Override
            protected LiveData<List<TabIndex>> loadFromDb() {
                Log.e("HomeRepository", "从数据库加载");
                return mTabIndexDao.queryTabIndexes();
            }
        }.asLiveData();*/


        return new NetworkBoundResource<List<TabIndex>, Map<String, List<TabIndex>>>() {

            MediatorLiveData<List<TabIndex>> mTabLiveData;

            @Override
            protected void onCallFailed() {
                Log.e("HomeRepository", "网络请求失败");
            }

            @Override
            protected void saveCallResponseToDb(@NonNull Map<String, List<TabIndex>> response) {
                List<TabIndex> tabIndices = MapUtil.getFirstElement(response);
                mTabLiveData.postValue(tabIndices);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<TabIndex>>>> createCall() {
                return mIpandaClientService.getTabIndex();
            }

            @Override
            protected boolean shouldCall(@Nullable List<TabIndex> data) {
                return true;
            }

            @Override
            protected LiveData<List<TabIndex>> loadFromDb() {
                if (mTabLiveData == null) {
                    mTabLiveData = new MediatorLiveData<>();
                    mTabLiveData.postValue(null);
                }
                return mTabLiveData;
            }
        }.asLiveData();

        /*return new NetworkBoundResource<List<TabIndex>, Map<String, List<TabIndex>>>() {

            @Override
            protected void onCallFailed() {
                Log.e("HomeRepository", "网络请求失败");
            }

            @Override
            protected void saveCallResponseToDb(@NonNull Map<String, List<TabIndex>> response) {

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<TabIndex>>>> createCall() {
                return null;
            }

            @Override
            protected boolean shouldCall(@Nullable List<TabIndex> data) {
                return false;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected LiveData<List<TabIndex>> loadFromDb() {
                Log.e("HomeRepository", "从数据库加载");
                return mTabIndexDao.queryTabIndexes();
            }
        }.asLiveData();*/

    }


}
