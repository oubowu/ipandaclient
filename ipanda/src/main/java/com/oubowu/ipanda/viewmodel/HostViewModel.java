package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.repository.HostRepository;
import com.oubowu.ipanda.util.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/1/12 14:45.
 */
public class HostViewModel extends ViewModel {

    private HostRepository mHostRepository;

    @Inject
    public HostViewModel(HostRepository hostRepository) {
        mHostRepository = hostRepository;
    }

    public LiveData<Resource<HomeIndex>> getHomeIndex(String url) {
        return mHostRepository.getHomeIndex(url);
    }

    public LiveData<Resource<List<VideoList>>> getWonderfulMomentIndex(String url) {
        return mHostRepository.getWonderfulMomentIndex(url);
    }

    public LiveData<Resource<List<VideoList>>> getGungunVideoIndex(String url) {
        return mHostRepository.getGungunVideoIndex(url);
    }

}
