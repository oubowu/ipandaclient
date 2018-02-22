package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.TabIndex;
import com.oubowu.ipanda.util.Resource;
import com.oubowu.ipanda.repository.HomeRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2017/12/24 23:59.
 */
public class HomeViewModel extends ViewModel {

    private HomeRepository mHomeRepository;

    @Inject
    public HomeViewModel(HomeRepository homeRepository) {
        mHomeRepository = homeRepository;
    }

    public LiveData<Resource<List<TabIndex>>> getTabIndex() {
        return mHomeRepository.getTabIndex();
    }

}
