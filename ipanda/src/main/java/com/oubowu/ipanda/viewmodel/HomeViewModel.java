package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.di.component.DaggerHomeRepositoryComponent;
import com.oubowu.ipanda.di.module.HomeRepositoryModule;
import com.oubowu.ipanda.http.IpandaApi;
import com.oubowu.ipanda.repository.HomeRepository;
import com.oubowu.ipanda1.BasicApp;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2017/12/24 23:59.
 */
public class HomeViewModel extends ViewModel {

    @Inject
    HomeRepository mHomeRepository;

    @Inject
    IpandaApi mIpandaApi;

    public HomeViewModel() {
        DaggerHomeRepositoryComponent.builder().homeRepositoryModule(new HomeRepositoryModule()).appComponent(BasicApp.getAppComponent()).build().inject(this);
    }

    public LiveData<List<TabIndex>> getTabIndex() {
        Log.e("xxx", "33行-getTabIndex(): " + mHomeRepository);
        Log.e("xxx", "33行-getTabIndex(): " + mIpandaApi);
        return mHomeRepository.getTabIndex();
    }

}
