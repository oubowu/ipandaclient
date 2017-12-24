package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.di.scope.PerActivity;
import com.oubowu.ipanda.http.service.IpandaService;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oubowu on 2017/12/24 23:45.
 */
@PerActivity
public class HomeRepository {

    private IpandaService mIpandaService;

    @Inject
    public HomeRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<List<TabIndex>> getTabIndex() {
        final MutableLiveData<List<TabIndex>> data = new MutableLiveData<>();
        mIpandaService.getTabIndex().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(stringListMap -> {
            Logger.d(stringListMap);
            if (stringListMap != null && !stringListMap.isEmpty()) {
                Collection<List<TabIndex>> values = stringListMap.values();
                if (!values.isEmpty()) {
                    List<TabIndex> tabIndexList = values.iterator().next();
                    return Observable.just(tabIndexList);
                }
            }
            return null;
        }).subscribe(tabIndices -> {
            Logger.d(tabIndices);
            data.setValue(tabIndices);
        }, throwable -> {
            Log.e("HomeActivity", "90行-onCreate(): " + " ", throwable);
        });
        return data;
    }

}
