package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.chinalive.ChinaLiveTab;
import com.oubowu.ipanda.repository.ChinaLiveRepository;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class ChinaLiveViewModel extends ViewModel {

    private ChinaLiveRepository mChinaLiveRepository;

    @Inject
    public ChinaLiveViewModel(ChinaLiveRepository chinaLiveRepository) {
        mChinaLiveRepository = chinaLiveRepository;
    }

    public LiveData<Resource<ChinaLiveTab>> getChinaLiveTab(String url) {
        return mChinaLiveRepository.getChinaLiveTab(url);
    }

}
