package com.oubowu.ipanda.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.base.RecordVideo;
import com.oubowu.ipanda.repository.VideoRepository;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/1/29 11:57.
 */
public class VideoViewModel extends ViewModel {

    private VideoRepository mVideoRepository;

    @Inject
    public VideoViewModel(VideoRepository videoRepository) {
        mVideoRepository = videoRepository;
    }

    public LiveData<Resource<RecordVideo>> getRecordVideo(String pid) {
        return mVideoRepository.getRecordVideo(pid);
    }

    public LiveData<Resource<LiveVideo>> getLiveVideo(String id) {
        return mVideoRepository.getLiveVideo(id);
    }

}
