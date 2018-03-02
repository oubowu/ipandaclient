package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.repository.PandaLiveOtherRepository;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class PandaLiveOtherViewModel extends ViewModel {

    private PandaLiveOtherRepository mPandaLiveOtherRepository;

    @Inject
    public PandaLiveOtherViewModel(PandaLiveOtherRepository pandaLiveOtherRepository) {
        mPandaLiveOtherRepository = pandaLiveOtherRepository;
    }

    public LiveData<Resource<RecordTab>> getRecordTab(String vsid, int number, int page) {
        return mPandaLiveOtherRepository.getRecordTab(vsid, number, page);
    }

}
