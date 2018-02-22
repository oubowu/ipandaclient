package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.pandalive.TabList;
import com.oubowu.ipanda.repository.PandaLiveRepository;
import com.oubowu.ipanda.util.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class PandaLiveViewModel extends ViewModel {

    private PandaLiveRepository mPandaLiveRepository;

    @Inject
    public PandaLiveViewModel(PandaLiveRepository pandaLiveRepository) {
        mPandaLiveRepository = pandaLiveRepository;
    }

    public LiveData<Resource<List<TabList>>> getTabList(String url) {
        return mPandaLiveRepository.getTabList(url);
    }

}
