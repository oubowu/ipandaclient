package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.CntvAppsService;
import com.oubowu.ipanda.bean.base.RecordVideo;
import com.oubowu.ipanda.util.NetworkBoundResource;
import com.oubowu.ipanda.util.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Oubowu on 2018/1/29 11:58.
 */
@Singleton
public class VideoRepository {

    private CntvAppsService mCntvAppsService;

    @Inject
    public VideoRepository(CntvAppsService cntvAppsService) {
        mCntvAppsService = cntvAppsService;
    }

    public LiveData<Resource<RecordVideo>> getRecordVideo(String pid) {
        return new NetworkBoundResource<RecordVideo, RecordVideo>() {

            MediatorLiveData<RecordVideo> mRecordVideoData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected void saveCallResponseToDb(@NonNull RecordVideo response) {
                mRecordVideoData.postValue(response);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RecordVideo>> createCall() {
                return mCntvAppsService.getRecordVideoDetail(pid);
            }

            @Override
            protected boolean shouldCall(@Nullable RecordVideo data) {
                return true;
            }

            @Override
            protected LiveData<RecordVideo> loadFromDb() {
                if (mRecordVideoData == null) {
                    mRecordVideoData = new MediatorLiveData<>();
                    mRecordVideoData.postValue(null);
                }
                return mRecordVideoData;
            }
        }.asLiveData();
    }

}
