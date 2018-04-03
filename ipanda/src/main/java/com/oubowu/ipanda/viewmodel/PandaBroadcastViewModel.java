package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastDetail;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastIndex;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastList;
import com.oubowu.ipanda.repository.PandaBroadcastRepository;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class PandaBroadcastViewModel extends ViewModel {

    private PandaBroadcastRepository mPandaBroadcastRepository;

    @Inject
    public PandaBroadcastViewModel(PandaBroadcastRepository pandaBroadcastRepository) {
        mPandaBroadcastRepository = pandaBroadcastRepository;
    }

    public LiveData<Resource<PandaBroadcastIndex>> getPandaBroadcastIndex(String url) {
        return mPandaBroadcastRepository.getPandaBroadcastIndex(url);
    }

    public LiveData<Resource<PandaBroadcastList>> getPandaBroadcastList(String url) {
        return mPandaBroadcastRepository.getPandaBroadcastList(url);
    }

    public LiveData<Resource<PandaBroadcastDetail>> getPandaBroadcastDetail(String id) {
        return mPandaBroadcastRepository.getPandaBroadcastDetail(id);
    }

}
