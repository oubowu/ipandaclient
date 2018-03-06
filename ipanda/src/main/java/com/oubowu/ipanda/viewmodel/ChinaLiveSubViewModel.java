package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.chinalive.ChinaLiveDetail;
import com.oubowu.ipanda.repository.ChinaLiveSubRepository;
import com.oubowu.ipanda.util.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class ChinaLiveSubViewModel extends ViewModel {

    private ChinaLiveSubRepository mChinaLiveSubRepository;

    @Inject
    public ChinaLiveSubViewModel(ChinaLiveSubRepository chinaLiveSubRepository) {
        mChinaLiveSubRepository = chinaLiveSubRepository;
    }

    public LiveData<Resource<List<ChinaLiveDetail>>> getChinaLiveDetail(String url) {
        return mChinaLiveSubRepository.getChinaLiveDetail(url);
    }

}
