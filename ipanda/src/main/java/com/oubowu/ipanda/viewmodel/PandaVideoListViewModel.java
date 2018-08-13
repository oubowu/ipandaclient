package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.repository.PandaVideoListRepository;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class PandaVideoListViewModel extends ViewModel {

    private PandaVideoListRepository mPandaVideoListRepository;

    @Inject
    public PandaVideoListViewModel(PandaVideoListRepository pandaVideoListRepository) {
        mPandaVideoListRepository = pandaVideoListRepository;
    }

    public LiveData<Resource<RecordTab>> getRecordTab(String vsid, int number, int page) {
        return mPandaVideoListRepository.getRecordTab(vsid, number, page);
    }

}
