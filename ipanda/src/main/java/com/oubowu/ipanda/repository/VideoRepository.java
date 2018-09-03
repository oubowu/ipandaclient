package com.oubowu.ipanda.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.api.service.CntvAppsService;
import com.oubowu.ipanda.api.service.CntvLiveService;
import com.oubowu.ipanda.bean.base.LiveVideo;
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

    private CntvLiveService mCntvLiveService;

    @Inject
    public VideoRepository(CntvAppsService cntvAppsService, CntvLiveService cntvLiveService) {
        mCntvAppsService = cntvAppsService;
        mCntvLiveService = cntvLiveService;
    }

    public LiveData<Resource<RecordVideo>> getRecordVideo(String pid) {
        return new NetworkBoundResource<RecordVideo, RecordVideo>() {

            MutableLiveData<RecordVideo> mRecordVideoData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected RecordVideo saveCallResponseToDb(@NonNull RecordVideo response) {
                mRecordVideoData.postValue(response);
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RecordVideo>> createCall() {
                return mCntvAppsService.getRecordVideoDetail(pid);
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable RecordVideo data) {
                return true;
            }

            @Override
            protected LiveData<RecordVideo> loadFromDb() {
                if (mRecordVideoData == null) {
                    mRecordVideoData = new MutableLiveData<>();
                    mRecordVideoData.postValue(null);
                }
                return mRecordVideoData;
            }
        }.asLiveData();
    }

    public LiveData<Resource<LiveVideo>> getLiveVideo(String id) {
        return new NetworkBoundResource<LiveVideo, LiveVideo>() {

            MutableLiveData<LiveVideo> mLiveVideoData;

            @Override
            protected void onCallFailed() {

            }

            @Override
            protected LiveVideo saveCallResponseToDb(@NonNull LiveVideo response) {
                mLiveVideoData.postValue(response);
                return response;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LiveVideo>> createCall() {
                return mCntvLiveService.getLiveVideoDetail("pa://cctv_p2p_hd" + id, "androidapp");
            }

            @Override
            protected boolean shouldFetchFromNetwork(@Nullable LiveVideo data) {
                return true;
            }

            @Override
            protected LiveData<LiveVideo> loadFromDb() {
                if (mLiveVideoData == null) {
                    mLiveVideoData = new MutableLiveData<>();
                    mLiveVideoData.postValue(null);
                }
                return mLiveVideoData;
            }
        }.asLiveData();
    }
}
