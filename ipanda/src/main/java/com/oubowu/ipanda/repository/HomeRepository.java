package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.db.IpandaDb;
import com.oubowu.ipanda.db.dao.TabIndexDao;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.MapUtil;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2017/12/24 23:45.
 */
@Singleton
public class HomeRepository {

    private static final String TAG = "HomeRepository";

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

        return new NetworkBoundResource<List<TabIndex>, Map<String, List<TabIndex>>>() {
            @Override
            protected boolean shouldLoadFromDb() {
                boolean shouldLoadFromDb = true;
                Log.e(TAG, "51行-shouldLoadFromDb(): " + "从数据库加载数据吗？" + shouldLoadFromDb);
                return shouldLoadFromDb;
            }

            @Override
            protected LiveData<List<TabIndex>> loadFromDb() {
                Log.e(TAG, "77行-loadFromDb(): " + "从数据库加载数据");
                return mTabIndexDao.queryTabIndexes();
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable List<TabIndex> data) {
                boolean empty = CommonUtil.isEmpty(data);
                Log.e(TAG, "70行-shouldCall(): " + "数据库查询数据为空吗？" + empty);
                return empty;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Map<String, List<TabIndex>>>> createCall() {
                Log.e(TAG, "65行-createCall(): " + "通过网络请求数据");
                return mIpandaClientService.getTabIndex();
            }

            @Override
            protected List<TabIndex> saveCallResponseToDb(@NonNull Map<String, List<TabIndex>> response) {
                Log.e(TAG, "59行-saveCallResponseToDb(): " + "保存请求成功数据到数据库");
                List<TabIndex> tabIndices = MapUtil.getFirstElement(response);
                // mIpandaDb.runInTransaction(() -> mTabIndexDao.insertTabIndexes(tabIndices));
                mTabIndexDao.insertTabIndexes(tabIndices);
                return tabIndices;
            }

            @Override
            protected void onCallFailed() {
                Log.e(TAG, "53行-onCallFailed(): " + "网络请求失败");
            }

        }.asLiveData();

    }


}
