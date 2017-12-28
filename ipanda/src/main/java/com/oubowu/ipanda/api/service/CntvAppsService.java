package com.oubowu.ipanda.api.service;

import android.arch.lifecycle.LiveData;

import com.oubowu.ipanda.api.response.ApiResponse;
import com.oubowu.ipanda.bean.base.RecordVideo;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Oubowu on 2017/12/28 03:13.
 */
public interface CntvAppsService {

    /**
     * 首页-大图-点击进去(pid来自2的bigImg的pid)
     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=f5f4a7638b2446ff9753006d658bcaae
     *
     * @param pid 视频id
     * @return 视频信息
     */
    @GET("api/getVideoInfoForCBox.do")
    LiveData<ApiResponse<RecordVideo>> getBannerDetail(@Query("pid") String pid);

    /**
     * 首页-熊猫播报-点击进去(pid来自2的pandaeye的items的pid)
     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=29abf1c6e9624cb19568808256662b3b
     *
     * @param pid 视频id
     * @return 视频信息
     */
    @GET("api/getVideoInfoForCBox.do")
    LiveData<ApiResponse<RecordVideo>> getPandaBroadcastDetail(@Query("pid") String pid);

    /**
     * 首页-精彩一刻-点击进去(来自3的list的pid)
     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=74c30918c1f34b59b3500affae080136
     *
     * @param pid 视频id
     * @return 视频信息
     */
    @GET("api/getVideoInfoForCBox.do")
    LiveData<ApiResponse<RecordVideo>> getWonderfulMomentDetail(@Query("pid") String pid);

    /**
     * 首页-滚滚视频-点击进去(来自4的list的pid)
     * http://vdn.apps.cntv.cn/api/getVideoInfoForCBox.do?pid=6116fbf58ac4484dbbef9d9925c864f1
     *
     * @param pid 视频id
     * @return 视频信息
     */
    @GET("api/getVideoInfoForCBox.do")
    LiveData<ApiResponse<RecordVideo>> getGungunVideoDetail(@Query("pid") String pid);

}
