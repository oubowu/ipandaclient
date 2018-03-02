package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;
import com.oubowu.ipanda.repository.PandaVideoRepository;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/2/5 14:42.
 */
public class PandaVideoViewModel extends ViewModel {

    private PandaVideoRepository mPandaVideoRepository;

    @Inject
    public PandaVideoViewModel(PandaVideoRepository pandaVideoRepository) {
        mPandaVideoRepository = pandaVideoRepository;
    }

    public LiveData<Resource<PandaVideoIndex>> getPandaVideoIndex(String url) {
        return mPandaVideoRepository.getPandaVideoIndex(url);
    }

}
