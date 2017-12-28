package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.oubowu.ipanda.api.service.IpandaService;
import com.oubowu.ipanda.bean.TabIndex;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/24 23:45.
 */
//@PerActivity
public class HomeRepository {

    private IpandaService mIpandaService;

//    @Inject
    public HomeRepository(IpandaService ipandaService) {
        mIpandaService = ipandaService;
    }

    public LiveData<List<TabIndex>> getTabIndex() {
        final MutableLiveData<List<TabIndex>> data = new MutableLiveData<>();
//        mIpandaService.getTabIndex().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(stringListMap -> {
//            Logger.d(stringListMap);
//            if (stringListMap != null && !stringListMap.isEmpty()) {
//                Collection<List<TabIndex>> values = stringListMap.values();
//                if (!values.isEmpty()) {
//                    List<TabIndex> tabIndexList = values.iterator().next();
//                    return Observable.just(tabIndexList);
//                }
//            }
//            return null;
//        }).subscribe(tabIndices -> {
//            Logger.d(tabIndices);
//            data.setValue(tabIndices);
//        }, throwable -> {
//            Log.e("HomeActivity", "90行-onCreate(): " + " ", throwable);
//        });
        return data;
    }

}
