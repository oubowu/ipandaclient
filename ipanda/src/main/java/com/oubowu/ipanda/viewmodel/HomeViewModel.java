package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.TabIndex;

import java.util.List;

/**
 * Created by Oubowu on 2017/12/24 23:59.
 */
public class HomeViewModel extends ViewModel {

//    @Inject
//    HomeRepository mHomeRepository;

//    @Inject
//    IpandaApi mIpandaApi;

    public HomeViewModel() {
//        DaggerHomeRepositoryComponent.builder().homeRepositoryModule(new HomeRepositoryModule()).appComponent(BasicApp.getAppComponent()).build().inject(this);
    }

    public LiveData<List<TabIndex>> getTabIndex() {
        // Log.e("xxx", "33行-getTabIndex(): " + mHomeRepository);
        // Log.e("xxx", "33行-getTabIndex(): " + mIpandaApi);
//        return mHomeRepository.getTabIndex();
        return null;
    }

}
