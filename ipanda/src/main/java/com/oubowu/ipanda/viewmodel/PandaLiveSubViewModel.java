package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.pandalive.LiveTab;
import com.oubowu.ipanda.bean.pandalive.MultipleLive;
import com.oubowu.ipanda.bean.pandalive.WatchTalk;
import com.oubowu.ipanda.repository.PandaLiveSubRepository;
import com.oubowu.ipanda.util.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class PandaLiveSubViewModel extends ViewModel {

    private PandaLiveSubRepository mPandaLiveSubRepository;

    @Inject
    public PandaLiveSubViewModel(PandaLiveSubRepository pandaLiveSubRepository) {
        mPandaLiveSubRepository = pandaLiveSubRepository;
    }

    public LiveData<Resource<LiveTab>> getLiveTab(String url) {
        return mPandaLiveSubRepository.getLiveTab(url);
    }

    public LiveData<Resource<List<MultipleLive>>> getMultipleLive(String url) {
        return mPandaLiveSubRepository.getMultipleLive(url);
    }

    public LiveData<Resource<WatchTalk>> getLiveWatchTalk(int prepage, int page, String itemid) {
        return mPandaLiveSubRepository.getLiveWatchTalk(prepage, page, itemid);
    }

}
